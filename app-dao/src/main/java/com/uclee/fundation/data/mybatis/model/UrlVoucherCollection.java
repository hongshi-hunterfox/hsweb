package com.uclee.fundation.data.mybatis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import scala.annotation.meta.setter;

public class UrlVoucherCollection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String activityname;
	private int dailylimit;
	private int frequencylimit;
	private Date starttime;
	private Date endtime;
	private String ruletext;
	private String start;
	private String end;
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getRuletext() {
		return ruletext;
	}
	public void setRuletext(String ruletext) {
		this.ruletext = ruletext;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivityname() {
		return activityname;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public int getDailylimit() {
		return dailylimit;
	}
	public void setDailylimit(int dailylimit) {
		this.dailylimit = dailylimit;
	}
	public int getFrequencylimit() {
		return frequencylimit;
	}
	public void setFrequencylimit(int frequencylimit) {
		this.frequencylimit = frequencylimit;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	

}
