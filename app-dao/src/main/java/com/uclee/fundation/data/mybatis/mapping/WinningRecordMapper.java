package com.uclee.fundation.data.mybatis.mapping;

import java.util.Date;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.WinningRecord;

public interface WinningRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WinningRecord record);

    int insertSelective(WinningRecord record);

    WinningRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WinningRecord record);

    int updateByPrimaryKey(WinningRecord record);

	List<WinningRecord> selectLevelOne(Date today);
	List<WinningRecord> selectLevelTwo(Date today);
	List<WinningRecord> selectLevelThree(Date today);

	int reset();
}