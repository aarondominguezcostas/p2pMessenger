package com.p2pmessenger.server;

import com.p2pmessenger.client.P2PClientInterface;
import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface P2PServerInterface extends Remote {

    // metodo para iniciar sesion en la aplicacion
    public UUID login(P2PClientInterface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para registrarse na aplicación
    public UUID signin(P2PClientInterface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para salir da aplicación
    public void logout(UUID clienteId, String id) throws java.rmi.RemoteException;

    // Método para enviar una solicitud de amistad
    void solicitarAmistad(String idDestinatario, UUID clienteId, String idCliente)
            throws java.rmi.RemoteException;

    // Método para actualizar unha solicitud de amistad no server
    public void aceptarSolicitud(String idAceptante, UUID clienteId, String idAceptado)
            throws java.rmi.RemoteException;

    // Método para obtener todas las solicitudes de amistad pendientes
    public ArrayList<String> getSolicitudesPendientes(UUID clientUuid, String idCliente)
            throws java.rmi.RemoteException;

    // metodo para obtener todos los amigos en linea
    public HashMap<String, P2PClientInterface> getAmigosOnline(UUID clientUuid, String idCliente)
            throws java.rmi.RemoteException;

    //metodo para comprobar que alguien esta online y su interfaz es correcta
    public boolean isOnlineAndCorrect(P2PClientInterface client, String idCliente) throws java.rmi.RemoteException;

}