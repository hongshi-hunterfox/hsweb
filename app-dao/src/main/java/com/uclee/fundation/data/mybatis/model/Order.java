package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
	
    private Integer orderId;

    private String orderSerialNum;

    private Integer userId;

    private Integer storeId;

    private Integer paymentOrderId;

    private String province;

    private String city;

    private String region;

    private String addrDetail;
    
    private String name;
    
    private String phone;
    
    private String zipCode;

    private Date createTime;
    
    private String createTimeStr;
    
    private String storeName;

    private Date payTime;

    private BigDecimal shippingCost;

    private BigDecimal totalPrice;
    
    private BigDecimal voucherPrice;
    
    private BigDecimal discount;
    
    private String voucherCode;

    private Short status;

    private String remark;
    
    private Date pickTime;
    
    private Boolean isSelfPick;
    
    private Integer firstDistId;
    
    private BigDecimal firstDistMoney;
    private BigDecimal cut;

    private Integer secondDistId;
    
    private BigDecimal secondDistMoney;
    
    private List<OrderItem> items;
    
    private UserProfile userProfile;
    
    private String payTimeStr;
    
    private Integer distLevel;
    
    private Integer syncStatus;

    private String pickTimeStr;
    
    private String paymentSerialNum;
    
    private String pickDateStr;

    private String pickUpImage;

    private String pickUpBarcode;
    
    private String value;
    
    private Integer invalid;
    
    public Integer getInvalid() {
		return invalid;
	}
    
    public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPickUpBarcode() {
		return pickUpBarcode;
	}

	public void setPickUpBarcode(String pickUpBarcode) {
		this.pickUpBarcode = pickUpBarcode;
	}

	public String getPickUpImage() {
		return pickUpImage;
	}

	public void setPickUpImage(String pickUpImage) {
		this.pickUpImage = pickUpImage;
	}

	public BigDecimal getCut() {
		return cut;
	}

	public void setCut(BigDecimal cut) {
		this.cut = cut;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getPickDateStr() {
		return pickDateStr;
	}

	public void setPickDateStr(String pickDateStr) {
		this.pickDateStr = pickDateStr;
	}

	public String getPaymentSerialNum() {
		return paymentSerialNum;
	}

	public void setPaymentSerialNum(String paymentSerialNum) {
		this.paymentSerialNum = paymentSerialNum;
	}

	public Date getPickTime() {
		return pickTime;
	}

	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}

	public String getPickTimeStr() {
		return pickTimeStr;
	}

	public void setPickTimeStr(String pickTimeStr) {
		this.pickTimeStr = pickTimeStr;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Integer getDistLevel() {
		return distLevel;
	}

	public void setDistLevel(Integer distLevel) {
		this.distLevel = distLevel;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}

	public Integer getFirstDistId() {
		return firstDistId;
	}

	public void setFirstDistId(Integer firstDistId) {
		this.firstDistId = firstDistId;
	}

	public BigDecimal getFirstDistMoney() {
		return firstDistMoney;
	}

	public void setFirstDistMoney(BigDecimal firstDistMoney) {
		this.firstDistMoney = firstDistMoney;
	}

	public Integer getSecondDistId() {
		return secondDistId;
	}

	public void setSecondDistId(Integer secondDistId) {
		this.secondDistId = secondDistId;
	}

	public BigDecimal getSecondDistMoney() {
		return secondDistMoney;
	}

	public void setSecondDistMoney(BigDecimal secondDistMoney) {
		this.secondDistMoney = secondDistMoney;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Boolean getIsSelfPick() {
		return isSelfPick;
	}

	public void setIsSelfPick(Boolean isSelfPick) {
		this.isSelfPick = isSelfPick;
	}

	public BigDecimal getVoucherPrice() {
		return voucherPrice;
	}

	public void setVoucherPrice(BigDecimal voucherPrice) {
		this.voucherPrice = voucherPrice;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSerialNum() {
        return orderSerialNum;
    }

    public void setOrderSerialNum(String orderSerialNum) {
        this.orderSerialNum = orderSerialNum == null ? null : orderSerialNum.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(Integer paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail == null ? null : addrDetail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}