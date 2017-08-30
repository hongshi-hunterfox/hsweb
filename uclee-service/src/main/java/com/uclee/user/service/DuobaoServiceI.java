package com.uclee.user.service;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.Category;
import com.uclee.fundation.data.web.dto.ProductDto;

public interface DuobaoServiceI {

	List<Category> getAllCat();
	
	String sendWXMessageSuccess(Integer userId,Integer successCount,String orderSerialNum);

	String getGolbalAccessToken();

	List<ProductDto> getAllProduct(Integer categoryId, Boolean isSaleDesc, Boolean isPriceDesc, String keyWord, Integer naviId);

	
}
