package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.mybatis.model.ProductImageLink;
import com.uclee.fundation.data.mybatis.model.ProductParameters;
import com.uclee.fundation.data.mybatis.model.Specification;

public class ProductDto extends Product{
	
	private Integer salesAmount;
	
	private String image;

	private String category;
	
	private List<ProductParameters> parameters;

	private List<ProductImageLink> images;

	private List<String> salesInfo;
	
	private List<String> giftCouponsInfo;

	private List<Specification> specifications;
	
	private BigDecimal price;
	
	private BigDecimal prePrice;
	
	private BigDecimal promotionPrice;
	
	private Date startTime;
	
	private Date endTime;
	
	private String startTimeStr;
	
	private String endTimeStr;
	
	private Date pickUpTime;
	
	private Date pickEndTime;	
    
    private String PickUpTimes;
    
    private String PickEndTimes;
	
	public String getPickEndTimes() {
		return PickEndTimes;
	}

	public void setPickEndTimes(String pickEndTimes) {
		PickEndTimes = pickEndTimes;
	}

	public String getPickUpTimes() {
		return PickUpTimes;
	}

	public void setPickUpTimes(String pickUpTimes) {
		PickUpTimes = pickUpTimes;
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

	public List<String> getGiftCouponsInfo() {
		return giftCouponsInfo;
	}

	public void setGiftCouponsInfo(List<String> giftCouponsInfo) {
		this.giftCouponsInfo = giftCouponsInfo;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	private Integer currentSpecValudId;

	public BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}

	public Integer getCurrentSpecValudId() {
		return currentSpecValudId;
	}

	public void setCurrentSpecValudId(Integer currentSpecValudId) {
		this.currentSpecValudId = currentSpecValudId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Integer salesAmount) {
		this.salesAmount = salesAmount;
	}

	public List<ProductImageLink> getImages() {
		return images;
	}

	public void setImages(List<ProductImageLink> images) {
		this.images = images;
	}

	public List<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<Specification> specifications) {
		this.specifications = specifications;
	}
	
	public List<ProductParameters> getParameters() {
		return parameters;
	}

	public void setParameters(List<ProductParameters> parameters) {
		this.parameters = parameters;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<String> getSalesInfo() {
		return salesInfo;
	}

	public void setSalesInfo(List<String> salesInfo) {
		this.salesInfo = salesInfo;
	}
}
