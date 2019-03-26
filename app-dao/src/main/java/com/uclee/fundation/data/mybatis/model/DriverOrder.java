package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class DriverOrder {
	private Integer id;
	private String code;
	private Date pickingTime;
	private String phone;
	private String address;
	private String departmentNumber;
	private String department;
	private String outerOrderCode;
	private String type;
	private String Time;
	private Integer processingState;
	private Integer isSelfPick;
		
	public Integer getIsSelfPick() {
		return isSelfPick;
	}
	public void setIsSelfPick(Integer isSelfPick) {
		this.isSelfPick = isSelfPick;
	}
	public Integer getProcessingState() {
		return processingState;
	}
	public void setProcessingState(Integer processingState) {
		this.processingState = processingState;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOuterOrderCode() {
		return outerOrderCode;
	}
	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getPickingTime() {
		return pickingTime;
	}
	public void setPickingTime(Date pickingTime) {
		this.pickingTime = pickingTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDepartmentNumber() {
		return departmentNumber;
	}
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
