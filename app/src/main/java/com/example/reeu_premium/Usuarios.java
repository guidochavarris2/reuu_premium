package com.example.reeu_premium;

public class Usuarios {
    String id, codigo, categoria, id_evento, fecha, hora, ubicacion, imagen;

    public Usuarios() {
    }

    public Usuarios(String id, String codigo, String categoria, String id_evento, String fecha, String hora, String ubicacion, String imagen) {
        this.id = id;
        this.codigo = codigo;
        this.categoria = categoria;
        this.id_evento = id_evento;
        this.fecha = fecha;
        this.hora = hora;
        this.ubicacion = ubicacion;
        this.imagen = imagen;




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

    public String getid_evento() {
        return id_evento;
    }

    public void setid_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }

    public String gethora() {
        return hora;
    }

    public void sethora(String hora) {
        this.hora = hora;
    }

    public String getubicacion() {
        return ubicacion;
    }

    public void setubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getimagen() {
        return imagen;
    }

    public void setimagen(String imagen) {
        this.imagen = imagen;
    }




}