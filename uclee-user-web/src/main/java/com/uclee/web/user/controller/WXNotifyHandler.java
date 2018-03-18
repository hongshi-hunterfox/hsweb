package com.uclee.web.user.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uclee.fundation.data.mybatis.mapping.PaymentCallBackDataMapper;
import com.uclee.fundation.data.mybatis.model.PaymentCallBackData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.uclee.payment.strategy.wcPaymetnTools.IOUtils;
import com.uclee.payment.strategy.wcPaymetnTools.PayImpl;
import com.uclee.payment.strategy.wcPaymetnTools.PayResultNotice;
import com.uclee.user.service.UserServiceI;

/**
 * @author Administrator
 *微信支付结果回调处理类
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/seller")
public class WXNotifyHandler {
	@Autowired
	private UserServiceI userService;

	@Autowired
	private PaymentCallBackDataMapper paymentCallBackDataMapper;
	private static final Logger logger = Logger.getLogger(WXNotifyHandler.class);
	/** 
	* @Title: WCNotifyHandler 
	* @Description: 处理微信支付结果回调 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/WCNotifyHandler")
	@ResponseBody
	public String WCNotifyHandler(HttpServletRequest request, HttpServletResponse response){
		logger.info("微信异步通知开始");
		try {
			String respXML = IOUtils.toString(request.getInputStream(),request.getCharacterEncoding());
			logger.info("微信回调通知：" + respXML);
			PayResultNotice payResultNotice = (PayResultNotice) PayImpl.turnObject(PayResultNotice.class, respXML);
			try{
				PaymentCallBackData paymentCallBackData = new PaymentCallBackData();
				paymentCallBackData.setData(respXML);
				paymentCallBackData.setPaymentSerialNum(payResultNotice.getOut_trade_no());
				paymentCallBackData.setCreateTime(new Date());
				paymentCallBackDataMapper.insertSelective(paymentCallBackData);
			}catch (Exception e){

			}

			logger.info("resultNtice:" + JSON.toJSONString(payResultNotice));
			logger.info("method:WCNotifyHandler name:payResultNotice data: " + JSON.toJSONString(payResultNotice));
			if(payResultNotice.getReturn_code()!=null && payResultNotice.getReturn_code().equals("SUCCESS")){
				if(payResultNotice.getResult_code()!=null && payResultNotice.getResult_code().equals("SUCCESS")){
					if(payResultNotice.getOut_trade_no()!=null && payResultNotice.getTransaction_id()!=null){
						//支付完成处理
						if(userService.WechatNotifyHandle(payResultNotice.getOut_trade_no(),payResultNotice.getTransaction_id(),payResultNotice.getAttach())){
							return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
						}
					}
					else{
						logger.info("微信支付订单号:"+payResultNotice.getTransaction_id());
					}
				}else{
					logger.error("return fail msg:"+ JSON.toJSON(payResultNotice.getResult_code()));
					return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[交易失败]]></return_msg></xml>";
				}
			}else {
				logger.error("return error msg: " +JSON.toJSONString(payResultNotice.getReturn_msg()));
				return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名失败]]></return_msg></xml>";

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
