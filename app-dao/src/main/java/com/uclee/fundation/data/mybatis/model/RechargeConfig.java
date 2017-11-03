package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RechargeConfig {
    private Integer id;

    private BigDecimal money;

    private BigDecimal rewards;
    
    private String voucherCode;
    
    private Integer type;
    
    private String voucherText;

    private Integer amount;

    private String voucherCodeSecond;

    private String voucherCodeThird;

    private Integer amountSecond;

    private Integer amountThird;

    private Date startTime;

    private Date endTime;

	private String startDateTmp;
	private String endDateTmp;
	private String startTimeTmp;
	private String endTimeTmp;

    private boolean inTime;

	public String getStartDateTmp() {
		return startDateTmp;
	}

	public void setStartDateTmp(String startDateTmp) {
		this.startDateTmp = startDateTmp;
	}

	public String getEndDateTmp() {
		return endDateTmp;
	}

	public void setEndDateTmp(String endDateTmp) {
		this.endDateTmp = endDateTmp;
	}

	public String getStartTimeTmp() {
		return startTimeTmp;
	}

	public void setStartTimeTmp(String startTimeTmp) {
		this.startTimeTmp = startTimeTmp;
	}

	public String getEndTimeTmp() {
		return endTimeTmp;
	}

	public void setEndTimeTmp(String endTimeTmp) {
		this.endTimeTmp = endTimeTmp;
	}

	public boolean isInTime() {
		return inTime;
	}

	public void setInTime(boolean inTime) {
		this.inTime = inTime;
	}

	private String startTimeStr;

    private String endTimeStr;

    private Integer limit;

    private List<String> extra;

	public List<String> getExtra() {
		return extra;
	}

	public void setExtra(List<String> extra) {
		this.extra = extra;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getVoucherCodeSecond() {
		return voucherCodeSecond;
	}

	public void setVoucherCodeSecond(String voucherCodeSecond) {
		this.voucherCodeSecond = voucherCodeSecond;
	}

	public String getVoucherCodeThird() {
		return voucherCodeThird;
	}

	public void setVoucherCodeThird(String voucherCodeThird) {
		this.voucherCodeThird = voucherCodeThird;
	}

	public Integer getAmountSecond() {
		return amountSecond;
	}

	public void setAmountSecond(Integer amountSecond) {
		this.amountSecond = amountSecond;
	}

	public Integer getAmountThird() {
		return amountThird;
	}

	public void setAmountThird(Integer amountThird) {
		this.amountThird = amountThird;
	}

	public String getVoucherText() {
		return voucherText;
	}

	public void setVoucherText(String voucherText) {
		this.voucherText = voucherText;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRewards() {
		return rewards;
	}

	public void setRewards(BigDecimal rewards) {
		this.rewards = rewards;
	}

	public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}