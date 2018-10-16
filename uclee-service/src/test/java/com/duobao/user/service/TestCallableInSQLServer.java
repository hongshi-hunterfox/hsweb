package com.duobao.user.service;

import com.alibaba.fastjson.JSON;
import com.uclee.dynamicDatasource.DataSourceFacade;
import com.uclee.fundation.data.mybatis.model.AddVipResult;
import com.uclee.fundation.data.mybatis.model.HongShiRecharge;
import com.uclee.fundation.data.mybatis.model.HongShiRechargeRecord;
import com.uclee.fundation.data.mybatis.model.HongShiVip;
import com.uclee.hongshi.service.HongShiVipServiceI;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class TestCallableInSQLServer extends AbstractServiceTests{
	
	@Autowired
	private HongShiVipServiceI hongShiVipService;
	@Autowired
	private DataSourceFacade datasource;
	@Test
	public void testAddVip(){
		datasource.switchDataSource("kf");
		List<HongShiVip> dd= hongShiVipService.getVipInfo("ocydnwkicQdKQgz5x4Pedh5LpFUM");
		System.out.println("res:"+JSON.toJSONString(dd));
	}
	
	@Test
	public void tesRechargeAddVip(){
		HongShiRecharge dd=new HongShiRecharge().setcWeiXinCode("ocydnwkicQdKQgz5x4Pedh5LpFUM")
				.setcWeiXinOrderCode("微信充值测试")
				.setnAddMoney(new BigDecimal(10));
		Integer res=hongShiVipService.hongShiRecharge(dd);
		System.out.println("res:"+res);
	}
	
	@Test
	public void testGetVip(){
		List<HongShiVip> res=hongShiVipService.getVipInfo("oH7hfuEN8qnZjC7fr2_zUFK7eVl8");
		System.out.println("res:"+JSON.toJSONString(res));
	}

	
	@Test
	public void testVipRecord(){
		List<HongShiRechargeRecord> t = hongShiVipService.getRechargeRecord(82979);
		System.out.println("ffff:"+JSON.toJSONString(t));
	}
}
