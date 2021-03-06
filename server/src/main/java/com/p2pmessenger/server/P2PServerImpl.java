package com.p2pmessenger.server;

import java.rmi.server.*;
import java.rmi.*;
import com.p2pmessenger.client.P2PClientInterface;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

public class P2PServerImpl extends UnicastRemoteObject implements P2PServerInterface {

    private DAOUsers daoUsers;
    private HashMap<String, P2PClientInterface> onlineClientList;
    private ArrayList<UserModel> usersInfo;

    // constructor
    public P2PServerImpl() throws RemoteException {
        try {
            daoUsers = new DAOUsers();
            onlineClientList = new HashMap<>();
            usersInfo = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al inicializar el servidor" + e.toString());
        }
    }

    /// METODOS INTERFAZ REMOTA ///

    @Override
    public synchronized UUID login(P2PClientInterface cliente, String id, String password) throws RemoteException {
        try {

            // verificar si el usuario ya esta online
            if (this.onlineClientList != null && this.onlineClientList.containsKey(id)) {
                return null;
            }

            UserModel user = this.daoUsers.getUserByUsername(id);
            if (user != null && user.getPassword().equals(password)) {
                UUID userUUID = UUID.randomUUID();
                user.setUuid(userUUID);
                this.usersInfo.add(user);
                this.onlineClientList.put(id, cliente);

                // aviso a los amigos
                this.updateOnlineList(id, cliente);

                return userUUID;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al iniciar sesion: " + e.toString());
            return null;
        }
    }

    @Override
    public synchronized UUID signin(P2PClientInterface cliente, String id, String contraseña)
            throws RemoteException {
        try {
            UserModel user = this.daoUsers.getUserByUsername(id);

            if (cliente == null) {
                System.out.println("Error en la interfaz");
            }

            if (user == null) {
                UUID userUUID = UUID.randomUUID();
                user = new UserModel(id, contraseña);
                user.setUuid(userUUID);
                this.daoUsers.addUser(user);
                this.usersInfo.add(user);
                this.onlineClientList.put(id, cliente);
                return userUUID;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.toString());
            return null;
        }
    }

    @Override
    public synchronized void solicitarAmistad(String idDestinatario, UUID clienteId, String idCliente)
            throws RemoteException {
        try {
            UserModel client = null;

            if(idDestinatario == idCliente) {
                return;
            }

            for (UserModel user : this.usersInfo) {
                if (user.getUuid().equals(clienteId)) {
                    client = user;
                    this.daoUsers.addFriendPetition(idCliente, idDestinatario);
                    break;
                }
            }

            if (client != null) {
                this.updateUserInfo(client.getUsername());

                if(this.onlineClientList.containsKey(idDestinatario)){
                    this.updateUserInfo(idDestinatario);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al solicitar amistad: " + e.toString());
        }
    }

    @Override
    public synchronized void aceptarSolicitud(String idAceptante, UUID clienteId, String idAceptado)
            throws RemoteException {
        try {

            UserModel client = null;

            for (UserModel user : this.usersInfo) {
                if (user.getUuid().equals(clienteId)) {
                    client = user;
                    this.daoUsers.acceptFriendRequest(idAceptado, idAceptante);
                    break;
                }
            }

            if (client != null) {
                this.updateUserInfo(client.getUsername());

                if(this.onlineClientList.containsKey(idAceptado)){
                    this.updateUserInfo(idAceptado);

                    //si los dos estan en linea, actualizo sus estados en linea
                    this.onlineClientList.get(idAceptado).newOnlineFriend(idAceptante, this.onlineClientList.get(idAceptante));
                    this.onlineClientList.get(idAceptante).newOnlineFriend(idAceptado, this.onlineClientList.get(idAceptado));

                }
            }

        } catch (Exception e) {
            System.out.println("Error al aceptar solicitud: " + e.toString());
        }

    }

    @Override
    public synchronized void rechazarSolicitud(String idRechazante, UUID clienteId, String idRechazado)
            throws RemoteException {
        try {

            UserModel client = null;

            for (UserModel user : this.usersInfo) {
                if (user.getUuid().equals(clienteId)) {
                    client = user;
                    this.daoUsers.rejectFriendRequest(idRechazado, idRechazante);
                    break;
                }
            }

            if (client != null) {
                this.updateUserInfo(client.getUsername());
            }

        } catch (Exception e) {
            System.out.println("Error al aceptar solicitud: " + e.toString());
        }

    }

    @Override
    public synchronized void logout(UUID clienteId, String id) throws RemoteException {
        try {

            UserModel client = null;
            P2PClientInterface cliente = null;
            for (UserModel user : this.usersInfo) {
                if (user.getUuid().equals(clienteId)) {
                    client = user;
                    cliente = this.onlineClientList.get(id);
                    this.onlineClientList.remove(id);
                    break;
                }
            }

            if (client != null) {
                this.usersInfo.remove(client);
            }

            this.updateOfflineList(id, cliente);

        } catch (Exception e) {
            System.out.println("Error al cerrar sesion: " + e.toString());
        }
    }

    @Override
    public synchronized ArrayList<String> getSolicitudesPendientes(UUID clientUuid, String idCliente)
            throws RemoteException {

        for (UserModel user : this.usersInfo) {
            if (user.getUuid().equals(clientUuid)) {
                return user.getPendingFriends();
            }
        }

        return null;
    }

    @Override
    public synchronized HashMap<String, P2PClientInterface> getAmigosOnline(UUID clienteUuid, String id)
            throws RemoteException {

        HashMap<String, P2PClientInterface> amigosOnline = new HashMap<>();

        for (UserModel user : this.usersInfo) {
            if (user.getUuid().equals(clienteUuid)) {
                for (String onlineClient : this.onlineClientList.keySet()) {
                    if (user.getFriends().contains(onlineClient)) {
                        amigosOnline.put(onlineClient, this.onlineClientList.get(onlineClient));
                    }
                }
                break;
            }
        }

        return amigosOnline;
    }

    @Override
    public boolean isOnlineAndCorrect(P2PClientInterface client, String idCliente) throws RemoteException {
        return this.onlineClientList.get(idCliente).equals(client);
    }

    // Método para obtener todas las solicitudes de amistad pendientes
    @Override
    public ArrayList<String> getFriends(UUID clientUuid, String idCliente)
            throws java.rmi.RemoteException {

        for (UserModel user : this.usersInfo) {
            if (user.getUuid().equals(clientUuid)) {
                return user.getFriends();
            }
        }
        return null;
    }

    /// METODOS AUXILIARES ///

    // cierra la conexion a la base de datos
    public void shutdown() {
        this.daoUsers.closeDb();
    }

    // itera por todos los clientes, si el nuevo cliente es amigo de alguno de
    // ellos, lo avisa de que se ha conectado
    private void updateOnlineList(String username, P2PClientInterface cliente) {
        try {
            for (UserModel user : this.usersInfo) {
                if (user.getFriends().contains(username)) {
                    P2PClientInterface clientIf = this.onlineClientList.get(user.getUsername());
                    clientIf.newOnlineFriend(username, cliente);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la lista de usuarios online: " + e.toString());
        }
    }

    // itera por todos los clientes cuando un cliente se desconecta y avisa a todos
    // sus amigos de que se ha desconectado
    private void updateOfflineList(String username, P2PClientInterface client) {
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
                UserModel userWithNewInfo = this.daoUsers.getUserByUsername(username);
                user.setFriends(userWithNewInfo.getFriends());
                user.setPendingFriends(userWithNewInfo.getPendingFriends());
                System.out.println("updated " + username);
                break;
            }
        }
    }
}