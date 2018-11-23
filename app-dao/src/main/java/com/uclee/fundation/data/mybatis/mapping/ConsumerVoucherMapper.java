package com.uclee.fundation.data.mybatis.mapping;
import java.util.List;
import com.uclee.fundation.data.mybatis.model.ConsumerVoucher;
public interface ConsumerVoucherMapper {
	int deleteByPrimaryKey(Integer id);
	
	int insert(ConsumerVoucher record);
    int insertSelective(ConsumerVoucher record);
	
	ConsumerVoucher selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKeySelective(ConsumerVoucher record);
    int updateByPrimaryKey(ConsumerVoucher record);
	
	List<ConsumerVoucher> selectAll();
	
    int deleteAll();
}