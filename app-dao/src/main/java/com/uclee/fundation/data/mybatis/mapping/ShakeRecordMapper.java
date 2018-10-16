package com.uclee.fundation.data.mybatis.mapping;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.ShakeRecord;

public interface ShakeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShakeRecord record);

    int insertSelective(ShakeRecord record);

    ShakeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShakeRecord record);

    int updateByPrimaryKey(ShakeRecord record);

	List<ShakeRecord> selectTodayByUserId(@Param("userId")Integer userId, @Param("today")Date today);

	int selectTotal(@Param("today")Date today);

	List<ShakeRecord> selectNotShow(@Param("today")Date today);

	List<ShakeRecord> selectToday(@Param("today")Date today);

	int reset();
}