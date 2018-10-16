package com.uclee.fundation.data.mybatis.model;

public class AdminDataSource {

	private Integer id;
	private String driverClassName;
	
	private String url;
	private String username;
	
	private String password;
	
	private Integer sourceStatus;

	public Integer getId() {
		return id;
	}

	public AdminDataSource setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public AdminDataSource setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public AdminDataSource setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public AdminDataSource setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public AdminDataSource setPassword(String password) {
		this.password = password;
		return this;
	}

	public Integer getSourceStatus() {
		return sourceStatus;
	}

	public AdminDataSource setSourceStatus(Integer sourceStatus) {
		this.sourceStatus = sourceStatus;
		return this;
	}

	
}
