package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.DeliverAddr;

public interface DeliverAddrMapper {
    int deleteByPrimaryKey(Integer deliveraddrId);

    int insert(DeliverAddr record);

    int insertSelective(DeliverAddr record);

    DeliverAddr selectByPrimaryKey(Integer deliveraddrId);

    int updateByPrimaryKeySelective(DeliverAddr record);

    int updateByPrimaryKey(DeliverAddr record);

	List<DeliverAddr> selectByUserId(Integer userId);

	DeliverAddr selectByUserAndAddrId(@Param("userId")Integer userId, @Param("deliveraddrId")Integer deliveraddrId);

	int deleteByUserIdAndAddrId(@Param("userId")Integer userId, @Param("deliveraddrId")Integer deliveraddrId);

	DeliverAddr selectDefaultByUserId(Integer userId);

	int clearDeault(Integer userId);
}