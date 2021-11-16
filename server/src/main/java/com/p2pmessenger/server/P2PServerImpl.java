package com.p2pmessenger.server;

import java.rmi.server.*;
import java.rmi.*;
import com.p2pmessenger.client.Client_Interface;
import java.util.ArrayList;

public class P2PServerImpl extends UnicastRemoteObject {

    private DAOUsers daoUsers;
    private ArrayList<Client_Interface> onlineClientList;

    // constructor
    public P2PServerImpl() throws RemoteException {
        try {
            daoUsers = new DAOUsers();
            onlineClientList = new ArrayList<>();

        } catch (Exception e) {
            System.out.println("Error al inicializar el servidor" + e.toString());
        }
    }


    
}
