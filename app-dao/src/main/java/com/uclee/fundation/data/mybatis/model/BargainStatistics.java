package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class BargainStatistics {
	private String name;
	private BigDecimal price;
	private BigDecimal minPrice;
	private String user;
	private BigDecimal bargainPrice;
	private BigDecimal surplusAmount;
	private Integer Number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public BigDecimal getBargainPrice() {
		return bargainPrice;
	}
	public void setBargainPrice(BigDecimal bargainPrice) {
		this.bargainPrice = bargainPrice;
	}
	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}
	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}
	public Integer getNumber() {
		return Number;
	}
	public void setNumber(Integer number) {
		Number = number;
	}

}
