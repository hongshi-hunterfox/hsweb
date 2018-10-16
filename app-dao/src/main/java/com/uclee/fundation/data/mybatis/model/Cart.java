package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class Cart {
    private Integer cartId;

    private Integer userId;

    private Integer productId;

    private Integer specificationValueId;
    
    private Integer amount;

    private Date createTime;
    
    private BigDecimal price;
    
    private String image;
    
    private String paramete;
    
    private Integer canshuValueId;
    
    private String csshuxing;
    
    private Integer activityMarkers;

	public Integer getActivityMarkers() {
		return activityMarkers;
	}

	public void setActivityMarkers(Integer activityMarkers) {
		this.activityMarkers = activityMarkers;
	}

	public String getCsshuxing() {
		return csshuxing;
	}

	public void setCsshuxing(String csshuxing) {
		this.csshuxing = csshuxing;
	}

	public String getParamete() {
		return paramete;
	}

	public void setParamete(String paramete) {
		this.paramete = paramete;
	}

	public Integer getCanshuValueId() {
		return canshuValueId;
	}

	public void setCanshuValueId(Integer canshuValueId) {
		this.canshuValueId = canshuValueId;
	}

    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public Cart setAmount(Integer amount) {
		this.amount = amount;
		return this;
	}

	public Integer getCartId() {
        return cartId;
    }

    public Cart setCartId(Integer cartId) {
        this.cartId = cartId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Cart setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public Cart setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getSpecificationValueId() {
        return specificationValueId;
    }

    public Cart setSpecificationValueId(Integer specificationValueId) {
        this.specificationValueId = specificationValueId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Cart setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}