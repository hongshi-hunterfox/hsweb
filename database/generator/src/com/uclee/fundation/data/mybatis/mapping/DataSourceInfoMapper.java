package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.DataSourceInfo;

public interface DataSourceInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DataSourceInfo record);

    int insertSelective(DataSourceInfo record);

    DataSourceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DataSourceInfo record);

    int updateByPrimaryKey(DataSourceInfo record);
}