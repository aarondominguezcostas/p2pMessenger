package com.p2pmessenger.server;

import java.util.ArrayList;
import org.bson.Document;

public class UserModel {
    private String username;
    private String password;
    private ArrayList<String> friends;
    private ArrayList<String> pendingFriends;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<String>();
        this.pendingFriends = new ArrayList<String>();
    }

    public void setFriends(ArrayList<String> friends) {
        for (String friend : friends) {
            this.friends.add(friend);
        }
    }

    public void setPendingFriends(ArrayList<String> pendingFriends) {
        for (String pendingFriend : pendingFriends) {
            this.pendingFriends.add(pendingFriend);
        }
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
