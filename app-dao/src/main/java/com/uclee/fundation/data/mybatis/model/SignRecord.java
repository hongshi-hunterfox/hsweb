package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class SignRecord {
    private Integer id;

    private Integer userId;

    private Date signTime;

    private Integer point;
    
    private Integer accumulation;

    public Integer getAccumulation() {
		return accumulation;
	}

	public void setAccumulation(Integer accumulation) {
		this.accumulation = accumulation;
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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}