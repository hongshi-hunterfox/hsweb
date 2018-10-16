package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.mybatis.model.ProductCategoryLink;
import com.uclee.fundation.data.mybatis.model.ProductCategoryLinkKey;

import java.util.List;

public interface ProductCategoryLinkMapper {
    int deleteByPrimaryKey(ProductCategoryLinkKey key);

    int insert(ProductCategoryLinkKey record);

    int insertSelective(ProductCategoryLinkKey record);

	ProductCategoryLinkKey selectByProductId(Integer productId);

    List<ProductCategoryLinkKey> selectByCId(Integer categoryId);
}