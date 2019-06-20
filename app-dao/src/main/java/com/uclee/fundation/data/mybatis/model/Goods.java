package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.List;

import com.uclee.fundation.data.web.dto.ValuePost;

import lombok.Data;

@Data
public class Goods {
	public int id;
	public String goodsname;
	public String goodsimg;
	public int goodscategory;
	public List<GoodsSpecifications> goodsSpecifications;
	public List<ValuePost> valuePost;
	public String images[];
	public BigDecimal hsPrice;
	public BigDecimal vipPrice;
	public String flavorone;
	public String flavortwo;
	public String flavorthree;
	public String flavorfour;
	public String flavors;
}
