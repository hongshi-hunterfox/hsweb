package com.uclee.hongshi.service;

import com.uclee.fundation.data.mybatis.model.HongShiRecharge;
import com.uclee.fundation.data.mybatis.model.HongShiRechargeRecord;
import com.uclee.fundation.data.mybatis.model.HongShiVip;

import java.util.List;

public interface HongShiVipServiceI {
	Integer addHongShiVipInfo(HongShiVip params);
	
	List<HongShiVip> getVipInfo(String cWeiXinCode);
	
	List<HongShiRechargeRecord> getRechargeRecord(Integer iVipID);

	Integer hongShiRecharge(HongShiRecharge params);
}
