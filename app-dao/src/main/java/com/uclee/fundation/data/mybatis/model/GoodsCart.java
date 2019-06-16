package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class GoodsCart {

	public int id;
	public int goodsId;
	public int specId;
	public int userId;
	public int amount;
	public BigDecimal hsPrice;
	public BigDecimal vipPrice;
	public String goodsImg;
	public String goodsName;
	public String name;
	public List<Category> category;
	public String code;
	public String flavorname;
}
