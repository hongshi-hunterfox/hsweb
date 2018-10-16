package com.uclee.web.user.controller;

import com.alibaba.fastjson.JSON;
import com.uclee.date.util.DateUtils;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.data.mybatis.mapping.BindingRewardsMapper;
import com.uclee.fundation.data.mybatis.mapping.EvaluationGiftsMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiVipMapper;
import com.uclee.fundation.data.mybatis.mapping.HsVipMapper;
import com.uclee.fundation.data.mybatis.mapping.OauthLoginMapper;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.hongshi.service.HongShiVipServiceI;
import com.uclee.sms.util.VerifyCode;
import com.uclee.user.service.UserServiceI;

import joptsimple.internal.Strings;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.*;


/**
 * @author Administrator
 * 洪石会员相关接口
 * @param <phone>
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-user-web/")
public class HongShiVipController<phone> {
	@Autowired
	private UserServiceI userService;

	@Autowired
	private HongShiMapper hongShiMapper;
	
	@Autowired
	private HongShiVipMapper hongShiVipMapper;
	
	@Autowired
	private HsVipMapper hsVipMapper;

	@Autowired
	private OauthLoginMapper oauthLoginMapper;

	@Autowired
	private BindingRewardsMapper bindingRewardsMapper;
	
	@Autowired
	private EvaluationGiftsMapper evaluationGiftsMapper;
	
	@Autowired
	private HongShiVipServiceI hongShiVipService;
	
	private static final Logger logger = Logger.getLogger(HongShiVipController.class);
	
	
	
	/** 
	* @Title: getVipInfo 
	* @Description: 调用存储过程得到会员信息
	* @param @param type
	* @param @param session
	* @param @return    设定文件 
	* @return HongShiVip    返回类型 
	* @throws 
	*/
	@RequestMapping("getVipInfo")
	public @ResponseBody HongShiVip  getVipInfo(Integer type,HttpSession session){
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		UserProfile userProfile = userService.getBasicUserProfile(userId);
		logger.info("user_id:"+userId);
		if(userId!=null){
			Integer userId2 = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
			logger.info("user_id2:"+userId2);
			//验证两次获取用户是否一致
			if(userId==userId2){
				OauthLogin tt = userService.getOauthLoginInfoByUserId(userId);
				if(tt!=null){
					List<HongShiVip> ret= hongShiVipService.getVipInfo(tt.getOauthId());//openid 去拿信息
					if(ret!=null&&ret.size()>0){
						if(userProfile!=null){
							//取得今天的日期如今天是3月14号，day=14
							String day= DateUtils.getDay(new Date());
							//获得现在是几点,hour=15
							String hour=DateUtils.getTime(new Date()).substring(0,2);
							char[]  dayChar=day.toCharArray();
							char[]  hourChar=hour.toCharArray();
							//以下就是邓彪要求的值
							String preFixStr=String.valueOf(dayChar[0]).concat(String.valueOf(hourChar[0]));//11
							String barcodeEndFixStr=String.valueOf(dayChar[1])+"+"+String.valueOf(hourChar[1]);//条形码是4+5
							String vipEndFixStr=String.valueOf(dayChar[1]).concat(String.valueOf(hourChar[1]));//二维码是45
							
							Integer a = hongShiVipService.getCodeSwitching();
							if(a!=0){
								ret.get(0).setVipImage(userService.getVipImage(preFixStr.concat(tt.getOauthId()).concat(vipEndFixStr),userId));

								try{
	
									ret.get(0).setVipJbarcode(userService.getVipJbarcode(preFixStr.concat(ret.get(0).getCardCode()).concat(barcodeEndFixStr),userId));
	
								}catch (Exception e){
									e.printStackTrace();
								}
							}else{
								ret.get(0).setVipImage(userService.getVipImage(tt.getOauthId(),userId));
								try{
									ret.get(0).setVipJbarcode(userService.getVipJbarcode(ret.get(0).getCardCode(),userId));
								}catch (Exception e){
									e.printStackTrace();
								}
							}
							ret.get(0).setAllowRecharge(true);
							ret.get(0).setAllowPayment(true);
							if(ret.get(0).getState()==0){
								ret.get(0).setAllowRecharge(false);
								ret.get(0).setAllowPayment(false);
								ret.get(0).setCardStatus("会员卡未启用");
							}
							if(ret.get(0).getDisable()==1){
								ret.get(0).setAllowRecharge(false);
								ret.get(0).setAllowPayment(false);
								ret.get(0).setCardStatus("会员卡已挂失");
							}
							if((ret.get(0).getVipType()&2)==0){
								ret.get(0).setAllowRecharge(false);
								ret.get(0).setCardStatus("会员卡不可充值");
							}
							if(ret.get(0).getIsVoucher()==1){
								ret.get(0).setAllowRecharge(false);
								ret.get(0).setCardStatus("会员卡是购物券");
							}
							if(ret.get(0).getEndTime().before(new Date())){
								ret.get(0).setAllowRecharge(false);
								ret.get(0).setAllowPayment(false);
								ret.get(0).setCardStatus("会员卡已超过使用期限");
							}
							VipLog vipLog = new VipLog();
							vipLog.setVcode(ret.get(0).getCardCode());
							vipLog.setForeignKey(tt.getOauthId());
							hongShiVipMapper.insertVipLog(vipLog);
						}
						return ret.get(0);
					}
				}
			}else{
				//如果两次获取userid不一致则返回false给页面
				HongShiVip hongShiVip = new HongShiVip();
				hongShiVip.setFail(false);
				return hongShiVip;
			}
		}
		
		return new HongShiVip();
	}
	
	
	/**
	 * @Title: changeVip
	 * @Description: 解绑会员卡
	 * @param type
	 * @param session
	 * @return hongShiVipService.changeVip
	 */
	@RequestMapping("/changeVip")
	public @ResponseBody Integer changeVip(Integer type,HttpSession session) {
		HongShiVip vip = getVipInfo(type,session);
		return hongShiVipService.changeVip(vip.getId());
	}
	
	/**
	 * @Title: discontinuationVip
	 * @Description: 会员卡挂失
	 * @param type
	 * @param session
	 * @return vip.getId()
	 */
	@RequestMapping("/discontinuationVip")
	public @ResponseBody Integer discontinuationVip(Integer type,HttpSession session) {
		HongShiVip vip = getVipInfo(type, session);
		return hsVipMapper.updateRecharge(vip.getId());
		 
	}
		
	
	/** 
	* @Title: addVipInfo 
	* @Description: 绑定会员卡处理
	* @param @param vip post的会员信息数据
	* @param @param session
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@RequestMapping("addVipInfo")
	public @ResponseBody Map<String,Object>  addVipInfo(@RequestBody HongShiVip vip,HttpSession session ){
		Map<String,Object> ret=new HashMap<String,Object>();
		ret.put("result", "fail");
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		logger.info("user_id:"+userId);
		if(vip==null){
			ret.put("reason", "没传数据");
			return ret;
		}
		if(vip.getcMobileNumber()==null||!vip.getcMobileNumber().isEmpty()){
			if(vip.getCode()==null||!vip.getCode().isEmpty()){
				if(!VerifyCode.checkVerifyCode(session,vip.getcMobileNumber(),vip.getCode())){
					ret.put("reason", "验证码错误");
					return ret;
				}
			}else {
				ret.put("reason", "无输入验证码");
				return ret;
			}

		}
		if(userId!=null){
			OauthLogin tt = userService.getOauthLoginInfoByUserId(userId);

			
			
			//判断是否注册过会员	
			List<Lnsurance> lnsurance = hongShiVipService.selectUsers(vip.getcMobileNumber());
			
			//如果list不为null长度大于0就说明该会员已有绑定记录
			if(lnsurance!=null&&lnsurance.size()>0){
				System.out.println("该外键有绑定会员卡记录");
				if(tt!=null){
					vip.setcWeiXinCode(tt.getOauthId());
					try{
						AddVipResult res=hongShiVipService.addHongShiVipInfo(vip);
						logger.info("addVipInfo res:"+JSON.toJSONString(res));
						if(res!=null&&res.getRetcode()!=0){
							ret.put("reason", res.getMsg());
							ret.put("result", "fail");
							return ret;
						}
					}catch (Exception e){
						ret.put("reason", "网络繁忙，请稍后重试");
						ret.put("result", "fail");
						e.printStackTrace();
						return ret;
					}	
					ret.put("result", "success");
					return ret;
				}
			}
			
			if(tt!=null){
				vip.setcWeiXinCode(tt.getOauthId());
				try{
					AddVipResult res=hongShiVipService.addHongShiVipInfo(vip);
					logger.info("addVipInfo res:"+JSON.toJSONString(res));
					if(res!=null&&res.getRetcode()!=0){
						ret.put("reason", res.getMsg());
						ret.put("result", "fail");
						return ret;
					}
				}catch (Exception e){
					ret.put("reason", "网络繁忙，请稍后重试");
					ret.put("result", "fail");
					e.printStackTrace();
					return ret;
				}	
				try{
					//赠送积分处理
					logger.info("user_id-----="+userId);
					UserProfile userProfile = userService.getBasicUserProfile(userId);
					if(userProfile!=null){
						userProfile.setRegistTime(new Date());
						userService.updateProfile(userId,userProfile);
					}
					List<BindingRewards> bindingRewards = bindingRewardsMapper.selectOne();
					OauthLogin oauthLogin = oauthLoginMapper.selectByUserId(userId);
					if(oauthLogin!=null&&bindingRewards!=null&&bindingRewards.size()>0){
						hongShiMapper.signInAddPoint(oauthLogin.getOauthId(),bindingRewards.get(0).getPoint(),"绑卡送积分");
						for(int i=0;i<bindingRewards.get(0).getAmount();i++){
							List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(bindingRewards.get(0).getVoucherCode());
							if (coupon != null && coupon.size() > 0) {
								try {
									hongShiMapper.saleVoucher(oauthLogin.getOauthId(), coupon.get(0).getVouchersCode(),
											bindingRewards.get(0).getVoucherCode(),"绑定会员赠送礼券");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					//赠送优惠券
				}catch (Exception e){

					e.printStackTrace();
				}
				ret.put("result", "success");
				return ret;
			}
		}
		logger.info("rec:"+JSON.toJSONString(vip));
		
		return ret;
	}
	

	
	/** 
	* @Title: rechargeRecord 
	* @Description: 会员消费明细，对应getviplog存储过程 
	* @param @param session
	* @param @return    设定文件 
	* @return List<HongShiRechargeRecord>    返回类型 
	* @throws 
	*/
	@RequestMapping("rechargeRecord")
	public @ResponseBody List<HongShiRechargeRecord>  rechargeRecord(HttpSession session ){
		List<HongShiRechargeRecord> ret=new ArrayList<HongShiRechargeRecord>();
		
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		logger.info("user_id:"+userId);
		if(userId!=null){
			OauthLogin tt = userService.getOauthLoginInfoByUserId(userId);
			if(tt!=null){
				List<HongShiVip> vip= hongShiVipService.getVipInfo(tt.getOauthId());//openid 去拿信息
				if(vip!=null&&vip.size()>0){
					ret= hongShiVipService.getRechargeRecord(vip.get(0).getId());
					logger.info("会员卡明细： " + JSON.toJSONString(ret));
				}
			}
		}
		return ret;
	}
	@RequestMapping("/vipRecordDetail")
	public @ResponseBody Map<String,Object>  vipRecordDetail(HttpSession session,String billCode,String Source){
		Map<String,Object> map=new HashMap<String,Object>();
		String[] strs=billCode.split(",");
		 billCode = "";
		 Source = "";
		for(int i=0,len=strs.length;i<len;i++){
			billCode=strs[0].toString();
			Source=strs[1].toString();
			System.out.println(strs[i].toString());
		}
		
		System.out.println("billCode=============="+billCode);
		System.out.println("Source=============="+Source);
	if(Source.equals("线上订单")){
		List<Orders> orders = hongShiVipService.selectOrders(billCode);
			if(orders!=null){
				for(int j =0; j<orders.size();j++){
					map.put("orders",orders);
				}
				System.out.println("billCode=============="+orders.get(0).getStoreName());
				map.put("danhao",orders.get(0).getDanhao());
				map.put("storeName",orders.get(0).getStoreName());
				map.put("beizhu",orders.get(0).getBeizhu());
				map.put("huijine",orders.get(0).getHuijine());
				map.put("songhuo",orders.get(0).getSonghuo());
				map.put("riqi",orders.get(0).getRiqi());
				map.put("jine",orders.get(0).getJine());
			
			}
	}else if(Source.equals("线下订单")){
		List<Orders> order = hongShiVipService.selectOrders(billCode);
		if(order!=null){
			for(int j =0; j<order.size();j++){
				List<UnderlineOrders> orders = hongShiVipService.selectUnderlineOrders(order.get(j).getDanhao());
				map.put("orders",orders);
			}
			System.out.println("billCode=============="+order.get(0).getStoreName());
			map.put("danhao",order.get(0).getDanhao());
			map.put("storeName",order.get(0).getStoreName());
			map.put("beizhu",order.get(0).getBeizhu());
			map.put("jine",order.get(0).getJine());
			map.put("songhuo",order.get(0).getSonghuo());
			map.put("huijine",order.get(0).getHuijine());
			map.put("riqi",order.get(0).getRiqi());
		}	
	}else if(Source.equals("零售")){
		List<RetailDetails> dingdan = hongShiVipService.selectRetailDetails(billCode);
		if(dingdan!=null){
			for(int j =0; j<dingdan.size();j++){
				List<RetailDetails> orders = hongShiVipService.selectRetailDetails(dingdan.get(j).getDanhao());
				map.put("orders",dingdan);
			}
			map.put("danhao",dingdan.get(0).getDanhao());
			map.put("storeName",dingdan.get(0).getStoreName());
			map.put("beizhu",dingdan.get(0).getBeizhu());
			map.put("jine",dingdan.get(0).getJine());
			map.put("riqi",dingdan.get(0).getRiqi());
			map.put("huijine",dingdan.get(0).getHuijine());
		}	
	
	}else if(Source.equals("充值")){
		List<ChongzhiDetailed> chongzhi = hongShiVipService.selectChongzhiDetailed(billCode);
		if(chongzhi!=null){
			for(int j =0; j<chongzhi.size();j++){
				List<ChongzhiDetailed> orders = hongShiVipService.selectChongzhiDetailed(chongzhi.get(j).getDanhao());
				map.put("orders",orders);
			}
		}	
	}else if(Source.equals("积分充值")){
		List<IntegralRecharge> jifenchongzhi = hongShiVipService.selectIntegralRecharge(billCode);
		if(jifenchongzhi!=null){
			for(int j =0; j<jifenchongzhi.size();j++){
				List<IntegralRecharge> orders = hongShiVipService.selectIntegralRecharge(jifenchongzhi.get(j).getDanhao());
				map.put("orders",orders);
			}
		}	
	
	}
		return map;
	}
	
	
}
