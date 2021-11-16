package com.p2pmessenger.server;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import java.rmi.server.*;
import java.rmi.*;

public class P2PServerImpl extends UnicastRemoteObject implements P2PServerInterface {

    // constructor
    public P2PServerImpl() throws RemoteException {
        try {

            //Conexion a la base de datos
            ConnectionString connectionString = new ConnectionString("mongodb+srv://dbadmin:rZ9vnPsmch3T7z7R@p2puserdatabase.lzdps.mongodb.net/p2pdb?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("p2pdb");

            System.out.println("Conectado a base de datos: " + database.getCollection("users").countDocuments());

            mongoClient.close();

        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos " + e.toString());
        }
    }
}
