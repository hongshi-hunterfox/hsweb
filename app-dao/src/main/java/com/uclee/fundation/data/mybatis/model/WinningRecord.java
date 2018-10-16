package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class WinningRecord {
    private Integer id;

    private Integer userId;

    private Integer winningLevel;

    private Date time;
    
    private String nickName;
    
    private String image;

    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

    public Integer getWinningLevel() {
        return winningLevel;
    }

    public void setWinningLevel(Integer winningLevel) {
        this.winningLevel = winningLevel;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}