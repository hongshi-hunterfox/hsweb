package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.sql.Date;

public class ChongzhiDetailed {
	private Integer id;
	
	private String danhao;
	
	private Integer pid;
	
	private String bianhao;
	
	private Integer jifen;
	
	private BigDecimal jine;
	
	private String beizhu;
	
	private String bumen;

	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public BigDecimal getJine() {
		return jine;
	}

	public void setJine(BigDecimal jine) {
		this.jine = jine;
	}

	private BigDecimal xianjin;
	
	private String riqi;

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

	public String getBianhao() {
		return bianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public Integer getJifen() {
		return jifen;
	}

	public void setJifen(Integer jifen) {
		this.jifen = jifen;
	}

	

	public BigDecimal getXianjin() {
		return xianjin;
	}

	public void setXianjin(BigDecimal xianjin) {
		this.xianjin = xianjin;
	}

	public String getRiqi() {
		return riqi;
	}

	public void setRiqi(String riqi) {
		this.riqi = riqi;
	}
	

}
