package com.example.elpaseov4.network;

import com.example.elpaseov4.model.News;
import com.example.elpaseov4.model.Product;

import java.util.List;

public class PaginationNews {
    private int totalElements;
    private List<News> page;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<News> getPage() {
        return page;
    }

    public void setPage(List<News> page) {
        this.page = page;
    }
}
