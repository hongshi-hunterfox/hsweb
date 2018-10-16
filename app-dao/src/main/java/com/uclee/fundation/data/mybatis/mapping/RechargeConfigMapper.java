package com.uclee.fundation.data.mybatis.mapping;

import java.math.BigDecimal;
import java.util.List;

<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;

=======
>>>>>>> f062f2ca2b06e4e4a3db08dda385a31ebe085515
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
<<<<<<< HEAD
	
	RechargeConfig selectByMoney(@Param("money") BigDecimal money, @Param("rewards") BigDecimal rewards);
	
	RechargeConfig getByMoney(BigDecimal money);
=======

	RechargeConfig selectByMoney(BigDecimal money);
>>>>>>> f062f2ca2b06e4e4a3db08dda385a31ebe085515
}