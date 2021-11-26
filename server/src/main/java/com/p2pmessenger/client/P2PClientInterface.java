package com.p2pmessenger.client;

public interface P2PClientInterface extends java.rmi.Remote {
    // Recibir mensaje
    public void recibirMensaje(String s, P2PClientInterface cliente) throws java.rmi.RemoteException;

    // Método que permite ao servidor notificar ao cliente a conexión de un novo
    // cliente
    public void newOnlineFriend(String username, P2PClientInterface cliente) throws java.rmi.RemoteException;

    // Notificar cliente desconectado
    public void newOfflineFriend(String username, P2PClientInterface cliente) throws java.rmi.RemoteException;
}