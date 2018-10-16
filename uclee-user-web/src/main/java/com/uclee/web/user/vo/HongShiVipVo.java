package com.uclee.web.user.vo;

import java.math.BigDecimal;

public class HongShiVipVo{
	private String cVipCode;
	
	private BigDecimal balance;

	public String getcVipCode() {
		return cVipCode;
	}

	public HongShiVipVo setcVipCode(String cVipCode) {
		this.cVipCode = cVipCode;
		return this;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public HongShiVipVo setBalance(BigDecimal balance) {
		this.balance = balance;
		return this;
	}

	
	

}
