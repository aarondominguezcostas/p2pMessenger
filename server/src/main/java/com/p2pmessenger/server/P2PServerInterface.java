package com.p2pmessenger.server;

import com.p2pmessenger.client.Client_Interface;
import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface P2PServerInterface extends Remote {

    //metodo para iniciar sesion en la aplicacion
    public boolean login(Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para registrarse na aplicación
    public boolean signin(Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para salir da aplicación
    public void logout(Client_Interface cliente, String id) throws java.rmi.RemoteException;

    // Método para enviar una solicitud de amistad
    void solicitarAmistad(String idDestinatario, Client_Interface cliente, String idCliente)
            throws java.rmi.RemoteException;

    // Método para actualizar unha solicitud de amistad no server
    public void aceptarSolicitud(String idAceptante, Client_Interface cliente, String idAceptado)
            throws java.rmi.RemoteException;

    // Método para obtener todas las solicitudes de amistad pendientes
    public ArrayList<String> getSolicitudesPendientes(Client_Interface cliente, String idCliente) throws java.rmi.RemoteException;

    //metodo para obtener todos los amigos en linea
    public HashMap<String, Client_Interface> getAmigosOnline(Client_Interface cliente, String idCliente) throws java.rmi.RemoteException;

}