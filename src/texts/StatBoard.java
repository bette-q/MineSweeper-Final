package texts;

import minesweeper.GameWindow;
import constant.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StatBoard extends JPanel{

	private static final long serialVersionUID = 1L;
	private GameWindow frame;
    private JLabel title;
    private JTable table;
    private JScrollPane sp;
    
    private static final String filler = "---";

    public StatBoard(GameWindow frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(240, 300));
        this.setBackground(Color.lightGray);
        
        title = new JLabel("LeaderBoard");
        
        title.setFont(StaticConst.TitleFont);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);
        title.setBorder(emptyBorder);

        JButton back = new JButton();
        
        back.setIcon(StaticConst.returnIcon);
        back.setPreferredSize(new Dimension(25, 25));
        back.setBorder(emptyBorder);
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!StaticConst.UnmuteSound){
                    StaticConst.playClick();
                }
                frame.ExitStat();
            }

        });
      
        this.add(back);
        this.add(title);

        sp = new JScrollPane();
        this.add(sp);
    }
    public void dataToTable(int level) {
        this.remove(sp);

        List<Stats> list = new ArrayList<>();
        if(level == 1) {
            list = StaticConst.statEasy;
            title.setText("Beginner");
        }
        else if (level == 2) {
            list = StaticConst.statMed;
            title.setText("Intermediate");
        }
        else if(level == 3) {
            list = StaticConst.statHard;
            title.setText("Advanced");
        }

        Object[][] data = listToArray(list);
        String[] columnName = {"Rank", "Player", "Time"};
        table = new JTable(new CustomTable(data, columnName));
        table.setBackground(Color.lightGray);

        //Create the scroll pane and add the table to it.
        sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(240, 180));
        sp.setBackground(Color.lightGray);

        this.add(sp);
    }
    private Object[][] listToArray(List<Stats> list) {
        List<List<Object>> stats = new ArrayList<>();
        
        //convert the top 10 stats to array for table
        int i = 0;
        int size = list.size();
        while(i < 10 && i < size) {
            Stats s = list.get(i);
            List<Object> line = new ArrayList<>();
            line.add(i + 1);
            line.add(s.getName());
            line.add(s.getTime());
            stats.add(new ArrayList<>(line));
            ++i;
        }
        //fills the remaining places if stat < 10
        if(size < 10) {
            while(i < 10) {
                List<Object> line = new ArrayList<>();
                line.add(i + 1);
                line.add(filler);
                line.add(filler);
                stats.add(new ArrayList<>(line));
                ++i;
            }
        }
        
        Object[][] data = new Object[10][3];
        int statSize = stats.size();
        for(int j = 0; j < statSize; ++j) {
            data[j] = stats.get(j).toArray();
        }
        
        return data;
    }
}
