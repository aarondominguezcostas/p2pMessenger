package com.p2pmessenger.client;
import java.rmi.*;
import com.p2pmessenger.server.P2PServerInterface;
import com.p2pmessenger.gui.Log_in;
public class Client {
    
    public static void main(String[] args) {
        try{
            String portNum="1099";
            String registryURL = "rmi://localhost:" + portNum + "/mainServer";
            //Busco servidor
            P2PServerInterface s =(P2PServerInterface)Naming.lookup(registryURL);
            System.out.println("Lookup completed " );
            //Creo clientecallback
            P2PClientInterface callbackObj= new P2PClientImpl();
            
            Log_in vlogin=new Log_in(null,s,(P2PClientImpl)callbackObj);
            vlogin.setVisible(true);



        }
        catch(Exception e){
            System.out.println("Error en callback client");
        }
    }
}
