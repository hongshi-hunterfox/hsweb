package com.uclee.web.user.controller;

import com.alibaba.fastjson.JSON;
import com.backend.service.BackendServiceI;
import com.uclee.dynamicDatasource.DataSourceFacade;
import com.uclee.datasource.service.DataSourceInfoServiceI;
import com.uclee.date.util.DateUtils;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.config.links.TermGroupTag;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.CartDto;
import com.uclee.fundation.data.web.dto.ProductDto;
import com.uclee.hongshi.service.HongShiVipServiceI;
import com.uclee.sms.util.VerifyCode;
import com.uclee.user.service.DuobaoServiceI;
import com.uclee.user.service.UserServiceI;
import com.uclee.user.util.JwtUtil;
import com.uclee.userAgent.util.UserAgentUtils;

import joptsimple.internal.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-user-web")
public class UserController extends CommonUserHandler{

	@Autowired
	private UserServiceI userService;
	@Autowired
	private DuobaoServiceI duobaoService;
	@Autowired
	private DataSourceFacade dataSource;
	@Autowired
	private BackendServiceI backendService;
	@Autowired
	private HongShiVipServiceI hongShiVipService;
	@Autowired
	private DataSourceFacade datasource;
	@Autowired
	private DataSourceInfoServiceI dataSourceInfoService;
	
	
	@RequestMapping("/getPageTitle")
	public @ResponseBody DataSourceInfo getShakePageData(HttpServletRequest request,String mCode) {
		dataSource.switchDataSource("master");
		DataSourceInfo t = dataSourceInfoService.getDataSourceInfoByCode(mCode);
		if(t!=null){
			return t;
		}
		return null;
	}
	
