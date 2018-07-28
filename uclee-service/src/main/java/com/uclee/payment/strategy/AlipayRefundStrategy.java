package com.uclee.payment.strategy;

import com.uclee.fundation.data.mybatis.model.RefundOrder;
import com.uclee.model.SpringContextUtil;
import com.uclee.user.model.RefundStrategyResult;
import com.uclee.user.service.UserServiceI;

public class AlipayRefundStrategy implements RefundHandlerStrategy{

    @Override
    public RefundStrategyResult refundHandler(RefundOrder refundOrder) {
        UserServiceI userService = (UserServiceI) SpringContextUtil.getBean("userServiceImpl");
        RefundStrategyResult refundStrategyResult = new RefundStrategyResult();
        refundStrategyResult.setRefundSerialNum(refundOrder.getRefundSerialNum());
        //暂时不用更新退款单表
        //userService.updatePaymentOrder(paymentOrderTmp);
        refundStrategyResult = userService.getAlipayForRefund(refundOrder);
        return refundStrategyResult;
    }
}