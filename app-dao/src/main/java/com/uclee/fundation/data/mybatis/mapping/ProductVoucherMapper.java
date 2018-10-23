package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.ProductVoucher;
import com.uclee.fundation.data.web.dto.ProductVoucherPost;

public interface ProductVoucherMapper {
	
	int insert(ProductVoucher record);
	
	int updateByPrimaryKey(ProductVoucher productVoucher);
	
	int deleteByPrimaryKey(Integer id);
	
	List<ProductVoucher> selectAllProductVoucher();
	
	ProductVoucher selectByPrimaryKey(Integer id);
	
	int insertCouponsProductsLinks (ProductVoucherPost record);
	
	ProductVoucherPost selectInspectionAlreadyExists(@Param("vid")Integer vid, @Param("pid")Integer pid);
	
	List<ProductVoucherPost> selectSelectedProducts(Integer vid);
	
	int delCouponsProductsLinks(@Param("vid")Integer vid, @Param("pid")Integer pid);
	
	int deleteByLinks(Integer vid);
	
	List<ProductVoucher> getProductCoupons(Integer pid);
}
