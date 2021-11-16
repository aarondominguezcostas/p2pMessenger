package com.p2pmessenger.client;
import java.io.*;
import java.rmi.*;
import com.p2pmessenger.server.P2PServerInterface;

public class Client {
    
    public static void main(String[] args) {
        try{
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);

            //Pido localización servidor
            int RMIPort;         
            String hostName;
            System.out.println("Enter the RMIRegistry host namer:");
            hostName = br.readLine();
            System.out.println("Enter the RMIregistry port number:");
            String portNum = br.readLine();
            RMIPort = Integer.parseInt(portNum); 
            String registryURL = "rmi://localhost:" + portNum + "/callback";
            //Busco servidor
            P2PServerInterface s =(P2PServerInterface)Naming.lookup(registryURL);
            System.out.println("Lookup completed " );
            //Creo clientecallback
            Client_Interface callbackObj= new Client_Impl();
            


            //ELEGIR Iniciar sesión o Registrarse
            String nick,contr;
            System.out.println("(a)Iniciar Sesión\n(b)Registrarse");
            String op=br.readLine();
            System.out.println("Introduzca el nombre de usuario:");
            nick=br.readLine();
            System.out.println("Introduzca la contraseña:");
            contr=br.readLine();
            Usuario u=new Usuario(nick,contr,callbackObj);
            if(op.equals("a")){
                //Registro conexión cliente
                //s.registrarConexion(callbackObj);

            }
            else if (op.equals("b")){
                //Registro novo usuario
                //s.registrarUsuario(callbackObj);

            }
            else{
                System.out.println("Opción no válida");
            }

            //RECIBIR PETICIÓNS DE AMISTAD PENDIENTES

            //MENU
            //enviar mensaje
            //enviar solicitud de amistad
            //desconectarse
            //borrar cuenta
        }
        catch(Exception e){
            System.out.println("Error en callback client");
        }
    }
    public static void enviarMensaje(String mensaje,Usuario u){
        try {
            u.getClient().recibirMensaje(mensaje);
        } catch (RemoteException e) {
            System.out.println("Error enviando mensaje");
        }

    }
    public static void enviarPeticionAmistad(String id){
        //código depende si vai directo ao outro cliente ou pasa polo servidor
    }
    public static void desconectarse(Usuario u){
        //chamar ao servidor para desconectarse e pasarlle o meu usuario
    }
}
