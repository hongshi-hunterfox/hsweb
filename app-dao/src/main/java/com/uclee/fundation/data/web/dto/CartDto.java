package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.uclee.fundation.data.mybatis.model.Cart;

public class CartDto extends Cart{
	
	private String title;
	
	private String image;
	
	private String specification;
	
	private BigDecimal money;
	
	private BigDecimal promotion;
	
	private Date startTime;
	
	private Date endTime;
	
	private int appointedTime;
	
	private Integer activityMarkers;
	
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
