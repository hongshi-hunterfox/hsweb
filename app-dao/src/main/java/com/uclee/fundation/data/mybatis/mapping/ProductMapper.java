package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.mybatis.model.ProductParameters;
import com.uclee.fundation.data.mybatis.model.ProductGe;
import com.uclee.fundation.data.mybatis.model.SpecificationValue;
import com.uclee.fundation.data.mybatis.model.UserLimit;
//import com.uclee.fundation.data.mybatis.model.ProductSale;
import com.uclee.fundation.data.web.dto.ProductDto;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

	ProductDto getProductById(Integer productId);

	List<ProductDto> selectOneImage(Integer productId);

	List<ProductDto> getAllProduct(@Param("categoryId") Integer categoryId, @Param("isSaleDesc") Boolean isSaleDesc, @Param("isPriceDesc") Boolean isPriceDesc, @Param("keyword") String keyword, @Param("naviId") Integer naviId);

    List<ProductDto> selectQuickNaviProduct(Integer naviId);
    
    List<ProductGe> selectProductsCategories(Integer categoryId);

    List<ProductDto> getAllProductByCatId(Integer categoryId);

    Product selectByTitle(String title);
    
    Product selectByExplain(String explain);

    int getMaxSortValue();
    
    List<ProductParameters> getParameters(Integer productId);
    
    List<ProductParameters> selectParameters(Integer productId);
    
    int insertParameters(ProductParameters record);
    
    int updateParameters(ProductParameters record);
    
    ProductParameters obtainParameters(Integer id);
    
    List<ProductParameters> obtainParameter(Integer id);
    
    int insertUserLimit(UserLimit userLimit);
    
    List<UserLimit> selectByLimit(@Param("userId")Integer userId, @Param("productId")Integer productId);
}