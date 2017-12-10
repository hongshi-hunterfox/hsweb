package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.FullCut;

import java.util.Date;
import java.util.List;

public interface FullCutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FullCut record);

    int insertSelective(FullCut record);

    FullCut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FullCut record);

    int updateByPrimaryKey(FullCut record);

    List<FullCut> selectAll();

    int deleteAll();

    List<FullCut> selectAllActive(Date date);
}