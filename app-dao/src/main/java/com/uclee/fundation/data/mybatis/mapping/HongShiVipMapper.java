package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.AddVipResult;
import com.uclee.fundation.data.mybatis.model.HongShiRecharge;
import com.uclee.fundation.data.mybatis.model.HongShiRechargeRecord;
import com.uclee.fundation.data.mybatis.model.HongShiVip;
import com.uclee.fundation.data.mybatis.model.VipLog;

public interface HongShiVipMapper {
	AddVipResult addVipInfo(HongShiVip params);

	List<HongShiVip> getVipInfo(String cWeiXinCode);
	
	List<HongShiRechargeRecord> getVipRechargeLog(Integer iVipID);

	int hongShiRecharge(HongShiRecharge params);

	Integer changeVip(Integer cVipLk);

	Integer getCodeSwitching();
	
	int insertVipLog(VipLog record);
}