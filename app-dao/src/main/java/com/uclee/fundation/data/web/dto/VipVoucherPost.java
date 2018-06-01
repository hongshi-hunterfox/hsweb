package com.uclee.fundation.data.web.dto;

import java.util.Map;

public class VipVoucherPost {
	
	private Map<Integer,String> myKey;
	
	private Map<Integer,String> myValue;

	private Map<Integer,String> myValue1;

	private Map<Integer,Integer> mySelect;

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

}
