package com.uclee.fundation.data.mybatis.model;

public class HongShiOrderItem {
	
	private Integer id;
	
	private Integer orderId;
	
	private String code;
	
	private double price;
	
	private double discountRate;
	
	private Integer count;
	
	private double totalAmount;

	private Integer productId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	private double sumDiscount;
	
	private HongShiGoods hongShiGoods;

	public HongShiGoods getHongShiGoods() {
		return hongShiGoods;
	}

	public void setHongShiGoods(HongShiGoods hongShiGoods) {
		this.hongShiGoods = hongShiGoods;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getSumDiscount() {
		return sumDiscount;
	}

	public void setSumDiscount(double sumDiscount) {
		this.sumDiscount = sumDiscount;
	}

}
