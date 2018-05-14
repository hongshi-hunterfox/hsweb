package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.Cart;
import com.uclee.fundation.data.web.dto.CartDto;

public interface CartMapper {
    int deleteByPrimaryKey(Integer cartId);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer cartId);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

	Cart selectExisted(Cart cart);

	List<CartDto> selectUserCart(Integer userId);

	CartDto selectByUserIdAndCartId(@Param("userId")Integer userId, @Param("cartId")Integer cartId);
	
	Cart selectValueId(@Param("userId")Integer userId, @Param("cartId")Integer cartId);

}