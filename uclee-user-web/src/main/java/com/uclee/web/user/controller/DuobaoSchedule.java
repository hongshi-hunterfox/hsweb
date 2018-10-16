package com.uclee.web.user.controller;

import com.alibaba.fastjson.JSON;
import com.uclee.datasource.service.DataSourceInfoServiceI;
import com.uclee.date.util.DateUtils;
import com.uclee.dynamicDatasource.DataSourceFacade;
import com.uclee.fundation.config.links.WebConfig;
import com.uclee.fundation.data.mybatis.mapping.ConfigMapper;
import com.uclee.fundation.data.mybatis.mapping.MessageMapper;
import com.uclee.fundation.data.mybatis.mapping.BargainSettingMapper;
import com.uclee.fundation.data.mybatis.mapping.BirthVoucherMapper;
import com.uclee.fundation.data.mybatis.mapping.UserProfileMapper;
import com.uclee.fundation.data.mybatis.mapping.OauthLoginMapper;
import com.uclee.fundation.data.mybatis.mapping.OrderItemMapper;
import com.uclee.fundation.data.mybatis.mapping.VarMapper;
import com.uclee.fundation.data.mybatis.model.BirthPush;
import com.uclee.fundation.data.mybatis.model.BirthVoucher;
import com.uclee.fundation.data.mybatis.model.Config;
import com.uclee.fundation.data.mybatis.model.DataSourceInfo;
import com.uclee.fundation.data.mybatis.model.HongShiCoupon;
import com.uclee.fundation.data.mybatis.model.HsVip;
import com.uclee.fundation.data.mybatis.model.Message;
import com.uclee.fundation.data.mybatis.model.MsgRecord;
import com.uclee.fundation.data.mybatis.model.OauthLogin;
import com.uclee.fundation.data.mybatis.model.Order;
import com.uclee.fundation.data.mybatis.model.OrderItem;
import com.uclee.fundation.data.mybatis.model.PaymentOrder;
import com.uclee.fundation.data.mybatis.model.SpecificationValue;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.mybatis.model.Var;
import com.uclee.fundation.data.mybatis.model.vipIdentity;
import com.uclee.user.service.DuobaoServiceI;
import com.uclee.user.service.UserServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.uclee.fundation.data.mybatis.mapping.PaymentOrderMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationValueMapper;
import com.uclee.fundation.data.mybatis.mapping.MsgRecordMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.mapping.HsVipMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@Configurable
@EnableScheduling
public class DuobaoSchedule {
	
	private static final Logger logger = Logger.getLogger(DuobaoSchedule.class);
	
	@Autowired
	private UserServiceI userService;
	@Autowired
	private DuobaoServiceI duobaoService;
	@Autowired
	private VarMapper varMapper;
	@Autowired
	private DataSourceFacade dataSource;
	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private DataSourceInfoServiceI dataSourceInfoService;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private PaymentOrderMapper paymentOrderMapper;
	@Autowired
	private BirthVoucherMapper birthVoucherMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private OauthLoginMapper oauthLoginMapper;
	@Autowired
	private MsgRecordMapper msgRecordMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private HongShiMapper hongShiMapper;
	@Autowired
	private BargainSettingMapper bargainSettingMapper;
	@Autowired
	private HsVipMapper hsVipMapper;
	@Autowired
	private SpecificationValueMapper specificationValueMapper;

	/*@Scheduled(cron="0 0 0 * * *")
	private void updateWXInfo(){
		userService.updateWXInfo();
	}*/
	
	@Scheduled(fixedRate = 1000 * 10)
	private void refreshWXToken(){
		dataSource.switchDataSource("master");
		List<DataSourceInfo> t = dataSourceInfoService.getAllDataSourceInfo();
		for(DataSourceInfo info:t) {
			if(!info.getMerchantCode().equals("master")) {
				dataSource.switchDataSource(info.getMerchantCode());
				Var var = varMapper.selectByPrimaryKey(new Integer(1));
				if (DateUtils.addSecond(var.getStorageTime(), 7200).before(new Date())) {
					logger.info("更新微信Token");
					duobaoService.getGolbalAccessToken();
				}
			}
		}
	}
	
	@Scheduled(fixedRate = 1000 * 2, initialDelay=8000)
	private void InitiativeCheck(){
		String[] datasourceStr = {"fcx","hs"};
		for(String tmp:datasourceStr){
			dataSource.switchDataSource(tmp);
			try {
				List<PaymentOrder> paymentOrders = userService.selectForTimer();
				for (PaymentOrder paymentOrder : paymentOrders) {
					paymentOrder.setCheckCount(paymentOrder.getCheckCount() + 1);
					paymentOrderMapper.updateCheckCount(paymentOrder);
					Map<String, String> ret = userService.wxInitiativeCheck(paymentOrder);
					if (ret.get("trade_state") != null && ret.get("trade_state").equals("SUCCESS")) {
						userService.WechatNotifyHandle(paymentOrder.getPaymentSerialNum(), ret.get("transaction_id"), tmp);
					}
				}
			}catch (Exception e){

			}
		}
	}
	
