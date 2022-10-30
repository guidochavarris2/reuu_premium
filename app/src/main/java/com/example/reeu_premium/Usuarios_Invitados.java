package com.example.reeu_premium;

public class Usuarios_Invitados {

    String  username, apellidos;

    public Usuarios_Invitados( String username, String apellidos) {

        this.username = username;
        this.apellidos = apellidos;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
