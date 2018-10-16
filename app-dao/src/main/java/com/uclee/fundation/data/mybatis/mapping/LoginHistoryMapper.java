package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.LoginHistory;

public interface LoginHistoryMapper {
    int deleteByPrimaryKey(Integer historyId);

    int insert(LoginHistory record);

    int insertSelective(LoginHistory record);

    LoginHistory selectByPrimaryKey(Integer historyId);

    int updateByPrimaryKeySelective(LoginHistory record);

    int updateByPrimaryKey(LoginHistory record);
}