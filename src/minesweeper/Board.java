package minesweeper;


import constant.StaticConst;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import java.util.Random;


public class Board extends JPanel{

	private static final long serialVersionUID = 1L;
	//board size
    private final int cellSize = 15;
    private final int rows = StaticConst.rows;
    private final int cols = StaticConst.cols;
    private final int height = rows * cellSize + 1;
    private final int width = cols * cellSize + 1;

    //cell
    private final int mine = 9;
    private final int covered = 10;
    private final int marked = 10;
    private final int empty = 0;
    private final int coveredMine = mine + covered;
    private final int markedMine = coveredMine + marked;


    private final int mineCount = StaticConst.mineCount;
    private int[] board;
    private int allCells;
    private int firstMine = -1;
    private GameWindow mainFrame;

    public Board(GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        init();

    }

    private void init() {
        setPreferredSize(new Dimension(width, height));
        addMouseListener(new GamePlay());
        newGame();
        //resets audio
        StaticConst.clipRestart();
    }
   
    private void newGame() {
        int cell;

        StaticConst.inGame = true;
        allCells = rows * cols;
        StaticConst.mineLeft = mineCount;

        //display panel
        mainFrame.getDisplay().updateMineCount(StaticConst.mineLeft);

        //cover all cells 
        board = new int[allCells];
        for(int i = 0; i < allCells; i++) {
            board[i] = covered;
        }

        var random = new Random();
        int i = 0;
        while(i < mineCount) {
            //get a random number
            int minePosition = (int) (allCells * random.nextDouble());
            if(minePosition < allCells && board[minePosition] != coveredMine) {
                board[minePosition] = coveredMine;
                i++;

                //1d to 2d
                int[] tmpMine = to_2D(minePosition);

                //all 8 cells around the mine increments by 1
                for(int r = tmpMine[0] - 1; r <= tmpMine[0] + 1; r++) {
                    if( r < 0 || r >= rows) {
                        continue;
                    }

                    for(int c = tmpMine[1] - 1; c <= tmpMine[1] + 1; c++) {
                        if(c < 0 || c >= cols) {
                            continue;
                        }

                        if(r == tmpMine[0] && c == tmpMine[1]) {
                            continue;
                        }
                        // 2d index to 1d
                        cell = to_1D(r, c);
                        if(board[cell] != coveredMine) {
                            board[cell] += 1;
                        }
                    }
                }
            }
        }
    }
    
    // 1d to 2D
    private int[] to_2D(int x) {
        int[] twoDposition = new int[2];
        twoDposition[0] = x / StaticConst.cols;
        twoDposition[1] = (x - twoDposition[0] * StaticConst.cols);
        return twoDposition;
    }
    
    // 2d to 1d
    private int to_1D(int x, int y) {
        int oneDpoistion = x * StaticConst.cols + y;
        return oneDpoistion;
    }

