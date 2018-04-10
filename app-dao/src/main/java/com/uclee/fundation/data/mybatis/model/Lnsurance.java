package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class Lnsurance {

	private Integer id;
	
	private Integer vipId;
	
	private String oauthId;
	
	private Date createTime;
	
	private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVipId() {
		return vipId;
	}

	public void setVipId(Integer vipId) {
		this.vipId = vipId;
	}

	public String getOauthId() {
		return oauthId;
	}

	public void setOauthId(String oauthId) {
		this.oauthId = oauthId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
