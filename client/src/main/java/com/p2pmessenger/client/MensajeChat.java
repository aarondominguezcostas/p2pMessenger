package com.p2pmessenger.client;

import java.util.UUID;
import java.sql.Timestamp;

public class MensajeChat {
    private String username;
    private String message;
    private Timestamp timestamp;

    
    public MensajeChat(String username, String message, Timestamp timestamp) {
        this.username = username;
        this.message = message;
        this.timestamp =  new Timestamp(System.currentTimeMillis());
    }


    // getters
    public String getMessage() {
        return message;
    }


    public String getUsername() {
        return username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
