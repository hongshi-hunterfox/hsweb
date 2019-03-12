package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.StoreInfo;
import com.uclee.fundation.data.mybatis.model.UrlVoucherCollection;

public interface StoreInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreInfo record);

    int insertSelective(StoreInfo record);

    StoreInfo selectByPrimaryKey(Integer id);
 
    int updateByPrimaryKeySelective(StoreInfo record);

    int updateByPrimaryKey(StoreInfo record);

	StoreInfo selectOne();
	
	List<UrlVoucherCollection> selectAll();
}