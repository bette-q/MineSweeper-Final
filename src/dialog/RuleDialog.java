package dialog;
import constant.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import texts.*;


public class RuleDialog extends JDialog{
   
	private static final long serialVersionUID = 1L;
	
	PauseDialog pause;
    JDialog ruleDialog;

    public RuleDialog(PauseDialog pause) {
        this.pause = pause;
        init();

    }

    public void init() {

        /** Set layout and title, basic setting */
        ruleDialog = new JDialog(pause, "Minesweeper Rules");
        ruleDialog.setLayout(new GridBagLayout());
        ruleDialog.setLocationRelativeTo(null);
        ruleDialog.setBackground(Color.LIGHT_GRAY);
        ruleDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel ruleLayout = new JPanel();
        ruleLayout.setBackground(Color.LIGHT_GRAY);

        /** Button: exit the rule Dialogue */

        JButton exitRule = new JButton();
        exitRule.setPreferredSize(new Dimension(25, 25));
        exitRule.setIcon(StaticConst.returnIcon);
        exitRule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();//Add Click sound
                }
                ruleDialog.dispose(); //remove the rule Dia         
            }

        });
        //use writeRule function, get the image as return
        JLabel rule = Rule.WriteRule();
        ruleLayout.add(exitRule,BorderLayout.CENTER);
        //utilize the constraints to make sure the image align with exitRule button
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 5;
        ruleDialog.add(ruleLayout,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 10;
        gbc.gridwidth = 10;
        
        ruleDialog.add(rule,gbc);
        ruleDialog.pack();
    }

    public void show() {
        ruleDialog.setVisible(true);
    }

    public void close() {
        ruleDialog.setVisible(false);
    }

    
}