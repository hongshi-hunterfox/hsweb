package com.uclee.payment.strategy;

import java.math.BigDecimal;

import com.uclee.fundation.data.mybatis.model.PaymentOrder;
import com.uclee.model.SpringContextUtil;
import com.uclee.user.model.PaymentStrategyResult;
import com.uclee.user.service.UserServiceI;

public class MemberCardPaymentStrategy implements PaymentHandlerStrategy {

	@Override
	public PaymentStrategyResult rechargeHandle(PaymentOrder paymentOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentStrategyResult paymentHandle(PaymentOrder paymentOrder) {
		UserServiceI userService = (UserServiceI)SpringContextUtil.getBean("userServiceImpl");
		return userService.memberCardPaymentHandler(paymentOrder);
	}

}
