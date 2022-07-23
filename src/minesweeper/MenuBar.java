package minesweeper;
import constant.StaticConst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar{
    
	private static final long serialVersionUID = 1L;

	private GameWindow mainFrame;

    private JMenu menuGame;
    private JMenu menuLevel;

    private JMenuItem backtoMenu;
    private JMenuItem backtoBeginner;
    private JMenuItem backtoIntermediate;
    private JMenuItem backtoAdvanced;

    public MenuBar (GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        insert();
    }

    private void insert() {

        setBackground(Color.LIGHT_GRAY);

        /**menuGame -- can go back to main menu and select level*/

        menuGame = new JMenu("Menu(M)");
        menuGame.setFont(StaticConst.font);
        
        menuGame.setMnemonic(KeyEvent.VK_M); //alt + m to invoke


        backtoMenu = new JMenuItem("Main Menu");
        backtoMenu.setFont(StaticConst.font);
        backtoMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                mainFrame.gobackMenu();
            }
        });

        //Beginner level
        menuLevel = new JMenu("Difficulty");
        menuLevel.setFont(StaticConst.font);
        menuGame.add(menuLevel);
        backtoBeginner = new JMenuItem("Beginner");
        backtoBeginner.setFont(StaticConst.font);
        backtoBeginner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                
                StaticConst.rows = 9;
                StaticConst.cols = 9;
                StaticConst.mineCount = 10;
                StaticConst.timeCount = 0;
                StaticConst.level = 1;
                
                if(StaticConst.inGameMenu == 1 || StaticConst.inGameMenu == 2 ) {
                    mainFrame.startGame();
                }else {
                    mainFrame.restartGame();
                    mainFrame.getDisplay().timer.stop();
                    StaticConst.timeStart = false;
                }
            }
        });

        //Intermediate level
        backtoIntermediate = new JMenuItem("Intermediate");
        backtoIntermediate.setFont(StaticConst.font);
        backtoIntermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                
                StaticConst.rows = 16;
                StaticConst.cols = 16;
                StaticConst.mineCount = 40;
                StaticConst.timeCount = 0;
                StaticConst.level = 2;
                //inGameMenu is determine which page you're. and decided the way to start game
                if(StaticConst.inGameMenu == 1 || StaticConst.inGameMenu == 2 ) {
                    mainFrame.startGame();
                }else {
                    mainFrame.restartGame();
                    mainFrame.getDisplay().timer.stop();
                    StaticConst.timeStart = false;
                }
            }
        });

        //Advanced level
        backtoAdvanced = new JMenuItem("Advanced");
        backtoAdvanced.setFont(StaticConst.font);
        backtoAdvanced.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                
                StaticConst.rows = 16;
                StaticConst.cols = 30;
                StaticConst.mineCount = 99;
                StaticConst.timeCount = 0;
                StaticConst.level = 3;
                
                if(StaticConst.inGameMenu == 1 || StaticConst.inGameMenu == 2 ) {
                    mainFrame.startGame();
                }else {
                    mainFrame.restartGame();
                    mainFrame.getDisplay().timer.stop();
                    StaticConst.timeStart = false;
                }
            }
        });
        menuGame.add(backtoMenu);
        menuLevel.add(backtoBeginner);
        menuLevel.add(backtoIntermediate);
        menuLevel.add(backtoAdvanced);
        menuGame.add(menuLevel);

        this.add(menuGame);
    }

}
