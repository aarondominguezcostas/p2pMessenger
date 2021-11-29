package com.p2pmessenger.client;

import java.util.UUID;
import java.sql.Timestamp;
public class Message {
    private UUID senderUUID;
    private String message;
    private Timestamp timestamp;

    //constructor
    public Message(UUID senderUUID, String message) {
        this.senderUUID = senderUUID;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    //getters
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.senderUUID.equals(((Message) obj).senderUUID) && this.timestamp.equals(((Message) obj).timestamp) && this.message.equals(((Message) obj).message)){
            return true;
        }
        return false;
        }

}
