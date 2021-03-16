package com.example.elpaseov4.network;

import com.example.elpaseov4.pojoModel.CartProductPojo;
import com.example.elpaseov4.pojoModel.GeneralPojo;
import com.example.elpaseov4.pojoModel.NodeDatePojo;
import com.example.elpaseov4.pojoModel.NodePojo;

import java.io.Serializable;

public class CartPostResponse implements Serializable {
    private String id;
    private GeneralPojo general;
    private NodeDatePojo nodeDate;
    private CartProductPojo cartProducts;
    private String total;
    private String observation;
    private String saleDate;
    private String posibleDeliveryDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeneralPojo getGeneral() {
        return general;
    }

    public void setGeneral(GeneralPojo general) {
        this.general = general;
    }

    public NodeDatePojo getNodeDate() {
        return nodeDate;
    }

    public void setNodeDate(NodeDatePojo nodeDate) {
        this.nodeDate = nodeDate;
    }

    public CartProductPojo getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(CartProductPojo cartProducts) {
        this.cartProducts = cartProducts;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getPosibleDeliveryDate() {
        return posibleDeliveryDate;
    }

    public void setPosibleDeliveryDate(String posibleDeliveryDate) {
        this.posibleDeliveryDate = posibleDeliveryDate;
    }
}
