package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.ProductSale;

public interface ProductSaleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductSale record);

    int insertSelective(ProductSale record);

    ProductSale selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductSale record);

    int updateByPrimaryKey(ProductSale record);

	ProductSale selectByProductId(Integer productId);
}