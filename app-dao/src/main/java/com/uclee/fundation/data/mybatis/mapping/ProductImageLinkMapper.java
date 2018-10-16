package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.ProductImageLink;

public interface ProductImageLinkMapper {
    int deleteByPrimaryKey(Integer linkId);

    int insert(ProductImageLink record);

    int insertSelective(ProductImageLink record);

    ProductImageLink selectByPrimaryKey(Integer linkId);

    int updateByPrimaryKeySelective(ProductImageLink record);

    int updateByPrimaryKey(ProductImageLink record);

	List<ProductImageLink> selectByProductId(Integer productId);

	ProductImageLink selectByProductIdLimit(Integer productId);

	ProductImageLink selectByHongShiGoodsCodeLimit(String code);

}