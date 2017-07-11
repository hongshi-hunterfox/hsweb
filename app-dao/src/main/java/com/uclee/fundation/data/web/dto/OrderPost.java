package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderPost {
	
	private Integer addrId;
	
	private String cartIds;
	
	private Integer storeId;
	
	private String voucherCode;
	
	private String remark;

	private String isSelfPick;
	
	private BigDecimal shippingFee;
	
	private String pickDateStr;
	
	private String pickTimeStr;
	
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPickDateStr() {
		return pickDateStr;
	}

	public void setPickDateStr(String pickDateStr) {
		this.pickDateStr = pickDateStr;
	}

	public String getPickTimeStr() {
		return pickTimeStr;
	}

	public void setPickTimeStr(String pickTimeStr) {
		this.pickTimeStr = pickTimeStr;
	}

	public BigDecimal getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getAddrId() {
		return addrId;
	}

	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}

	public String getCartIds() {
		return cartIds;
	}

	public void setCartIds(String cartIds) {
		this.cartIds = cartIds;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsSelfPick() {
		return isSelfPick;
	}

	public void setIsSelfPick(String isSelfPick) {
		this.isSelfPick = isSelfPick;
	}
	
}
