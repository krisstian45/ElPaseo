package com.example.elpaseov4.model;

import java.io.Serializable;

public class UserLogin implements Serializable {
    private String value;
    private User user;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
