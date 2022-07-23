package minesweeper;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import constant.StaticConst;

import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;


public class DisplayPanel extends JPanel{
   
	private static final long serialVersionUID = 1L;
	GameWindow mainFrame;
    private JPanel timeLabel;
    public Timer timer;
    private JButton reset;
    private JButton pause;

    //number displays for time and mineLeft
    private JLabel tG;
    private JLabel tS;
    private JLabel tB;
    private JLabel mG;
    private JLabel mS;
    private JLabel mB;
    private JPanel mineS;


    public DisplayPanel (GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        insert();
    }

    public JButton getFaceButton() {
        return reset;
    }
    public JButton getPauseButton() { 
    	return pause; 
    }

    public JLabel getLabelG() {
        return tG;
    }

    public JLabel getLabelS() {
        return tS;
    }

    public JLabel getLabelB() {
        return tB;
    }
    
    private void insert() {
        //border
        this.setBackground(Color.LIGHT_GRAY);
        Border borderLow = BorderFactory.createLoweredBevelBorder();
        Border borderEmpty = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        Border borderCom1 = BorderFactory.createCompoundBorder(borderEmpty, borderLow);
        this.setBorder(borderCom1);

        //mineLeft display
        mineS = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        mG = new JLabel();
        mS = new JLabel();
        mB = new JLabel();
        //initial the time 0hrs 0mins 0 secs
        mG.setIcon(new ImageIcon("resources/image2/d0.gif"));
        mS.setIcon(new ImageIcon("resources/image2/d0.gif"));
        mB.setIcon(new ImageIcon("resources/image2/d0.gif"));
        mineS.add(mB);
        mineS.add(mS);
        mineS.add(mG);
        add(mineS);


        //reset button
        reset = new JButton();
        reset.setPreferredSize(new Dimension(32, 32));
        Icon faceIcon = new ImageIcon("resources/image2/face0.png");
        reset.setIcon(faceIcon);
        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /**Make sure in unmutedSound situation*/
                mainFrame.restartGame();
                timer.stop();
                StaticConst.timeCount = 0;
                StaticConst.timeStart = false;
                //reset the timer to 000
                tG.setIcon(StaticConst.resetTime);
                tS.setIcon(StaticConst.resetTime);
                tB.setIcon(StaticConst.resetTime);

            }
        });
       add(reset);

        //pause button
        pause = new JButton();
        pause.setPreferredSize(new Dimension(32, 32));
        pause.setIcon(StaticConst.pauseButton); 
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getPause();
                /**Time changing is stop */
                StaticConst.clipTimer.stop();
                StaticConst.mousePause = true;
            }
        });
        add(pause);

        //time label
        timeLabel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tG = new JLabel("");
        tS = new JLabel("");
        tB = new JLabel("");
        Icon T0 = new ImageIcon("resources/image2/d0.gif");
        tG.setIcon(T0);
        tS.setIcon(T0);
        tB.setIcon(T0);
        timeLabel.add(tB);
        timeLabel.add(tS);
        timeLabel.add(tG);
        add(timeLabel);

        Timer();
    }
        /**Upate MinesLeft */
    public void updateMineCount(int mine) {
        int g = mine % 10;
        int s = mine / 10 % 10;
        int b = mine / 100;
        mB.setIcon(new ImageIcon("resources/image2/d" + b + ".gif"));
        mS.setIcon(new ImageIcon("resources/image2/d" + s + ".gif"));
        mG.setIcon(new ImageIcon("resources/image2/d" + g + ".gif"));


    }
     /*Changing timer as time goes */
     public void Timer() {
        timer = new Timer(999, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /**make sure in unmuted situation, time ++ -> sound changes */
                if(!StaticConst.UnmuteSound){
                    StaticConst.clipTimer.start();
                    /**make loop, as timeCount sound is << 999*/
                    StaticConst.clipTimer.loop(47);
                }
                if (StaticConst.timeCount > 150) {
                    StaticConst.timeCount = 999;
                   /*make sure in unmuted situation, time ++ -> sound changes */ 
                   /**when time stop. sound->stop*/
                    if (!StaticConst.UnmuteSound){
                        StaticConst.clipTimer.stop();
                    }
                }
                else {
                    StaticConst.timeCount++;
                }
                /** setting changing face at resetButton, surprise!! */
                if(StaticConst.timeCount > 500) {
                    reset.setIcon(StaticConst.downSmileIcon);
                }
                /**Diaplay time using images */
                int g = StaticConst.timeCount % 10;
                int s = StaticConst.timeCount / 10 % 10;
                int b = StaticConst.timeCount / 100;
                tG.setIcon(new ImageIcon("resources/image2/d"+ g +".gif"));
                tS.setIcon(new ImageIcon("resources/image2/d"+ s +".gif"));
                tB.setIcon(new ImageIcon("resources/image2/d"+ b +".gif"));
            }
        });

    }
}
