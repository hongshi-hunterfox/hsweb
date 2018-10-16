package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class HongShiVip {
	
	private Integer id;
	
	private String cWeiXinCode;
	
	private String cMobileNumber;
	
	private String cName;

	private String code;
	
	private String cBirthday;

	private String cardCode;
	
	private Integer bIsLunar;
	
	private String cVipCode;
	
	private BigDecimal balance;
	
	private String vipImage;
	
	private double bonusPoints;
	
	private String vipJbarcode;

	private Integer state;

	private Integer disable;

	private Integer isVoucher;

	private Integer vipType;

	private Date endTime;

	private Boolean isAllowRecharge;

	private Boolean isAllowPayment;

	private String cardStatus;
	
	private String cIdNumber;
	
	private String cCompany;
	
	private String cSex;
	
	private Boolean fail;
	
	public Boolean getFail() {
		return fail;
	}
	
	public void setFail(Boolean fail) {
		this.fail = fail;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Boolean getAllowPayment() {
		return isAllowPayment;
	}

	public void setAllowPayment(Boolean allowPayment) {
		isAllowPayment = allowPayment;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Boolean getAllowRecharge() {
		return isAllowRecharge;
	}

	public void setAllowRecharge(Boolean allowRecharge) {
		isAllowRecharge = allowRecharge;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDisable() {
		return disable;
	}

	public void setDisable(Integer disable) {
		this.disable = disable;
	}

	public Integer getIsVoucher() {
		return isVoucher;
	}

	public void setIsVoucher(Integer isVoucher) {
		this.isVoucher = isVoucher;
	}

	public Integer getVipType() {
		return vipType;
	}

	public void setVipType(Integer vipType) {
		this.vipType = vipType;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getVipJbarcode() {
		return vipJbarcode;
	}

	public void setVipJbarcode(String vipJbarcode) {
		this.vipJbarcode = vipJbarcode;
	}

	public double getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(double bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public String getVipImage() {
		return vipImage;
	}

	public void setVipImage(String vipImage) {
		this.vipImage = vipImage;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public HongShiVip setBalance(BigDecimal balance) {
		this.balance = balance;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public HongShiVip setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getcWeiXinCode() {
		return cWeiXinCode;
	}

	public HongShiVip setcWeiXinCode(String cWeiXinCode) {
		this.cWeiXinCode = cWeiXinCode;
		return this;
	}

	public String getcMobileNumber() {
		return cMobileNumber;
	}

	public HongShiVip setcMobileNumber(String cMobileNumber) {
		this.cMobileNumber = cMobileNumber;
		return this;
	}

	public String getcName() {
		return cName;
	}

	public HongShiVip setcName(String cName) {
		this.cName = cName;
		return this;
	}

	public String getcBirthday() {
		return cBirthday;
	}

	public HongShiVip setcBirthday(String cBirthday) {
		this.cBirthday = cBirthday;
		return this;
	}


	public Integer getbIsLunar() {
		return bIsLunar;
	}

	public HongShiVip setbIsLunar(Integer bIsLunar) {
		this.bIsLunar = bIsLunar;
		return this;
	}

	public String getcVipCode() {
		return cVipCode;
	}

	public HongShiVip setcVipCode(String cVipCode) {
		this.cVipCode = cVipCode;
		return this;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getcIdNumber() {
		return cIdNumber;
	}

	public HongShiVip setcIdNumber(String cIdNumber) {
		this.cIdNumber = cIdNumber;
		return this;
	}

	public String getcCompany() {
		return cCompany;
	}

	public HongShiVip setcCompany(String cCompany) {
		this.cCompany = cCompany;
		return this;
	}
	
	public String getcSex() {
		return cSex;
	}

	public void setcSex(String cSex) {
		this.cSex = cSex;
	}


}