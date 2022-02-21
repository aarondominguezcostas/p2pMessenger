package com.p2pmessenger.server;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
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
                System.getenv("DB_URL"));
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("p2pdb");
        collection = database.getCollection("users");
    }

    public UserModel getUserByUsername(String username) {

        Document userDoc = (Document) collection.find(new Document("username", username)).first();

        if (userDoc == null) {
            return null;
        }

        UserModel user = new UserModel(userDoc.getString("username"), userDoc.getString("password"));

        if (userDoc.get("friendList") != null) {
            ArrayList<String> friendList = new ArrayList<>(
                Arrays.asList(userDoc.get("friendList").toString().replace("[", "").replace("]", "")
                        .replaceAll("\\s+", "").split(",")));
            if("".equals(friendList.get(0))) {
                friendList.remove(0);
            }
            user.setFriends(friendList);   
        } else {
            user.setFriends(new ArrayList<String>());
        }
        if (userDoc.get("pendingFriend") != null) {
            ArrayList<String> pendingFriend = new ArrayList<String>(Arrays
            .asList(userDoc.get("pendingFriend").toString().replace("[", "").replace("]", "")
                    .replaceAll("\\s+", "").split(",")));
            if("".equals(pendingFriend.get(0))) {
                pendingFriend.remove(0);
            }
            user.setPendingFriends(pendingFriend);
        } else {
            user.setPendingFriends(new ArrayList<String>());
        }
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
        UserModel user1 = getUserByUsername(username1);
        UserModel user2 = getUserByUsername(username2);
        if (user1 != null && user2 != null) {
            if (user2.getPendingFriends().contains(username1)) {
                System.out.println("User " + username2 + " already has a pending friend request from " + username1);
            } else if (user1.getFriends().contains(username2)) {
                System.out.println("User " + username1 + " already has " + username2 + " as friend");
            } else if (user1.getPendingFriends().contains(username2)) {
                this.acceptFriendRequest(username2, username1);
            } else {
                ArrayList<String> friendsList = user2.getPendingFriends();

                for(String friend : friendsList) {
                    if(friend.equals("")) {
                        friendsList.remove(friend);
                    }
                }

                if(friendsList.isEmpty()) {
                    friendsList = new ArrayList<String>();
                }
                friendsList.add(username1);

                collection.updateOne(eq("username", username2),
                        combine(set("pendingFriend", friendsList)),
                        new UpdateOptions().upsert(true));
                System.out.println("User " + username2 + " added " + username1 + " to pendingFriends list");

            }
        }

    }

    // accept friend request: user2 accepts user1 -> add user1 to user2 firends list
    // & viceversa
    public void acceptFriendRequest(String username1, String username2) {
        try {

            UserModel user1 = getUserByUsername(username1);
            UserModel user2 = getUserByUsername(username2);

            if (user1 != null && user2 != null) {
                if (user2.getPendingFriends().contains(username1)) {

                    ArrayList<String> friendsListu2 = user2.getFriends();
                    friendsListu2.add(username1);

                    ArrayList<String> pendingFriendsListu2 = user2.getPendingFriends();
                    pendingFriendsListu2.remove(username1);

                    ArrayList<String> friendsListu1 = user1.getFriends();
                    friendsListu1.add(username2);

                    collection.updateOne(eq("username", username2),
                            combine(set("friendList", friendsListu2),
                                    set("pendingFriend", pendingFriendsListu2)),
                            new UpdateOptions().upsert(true));
                    collection.updateOne(eq("username", username1),
                            combine(set("friendList", friendsListu1)),
                            new UpdateOptions().upsert(true));
                    System.out.println("User " + username1 + " accepted " + username2 + " friend request");
                } else {
                    System.out.println("User " + username1 + " does not have a friend request from " + username2);
                }
            }

        } catch (Exception e) {
            System.out.println("No se pudo aceptar amistad");
        }

    }

    public void rejectFriendRequest(String username1, String username2) {
        try {
            UserModel user1 = getUserByUsername(username1);
            UserModel user2 = getUserByUsername(username2);

            if (user1 != null && user2 != null) {
                if (user2.getPendingFriends().contains(username1)) {

                    ArrayList<String> pendingFriendsListu2 = user2.getPendingFriends();
                    pendingFriendsListu2.remove(username1);

                    collection.updateOne(eq("username", username2),
                            combine(set("pendingFriend", pendingFriendsListu2)),
                            new UpdateOptions().upsert(true));

                    System.out.println("User " + username2 + " rejected " + username1 + " friend request");
                } else {
                    System.out.println("User " + username1 + " does not have a friend request from " + username2);
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo aceptar amistad");
        }
    }

    // close database connection
    public void closeDb() {
        this.mongoClient.close();
    }
}
