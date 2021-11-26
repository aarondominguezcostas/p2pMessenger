package com.p2pmessenger.client;

public class Usuario {
    
    private String nick;
    private String contraseña;
    private P2PClientInterface client;

    public Usuario(String nick, String contraseña, P2PClientInterface client) {
        this.nick = nick;
        this.contraseña = contraseña;
        this.client = client;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public P2PClientInterface getClient() {
        return client;
    }
    public void setClient(P2PClientInterface client) {
        this.client = client;
    }
}