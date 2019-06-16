package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

import lombok.Data;

@Data
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
	
	//外键
	private String oauthId;
	
}
