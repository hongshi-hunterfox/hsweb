package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class HongShiProduct {
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	private String hsSpec;
	
	private String unit;
	
	private BigDecimal hsPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getHsPrice() {
		return hsPrice;
	}

	public void setHsPrice(BigDecimal hsPrice) {
		this.hsPrice = hsPrice;
	}

}
