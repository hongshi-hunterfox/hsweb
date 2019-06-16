package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.Disparity;
import com.uclee.fundation.data.mybatis.model.GoodsSpecifications;

public interface DisparityMapper {
	List<Disparity> selectAll();
	int deleteByPrimaryKey(Integer id);
	int insert(Disparity disparit);
	int insertGoodsSpecifications(GoodsSpecifications goodsSpecifications);
	Disparity selectByDisParity(@Param("specId")Integer specId, @Param("goodsId") Integer goodsId, @Param("userId")Integer userId);
}
