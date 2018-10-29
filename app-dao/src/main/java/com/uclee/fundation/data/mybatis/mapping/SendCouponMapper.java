package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.SendCoupon;

public interface SendCouponMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SendCoupon record);

	int insertSelective(SendCoupon record);
	
	SendCoupon selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKeySelective(SendCoupon record);

    int updateByPrimaryKey(SendCoupon record);
    
	List<SendCoupon> selectOne();
	
	int deleteAll();
}
