package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class WxUser {
	private int userId;
	private String image;
	private String nickName;
	private BigDecimal randomAmount;
	public BigDecimal getRandomAmount() {
		return randomAmount;
	}
	public void setRandomAmount(BigDecimal randomAmount) {
		this.randomAmount = randomAmount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
