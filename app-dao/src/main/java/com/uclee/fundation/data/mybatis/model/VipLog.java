package com.uclee.fundation.data.mybatis.model;

public class VipLog {

	private Integer id;
	
	private String vcode;
	
	private String foreignKey;
	
//	private String recordingTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

//	public String getRecordingTime() {
//		return recordingTime;
//	}
//
//	public void setRecordingTime(String recordingTime) {
//		this.recordingTime = recordingTime;
//	}
}
