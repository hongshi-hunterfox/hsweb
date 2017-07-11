package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.BalanceLog;

public interface BalanceLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BalanceLog record);

    int insertSelective(BalanceLog record);

    BalanceLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BalanceLog record);

    int updateByPrimaryKey(BalanceLog record);
}