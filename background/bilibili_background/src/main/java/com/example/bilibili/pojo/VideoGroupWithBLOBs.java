package com.example.bilibili.pojo;

public class VideoGroupWithBLOBs extends VideoGroup {
    private String catagory;

    private String videos;

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory == null ? null : catagory.trim();
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos == null ? null : videos.trim();
    }
}