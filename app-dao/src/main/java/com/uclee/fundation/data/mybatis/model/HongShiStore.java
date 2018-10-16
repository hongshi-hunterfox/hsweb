package com.uclee.fundation.data.mybatis.model;

public class HongShiStore {
	
	private Integer id;
	
	private String code;
	
	private String abbreviation;
	
	private String fullName;
	
	private String address;
	
	private String phone;
	
	private String catAbbreviation;
	
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCatAbbreviation() {
		return catAbbreviation;
	}

	public void setCatAbbreviation(String catAbbreviation) {
		this.catAbbreviation = catAbbreviation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
