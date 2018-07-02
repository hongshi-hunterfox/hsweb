package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.NapaStore;

public interface NapaStoreMapper {
    int deleteByPrimaryKey(Integer storeId);

    int insert(NapaStore record);

    int insertSelective(NapaStore record);

    NapaStore selectByPrimaryKey(Integer storeId);

    int updateByPrimaryKeySelective(NapaStore record);

    int updateByPrimaryKey(NapaStore record);
}