package com.example.elpaseov4.model;

import java.io.Serializable;

public class Category implements Serializable {
    private Long id;
    private String name;
    private Imagen image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Imagen getImage() {
        return image;
    }

    public void setImage(Imagen image) {
        this.image = image;
    }
}
