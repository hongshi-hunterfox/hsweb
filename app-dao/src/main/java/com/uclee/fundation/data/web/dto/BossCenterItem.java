package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;

public class BossCenterItem {
	
	private String coption;
	
	private BigDecimal value;
	
	private String valueType;

	public String getCoption() {
		return coption;
	}

	public void setCoption(String coption) {
		this.coption = coption;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}
