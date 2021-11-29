package com.p2pmessenger.client;

import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.UUID;
import com.p2pmessenger.server.P2PServerInterface;
public class P2PClientImpl extends UnicastRemoteObject implements P2PClientInterface{
    
    private ArrayList<P2PClientInterface> amigosConectados;
    private UUID clientId;  
    private ArrayList<Message> mensajes;
    private P2PServerInterface server;
    private String username;

    protected P2PClientImpl() throws RemoteException {
        super();
        this.amigosConectados = new ArrayList<>();
        this.mensajes = new ArrayList<>();
        this.clientId = null;
    }

    public void setServer(P2PServerInterface server) {
        this.server = server;
    }

    //Recibir mensaje
    @Override
    public void recibirMensaje(Message s, P2PClientInterface cliente) throws java.rmi.RemoteException{
        //comprobar que en los mensajes enviados del cliente esté ese mensaje.
        if(this.amigosConectados.contains(cliente) && cliente.checkMessage(s)){
            this.mensajes.add(s);
        }
        //recibir mensaje
    }

    @Override
    public void newOnlineFriend(String username, P2PClientInterface cliente) throws RemoteException {

        if(this.server.getFriends(this.clientId,this.username).contains(username)){
            this.amigosConectados.add(cliente);
        }
        
    }

    @Override
    public void newOfflineFriend(String username, P2PClientInterface cliente) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean checkMessage(Message message) throws RemoteException {
        return this.mensajes.contains(message);
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

}
