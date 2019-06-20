package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GoodsOrderItem {

	public int orderId;
	public String goodCode;
	public int amount;
	public BigDecimal total;
	public int goodsId;
}
