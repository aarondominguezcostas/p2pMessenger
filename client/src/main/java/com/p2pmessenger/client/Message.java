package com.p2pmessenger.client;

import java.util.UUID;
import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable{
    private UUID senderUUID;
    private String message;
    private Timestamp timestamp;
    private String sender;

    // constructor
    public Message(UUID senderUUID, String message, String name) {
        this.sender = name;
        this.senderUUID = senderUUID;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // getters
    public String getMessage() {
        return message;
    }
    
    public String getSender() {
        return sender;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.senderUUID.equals(((Message) obj).senderUUID) && this.timestamp.equals(((Message) obj).timestamp)
                && this.message.equals(((Message) obj).message)) {
            return true;
        }
        return false;
    }

}
