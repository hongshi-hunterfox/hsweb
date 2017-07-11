package com.uclee.fundation.data.mybatis.mapping;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.LotteryRecord;

public interface LotteryRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LotteryRecord record);

    int insertSelective(LotteryRecord record);

    LotteryRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryRecord record);

    int updateByPrimaryKey(LotteryRecord record);

	List<LotteryRecord> selectTodayByUserId(@Param("userId")Integer userId, @Param("today")Date today);
}