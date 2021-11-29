package com.p2pmessenger.client;

import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;

public class P2PClientImpl extends UnicastRemoteObject implements P2PClientInterface{
    
    private ArrayList<Usuario> amigosconectados;

    protected P2PClientImpl() throws RemoteException {
        super();
    }

    //Recibir mensaje
    @Override
    public void recibirMensaje(String s, P2PClientInterface cliente) throws java.rmi.RemoteException{
        //comprobar que en los mensajes enviados del cliente esté ese mensaje.

        //recibir mensaje
    }

    @Override
    public void newOnlineFriend(String username, P2PClientInterface cliente) throws RemoteException {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void newOfflineFriend(String username, P2PClientInterface cliente) throws RemoteException {
        // TODO Auto-generated method stub
        
    }
}
