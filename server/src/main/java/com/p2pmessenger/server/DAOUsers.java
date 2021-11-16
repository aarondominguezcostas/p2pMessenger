package com.p2pmessenger.server;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import org.bson.*;

public class DAOUsers {
    private MongoCollection collection;
    public DAOUsers() {
            //Conexion a la base de datos
            ConnectionString connectionString = new ConnectionString("mongodb+srv://dbadmin:rZ9vnPsmch3T7z7R@p2puserdatabase.lzdps.mongodb.net/p2pdb?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("p2pdb");
            collection = database.getCollection("users");

            System.out.println("Conectado a base de datos: " + collection.countDocuments());        
            mongoClient.close();
    }

    public UserModel getUserByUsername(String username) {
        Document userDoc = (Document) collection.find(new Document("username", username)).first();
        return new UserModel(userDoc.getString("username"), userDoc.getString("password"));
    }
}
