package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class SendCoupon {
	 private Integer id;

	 private String voucher;

	 private Integer amount;
	 
	 private BigDecimal money;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher== null ? null : voucher.trim();
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}
