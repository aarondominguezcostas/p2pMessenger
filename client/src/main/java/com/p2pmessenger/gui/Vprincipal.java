package com.p2pmessenger.gui;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.p2pmessenger.client.Message;
import com.p2pmessenger.client.P2PClientImpl;

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
    private P2PClientImpl c;

    public Vprincipal(P2PClientImpl cliente) {
        initComponents();
        c=cliente;
        //Actualizo tabla
        this.c.updateOnlineFriendList();
        actualizarTabla(c.getOnlineFriends());
        jTextArea1.setText("");
        usuariopropio.setText(c.getUsername());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        EscribirMensaje = new javax.swing.JTextArea();
        BotonEnviar = new javax.swing.JButton();
        usuariopropio = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        Solicitudes = new javax.swing.JMenuItem();
        NuevoAmigo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tabla.setModel(new tablausuarios());
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        EscribirMensaje.setColumns(20);
        EscribirMensaje.setRows(5);
        jScrollPane3.setViewportView(EscribirMensaje);

        BotonEnviar.setText("Enviar");
        BotonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEnviarActionPerformed(evt);
            }
        });

        usuariopropio.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

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
                        .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(usuariopropio, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(usuariopropio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                                                              
    
    
    private void menuActionPerformed(java.awt.event.ActionEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

  
    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        // TODO add your handling code here:
        this.c.disconnect();
        this.dispose();
    }                 

    //detctar que cambiou o amigo seleccionado
    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
        //Pedimos a implementación do cliente o chat correspondiente
        ArrayList<Message> chat= c.getChat(tabla.getValueAt(tabla.getSelectedRow(),0).toString());
        String conc="";

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        for(int i=0;i<chat.size();i++){
            conc=conc+sdf1.format(chat.get(i).getTimestamp());
            conc=conc+" "+chat.get(i).getSender()+"   ";
            conc=conc+chat.get(i).getMessage()+"\n-----------------------------------\n";
            
        }
        jTextArea1.setText(conc);
    }                                  

    //abnir ventana de solicitudes
    private void SolicitudesActionPerformed(java.awt.event.ActionEvent evt) {                                            
        Solicitudes vsol= new Solicitudes(this,c);
        vsol.setVisible(true);
    }                                           

    //abrir ventana de nuevo amigo
    private void NuevoAmigoActionPerformed(java.awt.event.ActionEvent evt) {                                           
        NewAmigo na=new NewAmigo(this,c);
        na.setVisible(true);
    }                                          

    //enviar mensaje
    private void BotonEnviarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        //Envío mensaje
        String mensaje=EscribirMensaje.getText();
        EscribirMensaje.setText("");//vacío casilla donde escribo a mensaxe
        c.enviarMensaje(mensaje,tabla.getValueAt(tabla.getSelectedRow(),0).toString());
        //O introduzco no jtext
        String m="";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        m=m+dtf.format(LocalDateTime.now());
        m=m+" "+c.getUsername()+"   ";
        m=m+mensaje;
        jTextArea1.setText(jTextArea1.getText()+m+"\n-----------------------------------\n");

        //DUDA---->Si hai erro ao enviar habría que mostralo na GUI?
    } 

    //Código para actualizar o chat
    public void MensajeRecibido(String emisor,String s){
        //si a columna seleccionada corresponde co mensaje recibido a actualizamos
        System.out.println(tabla.getValueAt(tabla.getSelectedRow(),0).toString());
        if(tabla.getValueAt(tabla.getSelectedRow(),0).toString().equals(emisor)){
            System.out.println("AQUICHEGA");
            String m="";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            m=m+dtf.format(LocalDateTime.now());
            m=m+" "+emisor+"   ";
            m=m+s;
            jTextArea1.setText(jTextArea1.getText()+m+"\n-----------------------------------\n");
        }
    }

   

    public void actualizarTabla(ArrayList<String> conectados) {
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
            }
        };
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
    private javax.swing.JLabel usuariopropio;
    // End of variables declaration                  
}
