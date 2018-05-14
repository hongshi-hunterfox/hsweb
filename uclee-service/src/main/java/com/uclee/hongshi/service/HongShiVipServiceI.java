package com.uclee.hongshi.service;

import com.uclee.fundation.data.mybatis.model.AddVipResult;
import com.uclee.fundation.data.mybatis.model.HongShiRecharge;
import com.uclee.fundation.data.mybatis.model.HongShiRechargeRecord;
import com.uclee.fundation.data.mybatis.model.HongShiVip;
import com.uclee.fundation.data.mybatis.model.Lnsurance;

import java.util.List;
import java.util.Map;

public interface HongShiVipServiceI {
	
	AddVipResult addHongShiVipInfo(HongShiVip params);
	
	List<HongShiVip> getVipInfo(String cWeiXinCode);
	
	List<HongShiRechargeRecord> getRechargeRecord(Integer iVipID);

	Integer hongShiRecharge(HongShiRecharge params);
	
	Integer changeVip(Integer cVipLk);
	
	List<Lnsurance> selectUsers(String phone);
	
	Integer getCodeSwitching();
}