    @Override
    public void paintComponent(Graphics g) {
        //remaining covered cells
    	int UnderCover = 0;
        //paint the cells according to their state, cell val -> image in img[]
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                //2d index to 1d
                int cell = board[ i * cols + j ];

                //game continues 
                if (StaticConst.inGame){
                    //open mine -> game over
                    if (cell == mine){
                        StaticConst.inGame = false;
                    }
                    //marked cells
                    else if (cell > coveredMine) {
                        cell = 11;
                    }
                    //covered cells
                    else if (cell > mine){
                        cell = 10;
                        UnderCover++;
                    }
                }
                
                //game over
                if(!StaticConst.inGame){
                	//reveal all the mines
                    if (cell == coveredMine) {
                        cell = 9;
                    }
                    //correct mark -> flag
                    else if (cell == markedMine) {
                        cell = 11;
                    }
                    //wrongly marked cells
                    else if (cell > coveredMine) {
                        cell = 12;
                    //empty cells
                    }
                    else if (cell > mine) {
                        cell = 10;
                    }
                    //mine that user clicked to cause gameOver
                    if(i * cols + j == firstMine) {
                        cell = 13;
                    }
                }
                
                g.drawImage(StaticConst.img[cell], (j * cellSize), (i * cellSize), this);
            }
        }
        //all cells are revealed -> win
        if (UnderCover == 0 && StaticConst.inGame) {
            mainFrame.getDisplay().getFaceButton().setIcon(StaticConst.winSmileIcon);
            mainFrame.getDisplay().timer.stop();
            
            if (!StaticConst.UnmuteSound){
                if (StaticConst.clipTimer != null){
                    StaticConst.clipTimer.stop();

                }
            }
            StaticConst.clipWin.start();
            
            mainFrame.getDialog().show();
            
        } else if (!StaticConst.inGame){ //game lost
            mainFrame.getDisplay().getFaceButton().setIcon(StaticConst.SadSmileIcon);
            mainFrame.getDisplay().timer.stop();
            
            if (!StaticConst.UnmuteSound){
                if (StaticConst.clipTimer != null){
                    StaticConst.clipTimer.stop();
                }
            }
            StaticConst.clipOver.start();
        }
    }


    //all neighbors in 8 directions
    private static final int[][] dirs = new int[][] {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

    //given an empty cell idx, reveals the cell around it
    private void findEmptyCell(int id) {
        int i = id / cols;
        int j = id % cols;

        for(int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            int newId = x * cols + y;
            //empty cell = 10, 11- 18 = numbers, 19+ mines
            if(x < 0 || x >= rows || y < 0 || y >= cols|| board[newId] >= coveredMine) {
                continue;
            }
            //possble cells: uncovered cell, covered number cell, covered empty cell
            if(board[newId] > mine) { //if cell is covered
                board[newId] -= covered;
                if(board[newId] == empty) { //cell is empty, continue to reveal its neighbors
                    findEmptyCell(newId);
                }
            }
        }
    }
    
    //check if surrounding flags equals number in cell
    private boolean findFlag(int id) {
        int x = id / cols;
        int y = id % cols;
        int flag = 0;

        for(int[] dir : dirs) {
            int X = x + dir[0];
            int Y = y + dir[1];
            int idx = X * cols + Y;

            //out of bounds or already uncovered
            if(X < 0 || X >= rows || Y < 0 || Y >= cols || board[idx] < mine) {
                continue;
            }
            //check marked cells
            if(board[idx] > coveredMine) {
                if(board[idx] != markedMine) {
                    return false;
                }
                else{
                    flag++;
                }
            }
        }

        if(flag == board[id]) { //all mines in neighbors has been marked
            for(int[] dir : dirs) {
                int X = x + dir[0];
                int Y = y + dir[1];
                int idx = X * cols + Y;
    
                //out of bounds or already uncoverd or marked
                if(X < 0 || X >= rows || Y < 0 || Y >= cols || 
                    board[idx] < mine || board[idx] > coveredMine) {
                    continue;
                }
                //reveal remaining covered cells
                board[idx] -= covered; 
                if(board[idx] == empty) {
                    findEmptyCell(idx);
                }
            }
        }
        return true;
        
    }

    private class GamePlay extends MouseAdapter {
   
        @Override
        public void mousePressed(MouseEvent e) {
            int getCol = e.getX() / cellSize;
            int getRow = e.getY() / cellSize;
            int id = getRow * cols + getCol;
            boolean callRepaint = false;
            
            if (!StaticConst.UnmuteSound){
                StaticConst.playClick();
            }

            if(StaticConst.inGame && StaticConst.mousePause == false){
                if(id >= 0 && id < allCells) {
                    if(SwingUtilities.isRightMouseButton(e)) {
                        if(board[id] > mine) { //covered cell only
                            if(board[id] <= coveredMine) { //unmarked cover cell
                                if(StaticConst.mineLeft != 0) {
                                    board[id] += marked;
                                    //update mineCount display
                                    StaticConst.mineLeft--;
                                    mainFrame.getDisplay().updateMineCount(StaticConst.mineLeft);
                                }
                            }
                            else{
                                board[id] -= marked;
                                StaticConst.mineLeft++;
                                //update mineCount display
                                mainFrame.getDisplay().updateMineCount(StaticConst.mineLeft);
                            }
                            callRepaint = true;
                        }

                    }
                    else if(SwingUtilities.isLeftMouseButton(e)) {
                        //timeStart is false, the reset the timer
                        if(StaticConst.timeStart == false) {
                            StaticConst.timeCount = 0;
                            mainFrame.getDisplay().timer.start();
                            StaticConst.timeStart = true;
                        }

                        if(board[id] > coveredMine && board[id] < markedMine) { //marked but not mine
                            return;
                        }
                        if(board[id] == coveredMine || board[id] == markedMine) { //can't click on flagged mine either
                            StaticConst.inGame = false;
                            
                            //the mine clicked 
                            firstMine = id;
                        }

                        if(board[id] >= covered) { //covered num & empty cell
                            board[id] -= covered;
                            if(board[id] == empty) {
                                findEmptyCell(id);
                            }
                            callRepaint = true;
                        }
                        if(board[id] < mine) { //clicked on a revealed num cell
                            if(!findFlag(id)) {
                                StaticConst.inGame = false;
                                repaint();
                            }
                            else{
                                callRepaint = true;
                            }
                        }
                    }

                    if(callRepaint) {
                        repaint();
                    }
                }
            }


        }
    }
}

