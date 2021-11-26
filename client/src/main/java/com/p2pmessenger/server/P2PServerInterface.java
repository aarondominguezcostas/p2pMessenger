package com.p2pmessenger.server;

import com.p2pmessenger.client.P2PClientInterface;
import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface P2PServerInterface extends Remote {

    // metodo para iniciar sesion en la aplicacion
    public boolean login(P2PClientInterface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para registrarse na aplicación
    public boolean signin(P2PClientInterface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para salir da aplicación
    public void logout(P2PClientInterface cliente, String id) throws java.rmi.RemoteException;

    // Método para enviar una solicitud de amistad
    void solicitarAmistad(String idDestinatario, P2PClientInterface cliente, String idCliente)
            throws java.rmi.RemoteException;

    // Método para actualizar unha solicitud de amistad no server
    public void aceptarSolicitud(String idAceptante, P2PClientInterface cliente, String idAceptado)
            throws java.rmi.RemoteException;

    // Método para obtener todas las solicitudes de amistad pendientes
    public ArrayList<String> getSolicitudesPendientes(P2PClientInterface cliente, String idCliente)
            throws java.rmi.RemoteException;

    // metodo para obtener todos los amigos en linea
    public HashMap<String, P2PClientInterface> getAmigosOnline(P2PClientInterface cliente, String idCliente)
            throws java.rmi.RemoteException;

}