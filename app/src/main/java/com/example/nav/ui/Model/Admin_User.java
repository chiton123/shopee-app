package com.example.nav.ui.Model;

import java.io.Serializable;

public class Admin_User implements Serializable {
    private int iduser;
    private String name;
    private int phonenumber;
    private String address;
    private String email;

    public Admin_User(int iduser, String name, int phonenumber, String address, String email) {
        this.iduser = iduser;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.email = email;
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

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
