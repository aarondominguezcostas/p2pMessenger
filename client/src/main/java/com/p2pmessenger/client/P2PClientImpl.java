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
    private HashMap<String,ArrayList<Message>> chats;
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
        //comprobar que en los mensajes enviados del cliente esté ese mensaje.
        if(this.amigosConectados.get(username) != null && cliente.checkMessage(s)){
            //Añado o mensaje recibido ao chat
            System.out.println("\n"+username+"\n");
            System.out.println("\n\n"+this.chats.get(username).size()+"\n\n");
            this.chats.get(username).add(s);
            //notifico a ventana e ela se encarga de mostralo si o amigo seleccionado é o receptor
            window.MensajeRecibido(username, s.getMessage());
        }
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
            //Creo o chat, vacío de momento
            ArrayList<Message> c=new ArrayList<Message>();
            this.chats.put(username,c);
            this.actualizarVistaAmigosOnline(this.getOnlineFriends());
            
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

    //establece el UUID del cliente
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    //devuelve el servidor
    public P2PServerInterface getServer() {
        return server;
    }

    //envia un mensaje al destinatario
    public void enviarMensaje(String mensaje, String destinatario){
        if(this.amigosConectados.containsKey(destinatario)){
            Message m = new Message(this.clientId, mensaje, this.username);

            this.mensajesEnviados.add(m);
            
            try {
                this.amigosConectados.get(destinatario).recibirMensaje(m, this, this.username);
                //gardo o meu propio mensaje para poder mostralo por pantalla si cambio de chat e volvo
                this.chats.get(destinatario).add(m);

            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                System.out.println("Error enviando mensaje:"+e.toString());
                this.mensajesEnviados.remove(m);
            }
        }
    }

    //devuelve una lista de amigos conectados
    public ArrayList<String> getOnlineFriends() {
        try {
            //creo chat para eles
            for (String a : this.amigosConectados.keySet()){
                if(this.chats.get(a)==null){
                    //Creo o chat, vacío de momento
                    ArrayList<Message> c=new ArrayList<Message>();
                    this.chats.put(a,c);
                }
            }
            return this.amigosConectados.keySet().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }catch (Exception e) {
            System.out.println("Error al obtener los amigos conectados");
            return new ArrayList<>();
        }
    }

    //actualiza la lista de amigos conectados
    public void updateOnlineFriendList(){
        try{
            this.amigosConectados = this.server.getAmigosOnline(this.clientId, this.username);
            //creo chat para eles
            for (String a : this.amigosConectados.keySet()){
                if(this.chats.get(a)==null){
                    //Creo o chat, vacío de momento
                    ArrayList<Message> c=new ArrayList<Message>();
                    this.chats.put(a,c);
                }
            }

        }catch (Exception e){
            System.out.println("Error al obtener los amigos conectados");
        }
    }

    //devuelve un arraylist de  la lista pendiente de amigos
    public ArrayList<String> getPendingFriends() {
        try {
            return this.server.getSolicitudesPendientes(this.clientId, this.username); 
        }catch (Exception e) {
            System.out.println("Error al obtener las solicitudes pendientes");
            return new ArrayList<>();
        }
    }

    //devuelve el chat especifico para un amigo
    public ArrayList<Message> getChat(String username){
        return chats.get(username);
    }

    //actualiza los amigos conectados en la ventana principal
    public void actualizarVistaAmigosOnline(ArrayList<String> amigosOnline) {
        this.window.actualizarTabla(amigosOnline);
    }

    //devuelve el nombre de usuario 
    public String getUsername() {
        return username;
    }

    //añadir amigo a partir de su nombre de usuario
    public void addAmigo(String idNewAmigo){
        try {
            this.server.aceptarSolicitud(this.username, this.clientId, idNewAmigo);;
        }catch (Exception e) {
            System.out.println("Error al añadir amigo");
        }
    }

    //solicitar amistad 
    public void sendRequest(String idNewAmigo){
        try {
            this.server.solicitarAmistad(idNewAmigo, this.clientId, this.username);
        }catch (Exception e) {
            System.out.println("Error al solicitar amistad");
        }
    }

    //rechazar amistad
    public void rejectFriendRequest(String idRechazado){
        try {
            this.server.rechazarSolicitud(this.username, this.clientId, idRechazado);
        }catch (Exception e) {
            System.out.println("Error al rechazar amistad");
        }
    }

    //Desconectarse
    public void disconnect(){
        try {
            this.server.logout(this.clientId, this.username);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            System.out.println("Error desconectándose:"+e.toString());
        }
    }
    

}
