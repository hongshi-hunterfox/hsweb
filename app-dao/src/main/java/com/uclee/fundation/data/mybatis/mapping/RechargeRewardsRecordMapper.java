package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.RechargeRewardsRecord;
import org.apache.ibatis.annotations.Param;

public interface RechargeRewardsRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRewardsRecord record);

    int insertSelective(RechargeRewardsRecord record);

    RechargeRewardsRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRewardsRecord record);

    int updateByPrimaryKey(RechargeRewardsRecord record);

    RechargeRewardsRecord selectByConfigIdAndUserId(@Param("configId") Integer configId, @Param("userId")Integer userId);
}