package com.p2pmessenger.server;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.model.UpdateOptions;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DAOUsers {
    private MongoCollection<Document> collection;
    private MongoClient mongoClient;

    public DAOUsers() {
        // Conexion a la base de datos
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://dbadmin:rZ9vnPsmch3T7z7R@p2puserdatabase.lzdps.mongodb.net/p2pdb?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("p2pdb");
        collection = database.getCollection("users");

        System.out.println("Conectado a base de datos: " + collection.countDocuments());
    }

    public UserModel getUserByUsername(String username) {
        Document userDoc = (Document) collection.find(new Document("username", username)).first();
        UserModel user = new UserModel(userDoc.getString("username"), userDoc.getString("password"));
        user.setFriends((ArrayList<String>) (userDoc.get("friendList")));
        user.setPendingFriends((ArrayList<String>) (userDoc.get("pendingFriend")));
        return user;
    }

    public void addUser(UserModel user) {
        Document userDoc = user.toDocument();
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

        // todo: check if user1 is already in user2 pendingFriends or in friends list
        // check that both exist
        Bson filter = (eq("username", username2));
        Bson updateOperation = push("pendingFriend", username1);
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(filter, updateOperation, options);
    }

    // accept friend request: user2 accepts user1 -> add user1 to user2 firends list
    // & viceversa
    public void acceptFriendRequest(String username1, String username2) {

        Bson filter = eq("username", username2);
        Bson update1 = pull("pendingFriend", username1);
        collection.findOneAndUpdate(filter, update1);

        Bson updateOperation = push("friendList", username1);
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(filter, updateOperation, options);

        Bson filter2 = eq("username", username1);
        Bson updateOperation2 = push("friendList", username2);
        collection.updateOne(filter2, updateOperation2, options);

    }

    // close database connection
    public void closeDb() {
        this.mongoClient.close();
    }
}
