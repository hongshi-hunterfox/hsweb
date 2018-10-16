package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class HongShiCoupon {
	
	private String vouchersCode;
	
	private BigDecimal payQuota;
	
	private String state;
	
	private String vipCode;
	
	private String endTime;
	
	private String barCode;
	
	private String goodsCode;
	
	private BigDecimal fullamount;
	
	private String convertibleGoods;
	
	private String remarks;
	
	private String productNumber;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getConvertibleGoods() {
		return convertibleGoods;
	}

	public void setConvertibleGoods(String convertibleGoods) {
		this.convertibleGoods = convertibleGoods;
	}

	public BigDecimal getFullamount() {
		return fullamount;
	}

	public void setFullamount(BigDecimal fullamount) {
		this.fullamount = fullamount;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getVouchersCode() {
		return vouchersCode;
	}

	public void setVouchersCode(String vouchersCode) {
		this.vouchersCode = vouchersCode;
	}

	public BigDecimal getPayQuota() {
		return payQuota;
	}

	public void setPayQuota(BigDecimal payQuota) {
		this.payQuota = payQuota;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getVipCode() {
		return vipCode;
	}

	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

}
