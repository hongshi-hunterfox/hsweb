package com.uclee.web.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.uclee.fundation.data.mybatis.mapping.CommentMapper;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.payment.strategy.RefundHandlerStrategy;
import com.uclee.user.model.RefundStrategyResult;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.data.web.dto.OrderPost;
import com.uclee.fundation.data.web.dto.StockPost;
import com.uclee.number.util.NumberUtil;
import com.uclee.payment.strategy.PaymentHandlerStrategy;
import com.uclee.sms.util.VerifyCode;
import com.uclee.user.model.PaymentStrategyResult;
import com.uclee.user.service.DuobaoServiceI;
import com.uclee.user.service.UserServiceI;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-user-web")
public class UserHandler {
	private static final Logger logger = Logger.getLogger(UserHandler.class);
	@Autowired
	private UserServiceI userService;
	@Autowired
	private DuobaoServiceI duobaoService;
	@Autowired
	private CommentMapper commentMapper;

	/** 
	* @Title: invitation 
	* @Description: 分销邀请处理，根据当前用户和邀请者的序列号，绑定分销关系 
	* @param @param request
	* @param @param serialNum 邀请者的用户序列号
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@RequestMapping("/invitation")
	public @ResponseBody boolean invitation(HttpServletRequest request,String serialNum){
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		logger.info(serialNum);
		return userService.getInvitationHandler(userId,serialNum);
	}
	/** 
	* @Title: delOrder 
	* @Description: 删除未支付订单接口
	* @param @param request
	* @param @param orderSerialNum 订单编号
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@RequestMapping("/delOrder")
	public @ResponseBody boolean delOrder(HttpServletRequest request,String orderSerialNum){
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		userService.delOrder(orderSerialNum);
		logger.info("删除未支付的订单:微商城订单编号为"+orderSerialNum);
		return true;
	}
	/** 
	* @Title: signInHandler 
	* @Description: 签到处理接口，
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/signInHandler")
	public @ResponseBody Map<String,Object> signInHandler(HttpServletRequest request){
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		return userService.signInHandler(userId);
	}
	
	/** 
	* @Title: orderHandler 
	* @Description: 下单数据提交处理接口
	* @param @param request
	* @param @param orderPost
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/orderHandler")
	public @ResponseBody Map<String,Object> orderHandler(HttpServletRequest request, @RequestBody OrderPost orderPost) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map = userService.orderHandler(orderPost, userId, NumberUtil.generateSerialNum());
		return map;
	}
	
	/** 
	* @Title: stockCheck 
	* @Description: 库存检查
	* @param @param request
	* @param @param orderPost
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/stockCheck")
	public @ResponseBody Map<String,Object> stockCheck(HttpServletRequest request,@RequestBody StockPost stockPost) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		logger.info("stockPost: " + JSON.toJSONString(stockPost));
		map = userService.stockCheck(stockPost,userId);
		return map;
	}
	
	/** 
	* @Title: setDefaultAddr 
	* @Description: 设置默认收货地址
	* @param @param request
	* @param @param deliverAddr 
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/setDefaultAddr")
	@RequiresPermissions("customer:editAddr")
	public @ResponseBody Map<String,Object> setDefaultAddr(HttpServletRequest request,@RequestBody DeliverAddr deliverAddr) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		deliverAddr.setUserId(userId);
		logger.info(JSON.toJSONString(deliverAddr));
		String result = userService.setDefaultAddr(deliverAddr);
		map.put("result", result);
		return map;
	}
	
	/** 
	* @Title: delAddrHandler 
	* @Description: 删除收货地址请求处理
	* @param @param request
	* @param @param deliverAddr post过来的地址信息
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/delAddrHandler")
	public @ResponseBody Map<String,Object> delAddrHandler(HttpServletRequest request,@RequestBody DeliverAddr deliverAddr) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		deliverAddr.setUserId(userId);
		logger.info(JSON.toJSONString(deliverAddr));
		String result = userService.delAddrHandler(deliverAddr);
		map.put("result", result);
		return map;
	}
	
	/** 
	* @Title: editAddrHandler 
	* @Description: 编辑收货地址请求处理 
	* @param @param request
	* @param @param deliverAddr post过来的地址信息
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/editAddrHandler")
	public @ResponseBody Map<String,Object> editAddrHandler(HttpServletRequest request,@RequestBody DeliverAddr deliverAddr) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		deliverAddr.setUserId(userId);
		logger.info(JSON.toJSONString(deliverAddr));
		String result = userService.editAddrHandler(deliverAddr);
		map.put("result", result);
		return map;
	}
	
	/** 
	* @Title: cardAddHandler 
	* @Description: 增加购物车item数量请求处理
	* @param @param request
	* @param @param cartId 购物车id
	* @param @param amount 数量
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/cardAddHandler")
	public @ResponseBody Map<String,Object> cardAddHandler(HttpServletRequest request,Integer cartId,Integer amount, Integer activityMarkers) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map = userService.cardAddHandler(userId,cartId,amount,activityMarkers);
		return map;
	}
	/** 
	* @Title: cardDelHandler 
	* @Description: 处理删除购物车请求
	* @param @param request
	* @param @param cartId 待删除的购物车id
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/cardDelHandler")
	public @ResponseBody Map<String,Object> cardDelHandler(HttpServletRequest request,Integer cartId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map = userService.cardDelHandler(userId,cartId);
		return map;
	}
	
	
	/** 
	* @Title: cartHandler 
	* @Description: 添加到购物车请求处理
	* @param @param request
	* @param @param cart post的产品数据
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/cartHandler")
	public @ResponseBody Map<String,Object> cartHandler(HttpServletRequest request,@RequestBody Cart cart) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		map.put("result", false);
		if(cart.getAmount()==null||cart.getProductId()==null||cart.getSpecificationValueId()==null){
			map.put("reason", "请选择规格");
			return map;
		}
		if(cart.getParamete()!=null){
		if(cart.getCanshuValueId()==null){
			map.put("reason", "请选择"+cart.getParamete());
			return map;
		}
		}
		Product product = userService.getProductById(cart.getProductId());
		if(product==null||!product.getIsActive()){
			map.put("reason", "产品已下架");
			return map;
		}
		SpecificationValue specificationValue = userService.getSpecificationValue(cart.getProductId(),cart.getSpecificationValueId());
		if(specificationValue==null){
			map.put("reason", "该款式已下架");
			return map;
		}
		if(specificationValue.getHsStock()<cart.getAmount()){
			map.put("reason", "该款式库存不足");
			return map;
		}
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		cart.setUserId(userId);
		Integer cartId =userService.addToCart(cart);
		if(cartId!=null){
			map.put("cartId", cartId);
			map.put("result", true);
		}else{
			map.put("reason", "网络繁忙");
		}
		return map;
	}
	
	
	/** 
	* @Title: updateUserInfo 
	* @Description: 更新用户信息，暂时废弃不用 
	* @param @param request
	* @param @param userProfile
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/updateUserInfo")
	@RequiresPermissions("customer:editAddr")
	public @ResponseBody Map<String,Object> updateUserInfo(HttpServletRequest request,@RequestBody UserProfile userProfile) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		boolean result = userService.updateProfile(userId,userProfile);
		map.put("result", result);
		return map;
	}
	
	/** 
	* @Title: doUpdateVips 
	* @Description: 更新用户信息， 
	* @param @param request
	* @param @param userProfile
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("doUpdateVips")
	public @ResponseBody Map<String,Object> doUpdateVips(@RequestBody HsVip hsVip, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		Map<String,Object> map = new TreeMap<String,Object>();
		
		logger.info("hsVip:"+JSON.toJSONString(hsVip));
		HsVip up=new HsVip();
		up.setvName(hsVip.getvName());
		up.setvNumber(hsVip.getvNumber());
		up.setvBirthday(hsVip.getvBirthday());
		up.setvIdNumber(hsVip.getvIdNumber());
		up.setvCompany(hsVip.getvCompany());
		up.setvCode(hsVip.getvCode());
		up.setvSex(hsVip.getvSex());
		List<HsVip> vip = userService.selectVips(hsVip.getvNumber());	
			if(!VerifyCode.checkVerifyCode(session,hsVip.getvNumber(),hsVip.getCode())){
				map.put("result", "fail");	
				if(hsVip.getvCode()!= vip.get(0).getvCode()){
					if(hsVip.getCode()==null){
						map.put("reason", "请输入验证码");					
					}else{
						map.put("reason", "验证码错误");
						return map;	
					}
				}	
			}
			map.put("result", "success");
			if(vip.size()==0){			
				userService.updateVips(hsVip.getvCode(), up);			
				return map;
			}					
			String hVip = hsVip.getvCode();
			String sVip = vip.get(0).getvCode();
			map.put("result", "success");
			if(vip.size()!=0&&hVip.equals(sVip)){						
				userService.updateVips(hsVip.getvCode(), up);	
				map.put("result", "success");
			}else{
				map.put("reason","手机号已与其他会员卡绑定，不能修改！");
				map.put("result", "fail");	
			}
		return map; 
	}
	
	/** 
	* @Title: lotteryHandler 
	* @Description: 抽奖结果处理接口 
	* @param @param request
	* @param @param configCode 抽奖对应的配置项编码
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/lotteryHandler")
	public @ResponseBody Map<String,Object> lotteryHandler(HttpServletRequest request,String configCode) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map = userService.lotteryHandler(userId,configCode);
		return map;
	}
	/** 
	* @Title: tranferBalance 
	* @Description: 微商城余额转进会员卡接口 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/tranferBalance")
	public @ResponseBody Map<String,Object> tranferBalance(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map = userService.tranferBalance(userId);
		return map;
	}
	
	/** 
	* @Title: rechargeHandler 
	* @Description: 充值请求处理
	* @param @param request
	* @param @param paymentOrder post的充值订单数据
	* @param @return    设定文件 
	* @return PaymentStrategyResult    返回类型 
	* @throws 
	*/
	@RequestMapping(value="/seller/rechargeHandler",method = RequestMethod.POST)
	public @ResponseBody PaymentStrategyResult rechargeHandler(HttpServletRequest request,@RequestBody PaymentOrder paymentOrder) {
		logger.info(JSON.toJSONString(paymentOrder));
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		PaymentStrategyResult paymentStrategyResult = new PaymentStrategyResult();
		Payment payment = userService.getPaymentMethodById(paymentOrder.getPaymentId());
		//判断是否是微信浏览器
		boolean isWC = userService.isWC(request);
		paymentStrategyResult.setIsWC(isWC);
		if(payment!=null){
			PaymentOrder paymentOrderTmp = new PaymentOrder();
			paymentOrderTmp.setPaymentId(payment.getPaymentId());
			paymentOrderTmp.setUserId(userId);
			paymentOrderTmp.setMoney(paymentOrder.getMoney());
			paymentOrderTmp.setPaymentSerialNum(NumberUtil.generateSerialNum());
			paymentOrderTmp.setTransactionType((short) 2);
			userService.insertPaymentOrder(paymentOrderTmp);
			if(payment.getStrategyClassName().equals("AlipayPaymentStrategy")&&isWC){
				paymentStrategyResult.setType("alipay");
				paymentStrategyResult.setResult(true);
				paymentStrategyResult.setPayType(2);
				paymentStrategyResult.setPaymentSerialNum(paymentOrderTmp.getPaymentSerialNum());
				return paymentStrategyResult;
			}
			PaymentHandlerStrategy paymentHandlerStrategy;
			try {
				paymentHandlerStrategy = (PaymentHandlerStrategy) Class
						.forName("com.uclee.payment.strategy." + payment.getStrategyClassName()).newInstance();
				paymentStrategyResult = paymentHandlerStrategy.rechargeHandle(paymentOrderTmp);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			paymentStrategyResult.setReason("noPaymentMethod");
			paymentStrategyResult.setResult(false);
		}
		return paymentStrategyResult;
	}
	
	/** 
	* @Title: paymentAlipayHandler 
	* @Description: 处理支付宝支付请求
	* @param @param request
	* @param @param paymentOrder
	* @param @return    设定文件 
	* @return PaymentStrategyResult    返回类型 
	* @throws 
	*/
	@RequestMapping(value="/seller/paymentAlipayHandler",method = RequestMethod.POST)
	public @ResponseBody PaymentStrategyResult paymentAlipayHandler(HttpServletRequest request,@RequestBody PaymentOrder paymentOrder) {
		logger.info(JSON.toJSONString(paymentOrder));
		PaymentStrategyResult paymentStrategyResult = new PaymentStrategyResult();
		PaymentOrder paymentOrderTmp = userService.getPaymentOrderBySerialNum(paymentOrder.getPaymentSerialNum());
		paymentStrategyResult.setPaymentSerialNum(paymentOrderTmp.getPaymentSerialNum());
		userService.updatePaymentOrder(paymentOrderTmp);
		String title = "支付订单";
		String url;
		if(paymentOrder.getPayType()==1){
			url = "http://hs.uclee.com/recharge-list?needWx=1";
		}else{
			url = "http://hs.uclee.com/order-list?needWx=1";
		}
		paymentStrategyResult = userService.getAlipayForFastPay(paymentOrderTmp, title, null);
		return paymentStrategyResult;
	}

	/**
	 * 评论
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/commentHandler",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> commentHandler(HttpServletRequest request,@RequestBody Comment comment) {
		Map<String,Object> map = new TreeMap<String,Object>();
		logger.info(JSON.toJSONString(comment));
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		comment.setUserId(userId);
		if(comment.getOrderSerialNum()==null||comment.getQuality()==null||comment.getService()==null||comment.getDeliver()==null){
			map.put("reason","参数错误");
			map.put("result",false);
			return map;
		}
		Comment tmp = commentMapper.selectByOrderId(comment.getOrderSerialNum());
		if(tmp!=null){
			map.put("reason","订单已评论，请勿重复评论");
			map.put("result",false);
			return map;
		}
		if(commentMapper.insertSelective(comment)>0){
			map.put("result",true);
			return map;
		}else{
			map.put("reason","网络繁忙，请稍后重试");
			map.put("result",false);
			return map;
		}
	}

	/** 
	* @Title: paymentHandler 
	* @Description: 处理微信支付，会员卡支付，支付宝支付预处理请求
	* @param @param request
	* @param @param paymentOrderPost post过来的支付单数据
	* @param @return    设定文件 
	* @return PaymentStrategyResult    返回类型 
	* @throws 
	*/
	@RequestMapping(value="/seller/paymentHandler",method = RequestMethod.POST)
	public @ResponseBody PaymentStrategyResult paymentHandler(HttpServletRequest request,@RequestBody PaymentOrder paymentOrderPost) {
		PaymentStrategyResult paymentStrategyResult = new PaymentStrategyResult();
		logger.info(JSON.toJSONString(paymentOrderPost));
		PaymentOrder paymentOrder = userService.selectPaymentOrderBySerialNum(paymentOrderPost.getPaymentSerialNum());
		if(paymentOrder==null){
			paymentStrategyResult.setReason("noSuchOrder");
			paymentStrategyResult.setResult(false);
			return paymentStrategyResult;
		}
		if(paymentOrder.getIsCompleted()){
			paymentStrategyResult.setReason("illegel");
			paymentStrategyResult.setResult(false);
			return paymentStrategyResult;
		}
		Payment payment = userService.getPaymentMethodById(paymentOrderPost.getPaymentId());
		//判断是否是微信浏览器
		boolean isWC = userService.isWC(request);
		paymentStrategyResult.setIsWC(isWC);
		if(payment!=null){
			paymentOrder.setPaymentId(payment.getPaymentId());
			paymentOrder.setTransactionType((short) 1);
			userService.updatePaymentOrder(paymentOrder);
			if(payment.getStrategyClassName().equals("AlipayPaymentStrategy")&&isWC){
				paymentStrategyResult.setType("alipay");
				paymentStrategyResult.setResult(true);paymentStrategyResult
				.setPaymentSerialNum(paymentOrder.getPaymentSerialNum());
				paymentStrategyResult.setPayType(1);
				return paymentStrategyResult;
			}
			PaymentHandlerStrategy paymentHandlerStrategy;
			try {
				paymentHandlerStrategy = (PaymentHandlerStrategy) Class
						.forName("com.uclee.payment.strategy." + payment.getStrategyClassName()).newInstance();
				paymentStrategyResult = paymentHandlerStrategy.paymentHandle(paymentOrder);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			paymentStrategyResult.setReason("noPaymentMethod");
			paymentStrategyResult.setResult(false);
		}
		return paymentStrategyResult;
	}
	@RequestMapping(value="/firstDraw")
	public @ResponseBody Map<String,Object> firstDraw(HttpServletRequest request) {
		
		return userService.firstDrawHandler();
	}
	@RequestMapping(value="/secondDraw")
	public @ResponseBody Map<String,Object> secondDraw(HttpServletRequest request) {
		
		return userService.secondDrawHandler();
	}
	@RequestMapping(value="/thirdDraw")
	public @ResponseBody Map<String,Object> thirdDraw(HttpServletRequest request) {
		
		return userService.thirdDrawHandler();
	}
	@RequestMapping(value="/resetDraw")
	public @ResponseBody boolean resetDraw(HttpServletRequest request) {
		
		return userService.resetDraw();
	}

	@RequestMapping(value="/applyRefund")
	//订单列表上面点击开始退款按钮by chiangpan
	public @ResponseBody Map<String,Object> applyRefund(HttpServletRequest request,String outerOrderCode) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		logger.info("outerOrderCode: " + JSON.toJSONString(outerOrderCode));
		map = userService.applyRefund(outerOrderCode, userId);
		return map;
	}


	/**
	 * @Title: refundHandler
	 * @Description: 处理微信退款，会员卡退款，支付宝退款预处理请求(目前暂时只弄微信支付和支付宝支付)
	 * @param @param request
	 * @param @param refundOrderPost post过来的退款单数据
	 * @param @return    设定文件
	 * @return     返回类型
	 * @throws
	 */

	@RequestMapping(value="/seller/refundHandler",method = RequestMethod.POST)
	public @ResponseBody RefundStrategyResult refundHandler(HttpServletRequest request,@RequestBody RefundOrder refundOrderPost) {
		RefundStrategyResult refundStrategyResult = new RefundStrategyResult();
		logger.info(JSON.toJSONString(refundOrderPost));

		RefundOrder refundOrder=userService.selectRefundOrderBySerialNum(refundOrderPost.getRefundSerialNum());
		if(refundOrder==null){
			refundStrategyResult.setReason("noSuchOrder");
			refundStrategyResult.setResult(false);
			return refundStrategyResult;
		}
		if(refundOrder.isCompleted()){
			refundStrategyResult.setReason("illegel");
			refundStrategyResult.setResult(false);
			return refundStrategyResult;
		}
		if(refundOrder.getFlag()==3){
			refundStrategyResult.setReason("AllredaySuccessRefund");
			refundStrategyResult.setResult(false);
			return refundStrategyResult;
		}
		Payment payment = userService.getPaymentMethodById(refundOrder.getPaymentId());
		if(payment!=null){
			//这里以后再完善
			RefundHandlerStrategy refundHandlerStrategy;
			if(payment.getPaymentId()==2){
				//会员卡退款
				refundStrategyResult.setReason("notSupportMemberCard");
				refundStrategyResult.setResult(false);
			}else if(payment.getPaymentId()==3){
				//支付宝退款
				String reflectName="AlipayRefundStrategy";
				try{
					refundHandlerStrategy = (RefundHandlerStrategy) Class.forName("com.uclee.payment.strategy." + reflectName).newInstance();
					refundStrategyResult = refundHandlerStrategy.refundHandler(refundOrder);
				}catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
					e.printStackTrace();
				}
			}else{
				//微信退款  WCJSAPIRefundStrategy.refundHandler(refundOrder)
				String reflectName="WCJSAPIRefundStrategy";
				try {
					refundHandlerStrategy = (RefundHandlerStrategy) Class
							.forName("com.uclee.payment.strategy." + reflectName).newInstance();
					refundStrategyResult = refundHandlerStrategy.refundHandler(refundOrder);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}else{
			refundStrategyResult.setReason("noPaymentMethod");
			refundStrategyResult.setResult(false);
		}

		return refundStrategyResult;
	}

	@RequestMapping(value="/seller/refundApplyHandler",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> refundApplyHandler(HttpServletRequest request,@RequestBody RefundOrder refundOrderPost){
		Map<String,Object> map = new TreeMap<String,Object>();
		RefundOrder refundOrder=userService.selectRefundOrderBySerialNum(refundOrderPost.getRefundSerialNum());
		if(refundOrder==null){
			map.put("reason", "noSuchOrder");
			map.put("result", false);
			return map;
		}
		if(refundOrder.isCompleted()){
			map.put("reason","illegel");
			map.put("result", false);
			return map;
		}
		if(refundOrder.getFlag()==1){
			map.put("reason","allreadyApply");
			map.put("result", false);
			return map;
		}
		if(refundOrder.getPaymentId()==2){
			map.put("reason","notSupportMemberCard");
			map.put("result",false);
			return map;
		}

		//1设定为已经申请退款
		refundOrder.setFlag(1);
		refundOrder.setRefundDesc(refundOrderPost.getRefundDesc());
		//更新状态为1，同时插入到
		userService.updateRefundOrder(refundOrder);
		String openId ="";
		OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(refundOrder.getUserId());
		if(oauthLogin !=null){
			openId=oauthLogin.getOauthId();//外键
			Map pramMap=new HashMap();
			pramMap.put("paymentSerialNum",refundOrder.getPaymentSerialNum());
			pramMap.put("openId",openId);
			pramMap.put("flag",1);
			userService.insertOrderTrace(pramMap);
			map.put("result", true);
		}else{
			map.put("result",false);
			map.put("reson","notLogin");
		}
		return map;
	}
}
