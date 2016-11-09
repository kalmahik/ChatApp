package com.kalmahik.firstchat.entities;

public class AuthPayload {
    private String name;
    private String hash;

    public AuthPayload(String name, String hash) {
        this.name = name;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}