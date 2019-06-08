package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Disparity {
	public int id;
	public String paramname;
	public BigDecimal disparity;
}
