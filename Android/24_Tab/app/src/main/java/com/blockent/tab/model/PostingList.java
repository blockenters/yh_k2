package com.blockent.tab.model;

import java.util.List;

public class PostingList {

    private String result;
    private int count;
    private List<Posting> items;

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

    public List<Posting> getItems() {
        return items;
    }

    public void setItems(List<Posting> items) {
        this.items = items;
    }
}
