package com.uclee.hongshi.service.impl;

import com.uclee.fundation.data.mybatis.mapping.HongShiVipMapper;
import com.uclee.fundation.data.mybatis.model.AddVipResult;
import com.uclee.fundation.data.mybatis.model.HongShiRecharge;
import com.uclee.fundation.data.mybatis.model.HongShiRechargeRecord;
import com.uclee.fundation.data.mybatis.model.HongShiVip;
import com.uclee.fundation.data.mybatis.model.Lnsurance;
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

}
