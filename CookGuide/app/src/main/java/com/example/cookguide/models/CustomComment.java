package com.example.cookguide.models;

import android.widget.ImageView;

import java.sql.Timestamp;

public class CustomComment {
    private Long userId;
    private String fullName;
    private String avatar;
    private Long cmtId;
    private String content;
    private String urlImage;
    private ImageView imageView;
    private Timestamp time;
    private Boolean statusUser;

    public CustomComment(){}

    public CustomComment(Long userId, String fullName, String avatar, Long cmtId, String content, String urlImage, ImageView imageView, Timestamp time, Boolean statusUser) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatar = avatar;
        this.cmtId = cmtId;
        this.content = content;
        this.urlImage = urlImage;
        this.imageView = imageView;
        this.time = time;
        this.statusUser = statusUser;
    }

    public CustomComment(Comment comment){
        this.userId = comment.getUserId();
        this.fullName = comment.getFullName();
        this.avatar = comment.getAvatar();
        this.cmtId = comment.getCmtId();
        this.content = comment.getContent();
        this.urlImage = comment.getImage();
        this.time = comment.getTime();
        this.statusUser = comment.getStatusUser();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCmtId() {
        return cmtId;
    }

    public void setCmtId(Long cmtId) {
        this.cmtId = cmtId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(Boolean statusUser) {
        this.statusUser = statusUser;
    }
}
