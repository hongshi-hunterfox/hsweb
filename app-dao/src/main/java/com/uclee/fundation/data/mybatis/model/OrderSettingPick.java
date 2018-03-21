package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

/**
 * Created by jiang on 2018/3/16.
 * 商城上下单时，歇业时间和营业时间需在后台设置指定时间段
 */
public class OrderSettingPick {
    private int id;
    private Date closeStartDate;//歇业开始时间 2018-01-01
    private Date closeEndDate;//歇业结束时间 2018-01-01

    private String closeStartDateStr;
    private String closeEndDateStr;

    private String businessStartTime;//营业开始时间 00:00
    private String businessEndTime;//营业结束时间  23:59


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCloseStartDate() {
        return closeStartDate;
    }

    public void setCloseStartDate(Date closeStartDate) {
        this.closeStartDate = closeStartDate;
    }

    public Date getCloseEndDate() {
        return closeEndDate;
    }

    public void setCloseEndDate(Date closeEndDate) {
        this.closeEndDate = closeEndDate;
    }

    public String getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(String businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public String getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(String businessEndTime) {
        this.businessEndTime = businessEndTime;
    }


    public String getCloseStartDateStr() {
        return closeStartDateStr;
    }

    public void setCloseStartDateStr(String closeStartDateStr) {
        this.closeStartDateStr = closeStartDateStr;
    }

    public String getCloseEndDateStr() {
        return closeEndDateStr;
    }

    public void setCloseEndDateStr(String closeEndDateStr) {
        this.closeEndDateStr = closeEndDateStr;
    }
}
