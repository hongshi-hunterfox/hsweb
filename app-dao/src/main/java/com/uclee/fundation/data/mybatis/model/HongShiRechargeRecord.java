package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class HongShiRechargeRecord {

	/**
	 * 建立日期
	 */
	private String dealTim;
	/**
	 * 来源
	 */
	private String source;
	
	/**
	 * 单号
	 */
	private String billCode;
	
	/**
	 * 交易金额
	 */
	private BigDecimal amount;
	
	/**
	 * 余额
	 */
	private BigDecimal balance;
	
	private Integer bonusPoints;
	
	private Integer integral;
	private Integer logType;

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(Integer bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public String getDealTim() {
		return dealTim;
	}

	public HongShiRechargeRecord setDealTim(String dealTim) {
		this.dealTim = dealTim;
		return this;
	}

	public String getSource() {
		return source;
	}

	public HongShiRechargeRecord setSource(String source) {
		this.source = source;
		return this;
	}

	public String getBillCode() {
		return billCode;
	}

	public HongShiRechargeRecord setBillCode(String billCode) {
		this.billCode = billCode;
		return this;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public HongShiRechargeRecord setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public HongShiRechargeRecord setBalance(BigDecimal balance) {
		this.balance = balance;
		return this;
	}


	
}
