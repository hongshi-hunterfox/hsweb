package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.Freight;
import com.uclee.fundation.data.mybatis.model.IntegralInGifts;

public interface IntegralInGiftsMapper {
	  int deleteByPrimaryKey(Integer id);

	  int insert(IntegralInGifts record);

	  int insertSelective(IntegralInGifts record);

	  IntegralInGifts selectByPrimaryKey(Integer id);

	  int updateByPrimaryKeySelective(IntegralInGifts record);

	  int updateByPrimaryKey(IntegralInGifts record);

	  List<IntegralInGifts> selectOne();
	  
	  List<IntegralInGifts> selectDay();

	  int deleteAll();
}
