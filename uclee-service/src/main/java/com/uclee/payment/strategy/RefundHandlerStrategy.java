package com.uclee.payment.strategy;

import com.uclee.fundation.data.mybatis.model.RefundOrder;
import com.uclee.user.model.RefundStrategyResult;

//退款接口by chiangpan
public interface RefundHandlerStrategy {

    public RefundStrategyResult refundHandler(RefundOrder refundOrder);

}