	@RequestMapping("/getShakePageData")
	public @ResponseBody Map<String,Object> getShakePageData(HttpServletRequest request) {
		
		return userService.getShakePageData();
	}
	/** 
	* @Title: getShakeRecord 
	* @Description: 摇一摇记录接口 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getShakeRecord")
	public @ResponseBody Map<String,Object> getShakeRecord(HttpServletRequest request) {
		
		return userService.getShakeRecord();
	}
	
	/** 
	* @Title: prePaymentForAlipay
	* @Description: 微信里面使用支付宝支付预处理 
	* @param @param request
	* @param @param paymentSerialNum 支付单号
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/prePaymentForAlipay")
	public @ResponseBody Map<String,Object> prePaymentForAlipay(HttpServletRequest request,String paymentSerialNum) {
		Map<String,Object> map = new TreeMap<String,Object>();
		PaymentOrder paymentOrder  = userService.getPaymentOrderBySerialNum(paymentSerialNum);
		if(paymentOrder==null){
			map.put("paymentOrder", null);
			return map;
		}
		map.put("paymentOrder", paymentOrder);
		map.put("isWC", userService.isWC(request));
		return map;
	}
	
	/** 
	* @Title: getPaymentResult 
	* @Description: 微信里面使用支付宝预处理，定时取支付结果
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @param paymentSerialNum
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/getPaymentResult")
	@ResponseBody
	public Map<String, Object> getPaymentResult(ModelMap model, HttpServletRequest request,
			HttpServletResponse response,String paymentSerialNum) {
		Map<String, Object> map = new TreeMap<String, Object>();
		PaymentOrder paymentOrder = userService.getPaymentOrderBySerialNum(paymentSerialNum);
		if(paymentOrder==null){
			return map;
		}
		map.put("isPaid", paymentOrder.getIsCompleted());
		return map;
	}

	/** 
	* @Title: verifyCode 
	* @Description: 验证码发送类 
	* @param @param phone 目标手机
	* @param @param request
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	@RequestMapping("/verifyCode")
	public @ResponseBody Boolean verifyCode(String phone,HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Map<String,String> config = userService.getSMSConfig();
		return VerifyCode.sendVerifyCode(session,phone,config.get("aliAppkey"),config.get("aliAppSecret"),config.get("signName"),config.get("templateCode"));
	}
	/** 
	* @Title: checkVerifyCode 
	* @Description: 检验用户输入的验证码正确性 
	* @param @param vip
	* @param @param request
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	@RequestMapping("/checkVerifyCode")
	public @ResponseBody Boolean checkVerifyCode(@RequestBody HongShiVip vip,HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		if(!Strings.isNullOrEmpty(vip.getcMobileNumber())){
			if(!Strings.isNullOrEmpty(vip.getCode())){
				if(!VerifyCode.checkVerifyCode(session,vip.getcMobileNumber(),vip.getCode())){
					return false;
				}else{
					return true;
				}
			}else {
				return false;
			}

		}
		return false;
	}
	
	/** 
	* @Title: checkNapaStorePhone 
	* @Description: 老板助手登陆，验证用户输入的登陆手机号是否已非配到对应的老板 
	* @param @param phone
	* @param @param request
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	@RequestMapping("/checkNapaStorePhone")
	public @ResponseBody Boolean checkNapaStorePhone(String phone,HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		return userService.getNapaStoreByPhone(phone);
	}
	
	/** 
	* @Title: choujiang 
	* @Description: 轮盘抽奖类，暂时废弃，改用下面的lotteryConfig
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/choujiang")
	public @ResponseBody Map<String,Object> choujiang(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		Integer result = userService.getChoujiangResult(userId);
		map.put("result", result);
		return map;
	}
	
	/** 
	* @Title: home 
	* @Description: 取得微商城首页的banner和产品数据 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/home")
	public @ResponseBody Map<String,Object> home(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		String[] tags = {TermGroupTag.HOT_PRODUCT,TermGroupTag.RECOMMEND};
		List<ProductGroup> groups = userService.getTermGroups(tags);
		List<Banner> banner = userService.selectAllBanner();
		map.put("groups", groups);
		map.put("banner", banner);
		return map;
	}
	/** 
	* @Title: getAllProduct 
	* @Description: 根据筛选条件取得全部商品列表
	* @param @param request
	* @param @param categoryId 用户所选分类id
	* @param @param isSaleDesc	是否按照销量倒叙
	* @param @param isPriceDesc	是否按照价格倒叙
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getAllProduct")
	public @ResponseBody Map<String,Object> getAllProduct(HttpServletRequest request,Integer categoryId,Boolean isSaleDesc,Boolean isPriceDesc) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<Category> cat = duobaoService.getAllCat();
		map.put("cat", cat);
		List<ProductDto> products = duobaoService.getAllProduct(categoryId,isSaleDesc,isPriceDesc);
		map.put("products", products);
		return map;
	}
	/** 
	* @Title: getOrderList 
	* @Description: 对接洪石订单，取得配送中或者结单的订单
	* @param @param request
	* @param @param isEnd 是否结单
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getOrderList")
	public @ResponseBody Map<String,Object> getOrderList(HttpServletRequest request,Boolean isEnd) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		List<HongShiOrder> orders = userService.getHongShiOrder(userId,isEnd);
		map.put("orders", orders);
		return map;
	}
	/** 
	* @Title: getAddrById 
	* @Description: 根据地址id取得对应的地址信息
	* @param @param request
	* @param @param addrId  地址id
	* @param @return    设定文件 
	* @return DeliverAddr    返回类型 
	* @throws 
	*/
	@RequestMapping("/getAddrById")
	public @ResponseBody DeliverAddr getAddrById(HttpServletRequest request,Integer addrId) {
		return userService.selectAddrById(addrId);
	}
	
