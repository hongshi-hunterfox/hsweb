package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class GoodsSpecifications {
	public int id;
	public int goodsId;
	public String name;
	public BigDecimal hsPrice;
	public BigDecimal vipPrice;
	public int specPack;
	public List<Integer> storeIds;
	public String code;
}