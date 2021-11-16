package com.p2pmessenger.server;

//imports
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.*;

public class Server {

    public static void main(String[] args) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum, registryURL;
        try {
            System.out.println("Introduce el puerto del registro RMI");
            portNum = (br.readLine()).trim();
            int RMIPortNum = Integer.parseInt(portNum);
            startRegistry(RMIPortNum);
            P2PServerImpl p2pServer = new P2PServerImpl();
            registryURL = "rmi://localhost:" + portNum + "/mainServer";
            Naming.rebind(registryURL, p2pServer);
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
