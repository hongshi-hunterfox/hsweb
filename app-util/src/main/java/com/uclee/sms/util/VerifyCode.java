package com.uclee.sms.util;

import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Random;

public class VerifyCode {
	
    private static final Logger logger = LoggerFactory.getLogger(VerifyCode.class);
	
	public static boolean sendVerifyCode(HttpSession session,String phone,String appkey,String secret,String signName,String TemplateCode){
        String code = getCode();
        System.out.println("verifyCode:"+code);
		System.out.println("phone:"+phone);
        try {
        	session.setAttribute("v_code",code);
			boolean responseCode = SMSMessageUtil.send(phone, code,TemplateCode,appkey,secret,signName);
            return responseCode;
        } catch (ApiException e) {
            if (logger.isErrorEnabled()) {
            	logger.error(e.getMessage(), e);
            }
        }
		return true;
	}

	private static String getCode(){
		String ret="145345";
		int tt= new Random().nextInt(899999);
		ret=String.valueOf(100000+tt);
		return ret;
	}

	public static boolean checkVerifyCode(HttpSession session,String phone,String code ){
		try {
			String codeS=(String)session.getAttribute("v_code");
			if(codeS.equals(code)){
				session.setAttribute("v_code",null);
				return true;
			}
		}
		catch(Exception e){
			logger.error("验证码验证出现异常！"+e.getMessage());
		}
		return false;
	}
	
	public static void main(String[] args){
		System.out.println("");
	}
}
