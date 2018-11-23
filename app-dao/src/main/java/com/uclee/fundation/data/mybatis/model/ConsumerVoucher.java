package com.uclee.fundation.data.mybatis.model;
public class ConsumerVoucher {
	private Integer id;
	
	private String voucherCode;
	
	private Integer amount;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode== null?null : voucherCode.trim();
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}