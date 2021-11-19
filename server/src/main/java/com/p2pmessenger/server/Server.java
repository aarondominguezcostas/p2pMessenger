package com.p2pmessenger.server;

//imports
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server {
    private static P2PServerImpl server;

    // esta clase se encarga de que en caso de que se finalice la ejecucion se
    // cierre la conexion a la base de datos
    static class DBkiller extends Thread {

        public void run() {
            try {
                System.out.println("Killing db");
                server.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // añadimos el listener para el caso de que se cierre la aplicacion
        Runtime.getRuntime().addShutdownHook(new DBkiller());

        String registryURL;
        try {
            startRegistry(1099);
            server = new P2PServerImpl();
            registryURL = "rmi://localhost:1099/mainServer";
            Naming.rebind(registryURL, server);
            System.out.println("Servidor P2P listo");
        } catch (Exception e) {
            System.out.println("Exception en ejecucion del servidor: " + e.toString());
        }
    }

    // funcion que inicializa el registro RMI
    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();

        } catch (RemoteException e) {
            LocateRegistry.createRegistry(RMIPortNum);
        }
    }
}
