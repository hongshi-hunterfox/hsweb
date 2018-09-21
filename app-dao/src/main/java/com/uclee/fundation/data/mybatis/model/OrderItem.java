package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class OrderItem {
    private Integer itemId;

    private String itemSerialNum;

    private Integer orderId;

    private Integer storeId;

    private Integer productId;

    private String imageUrl;

    private Integer valueId;

    private String value;

    private Short amount;

    private BigDecimal price;
    
    private String title;
    
    private Integer invalid;
    
    public Integer getInvalid() {
		return invalid;
	}
    
    public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemSerialNum() {
        return itemSerialNum;
    }

    public void setItemSerialNum(String itemSerialNum) {
        this.itemSerialNum = itemSerialNum == null ? null : itemSerialNum.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}