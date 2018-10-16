package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class HongShiRecharge {
	
	private String cWeiXinCode;
	
	private BigDecimal nAddMoney;
	
	private String cWeiXinOrderCode;

	public String getcWeiXinCode() {
		return cWeiXinCode;
	}

	public HongShiRecharge setcWeiXinCode(String cWeiXinCode) {
		this.cWeiXinCode = cWeiXinCode;
		return this;
	}

	public BigDecimal getnAddMoney() {
		return nAddMoney;
	}

	public HongShiRecharge setnAddMoney(BigDecimal nAddMoney) {
		this.nAddMoney = nAddMoney;
		return this;
	}

	public String getcWeiXinOrderCode() {
		return cWeiXinOrderCode;
	}

	public HongShiRecharge setcWeiXinOrderCode(String cWeiXinOrderCode) {
		this.cWeiXinOrderCode = cWeiXinOrderCode;
		return this;
	}
	
	

}
