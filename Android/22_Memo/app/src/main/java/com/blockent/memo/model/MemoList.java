package com.blockent.memo.model;

import java.util.List;

public class MemoList  {

    private String result;
    private int count;
    private List<Memo> items;

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

    public List<Memo> getItems() {
        return items;
    }

    public void setItems(List<Memo> items) {
        this.items = items;
    }
}
