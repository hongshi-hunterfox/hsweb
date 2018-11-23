package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.Specification;
import com.uclee.fundation.data.mybatis.model.SpecificationValue;

public interface SpecificationValueMapper {
    int deleteByPrimaryKey(Integer valueId);

    int insert(SpecificationValue record);

    int insertSelective(SpecificationValue record);

    SpecificationValue selectByPrimaryKey(Integer valueId);
    
    List<SpecificationValue> selectByPrimaryKeys(Integer valueId);

    int updateByPrimaryKeySelective(SpecificationValue record);

    int updateByPrimaryKey(SpecificationValue record);
    
    int updateGuiGe(SpecificationValue record);

	SpecificationValue selectByProductIdAndValueId(@Param("productId")Integer productId,@Param("valueId") Integer valueId);

	void delByProductId(Integer productId);

	SpecificationValue selectByProductIdLimit(Integer productId);

	List<SpecificationValue> selectByProductId(Integer productId);

    List<String> selectHsCodeByProductId(Integer productId);
    
    List<SpecificationValue> selectByHsGoods(Integer valueId);
    
    SpecificationValue selectGoods(Integer valueId);
}