package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.BirthVoucher;
import com.uclee.fundation.data.mybatis.model.BirthVoucher;
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
import com.uclee.fundation.data.mybatis.model.VipLog;
import com.uclee.fundation.data.mybatis.model.VipVoucher;

public interface HongShiVipMapper {
	AddVipResult addVipInfo(HongShiVip params);

	List<HongShiVip> getVipInfo(String cWeiXinCode);
	
	List<HongShiRechargeRecord> getVipRechargeLog(Integer iVipID);

	int hongShiRecharge(HongShiRecharge params);

	Integer changeVip(Integer cVipLk);
	
	List<Lnsurance> getUsers(String oauthId);
	
	List<Lnsurance> selectUsers(String phone);
	
	List<HongShiVip> selectVip(String cMobileNumber);
	
	Integer getCodeSwitching(); 
	
	List<Orders> selectOrders(String danhao);
	
	int deleteAll();
	
	List<UnderlineOrders> selectUnderlineOrders(String danhao);
	
	List<VipVoucher> selectAll();
	
	List<RetailDetails> selectRetailDetails(String danhao);
	
	List<ChongzhiDetailed> selectChongzhiDetailed(String danhao);
	
	int insertSelective(VipVoucher record);
	
	int insertVipLog(VipLog record);
	
	List<IntegralRecharge> selectIntegralRecharge(String danhao);

	List<Orders> selectRecord(String merchantOrderNumber);
}