package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.Category;
import com.uclee.fundation.data.mybatis.model.ProductGroup;

public interface ProductGroupMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(ProductGroup record);
    
	List<ProductGroup> selectAll();

    int insertSelective(ProductGroup record);

    ProductGroup selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKeySelective(ProductGroup record);

    int updateByPrimaryKey(ProductGroup record);

	List<ProductGroup> selectByTags(String[] groupName);
	
	ProductGroup selectByGroupName(String groupName);
	
}