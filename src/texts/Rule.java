package texts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import constant.StaticConst;
import minesweeper.GameWindow;
public class Rule extends JPanel {
   
	private static final long serialVersionUID = 1L;
	
	GameWindow mainFrame;
    
    public Rule(GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        insert();
    }
    private void insert(){
        /** basic setting for frame */
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new GridBagLayout());
        /*setting title part, for later alignment */
        JPanel head = new JPanel();
        head.setSize(200,100);
        head.setBackground(Color.LIGHT_GRAY);
        /*setting exitRule button */
        JButton exitRule = new JButton();
        exitRule.setPreferredSize(new Dimension(32, 32));
        exitRule.setIcon(StaticConst.returnIcon);
        exitRule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                mainFrame.ExitRule();
            }
        });
        /** align the exitRule rule */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        head.add(exitRule,gbc);
        add(head, gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridheight = 10;
        gbc.gridwidth = 10;
        /**use Write Rule fun, which could be utilized in rule Dia, return a panel */
        JLabel keyinfo = WriteRule();
        JScrollPane sp = new JScrollPane(keyinfo);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(520, 400));
        add(sp, gbc);
    }

    public static JLabel WriteRule(){
        JLabel rule = new JLabel();
        rule.setBackground(Color.LIGHT_GRAY);
        rule.setIcon(StaticConst.ruleIcon);
        return rule;
    }
}