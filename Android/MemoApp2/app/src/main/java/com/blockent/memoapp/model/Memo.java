package com.blockent.memoapp.model;

public class Memo {

    public int id;
    public String title;
    public String content;

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
