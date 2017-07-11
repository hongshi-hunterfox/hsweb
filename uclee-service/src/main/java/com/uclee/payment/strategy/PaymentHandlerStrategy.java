package com.uclee.payment.strategy;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.uclee.fundation.data.mybatis.model.PaymentOrder;
import com.uclee.payment.exception.PaymentHandlerException;
import com.uclee.user.model.PaymentStrategyResult;

public interface PaymentHandlerStrategy {

	public PaymentStrategyResult rechargeHandle(PaymentOrder paymentOrder);

	public PaymentStrategyResult paymentHandle(PaymentOrder paymentOrder);
}
