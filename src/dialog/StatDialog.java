package dialog;

import javax.swing.*;

import constant.*;
import minesweeper.GameWindow;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class StatDialog{
    private GameWindow frame;
    private JDialog dialog;

    public StatDialog(GameWindow frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        dialog = new JDialog(frame, "You Won!");
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(3, 0));

        JLabel info = new JLabel("Enter Player Name: ");
        p.add(info);

        JTextField input = new JTextField(20);
        p.add(input);

        JButton confirm = new JButton("OK");
        confirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                //name is default if no input
                String name;
                if(input.getText().isEmpty()) {
                    name = "---";
                } else {
                    name = input.getText();
                }                    
                //saves user entered name and time
                Stats s = new Stats(name, StaticConst.timeCount);


                //update the list
                if(StaticConst.level == 1) {
                    StaticConst.statEasy.add(s);
                }
                else if (StaticConst.level == 2) {
                    StaticConst.statMed.add(s);
                }
                else if (StaticConst.level == 3) {
                    StaticConst.statHard.add(s);
                }

                //auto save
                try {
                    StaticConst.saveData();
                }
                catch(IOException io) {
                    System.out.println(io);
                }

                dialog.dispose();
                StatBoardDialog.show();
            }

        });
        p.add(confirm);

        dialog.add(p);
        dialog.pack();
    }

    public void show() {
        dialog.setVisible(true);
    }

}
