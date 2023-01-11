package com.reeu.reeu_premium;

public class Productos {
    private int id;
    private String titulo;
    private String precio;
    private String imagen;

    public Productos(int id, String titulo, String categoria, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.precio = categoria;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

