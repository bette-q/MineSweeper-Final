package constant;

import javax.swing.table.AbstractTableModel;


/* 
 * custom table to override editable cells in default table model 
 * */
public class CustomTable extends AbstractTableModel{
    
	private static final long serialVersionUID = 1L;
	private Object[][] data;
    private String[] columnName;

    public CustomTable(Object[][] data, String[] columnName) {
        this.data = data;
        this.columnName = columnName;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public String getColumnName(int col) {
        return columnName[col];
    }


}
