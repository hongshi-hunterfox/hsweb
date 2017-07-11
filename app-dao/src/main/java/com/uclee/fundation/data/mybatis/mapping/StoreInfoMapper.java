package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.StoreInfo;

public interface StoreInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreInfo record);

    int insertSelective(StoreInfo record);

    StoreInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreInfo record);

    int updateByPrimaryKey(StoreInfo record);

	StoreInfo selectOne();
}