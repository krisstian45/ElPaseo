package com.example.elpaseov4.network;

import com.example.elpaseov4.model.Product;

import java.io.Serializable;
import java.util.List;

public class Pagination implements Serializable {
    private int totalElements;
    private List<Product> page;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<Product> getPage() {
        return page;
    }

    public void setPage(List<Product> page) {
        this.page = page;
    }
}
