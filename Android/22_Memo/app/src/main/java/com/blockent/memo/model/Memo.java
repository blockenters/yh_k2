package com.blockent.memo.model;

import java.io.Serializable;

public class Memo implements Serializable {

    private String title;
    private String date;
    private String content;

    private int id;
    private String created_at;
    private String updated_at;
    private int user_id;

    public Memo(String title, String date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }
    // 기본 생성자는 무조건 만든다.
    public Memo(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
