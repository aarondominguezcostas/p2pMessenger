package com.p2pmessenger.server;

import java.rmi.*;

public interface P2PServerInterface extends Remote{
    
    public void login (Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;
    //Método para registrarse na aplicación
    public void signin (Client_Interface cliente, String id, String contraseña) throws java.rmi.RemoteException;
    //Método para enviar una solicitud de amistad
    /*Duda, aquí envío toda a info do cliente solicitante para checkear
    que non e outra persoa quen envía a petición o servidor xa que coa contraseña
    verifico que o solicitante é quen dice ser. Non sei se será necesario enviar a interface
    cliente, xa que poderíase buscar solo por id*/
    public void solicitarAmistad(String idDestinatario,Client_Interface cliente, String id, String contraseña)throws java.rmi.RemoteException;
    public void aceptarSolicitud(String idAceptante, String idSolicitante) throws java.rmi.RemoteException;
}