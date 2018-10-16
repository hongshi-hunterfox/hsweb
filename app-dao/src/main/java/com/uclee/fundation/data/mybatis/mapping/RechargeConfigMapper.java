package com.uclee.fundation.data.mybatis.mapping;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.RechargeConfig;

public interface RechargeConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeConfig record);

    int insertSelective(RechargeConfig record);

    RechargeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeConfig record);

    int updateByPrimaryKey(RechargeConfig record);

	List<RechargeConfig> selectAll();
	
	List<RechargeConfig> selectMonPeiZhi();

	int deleteAll();
	
	RechargeConfig selectByMoney(@Param("money") BigDecimal money, @Param("rewards") BigDecimal rewards);
	
	RechargeConfig getByMoney(BigDecimal money);
}