	/** 
	* @Title: getStoreAddr 
	* @Description: 取得当前所选加盟店对应的地理位置信息，用于地图api的调用
	* @param @param request
	* @param @param storeId
	* @param @return    设定文件 
	* @return NapaStore    返回类型 
	* @throws 
	*/
	@RequestMapping("/getStoreAddr")
	public @ResponseBody NapaStore getStoreAddr(HttpServletRequest request,Integer storeId) {
		NapaStore store = userService.getNapaStore(storeId);
		return store;
	}
	/** 
	* @Title: getCoupon 
	* @Description: 根据用户id取得当前用户可使用的优惠券列表 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getCoupon")
	public @ResponseBody Map<String,Object> getCoupon(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		List<HongShiCoupon> coupons = userService.selectCouponById(userId);
		map.put("coupons", coupons);
		return map;
	}
	
	/** 
	* @Title: order 
	* @Description: 提交订单页面的数据处理
	* @param @param request
	* @param @param cart 已选的购物车id集合
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/order")
	public @ResponseBody Map<String,Object> order(HttpServletRequest request,@RequestBody List<CartDto> cart){
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		DeliverAddr defaultAddr = userService.getDefaultAddrByUserId(userId);
		map.put("defaultAddr", defaultAddr);
		List<CartDto> carts = userService.selectCartByIds(userId,cart);
		BigDecimal total = new BigDecimal(0);
		for(CartDto item:carts){
			total = total.add(item.getMoney().multiply(new BigDecimal(item.getAmount())));
		}
		map.put("cartItems", carts);
		map.put("total", total);
		return map;
	}
	
	/** 
	* @Title: payment 
	* @Description: 支付页面的数据接口，返回支付方式，订单信息
	* @param @param request
	* @param @param paymentSerialNum 支付单号
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("payment")
	public @ResponseBody Map<String,Object> payment(HttpServletRequest request,String paymentSerialNum){
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		PaymentOrder paymentOrder = userService.selectPaymentOrderBySerialNum(paymentSerialNum);
		List<Payment> payments = new ArrayList<Payment>();
		if(paymentOrder!=null&&paymentOrder.getMoney().compareTo(new BigDecimal(0))>0){
			payments = userService.selectAllPayment();
		}else{
			payments = userService.selectMemberPayment();
		}
		map.put("payment", payments);
		HongShiVip hongShiVip = userService.getHongShiVip(userId);
		if(hongShiVip!=null){
			map.put("balance", hongShiVip.getBalance());
		}else{
			map.put("balance", 0);
		}
		map.put("paymentOrder", paymentOrder);
		return map;
	}
	
	/** 
	* @Title: unpayOrderList 
	* @Description: 根据用户id选择待支付订单列表信息
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("unpayOrderList")
	public @ResponseBody Map<String,Object> unpayOrderList(HttpServletRequest request){
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		List<Order> orders = userService.getUnpayOrderListByUserId(userId);
		map.put("orders", orders);
		return map;
	}
	
	/** 
	* @Title: wxConfig 
	* @Description: 调用微信jssdk的工具接口，取得必要的参数信息 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @param url
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/wxConfig")
	@ResponseBody
	public Map<String, String> wxConfig(ModelMap model, HttpServletRequest request,
			HttpServletResponse response,String url) {
		url = url.replaceAll("\\+", "&");
		return userService.WCScan(url, request);
	}
	
	/** 
	* @Title: distCenter 
	* @Description: 根据用户id取得对应的分销中心的数据接口 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/distCenter")
	@ResponseBody
	public Map<String, Object> distCenter(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map=userService.distCenter(userId);
		logger.info(JSON.toJSONString(map));
		return map;
	}
	/** 
	* @Title: distUser 
	* @Description: 根据用户id取得对应的下级用户列表 
	* @param @param request 
	* @param @param userId 指定的用户id
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/distUser")
	@ResponseBody
	public Map<String, Object> distUser(HttpServletRequest request,Integer userId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		if(userId==null){
			userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		}
		List<UserInvitedLink> users = userService.getInvitationList(userId);
		map.put("users", users);
		return map;
	}
	/** 
	* @Title: distOrder 
	* @Description: 根据用户id取得其下级，下下级的订单列表 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/distOrder")
	@ResponseBody
	public Map<String, Object> distOrder(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		List<Order> orders = userService.getInvitationOrder(userId);
		map.put("orders", orders);
		Balance balance = userService.selectBalanceByUserId(userId);
		if(balance!=null){
			map.put("money", balance.getBalance());
		}
		return map;
	}
	
	/** 
	* @Title: invitation 
	* @Description: 根据用户id取得对应的下级用户列表 ，已废弃，改为上述的 distUser
	* @param @param request
	* @param @param userId
	* @param @return    设定文件 
	* @return List<UserInvitedLink>    返回类型 
	* @throws 
	*/
	@RequestMapping("/invitationList")
	public @ResponseBody List<UserInvitedLink> invitation(HttpServletRequest request,Integer userId){
		HttpSession session = request.getSession();
		if(userId==null){
			userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		}
		return userService.getInvitationList(userId);
	}
	/** 
	* @Title: invitationOrder 
	* @Description: 根据用户id取得对应的下级订单列表 ，已废弃，改为上述的distOrder
	* @param @param request
	* @param @param serialNum
	* @param @return    设定文件 
	* @return List<Order>    返回类型 
	* @throws 
	*/
	@RequestMapping("/invitationOrder")
	public @ResponseBody List<Order> invitationOrder(HttpServletRequest request,String serialNum){
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		return userService.getInvitationOrder(userId);
	}
	
