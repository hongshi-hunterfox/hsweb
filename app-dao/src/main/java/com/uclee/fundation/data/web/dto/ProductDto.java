package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.mybatis.model.ProductImageLink;
import com.uclee.fundation.data.mybatis.model.Specification;

public class ProductDto extends Product{
	
	private Integer salesAmount;
	
	private String image;

	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	private List<ProductImageLink> images;

	private List<String> salesInfo;

	public List<String> getSalesInfo() {
		return salesInfo;
	}

	public void setSalesInfo(List<String> salesInfo) {
		this.salesInfo = salesInfo;
	}

	private List<Specification> specifications;
	
	private BigDecimal price;
	
	private BigDecimal prePrice;
	
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

}
