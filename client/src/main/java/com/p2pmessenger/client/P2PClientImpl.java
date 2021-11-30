package com.p2pmessenger.client;

import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;

import com.p2pmessenger.server.P2PServerInterface;

public class P2PClientImpl extends UnicastRemoteObject implements P2PClientInterface{
    
    private HashMap<String, P2PClientInterface> amigosConectados;
    private UUID clientId;  
    private ArrayList<Message> mensajesEnviados;
    private HashMap<P2PClientInterface,Message> chats;
    private P2PServerInterface server;
    private String username;

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

    //METODOS REMOTOS

    //Recibir mensaje
    @Override
    public void recibirMensaje(Message s, P2PClientInterface cliente, String username) throws java.rmi.RemoteException{
        //comprobar que en los mensajes enviados del cliente esté ese mensaje.
        if(this.amigosConectados.get(username) != null && cliente.checkMessage(s)){
            this.chats.put(cliente, s);
        }
        //recibir mensaje
    }

    //añadir amigo a los conectados
    @Override
    public void newOnlineFriend(String username, P2PClientInterface cliente) throws RemoteException {

        if(this.server.getFriends(this.clientId,this.username).contains(username)){
            this.amigosConectados.put(username,cliente);
        }
        
    }

    //eliminar al amigo de los conectados
    @Override
    public void newOfflineFriend(String username, P2PClientInterface cliente) throws RemoteException {
        if(this.server.getFriends(this.clientId, this.username).contains(username)){
            this.amigosConectados.remove(username);
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

    public void enviarMensaje(String mensaje, String destinatario) throws RemoteException {
        if(this.amigosConectados.containsKey(destinatario)){
            Message m = new Message(this.clientId, mensaje);
            this.mensajesEnviados.add(m);
            this.amigosConectados.get(destinatario).recibirMensaje(m, this, this.username);
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

    public ArrayList<String> getPendingFriends() {
        try {
            return this.server.getSolicitudesPendientes(this.clientId, this.username); 
        }catch (Exception e) {
            System.out.println("Error al obtener las solicitudes pendientes");
            return new ArrayList<>();
        }
    }
}
