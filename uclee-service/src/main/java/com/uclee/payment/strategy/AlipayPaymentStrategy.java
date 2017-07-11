package com.uclee.payment.strategy;

import java.math.BigDecimal;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.Order;
import com.uclee.fundation.data.mybatis.model.PaymentOrder;
import com.uclee.model.SpringContextUtil;
import com.uclee.number.util.NumberUtil;
import com.uclee.user.model.PaymentStrategyResult;
import com.uclee.user.service.UserServiceI;

public class AlipayPaymentStrategy implements PaymentHandlerStrategy {

	@Override
	public PaymentStrategyResult rechargeHandle(PaymentOrder paymentOrderTmp) {
		UserServiceI userService = (UserServiceI)SpringContextUtil.getBean("userServiceImpl");
		PaymentStrategyResult paymentStrategyResult = new PaymentStrategyResult();
		paymentStrategyResult.setPaymentSerialNum(paymentOrderTmp.getPaymentSerialNum());
		userService.updatePaymentOrder(paymentOrderTmp);
		String title = "充值订单";
		paymentStrategyResult = userService.getAlipayForFastPay(paymentOrderTmp, title, "http://hs.uclee.com/recharge-list");
		return paymentStrategyResult;
	}

	@Override
	public PaymentStrategyResult paymentHandle(PaymentOrder paymentOrder) {
		UserServiceI userService = (UserServiceI)SpringContextUtil.getBean("userServiceImpl");
		PaymentStrategyResult paymentStrategyResult = new PaymentStrategyResult();
		userService.updatePaymentOrder(paymentOrder);
		String title = "支付订单";
		paymentStrategyResult = userService.getAlipayForFastPay(paymentOrder, title, "http://hs.uclee.com/order-list");
		return paymentStrategyResult;
	}

}
