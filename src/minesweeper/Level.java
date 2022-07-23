package minesweeper;

import constant.StaticConst;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.*;

public class Level extends JPanel{
    
	private static final long serialVersionUID = 1L;
	private JPanel easyButton;
    private JPanel mediumButton;
    private JPanel hardButton;
    private JPanel customButton;

    private JLabel easyLabel;
    private JLabel mediumLabel;
    private JLabel hardLabel;
    private JLabel customLabel;

    private GameWindow mainFrame;

    private JSlider rowSlide;
    private JSlider colSlide;
    private JSlider mineSlide;

    public Level(GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        insert();
    }

    private void insert() {
        setPreferredSize(new Dimension(240, 280));
        JPanel pane = new JPanel();

        pane.setLayout(null);
        pane.setBackground(Color.LIGHT_GRAY);

        LevelListener levelListener;
        levelListener = new LevelListener();
        pane.addMouseListener(levelListener);

        //Beginner
        easyButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        easyButton.setBounds(40,20,160,32);
        easyButton.setOpaque(false);

        easyLabel = new JLabel("Beginner");
        easyLabel.setForeground(Color.DARK_GRAY);
        easyLabel.setFont(StaticConst.font);
        easyButton.add(easyLabel);

        pane.add(easyButton);

        //Intermediate
        mediumButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        mediumButton.setBounds(40,62,160,32);
        mediumButton.setOpaque(false);

        mediumLabel = new JLabel("Intermediate");
        mediumLabel.setForeground(Color.DARK_GRAY);
        mediumLabel.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        mediumLabel.setFont(StaticConst.font);
        mediumButton.add(mediumLabel);

        pane.add(mediumButton);

        //Advanced
        hardButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        hardButton.setBounds(40,104,160,32);
        hardButton.setOpaque(false);

        hardLabel = new JLabel("Advanced");
        hardLabel.setForeground(Color.DARK_GRAY);
        hardLabel.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        hardLabel.setFont(StaticConst.font);
        hardButton.add(hardLabel);

        pane.add(hardButton);

        //Custom
        customButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        customButton.setBounds(40,146,160,32);
        customButton.setOpaque(false);

        customLabel = new JLabel("Custom");
        customLabel.setForeground(Color.DARK_GRAY);
        customLabel.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        customLabel.setFont(StaticConst.font);
        customButton.add(customLabel);

        pane.add(customButton);


        JPanel slider = new JPanel(new GridLayout(4, 1));
        slider.setBackground(Color.LIGHT_GRAY);
        slider.setBounds(35,187,15 * 12,7* 15);
                
        //row slider
        JLabel row = new JLabel("Rows: 0");
        row.setFont(StaticConst.font);
        slider.add(row);

        rowSlide = new JSlider(JSlider.HORIZONTAL, 0, 24, 0);
        rowSlide.setMajorTickSpacing(5);
        rowSlide.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int val = rowSlide.getValue();
                row.setText("Rows: " + val);
                int max = (rowSlide.getValue() * colSlide.getValue() - 1) < 0? 0 : rowSlide.getValue() * colSlide.getValue() - 1; //rows and cols must be > 0
                mineSlide.setMaximum(max); //max mine depends on rows and cols
            }
        });
        slider.add(rowSlide);
        
        //column slider
        JLabel col = new JLabel("Columns: 0");
        col.setFont(StaticConst.font);
        slider.add(col);

        colSlide = new JSlider(JSlider.HORIZONTAL, 0, 30, 0);
        colSlide.setMajorTickSpacing(5);
        colSlide.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int val = colSlide.getValue();
                col.setText("Columns: " + val);
                int max = (rowSlide.getValue() * colSlide.getValue() - 1) < 0? 0 : rowSlide.getValue() * colSlide.getValue() - 1;
                mineSlide.setMaximum(max);
            }
        });
        slider.add(colSlide);

        //mine # slider
        JLabel mine = new JLabel("Mines: 0");
        mine.setFont(StaticConst.font);
        slider.add(mine);

        mineSlide = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
        mineSlide.setMajorTickSpacing(5);
        mineSlide.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int val = mineSlide .getValue();
                mine.setText("Mines: " + val);
            }
        });
        slider.add(mineSlide);
        pane.add(slider);
        this.setBackground(Color.lightGray);
        this.setLayout(new BorderLayout());
        this.add(pane, BorderLayout.CENTER);

    }
    
    //listens for mouse action within buttons' range
    private class LevelListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (!StaticConst.UnmuteSound){
                StaticConst.playClick();
            }
            int x = e.getX();
            int y = e.getY();
            if(x > 40 && x < 200) {
                 if(y > 146 && y < 178) {
                    StaticConst.level = 0;
                    StaticConst.rows = rowSlide.getValue();
                    StaticConst.cols = colSlide.getValue();
                    StaticConst.mineCount = mineSlide.getValue();

                    mainFrame.startGame();
                }
                else if (y > 104 && y < 136) {
                    StaticConst.level = 3;
                    StaticConst.rows = 16;
                    StaticConst.cols = 30;
                    StaticConst.mineCount = 99;

                    mainFrame.startGame();
                }
                else if(y > 60 && y < 94) {
                    StaticConst.level = 2;
                    StaticConst.rows = 16;
                    StaticConst.cols = 16;
                    StaticConst.mineCount = 40;

                    mainFrame.startGame();
                }
                else if(y > 20 && y < 52) {
                    StaticConst.level = 1;
                    StaticConst.rows = 9;
                    StaticConst.cols = 9;
                    StaticConst.mineCount = 10;

                    mainFrame.startGame();
                }
            }
        }
    }

}
