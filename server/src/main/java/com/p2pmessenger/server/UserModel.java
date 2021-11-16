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

    public Document toDocument() {
        Document usr = new Document();
        usr.append("username", this.username);
        usr.append("password", this.password);
        return usr;
    }

}
