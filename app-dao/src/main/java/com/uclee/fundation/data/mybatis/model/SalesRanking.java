package com.uclee.fundation.data.mybatis.model;

public class SalesRanking {

	String SortName;
	String GoodsName;
	Integer Sales;
	public String getSortName() {
		return SortName;
	}
	public void setSortName(String sortName) {
		SortName = sortName;
	}
	public String getGoodsName() {
		return GoodsName;
	}
	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}
	public Integer getSales() {
		return Sales;
	}
	public void setSales(Integer sales) {
		Sales = sales;
	}

}
