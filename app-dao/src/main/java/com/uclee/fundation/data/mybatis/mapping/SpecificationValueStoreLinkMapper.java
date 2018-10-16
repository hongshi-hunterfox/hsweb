package com.uclee.fundation.data.mybatis.mapping;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.SpecificationValueStoreLink;

public interface SpecificationValueStoreLinkMapper {
    int insert(SpecificationValueStoreLink record);

    int insertSelective(SpecificationValueStoreLink record);

	void delByProductId();

	SpecificationValueStoreLink selectByValueAndStoreId(@Param("valueId")Integer valueId, @Param("storeId")Integer storeId);

	void delByStoreId(Integer storeId);
}