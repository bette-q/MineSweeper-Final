package minesweeper;

import javax.swing.*;

import constant.StaticConst;

import java.awt.event.*;
import java.awt.*;


public class StatLevel extends JPanel{
  
	private static final long serialVersionUID = 1L;
	private GameWindow frame;
    private JPanel easyStatButton;
    private JPanel mediumStatButton;
    private JPanel hardStatButton;

    private JLabel easyStatLabel;
    private JLabel mediumStatLabel;
    private JLabel hardStatLabel;

    public StatLevel(GameWindow frame) {
        this.frame = frame;
        init();
    }

    private void init() {

        JPanel pane = new JPanel();
        pane.setLayout(null);
        pane.setPreferredSize(new Dimension(240, 250));
        pane.setBackground(Color.LIGHT_GRAY);
        
        pane.addMouseListener(new StatListener());

        easyStatButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        easyStatButton.setBounds(40,20,160,60);
        easyStatButton.setOpaque(false);

        easyStatLabel = new JLabel("Beginner");
        easyStatLabel.setForeground(Color.DARK_GRAY);
        easyStatLabel.setBorder(BorderFactory.createEmptyBorder(16,0,0,0));
        easyStatLabel.setFont(StaticConst.font);
        easyStatButton.add(easyStatLabel);

        pane.add(easyStatButton);

        mediumStatButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        mediumStatButton.setBounds(40,95,160,60);
        mediumStatButton.setOpaque(false);

        mediumStatLabel = new JLabel("Intermediate");
        mediumStatLabel.setForeground(Color.DARK_GRAY);
        mediumStatLabel.setBorder(BorderFactory.createEmptyBorder(16,0,0,0));
        mediumStatLabel.setFont(StaticConst.font);
        mediumStatButton.add(mediumStatLabel);

        pane.add(mediumStatButton);

        hardStatButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        //hardStatButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        hardStatButton.setBounds(40,170,160,60);
        hardStatButton.setOpaque(false);

        hardStatLabel = new JLabel("Advanced");
        hardStatLabel.setForeground(Color.DARK_GRAY);
        hardStatLabel.setBorder(BorderFactory.createEmptyBorder(16,0,0,0));
        hardStatLabel.setFont(StaticConst.font);
        hardStatButton.add(hardStatLabel);

        pane.add(hardStatButton);

        this.setBackground(Color.lightGray);
        this.setLayout(new BorderLayout());
        this.add(pane, BorderLayout.CENTER);

    }
    
    //listens for mouse action within buttons' range
    private class StatListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            /** In unmuted situation, play Cick Sound */
            if (!StaticConst.UnmuteSound){
                StaticConst.playClick();
            }
            int x = e.getX();
            int y = e.getY();
            if(x > 40 && x < 200) {

                if (y > 170 && y < 230) {
                    frame.getStat(3);
                }
                else if(y > 95 && y < 155) {
                    frame.getStat(2);
                }
                else if(y > 20 && y < 80) {
                    frame.getStat(1);
                }
            }
        }
    }

}
