package com.uclee.fundation.data.mybatis.model;

public class HomeQuickNavi {
    private Integer naviId;

    private String title;

    private String imageUrl;

    public Integer getNaviId() {
        return naviId;
    }

    public void setNaviId(Integer naviId) {
        this.naviId = naviId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }
}