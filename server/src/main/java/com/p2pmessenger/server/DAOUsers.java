package com.p2pmessenger.server;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import org.bson.*;

public class DAOUsers {
    private MongoCollection<Document> collection;

    public DAOUsers() {
        // Conexion a la base de datos
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://dbadmin:rZ9vnPsmch3T7z7R@p2puserdatabase.lzdps.mongodb.net/p2pdb?retryWrites=true&w=majority");
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

    public void addUser(UserModel user) {
        Document userDoc = new Document().append("username", user.getUsername()).append("password", user.getPassword());
        collection.insertOne(userDoc);
    }

    public boolean userExists(String username) {
        Document userDoc = (Document) collection.find(new Document("username", username)).first();
        return userDoc != null;
    }

    public boolean passwordCorrect(String username, String password) {
        Document userDoc = (Document) collection.find(new Document("username", username)).first();
        return userDoc.getString("password").equals(password);
    }

    // user 1 wants to add user 2 as friend -> add user 1 to user 2 pendingFriends
    // list
    public void addFriendPetition(String username1, String username2) {
        Document userDoc = (Document) collection.find(new Document("username", username2)).first();
    }

    // accept friend request: user2 accepts user1 -> add user1 to user2 firends list
    // & viceversa
    public void acceptFriendRequest(String username1, String username2) {
        Document userDoc = (Document) collection.find(new Document("username", username2)).first();

    }
}
