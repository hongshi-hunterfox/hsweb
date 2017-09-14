package com.uclee.fundation.data.mybatis.model;

import java.util.Date;
import java.util.List;

public class Comment {
    private Integer id;

    private Integer userId;

    private String orderSerialNum;

    private String title;

    private Integer deliver;

    private Integer service;

    private Integer quality;

    private String backTitle;

    private Date time;
    private Date backTime;

    private String timeStr;

    private String backTimeStr;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getBackTimeStr() {
        return backTimeStr;
    }

    public void setBackTimeStr(String backTimeStr) {
        this.backTimeStr = backTimeStr;
    }

    private Order order;

    private UserProfile userProfile;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderSerialNum() {
        return orderSerialNum;
    }

    public void setOrderSerialNum(String orderSerialNum) {
        this.orderSerialNum = orderSerialNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getDeliver() {
        return deliver;
    }

    public void setDeliver(Integer deliver) {
        this.deliver = deliver;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getBackTitle() {
        return backTitle;
    }

    public void setBackTitle(String backTitle) {
        this.backTitle = backTitle == null ? null : backTitle.trim();
    }
}