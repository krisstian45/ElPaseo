package com.example.elpaseov4.pojoModel;

import java.io.Serializable;

public class CartProductPojo implements Serializable {
    private ProductPojo product;
    private String quantity;

    public ProductPojo getProduct() {
        return product;
    }

    public void setProduct(ProductPojo product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
