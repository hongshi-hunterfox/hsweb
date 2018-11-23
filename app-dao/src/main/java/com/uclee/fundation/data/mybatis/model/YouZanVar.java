package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class YouZanVar {

	private Integer id;
	private String platform;
    private String name;
    private String value;
    private Date storageTime;
    private Integer expiresIn;
    private String refreshToken;
    
    public String getRefreshToken() {
		return refreshToken;
	}
    
    public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
    
    public Integer getExpiresIn() {
		return expiresIn;
	}
    public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getStorageTime() {
		return storageTime;
	}
	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}
    
    
}
