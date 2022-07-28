package com.blockent.movieapp.model;

import java.util.List;

public class ReviewList {

    private String result;
    private int count;
    private List<Review> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Review> getItems() {
        return items;
    }

    public void setItems(List<Review> items) {
        this.items = items;
    }
}
