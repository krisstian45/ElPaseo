package com.example.elpaseov4.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private Long id;//es long cambiar luego
    private String title;
    private String description;
    private String brand;
    private List<Imagen> images;
    private int unitQuantity;
    private Long price;
    private boolean isPromotion;

    /*public Product(Long id,String title, String description, String brand, int unitQuantity, Long price, boolean isPromotion){
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setBrand(brand);
        this.setUnitQuantity(unitQuantity);
        this.setPrice(price);
        this.setPromotion(isPromotion);
    }*/


    public List<Imagen> getImages() {
        return images;
    }

    public void setImages(List<Imagen> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(int unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }
}