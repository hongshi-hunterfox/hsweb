package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.DriverOrder;
import com.uclee.fundation.data.mybatis.model.Order;

public interface DriverOrderMapper {
	List<DriverOrder> selectByDepartment(String Department);
	List<DriverOrder> selectAll(@Param("departmentlist")List<String> departmentlist);
	List<DriverOrder> selectByDepartmentOne(String Department);
	List<DriverOrder> selectAllOne(@Param("departmentlist")List<String> departmentlist);
	List<DriverOrder> selectByDepartmentTwo(String Department);
	List<DriverOrder> selectAllTwo(@Param("departmentlist")List<String> departmentlist);
	List<DriverOrder> selectByDepartmentThree(String Department);
	List<DriverOrder> selectAllThree(@Param("departmentlist")List<String> departmentlist);
	int updateDetaileStart(Integer orderID);
	int updateDetaileEnd(Integer orderID);
	DriverOrder getMerchantOrderNumber(Integer ID);
	Order getUserId(String outerOrderCode);
}
