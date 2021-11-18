package com.p2pmessenger.server;

import java.rmi.server.*;
import java.rmi.*;
import com.p2pmessenger.client.Client_Interface;
import java.util.HashMap;

public class P2PServerImpl extends UnicastRemoteObject implements P2PServerInterface {

    private DAOUsers daoUsers;
    private HashMap<String, Client_Interface> onlineClientList;

    // constructor
    public P2PServerImpl() throws RemoteException {
        try {
            daoUsers = new DAOUsers();
            onlineClientList = new HashMap<>();

        } catch (Exception e) {
            System.out.println("Error al inicializar el servidor" + e.toString());
        }
    }

    @Override
    public boolean login(Client_Interface cliente, String id, String password) throws RemoteException {
        try{
            boolean isValid = this.daoUsers.passwordCorrect(id, password);
            if(isValid) {
                this.onlineClientList.put(id, cliente);
            }
            return isValid;
        }catch(Exception e){
            System.out.println("Error al iniciar sesion" + e.toString());
            return false;
        }

    }

    @Override
    public void signin(Client_Interface cliente, String id, String contraseña) throws RemoteException {
        try{
            UserModel user = new UserModel(id, contraseña);
            this.daoUsers.addUser(user);
            this.onlineClientList.put(id, cliente);
        }catch(Exception e){
            System.out.println("Error al registrar usuario" + e.toString());
        }
        
    }

    @Override
    public void solicitarAmistad(String idDestinatario, Client_Interface cliente) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void aceptarSolicitud(String idAceptante, String idSolicitante) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

}
