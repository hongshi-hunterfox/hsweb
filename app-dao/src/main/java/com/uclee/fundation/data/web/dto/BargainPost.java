package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BargainPost {

	private int id;

	private String name;

	private Date start;

	private Date closure;

	private String starts;

	private String ends;

	private String startTime;

	private String endTime;

	private BigDecimal price;

	private BigDecimal minprice;

	private BigDecimal maxprice;

	private String productName;

	private int triesLimit;

	private String imageUrl;

	private int productId;

	private int valueId;

	private int hsGoodsCode;

	private BigDecimal hsGoodsPrice;
	
	private String value;
	
	private Date end;
	
	private Integer hsStock;

	public Integer getHsStock() {
		return hsStock;
	}

	public void setHsStock(Integer hsStock) {
		this.hsStock = hsStock;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getValueId() {
		return valueId;
	}

	public void setValueId(int valueId) {
		this.valueId = valueId;
	}

	public int getHsGoodsCode() {
		return hsGoodsCode;
	}

	public void setHsGoodsCode(int hsGoodsCode) {
		this.hsGoodsCode = hsGoodsCode;
	}

	public BigDecimal getHsGoodsPrice() {
		return hsGoodsPrice;
	}

	public void setHsGoodsPrice(BigDecimal hsGoodsPrice) {
		this.hsGoodsPrice = hsGoodsPrice;
	}

	public String getStarts() {
		return starts;
	}

	public void setStarts(String starts) {
		this.starts = starts;
	}

	public String getEnds() {
		return ends;
	}

	public void setEnds(String ends) {
		this.ends = ends;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getClosure() {
		return closure;
	}

	public void setClosure(Date closure) {
		this.closure = closure;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMinprice() {
		return minprice;
	}

	public void setMinprice(BigDecimal minprice) {
		this.minprice = minprice;
	}

	public BigDecimal getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(BigDecimal maxprice) {
		this.maxprice = maxprice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getTriesLimit() {
		return triesLimit;
	}

	public void setTriesLimit(int triesLimit) {
		this.triesLimit = triesLimit;
	}

}
