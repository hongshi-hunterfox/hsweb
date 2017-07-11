package com.backend.service;


import com.backend.model.ProductForm;
import org.springframework.web.multipart.MultipartFile;


public interface ProductManageServiceI {
	boolean addProduct(ProductForm productForm);

	String uploadImage(MultipartFile file);

	boolean updateProduct(ProductForm productForm);

	Boolean delProduct(Integer productId);
}
