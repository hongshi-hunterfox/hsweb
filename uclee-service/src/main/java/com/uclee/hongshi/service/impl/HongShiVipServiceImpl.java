package com.uclee.hongshi.service.impl;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.data.mybatis.mapping.HongShiVipMapper;
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
import com.uclee.hongshi.service.HongShiVipServiceI;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class HongShiVipServiceImpl implements HongShiVipServiceI{
	
	@Autowired
	private HongShiVipMapper hongShiVipMapper;

	@Override
	public AddVipResult addHongShiVipInfo(HongShiVip params) {
		return hongShiVipMapper.addVipInfo(params);
	}

	@Override
	public List<HongShiRechargeRecord> getRechargeRecord(Integer iVipID) {
		return hongShiVipMapper.getVipRechargeLog(iVipID);
	}

	@Override
	public List<HongShiVip> getVipInfo(String cWeiXinCode) {
		return hongShiVipMapper.getVipInfo(cWeiXinCode);
	}

	@Override
	public Integer hongShiRecharge(HongShiRecharge params) {
		return hongShiVipMapper.hongShiRecharge(params);
	}
	
	
	@Override
	public Integer changeVip(Integer cVipLk) {
		return hongShiVipMapper.changeVip(cVipLk);
	}

	@Override
	public List<Lnsurance> selectUsers(String phone) {
		return hongShiVipMapper.selectUsers(phone);
	}

	@Override
	public Integer getCodeSwitching() {
		return hongShiVipMapper.getCodeSwitching();
	}
	
		@Override
	public List<Orders> selectOrders(String danhao) {
		return hongShiVipMapper.selectOrders(danhao);
	}
	
	@Override
	public List<UnderlineOrders> selectUnderlineOrders(String danhao) {		
		return hongShiVipMapper.selectUnderlineOrders(danhao);
	}
	
	@Override
	public List<RetailDetails> selectRetailDetails(String danhao) {
		return hongShiVipMapper.selectRetailDetails(danhao);
	}
	
	@Override
	public List<ChongzhiDetailed> selectChongzhiDetailed(String danhao) {
		return hongShiVipMapper.selectChongzhiDetailed(danhao);
	}
	
	@Override
	public List<IntegralRecharge> selectIntegralRecharge(String danhao) {
		return hongShiVipMapper.selectIntegralRecharge(danhao);
	}
	

}
