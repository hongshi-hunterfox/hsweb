package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.LinkCoupon;
import com.uclee.fundation.data.mybatis.model.LinkCouponLogs;

public interface LinkCouponMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(LinkCoupon record);

	int insertSelective(LinkCoupon record);
	
	LinkCoupon selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKeySelective(LinkCoupon record);

    int updateByPrimaryKey(LinkCoupon record);
    
	List<LinkCoupon> selectOne();
	
	int deleteAll();
	
	List<LinkCoupon> selectByPrimaryKey();
	
	int insertLinkCouponLog(LinkCouponLogs record);
	
	List<LinkCouponLogs> selectLinkCoponLog(@Param("name") String name, @Param("oauthId") String oauthId);
}
