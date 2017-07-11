package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.Freight;

public interface FreightMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Freight record);

    int insertSelective(Freight record);

    Freight selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Freight record);

    int updateByPrimaryKey(Freight record);

	List<Freight> selectAll();

	int deleteAll();

	List<Freight> selectAllAsc();
}