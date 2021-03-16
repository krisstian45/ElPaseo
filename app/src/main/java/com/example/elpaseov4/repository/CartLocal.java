package com.example.elpaseov4.repository;

import com.example.elpaseov4.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartLocal {
    private static List<Product> cartInstance = null;
    private Double precioFinal=new Double(0);
    public static List<Product> getInstance() {
        if(cartInstance == null) {
            cartInstance = new ArrayList<Product>();
        }
        return cartInstance;
    }

    public CartLocal(List<Product> cart, Double precioFinal) {
        this.cartInstance = cart;
        this.precioFinal = precioFinal;
    }
}
