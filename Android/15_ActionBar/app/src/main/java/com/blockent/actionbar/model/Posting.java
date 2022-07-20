package com.blockent.actionbar.model;

public class Posting {

    public int id;
    public int userId;
    public String title;
    public String body;

    public Posting(int id, int userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
