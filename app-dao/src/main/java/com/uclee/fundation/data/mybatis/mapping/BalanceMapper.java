package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.Balance;

public interface BalanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Balance record);

    int insertSelective(Balance record);

    Balance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Balance record);

    int updateByPrimaryKey(Balance record);

	Balance selectByUserId(Integer userId);
}