package dialog;


import constant.StaticConst;
import minesweeper.ButtonMaker;
import minesweeper.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;


public class PauseDialog extends JDialog{
    
	private static final long serialVersionUID = 1L;
	GameWindow mainFrame;
    private JPanel howToPlayButton;
    private JPanel resumeButton;
    private JPanel SoundButton;

    private JLabel howToPlayLabel;
    private JLabel resumeLabel;
    private JLabel soundLabel;
    private RuleDialog ruleDialog = new RuleDialog(this);

    JDialog dialog;

    public PauseDialog(GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    public void init() {
        //create a Jdialog which is centered over parent(mainFrame);
        dialog = new JDialog(mainFrame, "Deluxe Minesweeper");
        dialog.setUndecorated(true);
        dialog.setPreferredSize(new Dimension(16 * 15 + 1, 15 * 15 + 1));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(mainFrame);


        JPanel pane = new JPanel();
        pane.setLayout(null);
        pane.setBackground(Color.LIGHT_GRAY);
        pane.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        PauseListener pauseListener;
        pauseListener = new PauseListener();
        pane.addMouseListener(pauseListener);


        JPanel pausePanel = new JPanel();
        pausePanel.setBounds(8,10,15 * 15,3* 13);
        pausePanel.setBackground(Color.LIGHT_GRAY);

        JLabel pauseLabel = new JLabel("Pause");
        pauseLabel.setForeground(Color.DARK_GRAY);
        pauseLabel.setFont(StaticConst.TitleFont);
        pausePanel.add(pauseLabel);
        pane.add(pausePanel);

        SoundButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        SoundButton.setBounds(40,50,160,40);
        SoundButton.setOpaque(false);

        soundLabel = new JLabel("Sound");
        soundLabel.setForeground(Color.DARK_GRAY);
        soundLabel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        soundLabel.setFont(StaticConst.font);
        SoundButton.add(soundLabel);
        pane.add(SoundButton);

        howToPlayButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        howToPlayButton.setBounds(40,105,160,40);
        howToPlayButton.setOpaque(false);

        howToPlayLabel = new JLabel("How To Play");
        howToPlayLabel.setForeground(Color.DARK_GRAY);
        howToPlayLabel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        howToPlayLabel.setFont(StaticConst.font);
        howToPlayButton.add(howToPlayLabel);

        pane.add(howToPlayButton);

        resumeButton = new ButtonMaker(20, Color.LIGHT_GRAY);
        resumeButton.setBounds(40,160,160,40);
        resumeButton.setOpaque(false);

        resumeLabel = new JLabel("Resume");
        resumeLabel.setForeground(Color.DARK_GRAY);
        resumeLabel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        resumeLabel.setFont(StaticConst.font);
        resumeButton.add(resumeLabel);

        pane.add(resumeButton);

        dialog.add(pane);

        dialog.setVisible(true);

    }
    public void show() {
        dialog.setVisible(true);
    }

    public void close() {
        dialog.setVisible(false);
    }

    public class PauseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (!StaticConst.UnmuteSound){
                StaticConst.playClick();
            }
            int x = e.getX();
            int y = e.getY();
            if(x > 40 && x < 200) {
                if (y > 160 && y < 200) {
                    if(StaticConst.timeStart) {
                        mainFrame.getDisplay().timer.restart();
                    }
                    /* when StaticConst.mousePause is true, you can not press the board */
                    StaticConst.mousePause = false;
                    dialog.dispose();
                }
                else if(y > 105 && y < 145) {
                    ruleDialog.toFront();
                    ruleDialog.setVisible(true);
                }
                else if(y > 50 && y < 90) {
                    if (StaticConst.UnmuteSound){
                        StaticConst.loadSound();
                        StaticConst.UnmuteSound = false;
                    }
                    else if(!StaticConst.UnmuteSound){
                        StaticConst.clipOver.close();
                        StaticConst.clipWin.close();
                        StaticConst.UnmuteSound = true;
                    }

                }
            }
        }
    }
}
