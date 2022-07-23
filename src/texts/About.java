package texts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import constant.StaticConst;
import minesweeper.GameWindow;

public class About extends JPanel {
    
	private static final long serialVersionUID = 1L;
	
	GameWindow mainFrame;
    //web link title
    private static String str = "Visit Resource Code";
    
    public About(GameWindow mainFrame) {
        this.mainFrame = mainFrame;
        insert();
    }
    private void insert(){
        /**set layout && background*/
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new GridBagLayout());
        /**set exitAbout layout && action */
        JPanel head = new JPanel();
        head.setSize(200,100);
        head.setBackground(Color.LIGHT_GRAY);
        JButton exitAbout = new JButton();
        exitAbout.setPreferredSize(new Dimension(32, 32));
        exitAbout.setIcon(StaticConst.returnIcon);
        exitAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                mainFrame.ExitAbout();
            }
        });
        //set constraint make image and exitAbout align
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        head.add(exitAbout,gbc);       
        add(head, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridheight = 10;
        gbc.gridwidth = 10;
        //use writeAbout fun to return a panel as img
        JPanel keyinfo = WriteAbout();
        add(keyinfo,gbc);
    }
    //get key content in about, which could be also utilized by About Dialog if needed in future
    public static JPanel WriteAbout(){
        /** Basic setting */
        JPanel aboutArea = new JPanel();
        aboutArea.setLayout(new BorderLayout());
        aboutArea.setBackground(Color.LIGHT_GRAY);
    
        JPanel contactInfo = new JPanel();
        contactInfo.setLayout(new BorderLayout());
        //changing the style, Title in border
        TitledBorder tb = new TitledBorder(new LineBorder(Color.gray,5),"Authors");
        tb.setTitleFont(StaticConst.TitleFont);
        contactInfo.setBorder(tb);
        contactInfo.setBackground(Color.LIGHT_GRAY);
        /**set JTable, author coontact info involoved */
        Object[][] authorInfo = {{"Betty","bettyqin233@gmail.com"},
        {"Xiaoyu","xiaoyuchen007@gmail.com"},
        {"Jia","yujia00.qiu@gmail.com"}};
        String[] tableCol = {"Name","Email Address"}; //table cols name
        JTable authorTable = new JTable(authorInfo,tableCol);//combined as table
        JTableHeader Theader = authorTable.getTableHeader();
        //Basic setting for JTable
        Theader.setBackground(Color.white);
        Theader.setFont(StaticConst.TitleFont);
        //alignment for content in JTable
        TableCellRenderer rendererFromHeader = Theader.getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        authorTable.setBackground(Color.LIGHT_GRAY);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);       
        authorTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        authorTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
        authorTable.setCellSelectionEnabled(true);
        authorTable.setBounds(1,1,1,1);
        authorTable.setPreferredScrollableViewportSize(authorTable.getPreferredSize());
        JScrollPane sp = new JScrollPane(authorTable);

        //added github link
        JLabel link = new JLabel(str);
        link.setForeground(Color.BLUE.darker());
        link.setCursor(new Cursor(Cursor.HAND_CURSOR));
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();//make click sound
                }
                try {
                    URI uri = new URI("https://github.com/bette-q/minesweeper");
                    
                    Desktop dt = Desktop.getDesktop();
                    dt.browse(uri);
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();

                }

            }
            //when enterMouse, show key info
            @Override
            public void mouseEntered(MouseEvent e){
                link.setText(str);
            }
        });
        /**Show basic intro */
        String text =  "<html>";
        text += "A replica of the classic minesweeper made in Java<br>";
        text += "Swing Framework with personal aesthetic touches <br> ";
        text += "and additional functions such aspause button, <br>";
        text += "custom difficulties, and leader boards.It is an <br>";
        text += "executable program that automatically loads and <br>";
        text += "saves player< statistics locally.<br>";

        /** put the basic intro text into a panel, for later setting layout */
        JLabel mainText = new JLabel(text);
        JPanel info = new JPanel();
        info.setLayout(new BorderLayout());
        TitledBorder ab = new TitledBorder(new LineBorder(Color.gray,5),"About Minesweeper");
        ab.setTitleFont(StaticConst.TitleFont);
        info.setBorder(ab);
        info.setBackground(Color.LIGHT_GRAY); 
        info.add(mainText, BorderLayout.NORTH);
        info.add(link,BorderLayout.CENTER); //adding link 
        /**Formatting gif */
        JLabel figure = new JLabel();
        figure.setPreferredSize(new Dimension(190,250));
        figure.setIcon(StaticConst.aboutIcon);
        /**formatting the layout */
        contactInfo.add(figure, BorderLayout.NORTH);
        contactInfo.add(sp,BorderLayout.NORTH);
        aboutArea.add(info,BorderLayout.NORTH);
        aboutArea.add(contactInfo,BorderLayout.CENTER);
        aboutArea.add(figure,BorderLayout.SOUTH);     
        return aboutArea;
    }
}

