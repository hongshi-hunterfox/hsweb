package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer productId;

    private String title;

    private String description;

    private Date createTime;

    private Date lastModify;

    private Boolean isActive;
    
    private BigDecimal price;

    private Boolean isShippingFree;
    
    private String explain;

    private Boolean isShow;

    private Integer sortValue;
    
    private String parameter;
    
    private Integer appointedTime;
    
    private Date pickUpTime;
    
    private Date pickEndTime;
       
    private String PickUpTimes;
    
    private String PickEndTimes;

    private Integer frequency;
    
    private Date shelfTime;
    
    private Date downTime;
    
    private BigDecimal vipPrice;
    
    public BigDecimal getVipPrice() {
		return vipPrice;
	}
    
    public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}

    public Date getShelfTime() {
		return shelfTime;
	}

	public void setShelfTime(Date shelfTime) {
		this.shelfTime = shelfTime;
	}

	public Date getDownTime() {
		return downTime;
	}

	public void setDownTime(Date downTime) {
		this.downTime = downTime;
	}

	public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getPickUpTimes() {
		return PickUpTimes;
	}

	public void setPickUpTimes(String pickUpTimes) {
		PickUpTimes = pickUpTimes;
	}

	public String getPickEndTimes() {
		return PickEndTimes;
	}

	public void setPickEndTimes(String pickEndTimes) {
		PickEndTimes = pickEndTimes;
	}

	public Date getPickUpTime() {
		return pickUpTime;
	}

    public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}
    
    public Date getPickEndTime() {
		return pickEndTime;
	}
    
    public void setPickEndTime(Date pickEndTime) {
		this.pickEndTime = pickEndTime;
	}
    
	public Boolean getIsShippingFree() {
		return isShippingFree;
	}

	public void setIsShippingFree(Boolean isShippingFree) {
		this.isShippingFree = isShippingFree;
	}

	public Integer getAppointedTime() {
		return appointedTime;
	}

	public void setAppointedTime(Integer appointedTime) {
		this.appointedTime = appointedTime;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

    public Boolean getShippingFree() {
        return isShippingFree;
    }

    public void setShippingFree(Boolean shippingFree) {
        isShippingFree = shippingFree;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date lastModify) {
        this.lastModify = lastModify;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Integer getSortValue() {
        return sortValue;
    }

    public void setSortValue(Integer sortValue) {
        this.sortValue = sortValue;
    }
}