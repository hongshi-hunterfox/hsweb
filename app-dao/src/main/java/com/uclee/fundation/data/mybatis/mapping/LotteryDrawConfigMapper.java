package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.LotteryDrawConfig;

public interface LotteryDrawConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LotteryDrawConfig record);

    int insertSelective(LotteryDrawConfig record);

    LotteryDrawConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryDrawConfig record);

    int updateByPrimaryKey(LotteryDrawConfig record);

	List<LotteryDrawConfig> selectAll();

	int deleteAll();

	LotteryDrawConfig selectByConfigCode(String configCode);
}