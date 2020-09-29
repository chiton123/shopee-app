package com.example.nav.ui.Model;

public class User {
    private int iduser;
    private String name;

    public User(int iduser, String name) {
        this.iduser = iduser;
        this.name = name;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
