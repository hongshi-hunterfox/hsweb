package com.uclee.payment.strategy;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.data.mybatis.model.OauthLogin;
import com.uclee.fundation.data.mybatis.model.Order;
import com.uclee.fundation.data.mybatis.model.OrderItem;
import com.uclee.fundation.data.mybatis.model.PaymentOrder;
import com.uclee.model.SpringContextUtil;
import com.uclee.number.util.NumberUtil;
import com.uclee.payment.exception.PaymentHandlerException;
import com.uclee.user.model.PaymentStrategyResult;
import com.uclee.user.service.UserServiceI;
import com.uclee.user.service.impl.UserServiceImpl;

public class WCJSAPIPaymentStrategy implements PaymentHandlerStrategy {

	private static String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder" ;
	
	private static String RERUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund" ;
	
	private static String FIND_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery" ;
	
	private static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder" ;
	
	private static String DOWNLOADBILL = "https://api.mch.weixin.qq.com/pay/downloadbill" ;
	
	
	@Override
	public PaymentStrategyResult rechargeHandle(PaymentOrder paymentOrder) {
		PaymentStrategyResult result= new PaymentStrategyResult();
		UserServiceI userService = (UserServiceI)SpringContextUtil.getBean("userServiceImpl");
		OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(paymentOrder.getUserId());
		String openId = null;
		if(oauthLogin!=null){
			openId = oauthLogin.getOauthId();
		}
		if(openId!=null){
			try {
				result = userService.getWCPayment(openId,paymentOrder.getPaymentSerialNum(),paymentOrder.getMoney(),"会员卡充值");
				result.setType("WC");
			} catch (PaymentHandlerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			result.setResult(false);
		}
		return result;
	}

	@Override
	public PaymentStrategyResult paymentHandle(PaymentOrder paymentOrder) {
		UserServiceI userService = (UserServiceI)SpringContextUtil.getBean("userServiceImpl");
		OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(paymentOrder.getUserId());
		String openId = null;
		if(oauthLogin!=null){
			openId = oauthLogin.getOauthId();
		}
		List<Order> orders = userService.selectOrderByPaymentSerialNum(paymentOrder.getUserId(), paymentOrder.getPaymentSerialNum());
		userService.updatePaymentOrder(paymentOrder);
		String title = "";
		for(Order order : orders){
			for(OrderItem orderItem:order.getItems()){
				title = title + orderItem.getTitle() + "("+orderItem.getValue()+");";
			}
		}
		PaymentStrategyResult result=null;
		try {
			result = userService.getWCPayment(openId,paymentOrder.getPaymentSerialNum(),paymentOrder.getMoney(),title);
		} catch (PaymentHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setResult(false);
		}
		result.setType("WC");
		System.out.println("result:" + JSON.toJSONString(result));
		return result;
	}

}