	@Scheduled(fixedRate = 1000 * 3, initialDelay=12000)
	private void sendMessage(){
		dataSource.switchDataSource("master");
		List<DataSourceInfo> t = dataSourceInfoService.getAllDataSourceInfo();
		for(DataSourceInfo info:t) {
			if(!info.getMerchantCode().equals("master")) {
				try{
					dataSource.switchDataSource(info.getMerchantCode());
					List<Message> messages = userService.getUnSendMesg();
					Config config = configMapper.getByTag(WebConfig.payTmpId);
					for(Message item:messages){
						String[] key = {"keyword1","keyword2","keyword3","keyword4"};
						if(item.getPayType()==null){
							item.setPayType("线下门店支付");
						}
						String[] value = {item.getOrderNum(),DateUtils.format(item.getTime(), DateUtils.FORMAT_LONG).toString(),item.getMoney()+"元".toString(),item.getPayType()};
						if(userService.sendWXMessage(item.getOauthId(),config.getValue(),null,"尊敬的顾客，感谢您对本店的支持，您有一笔消费交易成功",key,value,"感谢您的惠顾，欢迎再次光临")){
							item.setIsSend(true);
							messageMapper.updateByPrimaryKeySelective(item);
						}
					}
				}catch (Exception e){

				}
			}
		}
	}
	/**
	 * 调用微信接口发送信息
	 * @param openId
	 * @param templateId
	 * @param url
	 * @param firstData
	 * @param key
	 * @param value
	 * @param remarkData
	 * @return
	 */
	public String sendWXMessage(String openId,String templateId,String url, String firstData,String[] key,String[] value,String remarkData) {
		Map<String,Object> sendData = new LinkedHashMap<String,Object>();
		sendData.put("touser", openId);
		sendData.put("template_id", templateId);
		sendData.put("topcolor", "#FF0000");
		sendData.put("url", url);
		Map<String,Object> mainData = new TreeMap<String,Object>();
		
		Map<String,Object> mapFirst = new TreeMap<String,Object>();
		mapFirst.put("value", firstData);
		mainData.put("first", mapFirst);
		
		for(int i=0;i<key.length;i++){
			Map<String,Object> Keyword = new TreeMap<String,Object>();
			Keyword.put("value", value[i]);
			mainData.put(key[i], Keyword);
		}
		
		Map<String,Object> mapRemark = new TreeMap<String,Object>();
		mapRemark.put("value", remarkData);
		mapRemark.put("color", "#173177");
		mainData.put("remark", mapRemark);
		
		sendData.put("data", mainData);
		try {
        Var var = varMapper.selectByPrimaryKey(new Integer(1));
        URL urlPost = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + var.getValue());// 创建连接  
        HttpURLConnection connection = (HttpURLConnection) urlPost  
                .openConnection();  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
        connection.setUseCaches(false);  
        connection.setInstanceFollowRedirects(true);  
        connection.setRequestMethod("POST"); // 设置请求方式  
        connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
        connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
        connection.connect();  
        OutputStreamWriter out = new OutputStreamWriter(  
                connection.getOutputStream(), "UTF-8"); // utf-8编码  
        out.append(JSON.toJSONString(sendData));  
        out.flush();  
        out.close();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        System.err.println(sb);
        reader.close();
        // 断开连接
        connection.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 定时检查未支付订单是否超过失效 时间
	 */
	@Scheduled(fixedRate = 1000 * 60)
	private void orderStatus() {
		dataSource.switchDataSource("master");
		List<DataSourceInfo> t = dataSourceInfoService.getAllDataSourceInfo();
		for(DataSourceInfo info:t) {
			if(!info.getMerchantCode().equals("master")) {
				try{
					dataSource.switchDataSource(info.getMerchantCode());
					List<Order> list = userService.getOrderListByStatus();
					if(list!=null&&list.size()>0){
						
						for(Order item:list){
							Date date = new Date(new Date().getTime() - 1800000);
							if (item.getCreateTime().before(date)){ 
								System.out.println("订单在失效范围内===="+item.getOrderSerialNum());
				      	 	   	Order order = userService.selectBySerialNum(item.getOrderSerialNum());
				      			//这里要把订单的状态改为-1即失效状态
				      			userService.updateByInvalid(item.getOrderSerialNum());
				      				if(order!=null){
				      					//释放库存
				      					List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
				      					logger.info(JSON.toJSONString(orderItems));
				      					for(OrderItem orderItem : orderItems){
				      						SpecificationValue value = specificationValueMapper.selectByPrimaryKey(orderItem.getValueId());
				      						if(value!=null){
				      							value.setHsStock(value.getHsStock()+orderItem.getAmount());
				      							logger.info(JSON.toJSONString(value));
				      							specificationValueMapper.updateByPrimaryKeySelective(value);
				      						}
				      					}
				      				}	
								
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}          

	}
	
	/**
	 * 此方法用来进行每日定时推送会员生日
	 * 信息到微信公众号
	 */
	@Scheduled(cron = "0 0 8 ? * *")
	private void timing(){
		dataSource.switchDataSource("master");
		List<DataSourceInfo> t = dataSourceInfoService.getAllDataSourceInfo();
		for(DataSourceInfo info:t) {
			if(!info.getMerchantCode().equals("master")) {
				try{
					dataSource.switchDataSource(info.getMerchantCode());
					//取提前的天数
					BirthPush birthPush = birthVoucherMapper.selectDay();
					//格式化时间
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					//当前时间加提前天数的时间
					calendar.add(calendar.DATE,birthPush.getDay());
					Date time = calendar.getTime();
					String day = formatter.format(time);
					System.out.println("增加天数后的时间============"+day);
					//如果没有填写生日执行
					List<UserProfile> userList = userProfileMapper.getBirthIsNull();
					for(UserProfile item:userList){
						OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(item.getUserId());
						if(login!=null){
							String nickName="";
							UserProfile profile = userProfileMapper.selectByUserId(item.getUserId());
							if(profile!=null){
								nickName = profile.getNickName();
							}
							String[] key = {"keyword1","keyword2","keyword3"};
							String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"生日祝福"};
							Config config = configMapper.getByTag("birthTmpId");
							Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
							Config config2 = configMapper.getByTag(WebConfig.domain);
							Config config3 = configMapper.getByTag(WebConfig.perfectBirthText);
							if(config!=null){
								//EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew
								sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"?merchantCode="+config1.getValue(), config3.getValue(), key,value, "");
								MsgRecord msgRecord = new MsgRecord();
								msgRecord.setType(1);
								msgRecord.setUserId(item.getUserId());
								msgRecordMapper.insertSelective(msgRecord);
							}
						}
					}
					//获取符合条件的会员
					List<UserProfile> userProfile = userProfileMapper.getUserListForBirth(day,day,sdf.format(date));
					for(UserProfile item : userProfile){
						item.setBirthStr(DateUtils.format(item.getBirth(), DateUtils.FORMAT_SHORT));
						OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(item.getUserId());
						if(login!=null){
							Calendar data = Calendar.getInstance();
							String salesTime = sdf.format(data);
							vipIdentity vip = bargainSettingMapper.selectVipIdentity(login.getOauthId());
							HsVip cartNumber = hsVipMapper.getVips(vip.getVid());
							List<com.uclee.fundation.data.mybatis.model.CouponSales> record = hongShiMapper.CouponSales(salesTime, cartNumber.getvCode());
							//如果今年没有生日推送记录执行
							if(record == null){
								String nickName="";
								UserProfile profile = userProfileMapper.selectByUserId(item.getUserId());
								if(profile!=null){
									nickName = profile.getNickName();
								}
								String[] key = {"keyword1","keyword2","keyword3"};
								String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"生日祝福"};
								Config config = configMapper.getByTag("birthTmpId");
								Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
								Config config2 = configMapper.getByTag(WebConfig.domain);
								Config config3 = configMapper.getByTag(WebConfig.birthText);
								if(config!=null){
									//EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew
									sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"?merchantCode="+config1.getValue(), config3.getValue(), key,value, "");
									MsgRecord msgRecord = new MsgRecord();
									msgRecord.setType(1);
									msgRecord.setUserId(item.getUserId());
									msgRecordMapper.insertSelective(msgRecord);
								}
								List<BirthVoucher> birthVouchers = birthVoucherMapper.selectAll();
								for(BirthVoucher birthVoucher:birthVouchers) {
									List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(birthVoucher.getVoucherCode());
									if (coupon != null && coupon.size() > 0) {
										try {
											for(int i=0;i<birthVoucher.getAmount();i++) {
												hongShiMapper.saleVoucher(login.getOauthId(), coupon.get(i).getVouchersCode(), birthVoucher.getVoucherCode(),"生日祝福赠送礼券");
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}
							
						}
					}
				}catch (Exception e){

				}
			}
		}
	}
}