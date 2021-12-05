package com.p2pmessenger.gui;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
public class tablaSolicitudes extends AbstractTableModel {

    private ArrayList<String> solicitudes;

    public void ModeloTablaUsuarios(ArrayList<String> sol) {
        this.solicitudes=sol;
    }

    public int getColumnCount() {
        return 1;
    }

    @Override
    public int getRowCount() {
        return solicitudes == null ? 0 : solicitudes.size();
    }

    @Override
    public String getColumnName(int columnNumber) {
        
        return "Solicitudes";
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
        
        return solicitudes.get(row);
    }

    public void setFilas(ArrayList<String> sol) {
        this.solicitudes=sol;
        fireTableDataChanged();
    }
}

