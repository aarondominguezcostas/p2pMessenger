package com.p2pmessenger.server;

import com.p2pmessenger.client.Client_Interface;
import java.rmi.*;

public interface P2PServerInterface extends Remote {

    public boolean login(Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para registrarse na aplicación
    public void signin(Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;

    // Método para enviar una solicitud de amistad
    /*
     * Duda, aquí envío toda a info do cliente solicitante para checkear que non e
     * outra persoa quen envía a petición o servidor xa que coa contraseña verifico
     * que o solicitante é quen dice ser. Non sei se será necesario enviar a
     * interface cliente, xa que poderíase buscar solo por id
     */
    void solicitarAmistad(String idDestinatario, Client_Interface cliente) throws java.rmi.RemoteException;

    // Método para actualizar unha solicitud de amistad no server
    /*
     * 2 alternativas ao aceptar, ou actualizamos directamente dende o cliente a súa
     * lista de amigos e logo actualizala no server, ou actualizala no server e que
     * o server se encargue de actualizala no cliente
     */
    public void aceptarSolicitud(String idAceptante, String idSolicitante) throws java.rmi.RemoteException;

}