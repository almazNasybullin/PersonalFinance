package gui.table.model;

import gui.Refresh;
import model.Common;
import settings.Text;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class MainTableModel extends AbstractTableModel implements Refresh {

    protected List<? extends Common> data;
    protected List<String> columns = new ArrayList<>();

    public MainTableModel(List data, String[] columns) {
        this.data = data;
        this.columns = new ArrayList<>(Arrays.asList(columns));
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return Text.get(columns.get(columnIndex));
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Object obj = getValueAt(0, columnIndex);
        if (obj == null) return Object.class;
        return obj.getClass();
    }

    public void refresh() {
        updateData();
        fireTableStructureChanged();
        fireTableDataChanged();
    }

    public Common getCommonByRow(int row) {
//        if (data.indexOf(row) == -1) return null;
        return data.get(row);
    }

    protected abstract void updateData();
}