	/** 
	* @Title: editAddr 
	* @Description: 编辑或者新增地址页面数据接口
	* @param @param request
	* @param @param deliverAddrId 如果是编辑，就传对应的地址id，得到源数据
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/editAddr")
	public @ResponseBody Map<String,Object> editAddr(HttpServletRequest request,Integer deliverAddrId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		if(deliverAddrId!=null){
			DeliverAddr deliverAddr = userService.getAddrInfo(userId,deliverAddrId);
			map.put("deliverAddr", deliverAddr);
			List<City> city = userService.getCities(deliverAddr.getProvinceId());
			map.put("city", city);
			List<Region> region = userService.getRegions(deliverAddr.getCityId());
			map.put("region", region);
		}
		List<Province> province = userService.getAllProvince();
		map.put("province", province);
		return map;
	}
	
	/** 
	* @Title: addrList 
	* @Description: 获取用户的地址列表数据 
	* @param @param request
	* @param @param productId
	* @param @return    设定文件 
	* @return List<DeliverAddr>    返回类型 
	* @throws 
	*/
	@RequestMapping("/addrList")
	public @ResponseBody List<DeliverAddr> addrList(HttpServletRequest request,Integer productId){
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		return userService.getDeliverAddrList(userId);
	}
	
	/** 
	* @Title: cart 
	* @Description: 根据userId和storeId获取当期用户的购物车数据 
	* @param @param request
	* @param @param storeId 所选加盟店id，用于判断购物车商品是否被当前加盟店支持
	* @param @return    设定文件 
	* @return List<CartDto>    返回类型 
	* @throws 
	*/
	@RequestMapping("/cart")
	public @ResponseBody List<CartDto> cart(HttpServletRequest request,Integer storeId){
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		return userService.getUserCart(userId,storeId);
	}
	
	/** 
	* @Title: productDetail 
	* @Description: 产品详情单页的数据接口 
	* @param @param request
	* @param @param productId 产品id
	* @param @return    设定文件 
	* @return ProductDto    返回类型 
	* @throws 
	*/
	@RequestMapping("/productDetail")
	public @ResponseBody ProductDto productDetail(HttpServletRequest request,Integer productId){
		return userService.getProductDtoById(productId);
	}
	
