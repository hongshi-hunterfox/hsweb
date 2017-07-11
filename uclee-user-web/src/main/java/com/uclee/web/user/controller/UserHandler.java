package com.uclee.web.user.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.config.links.TermGroupTag;
import com.uclee.fundation.data.mybatis.model.Cart;
import com.uclee.fundation.data.mybatis.model.DeliverAddr;
import com.uclee.fundation.data.mybatis.model.FinancialAccount;
import com.uclee.fundation.data.mybatis.model.Payment;
import com.uclee.fundation.data.mybatis.model.PaymentOrder;
import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.mybatis.model.Specification;
import com.uclee.fundation.data.mybatis.model.SpecificationValue;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.web.dto.OrderPost;
import com.uclee.fundation.dfs.fastdfs.data.Result;
import com.uclee.number.util.NumberUtil;
import com.uclee.payment.exception.PaymentHandlerException;
import com.uclee.payment.strategy.PaymentHandlerStrategy;
import com.uclee.user.model.PaymentStrategyResult;
import com.uclee.user.service.DuobaoServiceI;
import com.uclee.user.service.UserServiceI;
import com.uclee.userAgent.util.UserAgentUtils;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-user-web")
public class UserHandler {
	private static final Logger logger = Logger.getLogger(UserHandler.class);
	@Autowired
	private UserServiceI userService;
	@Autowired
	private DuobaoServiceI duobaoService;
	
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
	public @ResponseBody Map<String,Object> orderHandler(HttpServletRequest request,@RequestBody OrderPost orderPost) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		logger.info("orderPost: " + JSON.toJSONString(orderPost));
		map = userService.orderHandler(orderPost,userId);
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
	public @ResponseBody Map<String,Object> cardAddHandler(HttpServletRequest request,Integer cartId,Integer amount) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map = userService.cardAddHandler(userId,cartId,amount);
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
			map.put("reason", "参数错误");
			return map;
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
	* @Description: 处理充值请求 
	* @param @param request
	* @param @param paymentId 支付方式id
	* @param @param money 支付金额 
	* @return PaymentStrategyResult    返回类型 
	* @throws 
	*/
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
}
