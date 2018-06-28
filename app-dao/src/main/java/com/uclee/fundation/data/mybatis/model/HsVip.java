package com.uclee.fundation.data.mybatis.model;

public class HsVip {

	private Integer id;
	
	private String vCode;

	private String vName;
	
	private String vBirthday; 
	
	private String vNumber;
	
	private String vIdNumber;
	
	private String vCompany;
	
	private Integer vState;
	
	private String code;
	
	private String vSex;
	
	private int isLose;


	public int getIsLose() {
		return isLose;
	}

	public void setIsLose(int isLose) {
		this.isLose = isLose;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getvBirthday() {
		return vBirthday;
	}

	public void setvBirthday(String vBirthday) {
		this.vBirthday = vBirthday;
	}

	public String getvNumber() {
		return vNumber;
	}

	public void setvNumber(String vNumber) {
		this.vNumber = vNumber;
	}

	public String getvIdNumber() {
		return vIdNumber;
	}

	public void setvIdNumber(String vIdNumber) {
		this.vIdNumber = vIdNumber;
	}

	public String getvCompany() {
		return vCompany;
	}

	public void setvCompany(String vCompany) {
		this.vCompany = vCompany;
	}
	public String getvCode() {
		return vCode;
	}

	public void setvCode(String vCode) {
		this.vCode = vCode;
	}
	
	public Integer getvState() {
		return vState;
	}

	public void setvState(Integer vState) {
		this.vState = vState;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getvSex() {
		return vSex;
	}

	public void setvSex(String vSex) {
		this.vSex = vSex;
	}
}
