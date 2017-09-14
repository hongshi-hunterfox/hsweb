package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class ShippingFullCut {
    private Integer id;

    private double sLimit;

    private double uLimit;

    private BigDecimal condition;

    private Date time;

    private Date startTime;

    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getsLimit() {
        return sLimit;
    }

    public void setsLimit(double sLimit) {
        this.sLimit = sLimit;
    }

    public double getuLimit() {
        return uLimit;
    }

    public void setuLimit(double uLimit) {
        this.uLimit = uLimit;
    }

    public BigDecimal getCondition() {
        return condition;
    }

    public void setCondition(BigDecimal condition) {
        this.condition = condition;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}