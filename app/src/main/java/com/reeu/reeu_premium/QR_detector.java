package com.reeu.reeu_premium;

public class QR_detector {
    int id_usuario;
    String  clave;

    public QR_detector(int id_usuario, String clave) {

        this.id_usuario = id_usuario;
        this.clave = clave;
    }
    public int getid_usuario() {
        return id_usuario;
    }

    public void setid_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getclave() {
        return clave;
    }

    public void setclave(String clave) {
        this.clave = clave;
    }
}
