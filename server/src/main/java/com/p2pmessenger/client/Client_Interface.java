package com.p2pmessenger.client;
public interface Client_Interface extends java.rmi.Remote{
    //Recibir mensaje
    public void recibirMensaje(String s) throws java.rmi.RemoteException;
    //Método que permite ao servidor notificar ao cliente a conexión de un novo cliente
    public void NovaConexion(Usuario u) throws java.rmi.RemoteException;
    //Notificar cliente desconectado
    public void NovaDesconexion(Usuario u) throws java.rmi.RemoteException;

    //Creación de cliente necesitará chamar ao servidor para registrarse--->podemos devolver
    //a lista dos usuarios conectados nese mismo método

    public void RecibirSolicitudAmistad(Usuario u) throws java.rmi.RemoteException;
}