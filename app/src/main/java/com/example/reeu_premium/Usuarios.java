package com.example.reeu_premium;

public class Usuarios {
    String id, codigo, categoria;

    public Usuarios() {
    }

    public Usuarios(String id, String codigo, String categoria) {
        this.id = id;
        this.codigo = codigo;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}