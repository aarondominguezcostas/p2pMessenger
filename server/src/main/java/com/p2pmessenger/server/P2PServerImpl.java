package com.p2pmessenger.server;

import java.rmi.server.*;
import java.rmi.*;
import com.p2pmessenger.client.Client_Interface;
import java.util.HashMap;
import java.util.ArrayList;

public class P2PServerImpl extends UnicastRemoteObject implements P2PServerInterface {

    private DAOUsers daoUsers;
    private HashMap<String, Client_Interface> onlineClientList;
    private ArrayList<UserModel> usersInfo;

    // constructor
    public P2PServerImpl() throws RemoteException {
        try {
            daoUsers = new DAOUsers();
            onlineClientList = new HashMap<>();
            usersInfo = new ArrayList<>();
            this.daoUsers.addFriendPetition("admin", "admin2");
        } catch (Exception e) {
            System.out.println("Error al inicializar el servidor" + e.toString());
        }
    }

    /// METODOS INTERFAZ REMOTA ///

    @Override
    public synchronized boolean login(Client_Interface cliente, String id, String password) throws RemoteException {
        try {
            UserModel user = this.daoUsers.getUserByUsername(id);
            if (user.getPassword().equals(password)) {
                this.updateOnlineList(id, cliente);
                this.usersInfo.add(daoUsers.getUserByUsername(id));
                this.onlineClientList.put(id, cliente);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al iniciar sesion: " + e.toString());
            return false;
        }
    }

    @Override
    public synchronized boolean signin(Client_Interface cliente, String id, String contraseña) throws RemoteException {
        try {
            UserModel user = this.daoUsers.getUserByUsername(id);
            if (user == null) {
                this.daoUsers.addUser(new UserModel(id, contraseña));
                this.usersInfo.add(daoUsers.getUserByUsername(id));
                this.onlineClientList.put(id, cliente);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.toString());
            return false;
        }
    }

    @Override
    public synchronized void solicitarAmistad(String idDestinatario, Client_Interface cliente, String idCliente)
            throws RemoteException {
        try {
            if (this.onlineClientList.get(idCliente).equals(cliente)) {
                this.daoUsers.addFriendPetition(idCliente, idDestinatario);

                // actualizar informacion de los usuarios
                this.updateUserInfo(idCliente);
                this.updateUserInfo(idDestinatario);

            }
        } catch (Exception e) {
            System.out.println("Error al solicitar amistad: " + e.toString());
        }
    }

    @Override
    public synchronized void aceptarSolicitud(String idAceptante, Client_Interface cliente, String idAceptado)
            throws RemoteException {
        try {
            if (this.onlineClientList.get(idAceptante).equals(cliente)) {
                this.daoUsers.acceptFriendRequest(idAceptante, idAceptado);

                // actualizar informacion de los usuarios
                this.updateUserInfo(idAceptante);
                this.updateUserInfo(idAceptado);

            }
        } catch (Exception e) {
            System.out.println("Error al aceptar solicitud: " + e.toString());
        }

    }

    @Override
    public synchronized void logout(Client_Interface cliente, String id) throws RemoteException {
        try {
            this.onlineClientList.remove(id);
            this.usersInfo.remove(daoUsers.getUserByUsername(id));
            this.updateOfflineList(id, cliente);
        } catch (Exception e) {
            System.out.println("Error al cerrar sesion: " + e.toString());
        }
    }

    @Override
    public synchronized ArrayList<String> getSolicitudesPendientes(Client_Interface cliente, String idCliente)
            throws RemoteException {
        if (this.onlineClientList.get(idCliente).equals(cliente)) {
            for (UserModel user : this.usersInfo) {
                if (user.getUsername().equals(idCliente)) {
                    return user.getPendingFriends();
                }
            }
        }
        return null;
    }

    @Override
    public synchronized HashMap<String, Client_Interface> getAmigosOnline(Client_Interface cliente, String id)
            throws RemoteException {
        UserModel user = this.daoUsers.getUserByUsername(id);
        HashMap<String, Client_Interface> amigosOnline = new HashMap<>();
        for (String onlineClient : this.onlineClientList.keySet()) {
            if (user.getFriends().contains(onlineClient)) {
                amigosOnline.put(onlineClient, this.onlineClientList.get(onlineClient));
            }
        }
        return amigosOnline;
    }

    /// METODOS AUXILIARES ///

    // cierra la conexion a la base de datos
    public void shutdown() {
        this.daoUsers.closeDb();
    }

    // itera por todos los clientes, si el nuevo cliente es amigo de alguno de
    // ellos, lo avisa de que se ha conectado
    private void updateOnlineList(String username, Client_Interface cliente) {
        try {
            for (UserModel user : this.usersInfo) {
                if (user.getFriends().contains(username)) {
                    this.onlineClientList.get(user.getUsername()).newOnlineFriend(username, cliente);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la lista de usuarios online: " + e.toString());
        }
    }

    // itera por todos los clientes cuando un cliente se desconecta y avisa a todos
    // sus amigos de que se ha desconectado
    private void updateOfflineList(String username, Client_Interface client) {
        try {
            for (UserModel user : this.usersInfo) {
                if (user.getFriends().contains(username)) {
                    this.onlineClientList.get(user.getUsername()).newOfflineFriend(username, client);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la lista de usuarios online: " + e.toString());
        }
    }

    // actializa internamente la informacion de los usuarios online
    private void updateUserInfo(String username) {
        for (UserModel user : this.usersInfo) {
            if (user.getUsername().equals(username)) {
                user = this.daoUsers.getUserByUsername(username);
                break;
            }
        }
    }
}
