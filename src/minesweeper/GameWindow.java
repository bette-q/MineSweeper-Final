package minesweeper;

import constant.StaticConst;
import texts.*;
import dialog.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.Border;

public class GameWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private Board board;
    private JPanel panel;
    private DisplayPanel display = new DisplayPanel(this);
    private Menu menu = new Menu(this);
    private Level level;
    private About about;
    private MenuBar menuBar = new MenuBar(this);
    private StatDialog dialog = new StatDialog(this);
    private StatLevel statLevel = new StatLevel(this);
    private StatBoard statBoard = new StatBoard(this);
    @SuppressWarnings("unused")
    private StatBoardDialog statBoardDialog = new StatBoardDialog(this);
    @SuppressWarnings("unused")
	private PauseDialog pause;
    private Rule rule;

    public GameWindow() {
        init();

        this.setSize(240, 230);
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("Deluxe MineSweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void init() {
        this.add(menu);
        //loads stats
        try {
            StaticConst.loadData(StaticConst.statEasy, "resources/statEasy.txt");
            StaticConst.loadData(StaticConst.statMed, "resources/statMed.txt");
            StaticConst.loadData(StaticConst.statHard, "resources/statHard.txt");
        }
        catch (IOException io) {
            System.out.println(io);
        }
        
    }

    public void startGame() { //will be invoked by new game button

        if(StaticConst.inGameMenu == 2) {
            this.remove(statLevel);
        }else {
            this.remove(level);
        }
        this.setJMenuBar(menuBar);
        StaticConst.inGameMenu = 0;
        StaticConst.init();
        
        this.add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        //create a border decoration
        Border borderLow = BorderFactory.createLoweredBevelBorder();
        Border borderEmpty = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        Border borderCom1 = BorderFactory.createCompoundBorder(borderEmpty, borderLow);
        panel.setBorder(borderCom1);

        board = new Board(this);
        panel.add(board);
        this.add(panel, BorderLayout.CENTER);
        display.getPauseButton().setIcon(StaticConst.pauseButton);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void restartGame() {
        panel.remove(board);
        board = new Board(this);
        panel.add(board);
        this.add(panel, BorderLayout.CENTER);
        //everytime when you restartGame, will reset the reset the button and the timer
        display.getFaceButton().setIcon(StaticConst.smileIcon);
        display.getLabelG().setIcon(StaticConst.resetTime);
        display.getLabelS().setIcon(StaticConst.resetTime);
        display.getLabelB().setIcon(StaticConst.resetTime);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void getLevel() {
        this.remove(menu);
        this.setJMenuBar(menuBar);
        
        level = new Level(this);
        this.add(level);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    public void getPause() {
        display.timer.stop();
        pause = new PauseDialog(this);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void getAbout() {
        this.remove(menu);
        this.setLocationRelativeTo(null);
        
        about = new About(this);
        this.add(about);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public StatDialog getDialog() {
        return dialog;
    }

    public void gobackMenu() {

        if(StaticConst.inGameMenu == 1) {
            this.remove(level);
        }
        else if(StaticConst.inGameMenu == 2) {
            this.remove(statLevel);
        } else {
            this.remove(display);
            this.remove(panel);
        }

        this.setJMenuBar(null);
        display.timer.stop();
        
        if (!StaticConst.UnmuteSound){
            if (StaticConst.clipTimer != null){
                StaticConst.clipTimer.stop();
            }
        }

        StaticConst.timeStart = false;
        display.getLabelG().setIcon(StaticConst.resetTime);
        display.getLabelS().setIcon(StaticConst.resetTime);
        display.getLabelB().setIcon(StaticConst.resetTime);
        display.getFaceButton().setIcon(StaticConst.smileIcon);
        
        this.add(menu);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    public void ExitAbout() {
        this.remove(about);
        menu = new Menu(this);
        this.add(menu);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void getRule() {
        this.remove(menu);
        rule = new Rule(this);
        this.add(rule);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    public void ExitRule() {
        this.remove(rule);
        menu = new Menu(this);
        this.add(menu);

        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    public void showStat() {
        this.remove(menu);
        this.setJMenuBar(menuBar);
        this.add(statLevel);

        this.pack();
        this.setLocationRelativeTo(null);
    }
    public void getStat(int x) {
        this.remove(statLevel);
        this.setJMenuBar(null);

        statBoard.dataToTable(x);
        this.add(statBoard);

        this.pack();
        this.setLocationRelativeTo(null);

    }
    public void ExitStat() {
        this.remove(statBoard);
        this.setJMenuBar(menuBar);
        this.add(statLevel);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    public DisplayPanel getDisplay() {
        return display;
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new GameWindow();
            }
        });
    }
}
