package com.example.elpaseov4.repository;

import com.example.elpaseov4.model.Product;
import com.example.elpaseov4.model.User;

import java.util.List;

public class Cart {
    private Long id;
    private Long price;
    private List<Product> cartProducts;
    private User user;

}
