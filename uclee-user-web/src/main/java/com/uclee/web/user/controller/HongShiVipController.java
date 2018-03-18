package com.uclee.web.user.controller;

import com.alibaba.fastjson.JSON;
import com.uclee.date.util.DateUtils;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.data.mybatis.mapping.BindingRewardsMapper;
import com.uclee.fundation.data.mybatis.mapping.EvaluationGiftsMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
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
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-user-web/")
public class HongShiVipController {
	@Autowired
	private UserServiceI userService;

	@Autowired
	private HongShiMapper hongShiMapper;

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
	public @ResponseBody HongShiVip  getVipInfo(Integer type,HttpSession session ){
		Integer userId = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		//Integer userId=type;
		UserProfile userProfile = userService.getBasicUserProfile(userId);
		logger.info("user_id:"+userId);

		//獲得今天的日期如今天是3月14号，day=14
		String day= DateUtils.getDay(new Date());
		//获得现在是几点,hour=15
		String hour=DateUtils.getTime(new Date()).substring(0,2);

		char[]  dayChar=day.toCharArray();
		char[]  hourChar=hour.toCharArray();

		//以下就是邓彪要求的值
		String preFixStr=String.valueOf(dayChar[0]).concat(String.valueOf(hourChar[0]));//11
		String endFixStr=String.valueOf(dayChar[1]).concat(String.valueOf(hourChar[1]));//45

		if(userId!=null){
			OauthLogin tt = userService.getOauthLoginInfoByUserId(userId);
			if(tt!=null){
				List<HongShiVip> ret= hongShiVipService.getVipInfo(tt.getOauthId());//openid 去拿信息
				
				if(ret!=null&&ret.size()>0){
					if(userProfile!=null){

							ret.get(0).setVipImage(userService.getVipImage(preFixStr.concat(tt.getOauthId()).concat(endFixStr),userId));

						try{

							ret.get(0).setVipJbarcode(userService.getVipJbarcode(preFixStr.concat(ret.get(0).getCardCode()).concat(endFixStr),userId));

						}catch (Exception e){
							e.printStackTrace();
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
					}
					logger.info(JSON.toJSONString(ret.get(0)));
					return ret.get(0);
				}
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
	public  Integer changeVip(Integer type,HttpSession session) {
		HongShiVip oVIP = getVipInfo(type,session);
		return hongShiVipService.changeVip(oVIP.getId());
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
				try{
					//赠送积分处理
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
											bindingRewards.get(0).getVoucherCode());
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
					/*for(HongShiRechargeRecord record:ret){
						if(record.getSource().equals("订单")){
							record.setLogType(1);
						}else if(record.getSource().equals("充值")){
							record.setLogType(2);
						}else if(record.getSource().equals("签到送积分")){
							record.setLogType(3);
						}else{
							record.setLogType(4);
						}
					}*/
					logger.info("会员卡明细： " + JSON.toJSONString(ret));
				}
			}
		}
		return ret;
	}
}
