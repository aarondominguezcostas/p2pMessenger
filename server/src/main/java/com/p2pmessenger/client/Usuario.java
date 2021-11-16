package com.p2pmessenger.client;

public class Usuario {
    
    private String nick;
    private String contraseña;
    private Client_Interface client;

    public Usuario(String nick, String contraseña, Client_Interface client) {
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
    public Client_Interface getClient() {
        return client;
    }
    public void setClient(Client_Interface client) {
        this.client = client;
    }
}