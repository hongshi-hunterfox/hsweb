package com.uclee.fundation.data.mybatis.mapping;

import java.math.BigDecimal;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

	List<Category> selectAll();

	List<Category> selectByParentId(Integer parentId);

    Category selectByPId(Integer productId);

    Category selectByName(String category);
    List<Category> selectBybatchDiscount(String category);
}