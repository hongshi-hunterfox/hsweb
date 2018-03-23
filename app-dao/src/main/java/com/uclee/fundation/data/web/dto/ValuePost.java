package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ValuePost {
	
	private Integer valueId;
	
	private String name;
	
	private BigDecimal hsPrice;
	
	private String code;
	
	private Integer hsStock;
	
	private List<Integer> storeIds;
	
	private BigDecimal prePrice;

	private BigDecimal promotionPrice;
	
	private Date startTime;
	
	private Date endTime;
	
	private String startTimeStr;
	
	private String endTimeStr;
	
	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
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

	public BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}

	public Integer getValueId() {
		return valueId;
	}

	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getHsPrice() {
		return hsPrice;
	}

	public void setHsPrice(BigDecimal hsPrice) {
		this.hsPrice = hsPrice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getHsStock() {
		return hsStock;
	}

	public void setHsStock(Integer hsStock) {
		this.hsStock = hsStock;
	}

	public List<Integer> getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(List<Integer> storeIds) {
		this.storeIds = storeIds;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
		
	}

	public BigDecimal getPromotionPrice() {
		// TODO Auto-generated method stub
		return promotionPrice;
	}

	
}
