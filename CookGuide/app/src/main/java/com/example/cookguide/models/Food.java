package com.example.cookguide.models;

import java.sql.Date;

public class Food {
    private Long foodId;
    private String name;
    private String description;
    private int level;
    private int nSavoring;
    private int nHearts;
    private int nClaps;
    private String foodImage1;
    private String foodImage2;
    private String foodImage3;
    private Date date;
    private int totalTime;

    public Food(){}

    public Food(Long foodId, String name, String description, int level, int nSavoring, int nHearts, int nClaps, String foodImage1, String foodImage2, String foodImage3, Date date, int totalTime) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.level = level;
        this.nSavoring = nSavoring;
        this.nHearts = nHearts;
        this.nClaps = nClaps;
        this.foodImage1 = foodImage1;
        this.foodImage2 = foodImage2;
        this.foodImage3 = foodImage3;
        this.date = date;
        this.totalTime = totalTime;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getnSavoring() {
        return nSavoring;
    }

    public void setnSavoring(int nSavoring) {
        this.nSavoring = nSavoring;
    }

    public int getnHearts() {
        return nHearts;
    }

    public void setnHearts(int nHearts) {
        this.nHearts = nHearts;
    }

    public int getnClaps() {
        return nClaps;
    }

    public void setnClaps(int nClaps) {
        this.nClaps = nClaps;
    }

    public String getFoodImage1() {
        return foodImage1;
    }

    public void setFoodImage1(String foodImage1) {
        this.foodImage1 = foodImage1;
    }

    public String getFoodImage2() {
        return foodImage2;
    }

    public void setFoodImage2(String foodImage2) {
        this.foodImage2 = foodImage2;
    }

    public String getFoodImage3() {
        return foodImage3;
    }

    public void setFoodImage3(String foodImage3) {
        this.foodImage3 = foodImage3;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
