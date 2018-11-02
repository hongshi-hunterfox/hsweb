package com.uclee.web.user.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.backend.service.BackendServiceI;
import com.uclee.dynamicDatasource.DataSourceFacade;
import com.uclee.datasource.service.DataSourceInfoServiceI;
import com.uclee.date.util.DateUtils;
import com.uclee.file.util.FileUtil;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.config.links.TermGroupTag;
import com.uclee.fundation.config.links.WebConfig;
import com.uclee.fundation.data.mybatis.mapping.*;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.BargainPost;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private ConfigMapper configMapper;
	@Autowired
	private BackendServiceI backendService;
	@Autowired
	private HongShiVipServiceI hongShiVipService;
	@Autowired
	private DataSourceInfoServiceI dataSourceInfoService;
	@Autowired
	private SignRecordMapper signRecordMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private HongShiMapper hongShiMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private FullCutMapper fullCutMapper;
	@Autowired
	private ShippingFullCutMapper shippingFullCutMapper;
	@Autowired
	private RechargeRewardsRecordMapper rechargeRewardsRecordMapper;
	@Autowired
	private RechargeConfigMapper rechargeConfigMapper;
	@Autowired
	private BindingRewardsMapper bindingRewardsMapper;
	@Autowired
	private EvaluationGiftsMapper evaluationGiftsMapper;
	@Autowired
	private IntegralInGiftsMapper integralinGiftsMapper;
	
	
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
	 * 
	 * 校验手机号码--skx
	 */
	@RequestMapping("/isphone")
	public @ResponseBody Map<String,Object> isphone(String phone,HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[6])|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		    if (phone.length() != 11) {
		        map.put("fail","手机号应为11位数字");
		    } else {
		        Pattern p = Pattern.compile(regex);
		        Matcher m = p.matcher(phone);
		        boolean isMatch = m.matches();
		        System.out.println(isMatch);
		        if (!isMatch) {
		        	map.put("fail","请填入正确的手机号");
		        }else{
		        	map.put("fail","adopt");
		        }
		    }   
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
		HttpSession session = request.getSession();
		Map<String, String> config = userService.getSMSConfig();
		Integer userId = (Integer) session.getAttribute(GlobalSessionConstant.USER_ID);
		List<UserProfile> numbers = userService.selectAllProfileLists(userId);
		if(numbers.get(0).getPhone()!=null && numbers.size()>0){
			if(numbers.get(0).getPhone().equals(phone)){
				System.out.println("没有修改手机号");	
			}else{
				return VerifyCode.sendVerifyCode(session, phone, config.get("aliAppkey"), config.get("aliAppSecret"),
						config.get("signName"), config.get("templateCode"));
			}	
		}else{
			//第一次使用商城绑定会员卡，根据user_id取不到手机号执行下面代码
			return VerifyCode.sendVerifyCode(session, phone, config.get("aliAppkey"), config.get("aliAppSecret"),
					config.get("signName"), config.get("templateCode"));
		}
		
		return false;
	}
	
	/** 
	* @Title: verifyCode 
	* @Description: 老板助手验证码发送类 
	* @param @param phone 目标手机
	* @param @param request
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	@RequestMapping("/bossVerifyCode")
	public @ResponseBody Boolean bossVerifyCode(String phone,HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String,String> config = userService.getSMSConfig();
		return VerifyCode.sendVerifyCode(session,phone,config.get("aliAppkey"),config.get("aliAppSecret"),config.get("signName"),config.get("templateCode"));
	}
	
	/**
	 * @Title: verifyCodes 
	 * @value: 判断是否校验验证码
	 */
	@RequestMapping("/verifyCodes")
	public @ResponseBody Boolean verifyCodes(String phone, HttpServletRequest request) {
		HttpSession session = request.getSession();		
		Integer userId = (Integer) session.getAttribute(GlobalSessionConstant.USER_ID);
		List<UserProfile> numbers = userService.selectAllProfileLists(userId);
		if(numbers.get(0).getPhone()!=null && numbers.size()>0){
			if(numbers.get(0).getPhone().equals(phone)){
				System.out.println("没有修改手机号");	
	
			}else{
				return true;
			}
		}
		return false;
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
		List<HomeQuickNavi> quickNavis = userService.getQuickNavis();
		List<Banner> banner = userService.selectAllBanner();
		map.put("groups", groups);
		map.put("banner", banner);
		map.put("quickNavis", quickNavis);
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
	public @ResponseBody Map<String,Object> getAllProduct(HttpServletRequest request,Integer categoryId,Boolean isSaleDesc,Boolean isPriceDesc,String keyword,Integer naviId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<Category> cat = duobaoService.getAllCat();
		map.put("cat", cat);
		if(keyword!=null){
			keyword = "%"+keyword+"%";
		}
		List<ProductDto> products = duobaoService.getAllProduct(categoryId, isSaleDesc, isPriceDesc, keyword, naviId);
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
		//因为邓彪不修改存储过程，取回来的department是ID，不是名字。所以要修改一下by chiangpan
		for(int i=0;i<orders.size();i++){
			HongShiOrder hongShiOrder=orders.get(i);
			if(hongShiOrder.getDepartment()!=null){
				//根据hsCode获得到店铺名称
				String storeName = backendService.getHongShiStoreName(hongShiOrder.getDepartment());
				hongShiOrder.setDepartment(storeName);
			}
		}
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
	 * 新绑定会员赠送优惠券数据
	 *@param @param request
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
    */
	@RequestMapping("/getShowCoupon")
	public @ResponseBody Map<String,Object> getShowCoupon(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		List<HongShiCoupon> coupons = userService.selectCouponById(userId);		
		if(userId!=null){	
			OauthLogin tt = userService.getOauthLoginInfoByUserId(userId);			
			List<Lnsurance> lnsurance = userService.getUsers(tt.getOauthId());
			if(lnsurance!=null&&lnsurance.size()>0){
			List<Lnsurance> lnsurances = hongShiVipService.selectUsers(lnsurance.get(0).getPhone());
			if(lnsurances!=null&&lnsurances.size()>1){
				System.out.println("此会员以赠送过，不再显示！");
				return map;
			}
			}
			
			}
		map.put("coupons", coupons);
		return map;
	}
	/**
	 * 获取评论赠送提示内容
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCommentText")
	public @ResponseBody Map<String,Object> getCommentText(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		Config config = userService.getConfigByTag(WebConfig.commentText);
		if(config!=null){
			map.put("commentText", config.getValue());
		}else {
			map.put("commentText", "");
		}
		return map;
	}
	
	/**
	 * 获取会员绑定的配置文安
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBindText")
	public @ResponseBody Map<String,Object> getBindText(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		Config config = userService.getConfigByTag(WebConfig.bindText);
		if(config!=null){
			map.put("bindText", config.getValue());
		}else {
			map.put("bindText", "");
		}
		return map;
	}
	
	/**
	 * 获取签到奖品规则的配置文安
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSignText")
	public @ResponseBody Map<String,Object> getSignText(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		Config config = userService.getConfigByTag(WebConfig.signText);
		if(config!=null){
			map.put("signText", config.getValue());
		}else {
			map.put("signText", "");
		}
		return map;
	}
	//
	@RequestMapping("/isVoucherLimit")
	public @ResponseBody Map<String,Object> isVoucherLimit(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		

		if(userId!=null){
			//根据会员表有没有此手机号来决定跳转--外键获取的手机号有可能不是你本次输入的手机号，也不会跳转
			OauthLogin tt = userService.getOauthLoginInfoByUserId(userId);
			List<Lnsurance> lnsurance = userService.getUsers(tt.getOauthId());
			if(lnsurance!=null&&lnsurance.size()>0){
				List<HongShiVip> Vip = userService.selectVip(lnsurance.get(0).getPhone());
				if(Vip!=null&&Vip.size()>0){
					map.put("result",false);
					return map;
				}
			}
				
		}
		List<BindingRewards> bindingRewards = bindingRewardsMapper.selectOne();
		if(bindingRewards!=null&&bindingRewards.size()>=1){
			List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(bindingRewards.get(0).getVoucherCode());
			if(coupon.size()<bindingRewards.get(0).getAmount()||coupon.size()==0){
				map.put("result",false);
				return map;
			}
		}

		map.put("result",true);
		return map;
	}

	@RequestMapping("/Commentary")
	public @ResponseBody Map<String,Object> Commentary(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<EvaluationGifts> evaluationGift = evaluationGiftsMapper.selectOne();
		if(evaluationGift!=null&&evaluationGift.size()>=1){
			List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(evaluationGift.get(0).getVoucherCode());
			if(coupon.size()<evaluationGift.get(0).getAmount()){
				map.put("result",false);
				return map;
			}
		}
	map.put("result",true);
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
	@SuppressWarnings("deprecation")
	@RequestMapping("/order")
	public @ResponseBody Map<String,Object> order(HttpServletRequest request,@RequestBody List<CartDto> cart){
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		DeliverAddr defaultAddr = userService.getDefaultAddrByUserId(userId);
		map.put("defaultAddr", defaultAddr);
		List<CartDto> carts = userService.selectCartByIds(userId,cart);
		logger.info("cart post====="+JSON.toJSONString(carts));
		BigDecimal total = new BigDecimal(0);
		boolean isShippingFree=true;
		Date date = new Date();
		//拼接预定小时时间
		String appointedTime= "";
		for(CartDto item:carts){			
			date.setHours(new Date().getHours()+item.getAppointedTime());
			appointedTime=appointedTime+date.getHours()+",";
		}
		System.out.println("date2 = "+appointedTime);
		//转换字符串数组
		String str[] = appointedTime.split(","); 
		//字符串数组转为int数组
		Integer Hours[] = new Integer[str.length];  
		for(int i=0;i<str.length;i++){  
			Hours[i]=Integer.parseInt(str[i]);
		}
		//取最大值
		System.out.println("Hours"+Hours);
		int max = (int) Collections.max(Arrays.asList(Hours));
		System.out.println("Hours"+max);
		//取最大时间
		date.setHours(max);
		SimpleDateFormat time=new SimpleDateFormat("HH:mm");
		SimpleDateFormat riqi=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(time.format(date.getTime())); 
		map.put("appointedTime",time.format(date.getTime()));
		map.put("riqi",riqi.format(date));
		System.out.println("Hours"+time.format(date.getTime()));

		for(CartDto item:carts){
			long value = date.getTime();
			long value1 = 0;
            if(item.getStartTime()!=null){
            value1=item.getStartTime().getTime();
            }
            long value2 = 0;
			if(item.getEndTime()!=null){
			value2 = item.getEndTime().getTime();
			}
			BargainSetting price = userService.getPrice(item.getCartId());//获取砍价购买金额
			//判断提交订单商品是否有促销价
			if(value1!=0||value2!=0){
				if(item.getPromotion()!=null && value>value1 && value<value2){
					total = total.add(item.getPromotion().multiply(new BigDecimal(item.getAmount())));
				}else{
					//判断是否是砍价商品
					if(price==null){
						total = total.add(item.getMoney().multiply(new BigDecimal(item.getAmount())));
					}else{
						total = total.add(price.getPrice().multiply(new BigDecimal(item.getAmount())));
					}	
				}
			}else{
				//判断是否是砍价商品
				if(price==null){
					total = total.add(item.getMoney().multiply(new BigDecimal(item.getAmount())));
				}else{
					total = total.add(price.getPrice().multiply(new BigDecimal(item.getAmount())));
				}	
			}

					
			Product product = userService.getProductById(item.getProductId());
			if(product!=null&&!product.getShippingFree()){
				isShippingFree=false;
			}

			ProductParameters csshuxings = userService.obtainParameters(item.getCanshuValueId());	
			if(csshuxings!=null&&csshuxings.getSname()!=null){
				item.setCsshuxing(csshuxings.getSname());
			}
			//判断是否是砍价商品
			if(price!=null){
				item.setMoney(price.getPrice());
			}
		}
		//把goodscode以字符串拼接在一起
		String a="";
		for(int i=0;i<carts.size();i++){
			Cart valueid=userService.selectValueId(userId, carts.get(i).getCartId());
			SpecificationValue hsgooscode = userService.selectGoods(valueid.getSpecificationValueId());
			a=a+hsgooscode.getHsGoodsCode()+",";
		}
		map.put("hsgooscode",JSON.toJSONString(a));
		map.put("cartItems", carts);
		map.put("isShippingFree", isShippingFree);
		map.put("total", total);

		Config config = userService.getConfigByTag(WebConfig.supportDeliver);
		if(config!=null&&config.getValue().equals("yes")){
			map.put("supportDeliver",true);
		}else{
			map.put("supportDeliver",false);
			map.put("isSelfPick","true");
		}
		List<String> salesInfo = new ArrayList<String>();
		List<FullCut> fullCuts = fullCutMapper.selectAllActive(new Date());
		BigDecimal cut = new BigDecimal(0);
		for(FullCut fullCut:fullCuts){
			if(total.compareTo(fullCut.getCondition())>=0){
				cut = fullCut.getCut();
			}
		}
		map.put("cut", cut);
		Integer count = 1;
		for(FullCut fullCut:fullCuts){
			String tmp = "";
			tmp = count + ". 整单满" + fullCut.getCondition()+"元减"+fullCut.getCut()+"元";
			count++;
			salesInfo.add(tmp);
		}
		List<ShippingFullCut> shippingFullCuts = shippingFullCutMapper.selectAllShippingFullCutActive(new Date());
		for(ShippingFullCut shippingFullCut:shippingFullCuts){
			String tmp = "";
			tmp = count + ". "+shippingFullCut.getsLimit()+"-"+shippingFullCut.getuLimit()+"公里,"+"满"+shippingFullCut.getCondition()+"元免运费";
			count++;
			salesInfo.add(tmp);
		}
		map.put("salesInfo",salesInfo);
		return map;
	}
	
	/**
	 * @Description: 更新砍价发起记录状态
	 */
	@RequestMapping("/status")
	public @ResponseBody Map<String,Object> status(HttpServletRequest request,String paymentSerialNum){
		HttpSession session = request.getSession();
		Map<String,Object> map = new TreeMap<String,Object>();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		OauthLogin oauth = userService.getOauthLoginInfoByUserId(userId);
		vipIdentity vip = userService.selectVipIdentity(oauth.getOauthId());
		if(vip!=null){
			//判断是否加过购物车
			LaunchBargain record = userService.getbargainRecord(userId);
			if(record!=null){
				map.put("record", "已经加过购物车了,不能重复加入");
				userService.updateLaunchBargain(userId);
			}
		}
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
		//添加扫描规则
		paymentOrder.setCheckCount(0);
		paymentOrder.setCreateTime(new Date());
		userService.updatePaymentOrder(paymentOrder);
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
		return userService.getUserCart(userId, storeId);
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
	public @ResponseBody ProductDto productDetail(HttpServletRequest request,Integer productId,Integer tid){
		ProductDto productDto = userService.getProductDtoById(productId);
		System.out.println(JSON.toJSONString(productDto));
		return productDto;
	}

	@RequestMapping("/productDetailImg")
	public @ResponseBody ProductDto productDetailImg(HttpServletRequest request,Integer productId){
		ProductDto productDto = productMapper.getProductById(productId);
		productDto.setDescription(FileUtil.UrlRequest(productDto.getDescription()));
		return productDto;
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
	@RequestMapping(value="/comment")
	public @ResponseBody Map<String,Object> comment(HttpServletRequest request,String orderSerialNum) {
		Map<String,Object> map = new TreeMap<String,Object>();
		Comment tmp = commentMapper.selectByOrderId(orderSerialNum);
		map.put("comment",tmp);
		return map;
	}
	@RequestMapping(value="/getRechargeAble")
	public @ResponseBody Map<String,Object> getRechargeAble(HttpServletRequest request, BigDecimal money, BigDecimal rewards) {
		Map<String,Object> map = new TreeMap<String,Object>();
		RechargeConfig rechargeConfig = rechargeConfigMapper.selectByMoney(money, rewards);
		if(rechargeConfig==null||rechargeConfig.getEndTime().before(new Date())){
			map.put("result",false);
		}else{
			map.put("result",true);
		}
		return map;
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
		List<LotteryDrawConfig> tmp = userService.getLotteryConfig();
		List<LotteryDrawConfig> configs  = new ArrayList<LotteryDrawConfig>();
		for(LotteryDrawConfig config : tmp){
			if(config.getRate()>0){
				configs.add(config);
			}
		}
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
			result.put("rest", configs.get(0).getLimits()-records.size());
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
		List<RechargeConfig> configs = backendService.selectMonPeiZhi();
		Map<String,List<String>> extraData = new HashMap<String,List<String>>();
		for(RechargeConfig config:configs){
			if(config.getStartTime()!=null&&config.getEndTime()!=null&&new Date().after(config.getStartTime())&&new Date().before(config.getEndTime())){
				map.put("inTime", true);
				config.setInTime(true);
				RechargeRewardsRecord record = rechargeRewardsRecordMapper.selectByConfigIdAndUserId(config.getId(),userId);
				if(record==null||(config.getLimit()!=null&&config.getLimit()>record.getCount())){
					List<String> extra = new ArrayList<String>();
					int i = 1;
					if(!StringUtils.isEmpty(config.getVoucherCode())){
						List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(config.getVoucherCode());
						if (coupon!=null&&coupon.size()>0&&config.getAmount()!=null&&config.getAmount()>0) {
							HongShiCoupon name = hongShiMapper.getCouponName(coupon.get(0).getGoodsCode());
							//coupon.get(0).getPayQuota().setScale(2, BigDecimal.ROUND_DOWN)+"元现金优惠券"
							String tmp = i + "： " + name.getName() +config.getAmount()+"张";
							i++;
							extra.add(tmp);
						}
					}
					if(!StringUtils.isEmpty(config.getVoucherCodeSecond())) {
						List<HongShiCoupon> coupon2 = hongShiMapper.getHongShiCouponByGoodsCode(config.getVoucherCodeSecond());
						if (coupon2 != null && coupon2.size() > 0&&config.getAmountSecond()!=null&&config.getAmountSecond()>0) {
							HongShiCoupon name = hongShiMapper.getCouponName(coupon2.get(0).getGoodsCode());
							// coupon2.get(0).getPayQuota().setScale(2, BigDecimal.ROUND_DOWN) + "元现金优惠券" 
							String tmp = i + "： " + name.getName() + config.getAmountSecond() + "张";
							i++;
							extra.add(tmp);
						}
					}
					if(!StringUtils.isEmpty(config.getVoucherCodeSecond())) {
						List<HongShiCoupon> coupon3 = hongShiMapper.getHongShiCouponByGoodsCode(config.getVoucherCodeThird());
						if (coupon3 != null && coupon3.size() > 0&&config.getAmountThird()!=null&&config.getAmountThird()>0) {
							HongShiCoupon name = hongShiMapper.getCouponName(coupon3.get(0).getGoodsCode());
							//coupon3.get(0).getPayQuota().setScale(2, BigDecimal.ROUND_DOWN) + "元现金优惠券"
							String tmp = i + ". " + name.getName() + config.getAmountThird() + "张";
							i++;
							extra.add(tmp);
						}
					}
					if(extra!=null&&extra.size()>0) {
						extraData.put(String.valueOf(config.getMoney().multiply(new BigDecimal(100)).intValue()), extra);
					}
				}
			}else{
				map.put("inTime", false);
				config.setInTime(false);
				logger.error("不在充值优惠期间");
			}
		}
		map.put("config", configs);
		map.put("extraData", extraData);
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
	public @ResponseBody Map<String,Object> getShippingFee(HttpServletRequest request,double distance,BigDecimal total) {
		Map<String,Object> map = new TreeMap<String,Object>();
		logger.info("distance: " + JSON.toJSONString(distance));
		Double money=null;
		boolean isFullCut=false;
		List<ShippingFullCut> shippingFullCuts = shippingFullCutMapper.selectAllShippingFullCutActive(new Date());
		for(ShippingFullCut shippingFullCut:shippingFullCuts){
			logger.info("shippingFullCut.getuLimit()======"+shippingFullCut.getuLimit());
			if(shippingFullCut.getsLimit()<=distance&&shippingFullCut.getuLimit()>distance){
				if(total.compareTo(shippingFullCut.getCondition())>=0){
					money=0.0;
					isFullCut=true;
					break;
				}
			}
		}
		logger.info("isFullCut======"+isFullCut);
		if(!isFullCut) {
			logger.info("isFullCut======"+isFullCut);
			List<Freight> freights = userService.getAllFreightConfig();
			for (Freight freight : freights) {
				logger.info("freight.getCondition()======"+freight.getCondition());
				if (distance >= freight.getCondition()) {
					logger.info(freight.getMoney());
					money = freight.getMoney().doubleValue();
					logger.info(money);
					break;
				}
			}
		}
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
		map.put("userid", userId);
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
		
		SignRecord accumulations = userService.selectAccumulation(userId);
		if(accumulations!=null){
			map.put("accumulations",accumulations.getAccumulation());
		}
		
		SignRecord accumulation = userService.getAccumulation(userId);
			if(accumulation!=null){
				map.put("accumulation", accumulation.getAccumulation());
				logger.info("accumulation.getAccumulation()==="+accumulation.getAccumulation());
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
			List<HongShiOrder> orders = hongShiMapper.getHongShiOrder(tt.getOauthId(),false);
			for(int i=0; i<orders.size();i++){
				map.put("isvoid", orders.get(i).getVoid());
			}
			int deliCount = orders.size();
			map.put("deliCount", deliCount);
		}
		Date today = DateUtils.parse(DateUtils.format(new Date(), DateUtils.FORMAT_SHORT), DateUtils.FORMAT_SHORT);
		SignRecord existed = signRecordMapper.selectToday(userId,today);
		if(existed!=null){
			map.put("isSigned", true);
		}else{
			map.put("isSigned", false);
		}
		int unPayCount  = userService.getUnpayOrderCountByUserId(userId);
		map.put("unPayCount", unPayCount);
		int unCommentCount = userService.getUnCommentCount(userId);
		map.put("unCommentCount", unCommentCount);
		Config config = configMapper.getByTag(WebConfig.ucenterImg);
		if(config!=null) {
			map.put("ucenterImg", config.getValue());
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
	
	/**
	 * @Description: 小助手数据接口-skx
	 */
	@RequestMapping("/DataView")
	public @ResponseBody Map<String, Object> assistant(HttpServletRequest request,String QueryName,String phone,String hsCode){
		return userService.getMobJect(QueryName,phone,hsCode);
	}
	
	/**
	 * @Description: 评论赠送接口skx
	 */
	@RequestMapping("/zengSong")
	public @ResponseBody HongShiCommonResult zengsong(HttpServletRequest request,String oauthId,Integer point,String tag){
		//1、更新券数量 原来的券数量加上现在的web_evaluation_config里面的amount就可以了。
		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		
		OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(userId);
		
		List<EvaluationGifts> evaluationGifts = evaluationGiftsMapper.selectOne();
		//判断赠送数量
		for(int i=0;i<evaluationGifts.get(0).getAmount();i++){
		List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(evaluationGifts.get(0).getVoucherCode());
		if(coupon!=null && !coupon.isEmpty()){
			if(coupon != null && coupon.size()>0){                      
				int a= hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(0).getVouchersCode(),evaluationGifts.get(0).getVoucherCode(),"评价赠送礼券");
				if(a>0){
					System.out.println("发送成功");
				}else{
					System.out.println("发送失败");
				}
				
			}
		}else{
			System.out.println("券被抢光了");
		}
		}
		hongShiMapper.signInAddPoint(oauthLogin.getOauthId(),evaluationGifts.get(0).getPoint(),"评论赠积分");	
		//评论送金额
		HongShiRecharge dd=new HongShiRecharge().setcWeiXinCode(oauthLogin.getOauthId()).setcWeiXinOrderCode(null).setnAddMoney(evaluationGifts.get(0).getMoney());
		hongShiVipService.hongShiRecharge(dd);
	    return null;				
	}
	/**
	 * @Description: 签到赠送接口sjx
	 */
	@RequestMapping("/SigningGift")
	public @ResponseBody HongShiCommonResult SigningGift(HttpServletRequest request,String oauthId,Integer point,String tag){		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);		
		OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(userId);
		List<IntegralInGifts> integralinGifts = integralinGiftsMapper.selectOne();
		//判断是否在最大赠送活动天数内
		List<IntegralInGifts> daylist = integralinGiftsMapper.selectDay();
		SignRecord Accumulation = signRecordMapper.selectAccumulation(userId);
		if(Accumulation==null){
		int	accumulation = 1;
			if(accumulation <= daylist.get(0).getDay()){
				for(int i=0;i<integralinGifts.size();i++){					
					if(accumulation==integralinGifts.get(i).getDay()){
						logger.info("integralinGifts.get(i).getDay()======"+integralinGifts.get(i).getDay());
						//获取优惠券赠送数量
						int amount=integralinGifts.get(i).getAmount();
						for(int j=0;j<amount;j++){
							List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(integralinGifts.get(i).getVoucherCode());
							if(coupon!=null && !coupon.isEmpty()){
								if(coupon != null && coupon.size()>0){
					
									int s= hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(i).getVouchersCode(),integralinGifts.get(i).getVoucherCode(),"签到赠送礼券");
									if(s>0){
										System.out.println("发送成功");
									}else{
										System.out.println("发送失败");
									}					
								}
							}else{
								System.out.println("券被抢光了");
							}
			
						}
							
						//签到送金额	
						HongShiRecharge dd=new HongShiRecharge().setcWeiXinCode(oauthLogin.getOauthId()).setcWeiXinOrderCode(null).setnAddMoney(integralinGifts.get(i).getMoney());
						hongShiVipService.hongShiRecharge(dd);
					}
				}
			}
			return null;
		}

		
		if(Accumulation!=null){
			if(Accumulation.getAccumulation()+1 <= daylist.get(0).getDay()){

				for(int i=0;i<integralinGifts.size();i++){
				
					if(Accumulation.getAccumulation()+1==integralinGifts.get(i).getDay()){
						logger.info("integralinGifts.get(i).getDay()======"+integralinGifts.get(i).getDay());
						//获取优惠券赠送数量
						int amount=integralinGifts.get(i).getAmount();
						for(int j=0;j<amount;j++){
							List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(integralinGifts.get(i).getVoucherCode());
							if(coupon!=null && !coupon.isEmpty()){
								if(coupon != null && coupon.size()>0){
					
									int s= hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(i).getVouchersCode(),integralinGifts.get(i).getVoucherCode(),"签到赠送礼券");
									if(s>0){
										System.out.println("发送成功");
									}else{
										System.out.println("发送失败");
									}					
								}
							}else{
								System.out.println("券被抢光了");
							}
			
						}
							
						//签到送金额	
						HongShiRecharge dd=new HongShiRecharge().setcWeiXinCode(oauthLogin.getOauthId()).setcWeiXinOrderCode(null).setnAddMoney(integralinGifts.get(i).getMoney());
						hongShiVipService.hongShiRecharge(dd);
					}
				}
			}
		}
		return null;	

	}



	/**
	 * @Description : 订单详情页面 by chiangpan
	 */
	@RequestMapping(value="/getMyOrderDetail")
	public @ResponseBody Map<String,Object> getMyOrderDetail(HttpServletRequest request,String outerOrderCode){
		Map<String,Object> orderMap=new TreeMap<String,Object>();
		//根据微商城订单号取得订单
		Order order=userService.getOrderListSerailNum(outerOrderCode);
		System.out.println("order==============="+JSON.toJSONString(order));

		if(order!=null){
			NapaStore napaStore=userService.getNapaStore(order.getStoreId());//下单部门
			if(napaStore!=null){
				order.setStoreName(napaStore.getStoreName());//设置下单部门
			}

			order.setCreateTimeStr(DateUtils.format(order.getCreateTime(),DateUtils.FORMAT_LONG_CN));//下单时间
			order.setPickDateStr(DateUtils.format(order.getPickTime(),DateUtils.FORMAT_LONG_CN));//取货时间

		}

		orderMap.put("order",order);
		return orderMap;
	}
	
	/**
	 * @Description : 个人信息详情页面--jx
	 * @param vNumber
	 */
	@RequestMapping("/getVips")
	public @ResponseBody HsVip getVips(HttpServletRequest request, String vNumber) {
		HsVip res=new HsVip();
		
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute(GlobalSessionConstant.USER_ID);
		OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(userId);
		List<HongShiVip> ret= hongShiVipService.getVipInfo(oauthLogin.getOauthId());
		if(ret!=null&&ret.size()>0){

			List<HsVip> Vips = userService.selecthsVip(ret.get(0).getcVipCode());
			Map<String,Object> map = new TreeMap<String,Object>();
			for(HsVip item:Vips){
				String vName =item.getvName();
				String Number = item.getvNumber();
				String vBirthday= item.getvBirthday();
				String vIdNumber = item.getvIdNumber();
				String vCompany = item.getvCompany();
				String vCode=item.getvCode();
				String vSex=item.getvSex();
				res.setvName(vName);
				res.setvNumber(Number);
				res.setvBirthday(vBirthday);
				res.setvIdNumber(vIdNumber);
				res.setvCompany(vCompany);
				res.setvCode(vCode);
				res.setvSex(vSex);
				map.put("item", item);
			}

		}
		return res;
	}


	@RequestMapping("refund")
	public @ResponseBody Map<String,Object> refund(HttpServletRequest request,String refundSerialNum){
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		RefundOrder fund = userService.selectRefundOrderBySerialNum(refundSerialNum);

		Payment payment=userService.getPaymentMethodById(fund.getPaymentId());

		PaymentOrder paymentOrder=userService.getPaymentOrderBySerialNum(fund.getPaymentSerialNum());

		List<Order> orders=userService.selectOrderByPaymentSerialNum(userId,paymentOrder.getPaymentSerialNum());
		Order order=new Order();
		if(orders!=null && orders.size()>0){
			order=orders.get(0);
			String createTimeStr=DateUtils.format(order.getCreateTime());
			//下单时间
			order.setCreateTimeStr(createTimeStr);
			//下单部门
			NapaStore napaStore=userService.getNapaStore(order.getStoreId());

			order.setStoreName(napaStore.getStoreName());
		}

		map.put("order",order);
		map.put("payment", payment);
		map.put("paymentOrder", paymentOrder);
		map.put("refund",fund);
		return map;
	}
	
	/**
	 * @Description :砍价列表
	 */
	@RequestMapping(value="/getBargain")
	public @ResponseBody Map<String,Object> getBargain(HttpServletRequest request){
		Map<String,Object> map=new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		List<BargainPost> list = userService.getBargainList();		
		for(int i=0; i<list.size(); i++){
			//判断活动是否在时间范围内
//			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String start = sdf.format(list.get(i).getStart());
			String end = sdf.format(list.get(i).getClosure());
			list.get(i).setStarts(start);
			list.get(i).setEnds(end);
			map.put("list", list);		
		}
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		OauthLogin oauth = userService.getOauthLoginInfoByUserId(userId);
		//得到外键和会员id
		vipIdentity vip = userService.selectVipIdentity(oauth.getOauthId());
		if(vip!=null){
			LaunchBargain record = userService.getbargainRecord(userId);
			//判断该用户是否有砍价活动正在进行中
			if(record!=null){
				map.put("record", "检测到你有砍价活动正在进行中，即将进入砍价详情界面！");
				//取要进入的砍价信息
				BargainLog value = userService.getValueId(record.getPid(), userId);
				if(value!=null){
					map.put("valueId", value.getValueId());
					map.put("invitationcode", value.getInvitationCode());
				}
			}
		}else{
			map.put("vipFail","只有会员才能发起砍价，请先注册绑定会员");
		}
		System.out.println("date====="+System.currentTimeMillis());
		//以当前时间拼接用户id生成流水号
		String code = System.currentTimeMillis()+"="+userId;
		System.out.println("code====="+code);
		map.put("code", code);
		return map;
	}
	
	/**
	 * 插入记录到发起砍价表并把发起人砍价
	 * 记录插入到砍价记录表
	 */
	@RequestMapping("/LaunchBargain")
	public @ResponseBody Map<String,Object> LaunchBargain(HttpServletRequest request, String name, BigDecimal hsGoodsPrice, String codes) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		BargainSetting bargain = userService.selectName(name);
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		OauthLogin oauth = userService.getOauthLoginInfoByUserId(userId);
		//得到外键和会员id
		vipIdentity vip = userService.selectVipIdentity(oauth.getOauthId());
		LaunchBargain launchBargain = new LaunchBargain();
		if(vip!=null){
			launchBargain.setUid(userId);
			launchBargain.setOpenId(oauth.getOauthId());
			launchBargain.setPid(bargain.getId());
			launchBargain.setInitialAmount(hsGoodsPrice);
			//以设置的随机金额最大值来给发起砍价的人砍掉相应的金额--kx
			launchBargain.setCurrentAmount(bargain.getMaxprice());
			launchBargain.setLaunchTime(new Date());
			//状态1为发起状态，2为结束
			launchBargain.setStatus(1);
			System.out.println("code123=========="+codes);
			launchBargain.setInvitationCode(codes);
			System.out.println("插入到发起砍价表的信息"+JSON.toJSONString(launchBargain));
			LaunchBargain record = userService.getbargainRecord(userId);
			//判断是否有正在进行中的砍价活动
			if(record==null){
				userService.insertLaunchBargain(launchBargain);
				//把发起人的砍价记录插入到砍价记录表
				BargainLog log = new BargainLog();
				log.setPid(launchBargain.getId());
				log.setUid(launchBargain.getUid());
				log.setOpenId(launchBargain.getOpenId());
				//发起人砍掉的金额
				log.setRandomAmount(bargain.getMaxprice());
				log.setLaunchTime(new Date());
				//关联邀请码
				log.setInvitationCode(launchBargain.getInvitationCode());
				userService.insertBargainLog(log);
			}
		}
		return map;
	}
	
	/**
	 * 帮砍人帮砍记录
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/insertBargainLog")
	public @ResponseBody Map<String,Object> insertBargainLog(HttpServletRequest request, String codes, String productName, Integer valueId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		OauthLogin oauth = userService.getOauthLoginInfoByUserId(userId);
		vipIdentity vip = userService.selectVipIdentity(oauth.getOauthId());
		BargainSetting bargain = userService.selectName(productName);
		Random random = new Random();
		//根据活动设置生成砍价金额随机数
		Double min=bargain.getMinprice();
		Double max=bargain.getMaxprice();
		System.out.println("min======="+min);
		System.out.println("max======="+max);
		Double result = min + (Math.random() * ((max - min)));
		DecimalFormat df = new DecimalFormat("0.0");
		System.out.println("result======="+result);
			BargainLog log = new BargainLog();
			log.setPid(0);
			log.setUid(userId);
			log.setOpenId(oauth.getOauthId());
			BigDecimal sumMoney = userService.selectSumMoney(codes);
			//保证最后砍价金额等于最大能砍掉的金额
			BargainPost value = userService.getValue(valueId);
			double sum = sumMoney.intValue() + result;
			double price = value.getHsGoodsPrice().intValue() - value.getPrice().intValue();
			if(sum>price){				
				result = price - sumMoney.intValue();
			}
			result = new Double(df.format(result));
			map.put("result", result);
			log.setRandomAmount(result);
			log.setLaunchTime(new Date());
			//关联邀请码			
			log.setInvitationCode(codes);
			//取得砍价记录
			List<BargainLog> bargainLog = userService.selectbargainLog(userId, codes);
			System.out.println("bargainLog================="+bargainLog.size());
			
			//判断是否已经砍过价
			if(bargainLog==null||bargainLog.size()==0){
				userService.insertBargainLog(log);
			}else{
				System.out.println("有砍价记录");
			}
		return map;
	}
	/**
	 * 获取砍价记录数据
	 */
	@RequestMapping("/getBargainUser")
	public @ResponseBody Map<String,Object> getBargainUser(HttpServletRequest request, String codes) {
		Map<String,Object> map = new TreeMap<String,Object>();
		System.out.println("codesskx======"+codes);
		//取得砍价记录
		List<WxUser> users = userService.selectWxUser(codes);
		BigDecimal sumMoney = userService.selectSumMoney(codes);
		System.out.println("sumMoney========"+sumMoney.doubleValue());
		map.put("users", users);
        map.put("sunMoney", sumMoney.doubleValue());
		return map;
	}
	
	/**
	 * @Description:获取到要发起砍价的产品
	 */
	@RequestMapping("/goBargain")
	public @ResponseBody Map<String,Object> goBargain(HttpServletRequest request, Integer valueId, String codes) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		//得到会员信息
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		LaunchBargain launch = userService.selectLaunchLog(userId, codes);
		boolean status=false;
		if(launch==null){
			map.put("status", status);
		}else{
			status=true;
			map.put("status", status);
		}
		//取得砍价记录
		List<BargainLog> bargainLog = userService.selectbargainLog(userId, codes);
		System.out.println("bargainLog================="+bargainLog.size());
		map.put("size", bargainLog.size());
		System.out.println("valueId=="+valueId);
		BargainPost value = userService.getValue(valueId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String end = sdf.format(value.getClosure());
		value.setEnds(end);
		System.out.println("valuesid=="+value.getId());
		//判断活动是否过期
		BargainPost status1 = userService.getBargainOver(value.getId());
		if(status1 == null){
			map.put("status1", "活动已过期");
		}
		map.put("values", value);
		return map;
	}
	
	/**
	 * @Description:砍价活动发起成功通知
	 */
	@RequestMapping("/bargainMsg")
	public @ResponseBody boolean bargainMsg(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		return backendService.sendLaunchBargainhMsg(userId);
	}
	
	/**
	 * @Description:砍价活动砍价成功通知
	 */
	@RequestMapping("/sucessMsg")
	public @ResponseBody boolean sucessMsg(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		return backendService.sendSucessMsg(userId);
	}
	
	/**
	 * @Description:可领取礼券
	 */
	@RequestMapping("/linkCouponList")
	public @ResponseBody Map<String,Object> linkCouponList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<LinkCoupon> couponList = userService.selectByPrimaryKey();
		for(LinkCoupon item:couponList){
			List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(item.getVoucher());
			if(coupon==null || coupon.size()==0){
				item.setName("已抢光！");
			}
			map.put("couponList", couponList);
		}
		
		return map;
	}
	

	/**
	 * @Description:领取礼券
	 */
	@RequestMapping("/receiveCoupon")
	public @ResponseBody Map<String,Object> receiveCoupon(HttpServletRequest request, String voucher, String name) {
		Map<String,Object> map = new TreeMap<String,Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(userId);
		vipIdentity vip = userService.selectVipIdentity(oauthLogin.getOauthId());
		List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(voucher);
		List<LinkCouponLogs> log = userService.selectLinkCoponLog(name, oauthLogin.getOauthId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = sdf.format(log.get(0).getDay());//上次领取时间
		String d2 = sdf.format(new Date());//当前时间
		System.out.println(d1.equals(d2));//判断是否是同一天
		//不是同一天才能继续领取
		if(!d1.equals(d2)){
			if(vip != null){
				if(coupon!=null || coupon.size()>0){ 
					hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(0).getVouchersCode(),voucher,"会员领礼券");
					LinkCouponLogs linkCouponLogs = new LinkCouponLogs();
					linkCouponLogs.setName(name);
					linkCouponLogs.setOauthId(oauthLogin.getOauthId());
					linkCouponLogs.setDay(new Date());
					userService.insertLinkCouponLog(linkCouponLogs);
					map.put("success", true);
				}
			}else{
				map.put("vip", false);
			}
		}else{
			map.put("log", false);
		}
		return map;
	}
	
 }
