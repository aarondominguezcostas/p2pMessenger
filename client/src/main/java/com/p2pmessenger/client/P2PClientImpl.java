package com.p2pmessenger.client;

import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;

import com.p2pmessenger.server.P2PServerInterface;
import com.p2pmessenger.gui.Vprincipal;

public class P2PClientImpl extends UnicastRemoteObject implements P2PClientInterface{
    
    private HashMap<String, P2PClientInterface> amigosConectados;
    private UUID clientId;  
    private ArrayList<Message> mensajesEnviados;
    private HashMap<String,ArrayList<MensajeChat>> chats;
    private P2PServerInterface server;
    private String username;
    private Vprincipal window;

    protected P2PClientImpl() throws RemoteException {
        super();
        this.amigosConectados = new HashMap<>();
        this.mensajesEnviados = new ArrayList<>();
        this.chats = new HashMap<>();
        this.clientId = null;
    }

    public void setServer(P2PServerInterface server) {
        this.server = server;
    }

    public void setWindow(Vprincipal window) {
        this.window = window;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }


    //METODOS REMOTOS

    //Recibir mensaje
    @Override
    public void recibirMensaje(Message s, P2PClientInterface cliente, String username) throws java.rmi.RemoteException{
        /*//comprobar que en los mensajes enviados del cliente esté ese mensaje.
        if(this.amigosConectados.get(username) != null && cliente.checkMessage(s)){
            //Añado o mensaje recibido ao chat
            this.chats.get(username).add(new MensajeChat(username,s.getMessage(),s.getTimestamp()));
            //notifico a ventana e ela se encarga de mostralo si o amigo seleccionado é o receptor
            window.MensajeRecibido(username, s.getMessage());
        }*/
        //recibir mensaje
    }

    //añadir amigo a los conectados
    @Override
    public void newOnlineFriend(String username, P2PClientInterface cliente) throws RemoteException {

        if(this.server == null) {
            throw new RemoteException("Server not set");
        }

        if(this.server.getFriends(this.clientId,this.username).contains(username)){
            this.amigosConectados.put(username,cliente);
            this.actualizarVistaAmigosOnline(this.getOnlineFriends());
            //Creo o chat, vacío de momento
            ArrayList<MensajeChat> c=new ArrayList<MensajeChat>();
            this.chats.put(username,c);
        }
        
    }

    //eliminar al amigo de los conectados
    @Override
    public void newOfflineFriend(String username, P2PClientInterface cliente) throws RemoteException {
        if(this.server.getFriends(this.clientId, this.username).contains(username)){
            this.amigosConectados.remove(username);
            //elimino o chat
            this.chats.remove(username);
        }
    }

    //comprobar que el destinatario envio el mensaje
    @Override
    public boolean checkMessage(Message message) throws RemoteException {
        return this.mensajesEnviados.contains(message);
    }

    //METODOS DE LA IMPLEMENTACION DEL CLIENTE

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public P2PServerInterface getServer() {
        return server;
    }

    public void enviarMensaje(String mensaje, String destinatario){
        if(this.amigosConectados.containsKey(destinatario)){
            Message m = new Message(this.clientId, mensaje);


            //MOVER a despois do catch mellor??????
            this.mensajesEnviados.add(m);
            
            try {
                this.amigosConectados.get(destinatario).recibirMensaje(m, this, this.username);
                //gardo o meu propio mensaje para poder mostralo por pantalla si cambio de chat e volvo
                //this.chats.get(destinatario).add(new MensajeChat(this.username,mensaje,m.getTimestamp()));

            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                System.out.println("Error enviando mensaje:"+e.toString());
            }
        }
    }

    public ArrayList<String> getOnlineFriends() {
        try {
            return this.amigosConectados.keySet().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }catch (Exception e) {
            System.out.println("Error al obtener los amigos conectados");
            return new ArrayList<>();
        }
    }

    public void updateOnlineFriendList(){
        try{
            this.amigosConectados = this.server.getAmigosOnline(this.clientId, this.username);
        }catch (Exception e){
            System.out.println("Error al obtener los amigos conectados");
        }
    }

    public ArrayList<String> getPendingFriends() {
        try {
            return this.server.getSolicitudesPendientes(this.clientId, this.username); 
        }catch (Exception e) {
            System.out.println("Error al obtener las solicitudes pendientes");
            return new ArrayList<>();
        }
    }
    public ArrayList<MensajeChat> getChat(String username){
        return chats.get(username);
    }

    public void actualizarVistaAmigosOnline(ArrayList<String> amigosOnline) {
        this.window.actualizarTabla(amigosOnline);
    }

    public String getUsername() {
        return username;
    }

}
