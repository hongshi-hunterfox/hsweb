package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);
    
    Order selectByPaymentOrderId(Integer paymentOrderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	List<Order> getUnpayOrderListByUserId(Integer userId);

	List<Order> getInvitationOrder(Integer userId);

	List<Order> selectByPaymentSerialNum(@Param("userId")Integer userId, @Param("paymentSerialNum")String paymentSerialNum);

	Order selectBySerialNum(String orderSerialNum);

	List<Order> selectByVoucherCode(String voucherCode);

	int deleteByOrderSerialNum(String orderSerialNum);

    int getUnpayOrderCountByUserId(Integer userId);

    Order getOrderListByOrderSerailNum(String orderSerialNum);
    
    List<Order> getOrderListByStatus();

    int getUnCommentCount(Integer userId);
    
    int updateByInvalid(String orderSerialNum);
}