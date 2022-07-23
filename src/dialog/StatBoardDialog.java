package dialog;

import minesweeper.GameWindow;
import constant.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatBoardDialog {
    private GameWindow frame;
    private static JDialog dialog;
    private static JPanel pane;
    private static JLabel title;
    private static final String filler = "---";
    private static JScrollPane sp;

    public StatBoardDialog(GameWindow frame) {
        this.frame = frame;
        init();
        dialog.setVisible(false);
    }
    
    //see StatBoard
    private void init() {
        dialog = new JDialog(frame, "LeaderBoard");
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        pane = new JPanel();
        pane.setPreferredSize(new Dimension(240, 300));
        pane.setBackground(Color.lightGray);

        title = new JLabel("LeaderBoard");
        Font f = new Font("Arial", Font.PLAIN, 24);
        title.setFont(f);
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 0, 20, 0);
        title.setBorder(emptyBorder);
        pane.add(title);

        sp = new JScrollPane();
        pane.add(sp);
        dialog.add(pane);
        dialog.pack();
    }
    public static void dataToTable(int level) {
        pane.remove(sp);

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
        JTable table = new JTable(new CustomTable(data, columnName));
        table.setBackground(Color.lightGray);

        //Create the scroll pane and add the table to it.
        sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(240, 180));
        sp.setBackground(Color.lightGray);

        pane.add(sp);
    }
    private static Object[][] listToArray(List<Stats> list) {
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
    public static void show() {
        dataToTable(StaticConst.level);
        dialog.setVisible(true);
        dialog.toFront();
    }


    
}
