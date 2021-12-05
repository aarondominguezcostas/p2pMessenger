package com.p2pmessenger.gui;
/**
 *
 * @author ecl
 */
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ecl
 */
public class tablausuarios extends AbstractTableModel {

    private ArrayList<String> nombres;

    public void ModeloTablaUsuarios(ArrayList<String> amigos) {
        this.nombres=amigos;
    }

    public int getColumnCount() {
        return 1;
    }

    @Override
    public int getRowCount() {
        return nombres == null ? 0 : nombres.size();
    }

    @Override
    public String getColumnName(int columnNumber) {
        
        return "Amigos";
    }

    @Override
    public Class getColumnClass(int columnNumber) {
        return java.lang.String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {
        
        return nombres.get(row);
    }

    public void setFilas(ArrayList<String> amigos) {
        this.nombres=amigos;
        fireTableDataChanged();
    }
}

