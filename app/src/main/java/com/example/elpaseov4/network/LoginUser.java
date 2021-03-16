package com.example.elpaseov4.network;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public class LoginUser implements Serializable {
    @JsonSerialize
    private  String userName;
    @JsonSerialize
    private  String userPassword;


    public LoginUser(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
