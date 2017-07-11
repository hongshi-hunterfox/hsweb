package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.ProductGroupLink;

public interface ProductGroupLinkMapper {
    int insert(ProductGroupLink record);

    int insertSelective(ProductGroupLink record);

	List<ProductGroupLink> selectByGroupId(Integer groupId);

	List<ProductGroupLink> selectAll();

	int del(@Param("groupId")Integer groupId, @Param("productId")Integer productId);

	ProductGroupLink selectByGroupIdAndProductId(@Param("groupId")Integer preGroupId, @Param("productId")Integer preProductId);

	List<ProductGroupLink> selectByTag(String tag);
}