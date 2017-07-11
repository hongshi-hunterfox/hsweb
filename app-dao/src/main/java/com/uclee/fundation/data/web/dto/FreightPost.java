package com.uclee.fundation.data.web.dto;

import java.util.Map;

public class FreightPost {
	
	private Map<Integer,Double> myKey;
	
	private Map<Integer,String> myValue;
	
	private Map<Integer,Integer> mySelect;

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
