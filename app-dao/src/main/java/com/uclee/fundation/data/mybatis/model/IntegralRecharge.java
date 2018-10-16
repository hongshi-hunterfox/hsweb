package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class IntegralRecharge {
    private Integer id;
	
	private String danhao;
	
	private Integer pid;
	
	private BigDecimal hejijine;
	
	private String beizhu;
	
	private String riqi;
	

	public String getRiqi() {
		return riqi;
	}

	public void setRiqi(String riqi) {
		this.riqi = riqi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDanhao() {
		return danhao;
	}

	public void setDanhao(String danhao) {
		this.danhao = danhao;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public BigDecimal getHejijine() {
		return hejijine;
	}

	public void setHejijine(BigDecimal hejijine) {
		this.hejijine = hejijine;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

}
