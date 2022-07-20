package com.blockent.youtube.model;

public class Video {

    public String videoId;
    public String title;
    public String description;
    public String imgUrl;

    public Video(String title, String description, String imgUrl, String videoId) {
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.videoId = videoId;
    }
}
