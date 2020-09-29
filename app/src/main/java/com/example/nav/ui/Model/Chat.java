package com.example.nav.ui.Model;

public class Chat {
    private int sender;
    private int reciever;
    private String message;

    public Chat(int sender, int reciever, String message) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReciever() {
        return reciever;
    }

    public void setReciever(int reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
