package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class BargainLog {
	private int id;
	private int pid;
	private int uid;
	private String openId;
	private double randomAmount;
	private Date launchTime;
	private String invitationCode;
	private Integer valueId;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Integer getValueId() {
		return valueId;
	}
	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public double getRandomAmount() {
		return randomAmount;
	}
	public void setRandomAmount(double randomAmount) {
		this.randomAmount = randomAmount;
	}
	public Date getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}
}
