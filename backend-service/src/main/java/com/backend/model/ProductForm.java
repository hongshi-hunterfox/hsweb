package com.backend.model;

import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.mybatis.model.ProductImageLink;
import com.uclee.fundation.data.web.dto.ValuePost;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductForm extends Product {
    
    private Integer categoryId;
    
    private String[] images;
    
    private List<ValuePost> valuePost;

    private Integer sale;
    
    private String attribute1;
    
    private String attribute2;
    
    private String attribute3;

    private String attribute4;
  
    private String attribute5;
    
    private String attribute6;
    
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public List<ValuePost> getValuePost() {
		return valuePost;
	}

	public void setValuePost(List<ValuePost> valuePost) {
		this.valuePost = valuePost;
	}
    
}
