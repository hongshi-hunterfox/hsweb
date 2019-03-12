package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.UrlVoucherCollection;
public interface UrlVoucherCollectionMapper{
	int insertSelective(UrlVoucherCollection urlVoucherCollection);
	List<UrlVoucherCollection> selectAll();
	int deleteById(Integer id);
	UrlVoucherCollection selectById(Integer id);
	int updateById(UrlVoucherCollection urlVoucherCollection);
}
