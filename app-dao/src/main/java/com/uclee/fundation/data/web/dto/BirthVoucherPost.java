package com.uclee.fundation.data.web.dto;

import java.util.Map;

public class BirthVoucherPost {
	
	private Map<Integer,String> myKey;
	
	private Map<Integer,String> myValue;

	private Map<Integer,String> myValue1;

	private Map<Integer,Integer> mySelect;

	private String startTimeStr;

	private String endTimeStr;
	
	private int day;
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public int getDay() {
		return day;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public Map<Integer, String> getMyValue1() {
		return myValue1;
	}

	public void setMyValue1(Map<Integer, String> myValue1) {
		this.myValue1 = myValue1;
	}

	public Map<Integer, Integer> getMySelect() {
		return mySelect;
	}

	public void setMySelect(Map<Integer, Integer> mySelect) {
		this.mySelect = mySelect;
	}

	public Map<Integer, String> getMyKey() {
		return myKey;
	}

	public void setMyKey(Map<Integer, String> myKey) {
		this.myKey = myKey;
	}

	public Map<Integer, String> getMyValue() {
		return myValue;
	}

	public void setMyValue(Map<Integer, String> myValue) {
		this.myValue = myValue;
	}

}
