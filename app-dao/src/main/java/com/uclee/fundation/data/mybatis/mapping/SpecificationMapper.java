package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.Specification;

public interface SpecificationMapper {
    int deleteByPrimaryKey(Integer specificationId);

    int insert(Specification record);

    int insertSelective(Specification record);

    Specification selectByPrimaryKey(Integer specificationId);

    int updateByPrimaryKeySelective(Specification record);

    int updateByPrimaryKey(Specification record);

	List<Specification> getByProductId(Integer productId);
}