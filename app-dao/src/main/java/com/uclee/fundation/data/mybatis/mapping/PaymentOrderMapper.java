package com.uclee.fundation.data.mybatis.mapping;

import java.util.Date;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.PaymentOrder;
import org.apache.ibatis.annotations.Param;

public interface PaymentOrderMapper {
    int deleteByPrimaryKey(Integer paymentOrderId);

    int insert(PaymentOrder record);

    int insertSelective(PaymentOrder record);

    PaymentOrder selectByPrimaryKey(Integer paymentOrderId);

    int updateByPrimaryKeySelective(PaymentOrder record);

    int updateByPrimaryKey(PaymentOrder record);

	PaymentOrder selectByPaymentSerialNum(String paymentSerialNum);

	List<PaymentOrder> selectComissionPayByTermId(Integer termId);

    List<PaymentOrder> selectForTimer(@Param("target") Date target);

    int updatePaymentResult(PaymentOrder paymentOrder);

    int updateCheckCount(PaymentOrder paymentOrder);
}