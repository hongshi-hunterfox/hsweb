package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class HongShiCreateOrder {
	
	private String OrderCode;
	
	private String WSC_TardNo;
	
	private Date PickUpTime;
	
	private String WeiXinCode;
	
	private String CallNumber;
	
	private String Remarks;
	
	private String Destination;

	private BigDecimal shipping;

	private BigDecimal deducted;
	
	private BigDecimal TotalAmount;
	
	private BigDecimal Payment;
	
	private BigDecimal Voucher;
	
	private String Department;

	private String Vouchers;

	private String oauthId;

	public BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}

	public BigDecimal getDeducted() {
		return deducted;
	}

	public void setDeducted(BigDecimal deducted) {
		this.deducted = deducted;
	}

	public String getOauthId() {
		return oauthId;
	}

	public void setOauthId(String oauthId) {
		this.oauthId = oauthId;
	}

	public String getVouchers() {
		return Vouchers;
	}

	public void setVouchers(String vouchers) {
		Vouchers = vouchers;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getOrderCode() {
		return OrderCode;
	}

	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}

	public String getWSC_TardNo() {
		return WSC_TardNo;
	}

	public void setWSC_TardNo(String wSC_TardNo) {
		WSC_TardNo = wSC_TardNo;
	}

	public Date getPickUpTime() {
		return PickUpTime;
	}

	public void setPickUpTime(Date pickUpTime) {
		PickUpTime = pickUpTime;
	}

	public String getWeiXinCode() {
		return WeiXinCode;
	}

	public void setWeiXinCode(String weiXinCode) {
		WeiXinCode = weiXinCode;
	}

	public String getCallNumber() {
		return CallNumber;
	}

	public void setCallNumber(String callNumber) {
		CallNumber = callNumber;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getDestination() {
		return Destination;
	}

	public void setDestination(String destination) {
		Destination = destination;
	}

	public BigDecimal getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		TotalAmount = totalAmount;
	}

	public BigDecimal getPayment() {
		return Payment;
	}

	public void setPayment(BigDecimal payment) {
		Payment = payment;
	}

	public BigDecimal getVoucher() {
		return Voucher;
	}

	public void setVoucher(BigDecimal voucher) {
		Voucher = voucher;
	}
	

}
