package com.uclee.fundation.data.web.dto;

import java.util.Date;
import java.util.Map;

public class LotteryConfigPost {
	
	private Map<Integer,Integer> value;
	
	private Map<Integer,String> key;
	
	private Map<Integer,Integer> count;
	
	private Map<Integer,Integer> rate;
	
	private Integer limits;
	
	private String timeStart;
	
	private String dateStart;
	
	private String dateEnd;
	
	private String timeEnd;

	public Integer getLimits() {
		return limits;
	}

	public void setLimits(Integer limits) {
		this.limits = limits;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Map<Integer, Integer> getCount() {
		return count;
	}

	public void setCount(Map<Integer, Integer> count) {
		this.count = count;
	}

	public Map<Integer, Integer> getRate() {
		return rate;
	}

	public void setRate(Map<Integer, Integer> rate) {
		this.rate = rate;
	}

	public Map<Integer, Integer> getValue() {
		return value;
	}

	public void setValue(Map<Integer, Integer> value) {
		this.value = value;
	}

	public Map<Integer, String> getKey() {
		return key;
	}

	public void setKey(Map<Integer, String> key) {
		this.key = key;
	}
	

}
