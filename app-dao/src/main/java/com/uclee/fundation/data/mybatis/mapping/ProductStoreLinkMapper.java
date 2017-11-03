package com.uclee.fundation.data.mybatis.mapping;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.ProductStoreLinkKey;

public interface ProductStoreLinkMapper {
    int deleteByPrimaryKey(ProductStoreLinkKey key);

    int insert(ProductStoreLinkKey record);

    int insertSelective(ProductStoreLinkKey record);

	ProductStoreLinkKey selectByPIdAndStoreId(@Param("productId")Integer productId, @Param("selectedStoreId")Integer selectedStoreId);

    int insertAll(Integer storeId);
}