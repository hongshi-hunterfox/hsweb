package com.duobao.user.service;

import com.alibaba.fastjson.JSON;
import com.backend.service.BackendServiceI;
import com.uclee.QRCode.util.BarcodeUtil;
import com.uclee.QRCode.util.MyQRCode;
import com.uclee.datasource.service.DataSourceInfoServiceI;
import com.uclee.date.util.DateUtils;
import com.uclee.dynamicDatasource.DBContextHolder;
import com.uclee.dynamicDatasource.DataSourceFacade;
import com.uclee.dynamicDatasource.DynamicDataSourceManager;
import com.uclee.dynamicDatasource.DynamicDataSourceManagerHeyp;
import com.uclee.fundation.config.links.TermGroupTag;
import com.uclee.fundation.config.links.WebConfig;
import com.uclee.fundation.data.mybatis.mapping.*;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.mapping.NapaStoreMapper;
import com.uclee.fundation.data.mybatis.mapping.OauthLoginMapper;
import com.uclee.fundation.data.mybatis.mapping.OrderItemMapper;
import com.uclee.fundation.data.mybatis.mapping.OrderMapper;
import com.uclee.fundation.data.mybatis.mapping.PaymentMapper;
import com.uclee.fundation.data.mybatis.mapping.PaymentOrderMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductImageLinkMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductSaleMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationValueMapper;
import com.uclee.fundation.data.mybatis.mapping.VarMapper;
import com.uclee.fundation.data.web.dto.OrderPost;
import com.uclee.fundation.data.web.dto.ProductDto;
import com.uclee.fundation.dfs.fastdfs.FDFSFileUpload;
import com.uclee.hongshi.service.HongShiVipServiceI;
import com.uclee.payment.strategy.wcPaymetnTools.PayImpl;
import com.uclee.payment.strategy.wcPaymetnTools.UniteOrder;
import com.uclee.user.model.PaymentStrategyResult;
import com.uclee.user.service.DuobaoServiceI;
import com.uclee.user.service.UserServiceI;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class DuobaoServiceTest extends AbstractServiceTests {
	

	@Autowired
	private UserServiceI userService;
	@Autowired
	private DuobaoServiceI duobaoService;
	@Autowired
	private VarMapper varMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductImageLinkMapper productImageLinkMapper;
	@Autowired
	private SpecificationMapper specificationMapper;

	@Autowired
	private ShakeRecordMapper shakeRecordMapper;
	@Autowired
	private DataSourceFacade dataSource;

	@Autowired
	private DataSourceInfoMapper dataSourceInfoMapper;
	@Autowired
	private HongShiMapper hongShiMapper;
	@Autowired
	private OauthLoginMapper oauthLoginMapper;
	@Autowired
	private NapaStoreMapper napaStoreMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private RechargeConfigMapper rechargeConfigMapper;
	@Autowired
	private SpecificationValueMapper specificationValueMapper;
	@Autowired
	private HongShiVipServiceI hongShiVipService;
	@Autowired
	private PaymentMapper paymentMapper;
	@Autowired
	private PaymentOrderMapper paymentOrderMapper;
	@Autowired
	private FDFSFileUpload fDFSFileUpload;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private BackendServiceI backendService;
	@Autowired
	private ProductSaleMapper productSaleMapper;
	@Autowired
	private DataSourceInfoServiceI dataSourceInfoService;
	@Autowired
	private DataSourceFacade datasource;
	@Autowired
	private ProductGroupMapper productGroupMapper;
	@Autowired
	private ProductGroupLinkMapper productGroupLinkMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private RechargeRewardsRecordMapper rechargeRewardsRecordMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BalanceMapper balanceMapper;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private  BalanceLogMapper balanceLogMapper;
	@Test
	public void testFreight(){
		datasource.switchDataSource("hs");
		System.out.println(JSON.toJSONString(userService.getHongShiOrder(5, false)));
	}

	@Test
	public void testGetCoupon(){
		List<ProductDto> products = productMapper.getAllProduct(null,null,false,null, null);
		for(ProductDto item:products){
			ProductImageLink productImageLink = productImageLinkMapper.selectByProductIdLimit(item.getProductId());
			if(productImageLink!=null){
				item.setImage(productImageLink.getImageUrl());
			}
			SpecificationValue value = specificationValueMapper.selectByProductIdLimit(item.getProductId());
			if(value!=null){
				item.setPrice(value.getHsGoodsPrice());
				item.setPrePrice(value.getPrePrice());
			}
			ProductSale sale = productSaleMapper.selectByProductId(item.getProductId());
			if(sale!=null){
				item.setSalesAmount(sale.getCount());
			}else{
				item.setSalesAmount(0);
			}
		}
		logger.info(JSON.toJSONString(products));
	}
	@Test
	public void point(){
		hongShiMapper.saleVoucher("83001", "oH7hfuEN8qnZjC7fr2_zUFK7eVl8", "10011","测试赠送礼券");
		//hongShiMapper.recoverVoucher(6, "oH7hfuEN8qnZjC7fr2_zUFK7eVl8", "订单消费");
	}


	@Test
	public void PaymentSuccess(){
		dataSource.switchDataSource("ycgxy");
		PaymentOrder paymentOrder = paymentOrderMapper.selectByPaymentSerialNum("15321559195849346");
		paymentOrder.setIsCompleted(true);
		paymentOrder.setCompleteTime(new Date());
		OauthLogin oauthLogin = oauthLoginMapper.selectByUserId(635);
		userService.paymentSuccessHandler(paymentOrder,oauthLogin);
	}

	@Test
	public void wxInitiativeCheck(){
		dataSource.switchDataSource("kf");
		List<PaymentOrder> paymentOrders = userService.selectForTimer();
		for(PaymentOrder paymentOrder : paymentOrders){
			paymentOrder.setCheckCount(paymentOrder.getCheckCount()+1);
			paymentOrderMapper.updateByPrimaryKeySelective(paymentOrder);

			Map<String,String> ret = userService.wxInitiativeCheck(paymentOrder);

			if(ret.get("trade_state")!=null&&ret.get("trade_state").equals("SUCCESS")){
				logger.info("支付成功");
				userService.WechatNotifyHandle(paymentOrder.getPaymentSerialNum(),ret.get("transaction_id"),"kf");
			}
		}
	}
	@Test
	public void testChoujiang(){
		Config webConfig = userService.getLotteryWebConfig();
		if(webConfig!=null){
			logger.info(JSON.toJSONString("进入抽奖扣除积分"));
			int point = 1;
			try {
				point = Integer.parseInt(webConfig.getValue());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(JSON.toJSONString("进入抽奖扣除积分1"));
			hongShiMapper.lotteryPoint("oH7hfuEN8qnZjC7fr2_zUFK7eVl8",point);
		}
	}
	@Test
	public void testRechargePayment(){
		UniteOrder order = new UniteOrder();
		order.setAttach("tmnh");
		order.setAppid("wx24d308bcbbb08914");
		order.setMch_id("1296310801");
		order.setOpenid("obBd-wrkoZkaP8ugkbYb9PwHqN2g");
		order.setDevice_info("10.252.149.52");
		order.setNonce_str("e056c27c053ada4288bf9e68066e6062");
		
			order.setBody("小蛋糕(款式：果果蛋糕);");
		order.setDetail("小蛋糕(款式：果果蛋糕);");
		order.setOut_trade_no("15005302297434391");
		order.setFee_type("CNY");
		
		order.setTotal_fee("1");
		order.setSpbill_create_ip("10.252.149.52");
		order.setNotify_url("http://wsc1.in80s.com/seller/WCNotifyHandler");
		order.setTrade_type("JSAPI");  
		order.setProduct_id("15005302297434391");
		String reqXML = PayImpl.generateXML(order,"5e4a73a681ac455e9cb5ce921c570071");
		System.out.println(JSON.toJSONString(reqXML));
	}
	@Test
	public void testHome(){
		String[] tags = {TermGroupTag.HOT_PRODUCT,TermGroupTag.RECOMMEND};
		List<ProductGroup> groups = userService.getTermGroups(tags);
		System.out.println(JSON.toJSONString(groups));
	}
	@Test
	public void testGetUnpayOrderListByUserId(){
		System.err.println(JSON.toJSONString(userService.getUnpayOrderListByUserId(7)));
	}
	@Test
	public void testCreateOrder(){
		OrderPost orderPost = new OrderPost();
		orderPost.setAddrId(2);
		List<Integer> id = new ArrayList<Integer>();
		id.add(1);
		id.add(2);
		orderPost.setCartIds("5,6");
		orderPost.setIsSelfPick("false");
		orderPost.setRemark("请及时送达");
		orderPost.setVoucherCode("");
		System.out.println(JSON.toJSONString(userService.orderHandler(orderPost,7, "88888888")));
	}
	@Test
	public void testGetInvitationId(){
		System.err.println(JSON.toJSONString(userService.getInvitation(6)));
	}
	@Test
	public void testGetPaymentData(){
		Map<String,Object> map = new TreeMap<String,Object>();
		//Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		Integer userId = 6;
		List<Payment> payments = userService.selectAllPayment();
		map.put("payment", payments);
		List<Order> orders = userService.selectOrderByPaymentSerialNum(userId,"14956956740599840");
		PaymentOrder paymentOrder = userService.selectPaymentOrderBySerialNum("14956956740599840");
		paymentOrder.setOrders(orders);
		map.put("paymentOrder", paymentOrder);
		System.err.println(JSON.toJSONString(map));
	}
	@Test
	public void testGetOrderPost(){
		dataSource.switchDataSource("druidDataSource1");
		Date today = DateUtils.parse(DateUtils.format(new Date(), DateUtils.FORMAT_SHORT), DateUtils.FORMAT_SHORT);
		List<ShakeRecord> record = shakeRecordMapper.selectToday(today);
		System.out.println(JSON.toJSONString(record));
	}
	@Test
	public void testGetHongShiOrder(){
		List<HongShiOrder> orders = hongShiMapper.getHongShiOrder("oH7hfuEN8qnZjC7fr2_zUFK7eVl8",true);
		for(HongShiOrder order:orders){
			List<HongShiOrderItem> orderItems = hongShiMapper.getHongShiOrderItems(order.getId());
			for(HongShiOrderItem item:orderItems){
				HongShiGoods goods = hongShiMapper.getHongShiGoods(item.getCode());
				if(goods!=null){
					ProductImageLink link = productImageLinkMapper.selectByHongShiGoodsCodeLimit(goods.getCode());
					if(link!=null){
						goods.setImage(link.getImageUrl());
					}
				}
				item.setHongShiGoods(goods);
			}
			order.setOrderItems(orderItems);
		}
		System.out.println(JSON.toJSONString(orders));
	}
	
	@Test
	public void datasource(){
		List<User> users = new ArrayList<User>();
		DBContextHolder.setDBType("0");
		User user = userService.getUserBySerialNum("1480163164631119209");
		DBContextHolder.setDBType("1");
		User user1 = userService.getUserBySerialNum("1480163164631119209");
		users.add(user);
		users.add(user1);
		System.err.println(JSON.toJSONString(users));
	}
	@Test
	public void test() throws Exception{
		DynamicDataSourceManager.init();
        //获取数据源连接池
        System.out.println("------------------->数据源1");
        //
        JdbcTemplate jdbcTemplatefwefewf = DynamicDataSourceManager.getDataSourcePoolBySourceID(1);
        //
        String sql = "show tables;";
        List<Map<String, Object>> retList2 = jdbcTemplatefwefewf.queryForList(sql);
        for(Map<String, Object> entityMap : retList2) {
            System.out.println("-------查询结果:"+entityMap);
        }
        System.out.println("------------------->数据源2");
        JdbcTemplate jdbcTemplatefwefwef = DynamicDataSourceManager.getDataSourcePoolBySourceID(2);
        //
        sql="CREATE TABLE web_role_permission_link(   role_id int  NOT NULL,   permission_id int NOT NULL);";
        jdbcTemplatefwefwef.execute(sql);

	}
	private Balance getBalance(Integer userId) {
		Balance balance = balanceMapper.selectByUserId(userId);
		if(balance==null) {
			Balance tmp = new Balance();
			tmp.setBalance(new BigDecimal(0));
			tmp.setUserId(userId);
			if(balanceMapper.insertSelective(tmp)>0){
				return tmp;
			}else{
				return null;
			}

		}
		return balance;
	}
	@Test
	public void test1() throws Exception{
		datasource.switchDataSource("hs");
		String[] key = {"keyword1","keyword2"};
		String[] value = {DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),0.01+"元".toString()};
		Config config = configMapper.getByTag("rechargeTmpId");
		Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
		Config config2 = configMapper.getByTag(WebConfig.domain);
		Config config3 = configMapper.getByTag(WebConfig.signName);
		if(config!=null) {
			userService.sendWXMessage("oH7hfuEN8qnZjC7fr2_zUFK7eVl8", config.getValue(), config2.getValue()+"/recharge-list?merchantCode="+config1.getValue(), "尊敬的会员，您本次充值成功到账", key, value, "如有疑问，请点击这里");
		}
	}
	
	@Test
	public void testWxMessage(){
		dataSource.switchDataSource("kf");
		RechargeConfig rechargeConfig = rechargeConfigMapper.getByMoney(new BigDecimal("0.01"));
		OauthLogin oauthLogin = oauthLoginMapper.selectByUserId(6);
		if(rechargeConfig!=null){
			//优惠券处理
			if(rechargeConfig.getStartTime()!=null&&rechargeConfig.getEndTime()!=null&&new Date().after(rechargeConfig.getStartTime())&&new Date().before(rechargeConfig.getEndTime())){
				try{
					RechargeRewardsRecord record = rechargeRewardsRecordMapper.selectByConfigIdAndUserId(rechargeConfig.getId(),6);
					boolean isSend=false;
					if(record==null||(rechargeConfig.getLimit()!=null&&rechargeConfig.getLimit()>record.getCount())) {
						if(rechargeConfig.getAmount()!=null) {
							for (int i = 0; i < rechargeConfig.getAmount(); i++) {
								List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(rechargeConfig.getVoucherCode());
								if (coupon != null && coupon.size() > 0) {
									try {
										hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(0).getVouchersCode(), rechargeConfig.getVoucherCode(),"测试赠送礼券1");
										isSend = true;
									} catch (Exception e) {

									}
								}
							}
						}
						if(rechargeConfig.getAmountSecond()!=null) {
							for (int i = 0; i < rechargeConfig.getAmountSecond(); i++) {
								List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(rechargeConfig.getVoucherCodeSecond());
								if (coupon != null && coupon.size() > 0) {
									try {
										hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(0).getVouchersCode(), rechargeConfig.getVoucherCodeSecond(),"测试赠送礼券2");
										isSend = true;
									} catch (Exception e) {

									}
								}
							}
						}
						if(rechargeConfig.getAmountThird()!=null) {
							for (int i = 0; i < rechargeConfig.getAmountThird(); i++) {
								List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(rechargeConfig.getVoucherCodeThird());
								if (coupon != null && coupon.size() > 0) {
									try {
										hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(0).getVouchersCode(), rechargeConfig.getVoucherCodeThird(),"测试赠送礼券3");
										isSend = true;
									} catch (Exception e) {

									}
								}
							}
						}
						if(isSend){
							if(record==null){
								RechargeRewardsRecord tmp = new RechargeRewardsRecord();
								tmp.setConfigId(rechargeConfig.getId());
								tmp.setCount(1);
								tmp.setUserId(6);
								rechargeRewardsRecordMapper.insertSelective(tmp);
							}else{
								record.setCount(record.getCount()+1);
								rechargeRewardsRecordMapper.updateByPrimaryKeySelective(record);
							}
						}
					}else{
						logger.error("赠送上限");
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}else{
				logger.error("不在赠送时期");
			}
		}
	}
	
	@Test
	public void testUpdateWXAccessToken(){
		//dataSource.switchDataSource("jyzc"); hyp's code annotation by chiangpan
		logger.info("更新微信Token:  " + duobaoService.getGolbalAccessToken());
	}

	@Test
	public void testRecharge() {
		dataSource.switchDataSource("kf");
		HongShiRecharge dd = new HongShiRecharge().setcWeiXinCode("ocydnwkicQdKQgz5x4Pedh5LpFUM")
				.setcWeiXinOrderCode("微商城积分抽奖赠送余额").setnAddMoney(new BigDecimal(10));
		Integer res = hongShiVipService.hongShiRecharge(dd);
	}
	@Test
	public void testPaymentMessage() {
		dataSource.switchDataSource("kf");
		String[] key = {"keyword1","keyword2","keyword3","keyword4"};
		String[] value = {"test", "2017-09-13", "2元","微信支付"};
		userService.sendWXMessage("ocydnwkicQdKQgz5x4Pedh5LpFUM", "XKZJz1iRLSDOZIbvjXs0CJekeW7UeEkxJWwDF395Evk", "hs.uclee.com/order-list", "尊敬的会员，您有一笔订单已经支付成功", key,value, "感谢您的惠顾");
	}
	
	@Test
	public void testProduct(){
		dataSource.switchDataSource("druidDataSource1");
		ProductDto productDto = productMapper.getProductById(1);
		List<ProductImageLink> images = productImageLinkMapper.selectByProductId(1);
		productDto.setImages(images);
		logger.info(JSON.toJSONString(images));
		List<Specification> specifications = specificationMapper.getByProductId(1);
		logger.info(JSON.toJSONString(specifications));
		productDto.setSpecifications(specifications);
		productDto.setSalesAmount(1000);
		logger.info(JSON.toJSONString(productDto));
	}

	@Test
	public void testDataSource(){
	    dataSource.switchDataSource("druidDataSource");
		DataSourceInfo info=new DataSourceInfo();
		info.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		info.setUrl("jdbc:jtds:sqlserver://112.74.108.24:9630/masterdb");
		info.setUsername("sa");
		info.setPassword("to119,0002");
		info.setMerchantCode("druidDataSource1");

		dataSourceInfoMapper.insertSelective(info);
	}

	@Test
	public void testAddToCart(){
		Cart cart = new Cart().setAmount(100).setProductId(1).setSpecificationValueId(1).setUserId(7);
		Map<String,Object> map = new TreeMap<String,Object>();
		map.put("result", false);
		if(cart.getAmount()==null||cart.getProductId()==null||cart.getSpecificationValueId()==null){
			map.put("reason", "参数错误");
		}
		Product product = userService.getProductById(cart.getProductId());
		if (product == null || !product.getIsActive()) {
			map.put("reason", "产品已下架");
		}
		SpecificationValue specificationValue = userService.getSpecificationValue(cart.getProductId(),cart.getSpecificationValueId());
		if(specificationValue==null){
			map.put("reason", "该款式已下架");
		}
		if(specificationValue.getHsStock()<cart.getAmount()){
			map.put("reason", "该款式库存不足");
		}
		cart.setUserId(6);
		if(userService.addToCart(cart)!=null){
			map.put("result", true);
		}else{
			map.put("reason", "网络繁忙");
		}
		System.out.println(JSON.toJSONString(map));
	}
	@Test
	public void testInvitationList(){
		logger.info(JSON.toJSONString(userService.getInvitationList(6)));
	}
	@Test
	public void testCart(){
		System.err.println(JSON.toJSONString(userService.getUserCart(6,1)));
	}
	@Test
	public void testgetInvitationOrder(){
		System.err.println(JSON.toJSONString(userService.getInvitationOrder(6)));
	}
	
	@Test
	public void testAddr(){
		System.err.println(JSON.toJSONString(userService.getDeliverAddrList(6)));
	}
	
	@Test
	public void testCreateHongShiOrder(){
		List<Order> orders = userService.selectOrderByPaymentSerialNum(6,"14962356792227245");
		for(Order order:orders){
			order.setStatus((short)2);
			//调用存储过程插入洪石订单
			int hongShiResult=0;
			try {
				HongShiCreateOrder createOrderData = new HongShiCreateOrder();
				NapaStore napaStore = napaStoreMapper.selectByPrimaryKey(order.getStoreId());
				if(napaStore!=null){
					createOrderData.setDepartment(napaStore.getHsCode());;
				}else{
					logger.info("加盟店不存在");
				}
				createOrderData.setCallNumber(order.getPhone());
				createOrderData.setDestination(order.getProvince()+order.getCity()+order.getRegion()+order.getAddrDetail()+"(收货人:" + order.getName()+")");
				createOrderData.setOrderCode(null);
				createOrderData.setRemarks(order.getRemark());
				createOrderData.setTotalAmount(order.getTotalPrice());
				createOrderData.setWeiXinCode("oH7hfuEN8qnZjC7fr2_zUFK7eVl8");
				createOrderData.setWSC_TardNo(order.getOrderSerialNum());
				createOrderData.setPayment(new BigDecimal(0));
				createOrderData.setVoucher(new BigDecimal(0));
				CreateOrderResult createOrderResult = hongShiMapper.createOrder(createOrderData);
				
				//调用存储过程插入订单明细
				List<OrderItem> items = orderItemMapper.selectByOrderId(order.getOrderId());
				for(OrderItem item:items){
					HongShiCreateOrderItem createOrderItem = new HongShiCreateOrderItem();
					SpecificationValue value = specificationValueMapper.selectByPrimaryKey(item.getValueId());
					if(value!=null){
						createOrderItem.setGoodsCode(value.getHsGoodsCode());
					}
					createOrderItem.setGoodsCount(item.getAmount().intValue());
					createOrderItem.setpId(createOrderResult.getOrderID());
					createOrderItem.setPrice(item.getPrice());
					createOrderItem.setTotalAmount(item.getPrice());
					hongShiMapper.createOrderItem(createOrderItem);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
