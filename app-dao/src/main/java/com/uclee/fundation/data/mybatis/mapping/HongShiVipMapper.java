package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.HongShiRecharge;
import com.uclee.fundation.data.mybatis.model.HongShiRechargeRecord;
import com.uclee.fundation.data.mybatis.model.HongShiVip;

public interface HongShiVipMapper {
	Integer addVipInfo(HongShiVip params);

	List<HongShiVip> getVipInfo(String cWeiXinCode);
	
	List<HongShiRechargeRecord> getVipRechargeLog(Integer iVipID);
	
	int hongShiRecharge(HongShiRecharge params);	
	
	
}