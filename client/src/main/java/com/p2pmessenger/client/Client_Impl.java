package com.p2pmessenger.client;

import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;

public class Client_Impl extends UnicastRemoteObject implements Client_Interface{
    
    protected Client_Impl() throws RemoteException {
        super();
    }

    private ArrayList<Usuario> amigosconectados;
    //Recibir mensaje
    public void recibirMensaje(String s) throws java.rmi.RemoteException{
        System.out.println("Mensaje: "+s);
    }
    //Método que permite ao servidor notificar ao cliente a conexión de un novo cliente
    public void NovaConexion(Usuario u) throws java.rmi.RemoteException{
        amigosconectados.add(u);
    }
    //Notificar cliente desconectado
    public void NovaDesconexion(Usuario u) throws java.rmi.RemoteException{
        amigosconectados.remove(u);
    }

    //Creación de cliente necesitará chamar ao servidor para registrarse--->podemos devolver
    //a lista dos usuarios conectados nese mismo método

    public void RecibirSolicitudAmistad(Usuario u) throws java.rmi.RemoteException{

    }
}
