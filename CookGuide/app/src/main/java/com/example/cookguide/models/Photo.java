package com.example.cookguide.models;

public class Photo {
    private String url;
    public Photo(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    //    private int resourceId;
//
//    public Photo(int resourceId) {
//        this.resourceId = resourceId;
//    }
//
//    public int getResourceId() {
//        return resourceId;
//    }
//
//    public void setResourceId(int resourceId) {
//        this.resourceId = resourceId;
//    }
}
