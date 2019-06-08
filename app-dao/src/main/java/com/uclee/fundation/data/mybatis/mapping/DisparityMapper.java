package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.Disparity;
import com.uclee.fundation.data.mybatis.model.GoodsSpecifications;

public interface DisparityMapper {
	List<Disparity> selectAll();
	int deleteByPrimaryKey(Integer id);
	int insert(Disparity disparit);
	int insertGoodsSpecifications(GoodsSpecifications goodsSpecifications);
}
