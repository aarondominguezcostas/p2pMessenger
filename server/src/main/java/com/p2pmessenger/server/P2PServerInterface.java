package com.p2pmessenger.server;

import com.p2pmessenger.client.Client_Interface;
import java.rmi.*;

public interface P2PServerInterface extends Remote {

    public boolean login(Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para registrarse na aplicación
    public void signin(Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para salir da aplicación
    public void logout(Client_Interface cliente, String id) throws java.rmi.RemoteException;

    // Método para enviar una solicitud de amistad
    void solicitarAmistad(String idDestinatario, Client_Interface cliente, String idCliente)
            throws java.rmi.RemoteException;

    // Método para actualizar unha solicitud de amistad no server
    public void aceptarSolicitud(String idAceptante, Client_Interface cliente, String idAceptado)
            throws java.rmi.RemoteException;

}