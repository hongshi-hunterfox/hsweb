package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class RechargeConfig {
    private Integer id;

    private BigDecimal money;

    private BigDecimal rewards;
    
    private String voucherCode;
    
    private Integer type;
    
    private String voucherText;
    
    public String getVoucherText() {
		return voucherText;
	}

	public void setVoucherText(String voucherText) {
		this.voucherText = voucherText;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRewards() {
		return rewards;
	}

	public void setRewards(BigDecimal rewards) {
		this.rewards = rewards;
	}

	public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}