	/** 
	* @Title:  
	* @Description: 获取微信appid配置 
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/getAppId")
	public @ResponseBody String getAppId(HttpServletRequest request,String merchantCode){
		return userService.getAppId(merchantCode);
	}
	
	
	/** 
	* @Title: shakeHandler 
	* @Description: 摇一摇数据处理
	* @param @param request
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@RequestMapping("/shakeHandler")
	public @ResponseBody boolean shakeHandler(HttpServletRequest request){
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		//是否关注公众号
		if(!userService.getIsSubScribe(userId)){
			return false;
		}
		return userService.shakeHandler(userId);
	}
	
	/** 
	* @Title: getLotteryConfig 
	* @Description: 积分抽奖页面数据接口，获得积分抽奖配置等信息 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getLotteryConfig")
	public @ResponseBody Map<String,Object> getLotteryConfig(HttpServletRequest request){
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		Map<String,Object> result = new TreeMap<String,Object>();
		List<LotteryDrawConfig> configs = userService.getLotteryConfig();
		result.put("configs", configs);
		if(configs!=null&&configs.size()>0){
			result.put("limits", configs.get(0).getLimits());
			List<LotteryRecord> records = userService.getUserLotteryRecord(userId);
			//判断是否超过限制次数
			if(records.size()<configs.get(0).getLimits()){
				result.put("isAllow", true);
			}else{
				result.put("isAllow", false);
			}
			result.put("startTime", DateUtils.format(configs.get(0).getTimeStart(), DateUtils.FORMAT_LONG));
			result.put("endTime", DateUtils.format(configs.get(0).getTimeEnd(), DateUtils.FORMAT_LONG));
			Date now = new Date();
			//是否在活动时间内
			if(configs.get(0).getTimeStart().before(now)&&configs.get(0).getTimeEnd().after(now)){
				result.put("isInTime", true);
			}else{
				result.put("isInTime", false);
			}
		}else{
			result.put("isAllow", false);
			result.put("isInTime", false);
		}
		Config config = userService.getLotteryWebConfig();
		if(config!=null){
			result.put("cost", config.getValue());
		}
		OauthLogin login = userService.getOauthLoginInfoByUserId(userId);
		//是否关注公众号
		if(login!=null){
			result.put("isSubscribe", login.getIsSubcribe());
		}else{
			result.put("isSubscribe", false);
		}
		if(login!=null){
			List<HongShiVip> vip = hongShiVipService.getVipInfo(login.getOauthId());
			if(vip!=null&&vip.size()>0){
				result.put("point", vip.get(0).getBonusPoints());
			}
		}
		return result;
	}
	
	/** 
	* @Title: recharge 
	* @Description: 充值页面数据接口 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/seller/recharge")
	public @ResponseBody Map<String,Object> recharge(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		Map<String,Object> map = new TreeMap<String,Object>();
		//获取支付方式
		List<Payment> payments = userService.selectPaymentForRecharge();
		map.put("payment", payments);
		//获取充值配置
		List<RechargeConfig> config = backendService.selectAllRechargeConfig();
		map.put("config", config);
		OauthLogin login = userService.getOauthLoginInfoByUserId(userId);
		if(login!=null){
			List<HongShiVip> ret= hongShiVipService.getVipInfo(login.getOauthId());//openid 去拿信息
			if(ret!=null&&ret.size()>0){
				map.put("hongShiVip", ret.get(0));
			}else{
				map.put("hongShiVip", null);
			}
		}
		return map;
	}
	
	/** 
	* @Title: getProvince 
	* @Description: 获取省份数据 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getProvinces")
	public @ResponseBody Map<String,Object> getProvince(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		List<Province> state = userService.getAllProvince();
		map.put("state", state);
		return map;
	}
	
	/** 
	* @Title: getShippingFee 
	* @Description: 根据距离获取两地址的运费 
	* @param @param request
	* @param @param distance
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getShippingFee")
	public @ResponseBody Map<String,Object> getShippingFee(HttpServletRequest request,double distance) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		logger.info("distance: " + JSON.toJSONString(distance));
		List<Freight> freights = userService.getAllFreightConfig();
		Double money=null;
		for(Freight freight:freights){
			if(distance > freight.getCondition()){
				logger.info(freight.getMoney());
				money=freight.getMoney().doubleValue();
				logger.info(money);
				break;
			}
		}
//		if(money==null&&freights!=null&&freights.size()>0){
//			money=freights.get(freights.size()-1).getMoney().doubleValue();
//		}
		if(money==null){
			money=(double) 0;
		}
		map.put("money", money);
		return map;
	}
	
	/** 
	* @Title: getCities 
	* @Description: 根据省份id获取对应的城市列表 
	* @param @param request
	* @param @param provinceId 省份id
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getCities")
	public @ResponseBody Map<String,Object> getCities(HttpServletRequest request,Integer provinceId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		if(provinceId!=null){
			List<City> city = userService.getCities(provinceId);
			logger.info(JSON.toJSONString(city));
			map.put("city", city);
		}
		return map;
	}
	
	/** 
	* @Title: getCities 
	* @Description: 根据省份名称获取对应城市列表 
	* @param @param request
	* @param @param stateId 省份名称
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getCitiesByStr")
	public @ResponseBody Map<String,Object> getCities(HttpServletRequest request,String stateId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		if(stateId!=null){
			List<City> city = userService.getCitiesByStr(stateId);
			logger.info(JSON.toJSONString(city));
			map.put("city", city);
		}
		return map;
	}
	
	/** 
	* @Title: getRegions 
	* @Description:根据城市id获取对应区列表
	* @param @param request
	* @param @param cityId 城市id
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getRegions")
	public @ResponseBody Map<String,Object> getRegions(HttpServletRequest request,Integer cityId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		if(cityId!=null){
			List<Region> region = userService.getRegions(cityId);
			map.put("region", region);
		}
		return map;
	}
	/** 
	* @Title: getRegionsByStr 
	* @Description: 根据城市名称获取对应的区列表
	* @param @param request
	* @param @param cityId
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getRegionsByStr")
	public @ResponseBody Map<String,Object> getRegionsByStr(HttpServletRequest request,String cityId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		if(cityId!=null){
			List<Region> region = userService.getRegionsByStr(cityId);
			map.put("region", region);
		}
		return map;
	}

	
	/** 
	* @Title: getUserInfo 
	* @Description: 获取用户的基本信息 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	public Map<String,Object> getUserInfo( HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		map.put("result","fail");
		if(userId!=null){
			UserProfile profile = userService.getBasicUserProfile(userId);
			User user = userService.getUserById(userId);
			if(user!=null){
				map.put("serialNum", user.getSerialNum());
			}
			if (profile!=null) {
				map.put("phone", profile.getPhone());
				map.put("nickName", profile.getNickName());
				map.put("image", profile.getImage());
				map.put("result","success");
			}
		}
		OauthLogin tt = userService.getOauthLoginInfoByUserId(userId);
		if(tt!=null){
			List<HongShiVip> ret= hongShiVipService.getVipInfo(tt.getOauthId());//openid 去拿信息
			if(ret!=null&&ret.size()>0){
				map.put("point", ret.get(0).getBonusPoints());
				map.put("balance", ret.get(0).getBalance());
			}
			List<HongShiCoupon> coupons = userService.selectCouponById(userId);
			if(coupons!=null){
				map.put("couponAmount", coupons.size());
			}else{
				map.put("couponAmount",0);
			}
		}
		
		return map;
	}


	/** 
	* @Title: tokenLogin 
	* @Description: 用token来长期保存登陆状态 
	* @param @param token
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/tokenLogin")
	@ResponseBody
	public Map<String,Object> tokenLogin(String token,HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		map.put("result","fail");
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		if(userId!=null){
			map.put("result","success");
		}else {
			Integer uid = JwtUtil.decodeTokenToGetUserId(token);
			if (!uid.equals(0)) {
				map.put("result", "success");
				session.setAttribute(GlobalSessionConstant.USER_ID, uid);
			} else {
				map.put("reason", "token_invalid");
			}
		}
		return map;
	}
	
	/** 
	* @Title: ipAddr 
	* @Description: 获取当前请求的ip
	* @param @param request
	* @param @param cityId
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/ipAddr")
	public @ResponseBody Map<String,Object> ipAddr(HttpServletRequest request,Integer cityId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		String ipAddr = userService.getIpAddr(request);
		map.put("ipAddr", ipAddr);
		return map;
	}
	
	/** 
	* @Title: isSubscribe 
	* @Description:判断当前用户是否关注公众号 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/isSubscribe")
	public @ResponseBody Map<String,Object> isSubscribe(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		boolean isSubscribe = userService.getIsSubScribe(userId);
		map.put("isSubscribe", isSubscribe);
		return map;
	}
	/** 
	* @Title: getSerialNum 
	* @Description: 根据用户id获取其对应的用户序列号 
	* @param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/getSerialNum")
	public @ResponseBody Map<String,Object> getSerialNum(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		User user = userService.getUserById(userId);
		if(user!=null){
			map.put("serialNum", user.getSerialNum());
		}else{
			map.put("serialNum", "");
		}
		return map;
	}
	
	/** 
	* @Title: isWC 
	* @Description: 根据请求头判断当前请求是否属于微信请求
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/isWC")
	@ResponseBody
	public Map<String, Object> isWC(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new TreeMap<String, Object>();
		String browser = UserAgentUtils.getBrowserInfo(JSON.toJSONString(request.getHeader("User-Agent")));
		if (browser.contains("MicroMessenger")) {
			int version = Integer.parseInt(browser.substring(browser.length() - 1, browser.length()));
			if (version >= 5) {
				map.put("isWC", true);
			} else {
				map.put("isWC", false);
			}
		} else {
			map.put("isWC", false);
		}
		return map;
	}

	/** 
	* @Title: fastPaymentScan 
	* @Description: 方法已废弃
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @param url
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/fastPaymentScan")
	@ResponseBody
	public Map<String, String> fastPaymentScan(ModelMap model, HttpServletRequest request,
			HttpServletResponse response,String url) {
		logger.info("进入fastPaymentScan");
		logger.info(url);
		url = url.replaceAll("\\+", "&");
		return userService.WCScan(url, request);
	}
	/** 
	* @Title: wCVerify 
	* @Description: 测试方法，请忽略
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @param echostr
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/wCVerify")
	@ResponseBody
	public String wCVerify(ModelMap model, HttpServletRequest request,
			HttpServletResponse response,String echostr) {
		return echostr;
	}
	
	
	/** 
	* @Title: testWXMessage 
	* @Description: 测试发送微信短信 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/testWXMessage")
	@ResponseBody
	public String testWXMessage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		duobaoService.sendWXMessageSuccess(105,5,"14782464907416592");
		return "";
	}
	
	/** 
	* @Title: bossCenter 
	* @Description: 老板助手数据接口 
	* @param @param request
	* @param @param phone
	* @param @param hsCode
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("/bossCenter")
	public @ResponseBody Map<String, Object> bossCenter(HttpServletRequest request,String phone,String hsCode){
		
		return userService.getBossCenter(phone,hsCode);
	}
}
