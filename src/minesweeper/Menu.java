package minesweeper;

import javax.swing.*;
import javax.swing.border.Border;

import constant.StaticConst;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JPanel {
	 
	private static final long serialVersionUID = 1L;
	private JPanel startGamePanel;
    private JPanel statPanel;
    private JPanel howToplayPanel;
    private JPanel aboutPanel;
    private JPanel exitPanel;
    private JLabel startGameLabel;
    private JLabel statLabel;
    private JLabel howToplayLabel;
    private JLabel aboutLabel;
    private JLabel exitLabel;

    private GameWindow mainFrame;

    public Menu(GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        StaticConst.init();
        init();
    }
    private void init() {
        setPreferredSize(new Dimension(240, 305));
        JPanel pane = new JPanel();

        pane.setLayout(null);
        pane.setBackground(Color.LIGHT_GRAY);

        pane.addMouseListener(new MenuListener());

        //Start Game
        startGamePanel = new ButtonMaker(20);
        startGamePanel.setBackground(Color.LIGHT_GRAY);

        startGamePanel.setBounds(40,20,160,32);
        startGamePanel.setOpaque(false);

        startGameLabel = new JLabel("Start Game");
        startGameLabel.setForeground(Color.DARK_GRAY);
        startGameLabel.setFont(StaticConst.font);
        startGamePanel.add(startGameLabel);
        pane.add(startGamePanel);

        //Stat
        statPanel = new ButtonMaker(20, Color.LIGHT_GRAY);
        statPanel.setBounds(40,62,160,32);
        statPanel.setOpaque(false);
        statLabel = new JLabel("Stat");
        statLabel.setForeground(Color.DARK_GRAY);
        statLabel.setFont(StaticConst.font);
        statPanel.add(statLabel);
        pane.add(statPanel);

        //How To Play
        howToplayPanel = new ButtonMaker(20, Color.LIGHT_GRAY);
        howToplayPanel.setBounds(40,104,160,32);
        howToplayPanel.setOpaque(false);
        howToplayLabel = new JLabel("How To Play");
        howToplayLabel.setFont(StaticConst.font);
        howToplayLabel.setForeground(Color.DARK_GRAY);
        howToplayPanel.add(howToplayLabel);
        pane.add(howToplayPanel);

        //About
        aboutPanel = new ButtonMaker(20, Color.LIGHT_GRAY);
        aboutPanel.setBounds(40,146,160,32);
        aboutPanel.setOpaque(false);
        aboutLabel = new JLabel("About");
        aboutLabel.setForeground(Color.DARK_GRAY);
        aboutLabel.setFont(StaticConst.font);
        aboutPanel.add(aboutLabel);
        pane.add(aboutPanel);

        //Exit
        exitPanel = new ButtonMaker(20, Color.LIGHT_GRAY);
        exitPanel.setBounds(40,188,160,32);
        exitPanel.setOpaque(false);
        exitLabel = new JLabel("Exit");
        exitLabel.setForeground(Color.DARK_GRAY);
        exitLabel.setFont(StaticConst.font);
        exitPanel.add(exitLabel);
        pane.add(exitPanel);
        
        JLabel title = new JLabel("MineSweeper", SwingConstants.CENTER);
        title.setFont(StaticConst.TitleFont);
        Border emptyBorder = BorderFactory.createEmptyBorder(30, 0, 0, 0);
        title.setBorder(emptyBorder);
        
        this.setBackground(Color.lightGray);
        this.setLayout(new BorderLayout());
        
        this.add(title, BorderLayout.NORTH);
        this.add(pane, BorderLayout.CENTER);
    }
    
    //listens for mouse action within buttons' range
    private class MenuListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
             /** In unmuted situation, play Cick Sound */
            if (!StaticConst.UnmuteSound){
                StaticConst.playClick();
            }
            int x = e.getX();
            int y = e.getY();
            if(x > 40 && x < 200) {
                if(y > 191 && y < 223) {
                    System.exit(0);
                }
                else if(y > 148 && y < 180) {
                    mainFrame.getAbout();
                }
                else if (y > 105 && y < 138) {
                    mainFrame.getRule();
                }
                else if(y > 62 && y < 94) {
                    mainFrame.showStat();
                    StaticConst.inGameMenu = 2;
                }
                else if(y > 20 && y < 52) {
                    mainFrame.getLevel();
                    StaticConst.inGameMenu = 1;
                }
            }
        }
    }

}
