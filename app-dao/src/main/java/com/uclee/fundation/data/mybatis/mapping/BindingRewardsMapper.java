package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.BindingRewards;

import java.util.List;

public interface BindingRewardsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BindingRewards record);

    int insertSelective(BindingRewards record);

    BindingRewards selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BindingRewards record);

    int updateByPrimaryKey(BindingRewards record);

    List<BindingRewards> selectOne();

    int deleteAll();
}