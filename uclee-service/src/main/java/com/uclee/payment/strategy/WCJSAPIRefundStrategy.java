package com.uclee.payment.strategy;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.data.mybatis.model.OauthLogin;
import com.uclee.fundation.data.mybatis.model.RefundOrder;
import com.uclee.model.SpringContextUtil;
import com.uclee.payment.exception.RefundHandlerException;
import com.uclee.user.model.RefundStrategyResult;
import com.uclee.user.service.UserServiceI;
import org.apache.log4j.Logger;

public class WCJSAPIRefundStrategy implements RefundHandlerStrategy{

    private static final Logger logger = Logger.getLogger(WCJSAPIRefundStrategy.class);
    @Override
    public RefundStrategyResult refundHandler(RefundOrder refundOrder) {
        UserServiceI userService = (UserServiceI) SpringContextUtil.getBean("userServiceImpl");
        OauthLogin oauthLogin = userService.getOauthLoginInfoByUserId(refundOrder.getUserId());
        String openId = null;
        if(oauthLogin!=null){
            openId = oauthLogin.getOauthId();
        }

        RefundStrategyResult result=null;
        try {
            result = userService.getWCRefund(openId, refundOrder);

        } catch (RefundHandlerException e) {
            e.printStackTrace();
            result.setResult(false);
        }
        result.setType("WC");
        //logger.info("请求微信退款返回给前端的结果:" + JSON.toJSONString(result));
        return result;

    }
}