package com.p2pmessenger.client;

import java.sql.Timestamp;

public class MensajeChat {
    private String username;
    private String mensaje;
    private Timestamp timestamp;

    
    public MensajeChat(String username, String message, Timestamp timestamp) {
        this.username = username;
        this.mensaje = message;
        this.timestamp =  new Timestamp(System.currentTimeMillis());
    }


    // getters
    public String getMensaje() {
        return mensaje;
    }


    public String getUsername() {
        return username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
