package com.example.elpaseov4.network;

import com.example.elpaseov4.pojoModel.CartProductPojo;
import com.example.elpaseov4.pojoModel.GeneralPojo;
import com.example.elpaseov4.pojoModel.NodeDatePojo;
import com.example.elpaseov4.pojoModel.UserPojo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public class CartPost implements Serializable {
    @JsonSerialize
    private CartProductPojo cartProducts;
    @JsonSerialize
    private NodeDatePojo nodeDate;
    @JsonSerialize
    private String observation;
    @JsonSerialize
    private UserPojo user;
    @JsonSerialize
    private GeneralPojo general;

    public CartProductPojo getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(CartProductPojo cartProducts) {
        this.cartProducts = cartProducts;
    }

    public NodeDatePojo getNodeDate() {
        return nodeDate;
    }

    public void setNodeDate(NodeDatePojo nodeDate) {
        this.nodeDate = nodeDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public UserPojo getUser() {
        return user;
    }

    public void setUser(UserPojo user) {
        this.user = user;
    }

    public GeneralPojo getGeneral() {
        return general;
    }

    public void setGeneral(GeneralPojo general) {
        this.general = general;
    }
}
