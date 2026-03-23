package com.pixelpeek;

public class PhotoData {
    public String imageUrl;
    public int width;
    public int height;
    public String color;
    public String description;
    public String photographerName;
    public String photographerUrl;
    public String query;

    public PhotoData(String imageUrl, int width, int height, String color,
                     String description, String photographerName,
                     String photographerUrl, String query) {
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
        this.color = color;
        this.description = description;
        this.photographerName = photographerName;
        this.photographerUrl = photographerUrl;
        this.query = query;
    }
}
