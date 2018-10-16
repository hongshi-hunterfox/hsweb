package com.uclee.fundation.data.mybatis.mapping;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.SignRecord;

public interface SignRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SignRecord record);

    int insertSelective(SignRecord record);

    SignRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignRecord record);

    int updateByPrimaryKey(SignRecord record);

	SignRecord selectToday(@Param("userId")Integer userId, @Param("signTime")Date signTime);
	
	SignRecord selectAccumulation(Integer userId);
	
	SignRecord getAccumulation(Integer userId);

}