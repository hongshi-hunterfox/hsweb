package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class SpecificationValue {
    private Integer valueId;

    private Integer specificationId;

    private String value;

    private String hsGoodsCode;

    private BigDecimal hsGoodsPrice;
    
    private Integer hsStock;
    
    private BigDecimal prePrice;

    public BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}

	public Integer getHsStock() {
		return hsStock;
	}

	public void setHsStock(Integer hsStock) {
		this.hsStock = hsStock;
	}

	public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getHsGoodsCode() {
		return hsGoodsCode;
	}

	public void setHsGoodsCode(String hsGoodsCode) {
		this.hsGoodsCode = hsGoodsCode;
	}

	public BigDecimal getHsGoodsPrice() {
        return hsGoodsPrice;
    }

    public void setHsGoodsPrice(BigDecimal hsGoodsPrice) {
        this.hsGoodsPrice = hsGoodsPrice;
    }
}