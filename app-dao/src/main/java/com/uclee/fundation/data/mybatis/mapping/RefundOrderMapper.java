package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.RefundOrder;
import com.uclee.fundation.data.web.dto.AuditRefundDto;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiang on 2018/4/2.
 */
public interface RefundOrderMapper {
    int deleteByPrimaryKey(Integer RefundOrderId);

    int insert(RefundOrder record);

    int insertSelective(RefundOrder record);

    RefundOrder selectByPrimaryKey(Integer RefundOrderId);

    int updateByPrimaryKeySelective(RefundOrder record);

    int updateByPrimaryKey(RefundOrder record);

    RefundOrder selectByRefundSerialNum(String refundSerialNum);

    //根据支付单判断是否已经存在退款单了
    List<RefundOrder> selectExistByPaymentSerialNum(String paymentSerialNum);

    //审核退款时首先查找所有的退款单列表
    List<AuditRefundDto> getRefundOrderList(@Param("orderSerialNum") String orderSerialNum);

    RefundOrder selectByTransactionId(String transactionId);

    int insertOrderTrace(Map paraMap);
}
