package com.reeu.reeu_premium;

public class User {
    int id;
    String name, apellidos, email, dni, gender;

    public User(int id, String name, String apellidos, String email, String dni, String gender) {
        this.id = id;
        this.email = email;
        this.dni = dni;
        this.gender = gender;
        this.name = name;
        this.apellidos = apellidos;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
