package com.uclee.fundation.data.web.dto;

import java.util.List;
import java.util.Map;

import com.uclee.fundation.data.mybatis.model.Freight;

public class FreightPost {
	
	private Map<Integer,Double> myKey;
	
	private Map<Integer,String> myValue0;
	
	private Map<Integer,String> myValue;

	private Map<Integer,String> myValue1;

	private Map<Integer,Integer> mySelect;

	private String startTimeStr;

	private String endTimeStr;

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
	
	public Map<Integer, String> getMyValue0() {
		return myValue0;
	}

	public void setMyValue0(Map<Integer, String> myValue0) {
		this.myValue0 = myValue0;
	}

	public Map<Integer, Integer> getMySelect() {
		return mySelect;
	}

	public void setMySelect(Map<Integer, Integer> mySelect) {
		this.mySelect = mySelect;
	}

	public Map<Integer, Double> getMyKey() {
		return myKey;
	}

	public void setMyKey(Map<Integer, Double> myKey) {
		this.myKey = myKey;
	}

	public Map<Integer, String> getMyValue() {
		return myValue;
	}

	public void setMyValue(Map<Integer, String> myValue) {
		this.myValue = myValue;
	}


}
