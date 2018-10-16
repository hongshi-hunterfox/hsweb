package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.HomeQuickNavi;

import java.util.List;

public interface HomeQuickNaviMapper {
    int deleteByPrimaryKey(Integer naviId);

    int insert(HomeQuickNavi record);

    int insertSelective(HomeQuickNavi record);

    HomeQuickNavi selectByPrimaryKey(Integer naviId);

    int updateByPrimaryKeySelective(HomeQuickNavi record);

    int updateByPrimaryKey(HomeQuickNavi record);

    List<HomeQuickNavi> selectAll();
}