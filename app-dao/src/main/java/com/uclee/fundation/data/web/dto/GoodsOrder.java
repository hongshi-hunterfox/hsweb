package com.uclee.fundation.data.web.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GoodsOrder {
	public int id;
	//订单金额
	public BigDecimal money;
	//店铺id
	public String storeId;
	//台号
	public int station;
	//是否堂食
	public int type;
	//单号
	public String tardno;
	//外键
	public String weixincode;
	//备注
	public String remarks;
	//整单数量合计
	public int totalnum;
	//优惠金额
	public BigDecimal singledisco;
	//餐盒费
	public BigDecimal boxmoney;
}
