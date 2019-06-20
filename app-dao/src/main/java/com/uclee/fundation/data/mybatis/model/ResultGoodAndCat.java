package com.uclee.fundation.data.mybatis.model;

import java.util.List;

import lombok.Data;

@Data
public class ResultGoodAndCat {
	public String catName;
	public List<Goods> goods;
}
