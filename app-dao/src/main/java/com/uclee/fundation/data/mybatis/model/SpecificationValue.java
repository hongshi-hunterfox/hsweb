package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class SpecificationValue {
    private Integer valueId;

    private Integer specificationId;

    private String value;

    private String hsGoodsCode;

    private BigDecimal hsGoodsPrice;
    
    private Integer hsStock;
    
    private BigDecimal prePrice;
    
    private BigDecimal promotionPrice;
    
    private Date startTime;
    
    private String startTimeStr;
    
    private Date endTime;
    
    private String endTimeStr;
    
    private BigDecimal vipPrice;
    
    public BigDecimal getVipPrice() {
		return vipPrice;
	}
    
    public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}

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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

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