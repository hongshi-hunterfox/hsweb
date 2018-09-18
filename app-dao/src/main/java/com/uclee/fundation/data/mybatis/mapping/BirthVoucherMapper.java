package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.BirthPush;
import com.uclee.fundation.data.mybatis.model.BirthVoucher;

import java.util.List;

public interface BirthVoucherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BirthVoucher record);

    int insertSelective(BirthVoucher record);

    BirthVoucher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BirthVoucher record);

    int updateByPrimaryKey(BirthVoucher record);

    List<BirthVoucher> selectAll();

    int deleteAll();
    
    int updateBrithPush(Integer day);
    
    BirthPush selectDay();
}