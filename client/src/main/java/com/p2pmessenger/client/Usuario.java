package com.p2pmessenger.client;

import com.p2pmessenger.server.P2PServerInterface;
import java.util.UUID;

public class Usuario {
    
    private P2PClientInterface client;
    private P2PServerInterface server;
    private UUID uuid;

    public Usuario(P2PClientInterface client) {
        this.client = client;
    }
    
    public P2PClientInterface getClient() {
        return client;
    }
    public void setClient(P2PClientInterface client) {
        this.client = client;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public P2PServerInterface getServer() {
        return server;
    }

    public void setServer(P2PServerInterface server) {
        this.server = server;
    }
}