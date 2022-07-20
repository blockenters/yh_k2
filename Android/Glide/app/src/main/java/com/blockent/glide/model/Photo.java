package com.blockent.glide.model;

public class Photo {

    public int albumId;
    public int id;
    public String title;
    public String url;
    public String thumbnailUrl;

    public Photo(int id, int albumId, String title, String url, String thumbnailUrl) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }
}
