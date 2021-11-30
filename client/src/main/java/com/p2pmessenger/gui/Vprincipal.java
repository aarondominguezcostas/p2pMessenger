package com.p2pmessenger.gui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.p2pmessenger.client.P2PClientImpl;
import com.p2pmessenger.client.P2PClientInterface;
import com.p2pmessenger.server.P2PServerInterface;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ecl
 */
public class Vprincipal extends javax.swing.JFrame {

    /**
     * Creates new form principal
     */
    private P2PServerInterface s;
    private P2PClientImpl c;
    private String id;
    private UUID uuidCliente;
    private HashMap<String,P2PClientInterface> amigos;
    public Vprincipal(P2PServerInterface servidor,P2PClientImpl cliente,String idp, UUID uuid) {
        initComponents();
        s=servidor;
        c=cliente;
        id=idp;
        uuidCliente=uuid;
        //Actualizo tabla
        actualizarTabla(c.getOnlineFriends());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        EscribirMensaje = new javax.swing.JTextArea();
        BotonEnviar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        Solicitudes = new javax.swing.JMenuItem();
        NuevoAmigo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabla.setModel(new tablausuarios());
        jScrollPane1.setViewportView(tabla);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        EscribirMensaje.setColumns(20);
        EscribirMensaje.setRows(5);
        jScrollPane3.setViewportView(EscribirMensaje);

        BotonEnviar.setText("Enviar");

        menu.setText("Menu");
        menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActionPerformed(evt);
            }
        });

        Solicitudes.setText("Solicitudes");
        Solicitudes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolicitudesActionPerformed(evt);
            }
        });
        menu.add(Solicitudes);

        NuevoAmigo.setText("Añadir amigo");
        NuevoAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoAmigoActionPerformed(evt);
            }
        });
        menu.add(NuevoAmigo);

        jMenuBar1.add(menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                         

    private void menuActionPerformed(java.awt.event.ActionEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void SolicitudesActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        System.out.println("Chega");
        Solicitudes vsol= new Solicitudes(this,s,c,id,uuidCliente);
        vsol.setVisible(true);
    }                                           

    private void NuevoAmigoActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        NewAmigo na=new NewAmigo(this,s,c,id,uuidCliente);
        na.setVisible(true);
    }                                          


   

    public void actualizarTabla(ArrayList<String> conectados) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Amigos");
       
        for(Object s:conectados){
            modelo.addRow(new Object[]{s});
        }
        tabla.setModel((TableModel) modelo);
        if (modelo.getRowCount() > 0) {
            tabla.setRowSelectionInterval(0, 0);
        }
    }
    
      
    // Variables declaration - do not modify                     
    private javax.swing.JButton BotonEnviar;
    private javax.swing.JTextArea EscribirMensaje;
    private javax.swing.JMenuItem NuevoAmigo;
    private javax.swing.JMenuItem Solicitudes;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenu menu;
    private javax.swing.JTable tabla;
    // End of variables declaration          
}
