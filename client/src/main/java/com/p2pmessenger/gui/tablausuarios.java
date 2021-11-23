package com.p2pmessenger.gui;
/**
 *
 * @author ecl
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ecl
 */
public class tablausuarios extends AbstractTableModel {

    private String[] nombres;

    public void ModeloTablaUsuarios(Set amigos) {
        this.nombres=(String[]) amigos.toArray();
    }

    public int getColumnCount() {
        return 1;
    }

    @Override
    public int getRowCount() {
        return nombres == null ? 0 : nombres.length;
    }

    @Override
    public String getColumnName(int columnNumber) {
        
        return "Amigos";
    }

    @Override
    public Class getColumnClass(int columnNumber) {
        Class clase = null;

        return java.lang.String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {
        
        return nombres[row];
    }

    public void setFilas(Set amigos) {
        this.nombres=(String[]) amigos.toArray();
        fireTableDataChanged();
    }
}

