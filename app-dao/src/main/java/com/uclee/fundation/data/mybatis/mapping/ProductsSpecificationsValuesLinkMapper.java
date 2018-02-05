package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.ProductsSpecificationsValuesLink;

public interface ProductsSpecificationsValuesLinkMapper {
    int deleteByPrimaryKey(String specificationId);

    int insert(ProductsSpecificationsValuesLink record);

    int insertSelective(ProductsSpecificationsValuesLink record);

    ProductsSpecificationsValuesLink selectByPrimaryKey(String specificationId);

    int updateByPrimaryKeySelective(ProductsSpecificationsValuesLink record);

    int updateByPrimaryKey(ProductsSpecificationsValuesLink record);

	void delByProductId(Integer productId);

    ProductsSpecificationsValuesLink selectByValueId(Integer valueId);
}