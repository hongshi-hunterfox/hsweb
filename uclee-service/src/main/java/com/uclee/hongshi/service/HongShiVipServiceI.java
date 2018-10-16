package com.uclee.hongshi.service;

import com.uclee.fundation.data.mybatis.model.AddVipResult;
import com.uclee.fundation.data.mybatis.model.ChongzhiDetailed;
import com.uclee.fundation.data.mybatis.model.HongShiRecharge;
import com.uclee.fundation.data.mybatis.model.HongShiRechargeRecord;
import com.uclee.fundation.data.mybatis.model.HongShiVip;
import com.uclee.fundation.data.mybatis.model.IntegralRecharge;
import com.uclee.fundation.data.mybatis.model.Lnsurance;
import com.uclee.fundation.data.mybatis.model.Orders;
import com.uclee.fundation.data.mybatis.model.RetailDetails;
import com.uclee.fundation.data.mybatis.model.UnderlineOrders;

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
	
	List<Orders> selectOrders(String danhao);
	
	List<UnderlineOrders> selectUnderlineOrders(String danhao);
	
	List<RetailDetails> selectRetailDetails(String danhao);
	
	List<ChongzhiDetailed> selectChongzhiDetailed(String danhao);
	
	List<IntegralRecharge> selectIntegralRecharge(String danhao);
	
}
