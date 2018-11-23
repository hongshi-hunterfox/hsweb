package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.Cart;
import com.uclee.fundation.data.mybatis.model.HongShiVip;

public class CartDto extends Cart{
	
	private String title;
	
	private String image;
	
	private String specification;
	
	private BigDecimal money;
	
	private BigDecimal promotion;
	
	private BigDecimal vip;
	
	private Date startTime;
	
	private Date endTime;
	
	private int appointedTime;
	
	private String pickUpTimes;
	
	private String pickEndTimes;
	
	private Integer activityMarkers;
	
	private String hsVipCode;
	
	public String getHsVipCode() {
		return hsVipCode;
	}
	
	public void setHsVipCode(String hsVipCode) {
		this.hsVipCode = hsVipCode;
	}
	
	public BigDecimal getVip() {
		return vip;
	}
	public void setVip(BigDecimal vip) {
		this.vip = vip;
	}
	
	public String getPickUpTimes() {
		return pickUpTimes;
	}

	public void setPickUpTimes(String pickUpTimes) {
		this.pickUpTimes = pickUpTimes;
	}

	public String getPickEndTimes() {
		return pickEndTimes;
	}

	public void setPickEndTimes(String pickEndTimes) {
		this.pickEndTimes = pickEndTimes;
	}

	public Integer getActivityMarkers() {
		return activityMarkers;
	}

	public void setActivityMarkers(Integer activityMarkers) {
		this.activityMarkers = activityMarkers;
	}

	public int getAppointedTime() {
		return appointedTime;
	}

	public void setAppointedTime(int appointedTime) {
		this.appointedTime = appointedTime;
	}

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

	public BigDecimal getPromotion() {
		return promotion;
	}

	public void setPromotion(BigDecimal promotion) {
		this.promotion = promotion;
	}

	private Integer stock;
	
	private Boolean isDisabled;

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

}
