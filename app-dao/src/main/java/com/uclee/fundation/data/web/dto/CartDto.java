package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;

import com.uclee.fundation.data.mybatis.model.Cart;

public class CartDto extends Cart{
	
	private String title;
	
	private String image;
	
	private String specification;
	
	private BigDecimal money;
	
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
