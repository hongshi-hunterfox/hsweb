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
