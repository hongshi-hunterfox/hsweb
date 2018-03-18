package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.List;

public class HongShiOrder {
	private Integer id;
	
	private String orderCode;
	
	private String date;
	private String createTime;

	private double totalAmount;
	
	private double accounts;
	
	private Boolean isEnd;
	
	private String outerOrderCode;
	
	private BigDecimal shippingCost;
	
	private Boolean isSelfPick;
	
	private BigDecimal discount;
	
	private String pickAddr;

	private Boolean isComment;

	private BigDecimal cut;

	private String pickUpCode;

	private String pickUpImageUrl;

	private String barcode;

	private Boolean isVoid;

	//add by jp for orderDetail
	private String department;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Boolean getVoid() {
		return isVoid;
	}

	public void setVoid(Boolean aVoid) {
		isVoid = aVoid;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getPickUpImageUrl() {
		return pickUpImageUrl;
	}

	public void setPickUpImageUrl(String pickUpImageUrl) {
		this.pickUpImageUrl = pickUpImageUrl;
	}

	public String getPickUpCode() {
		return pickUpCode;
	}

	public void setPickUpCode(String pickUpCode) {
		this.pickUpCode = pickUpCode;
	}

	public BigDecimal getCut() {
		return cut;
	}

	public void setCut(BigDecimal cut) {
		this.cut = cut;
	}

	public Boolean getIsComment() {
		return isComment;
	}

	public void setIsComment(Boolean comment) {
		isComment = comment;
	}

	public String getPickAddr() {
		return pickAddr;
	}

	public void setPickAddr(String pickAddr) {
		this.pickAddr = pickAddr;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Boolean getIsSelfPick() {
		return isSelfPick;
	}

	public void setIsSelfPick(Boolean isSelfPick) {
		this.isSelfPick = isSelfPick;
	}

	public BigDecimal getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getOuterOrderCode() {
		return outerOrderCode;
	}

	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}

	public Boolean getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}

	public double getAccounts() {
		return accounts;
	}

	public void setAccounts(double accounts) {
		this.accounts = accounts;
	}

	private List<HongShiOrderItem> orderItems;

	public List<HongShiOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<HongShiOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	//订单详情页面增加的字段
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
