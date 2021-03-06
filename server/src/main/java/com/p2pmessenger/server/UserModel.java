package com.p2pmessenger.server;

import java.util.ArrayList;
import org.bson.Document;
import java.util.UUID;


public class UserModel {
    private String username;
    private String password;
    private UUID uniqueId;
    private ArrayList<String> friends;
    private ArrayList<String> pendingFriends;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<String>();
        this.pendingFriends = new ArrayList<String>();
    }

    public void setUuid(UUID uuid) {
        this.uniqueId=uuid;
    }

    public UUID getUuid(){
        return this.uniqueId;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void setPendingFriends(ArrayList<String> pendingFriends) {
        this.pendingFriends = pendingFriends;
    }

    public Document toDocument() {
        Document usr = new Document();
        usr.append("username", this.username);
        usr.append("password", this.password);
        return usr;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getPendingFriends() {
        return pendingFriends;
    }

    // toString method
    @Override
    public String toString() {
        return "UserModel{" + "username=" + username + ", password=" + password + ", friends=" + friends
                + ", pendingFriends=" + pendingFriends + '}';
    }

}
