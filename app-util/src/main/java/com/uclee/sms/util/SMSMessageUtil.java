package com.uclee.sms.util;
/**
 * Created by super13 on 6/1/17.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.log4j.Logger;

import java.util.Date;


public class SMSMessageUtil {

    private final static Logger LOG = Logger.getLogger(SMSMessageUtil.class);

    private final static String URL = "http://gw.api.taobao.com/router/rest";

    //private final static String APP_KEY = "23887342";
    //private final static String SECRET = "0db633205f62d240086ee62fe892380f";

    //短信类型，传入值请填写normal
    private final static String SMS_TYPE = "normal";


    private final static String TEMPLATE_CODE = "SMS_67316055";


    public static Boolean send(String mobile, String code,String APP_KEY,String SECRET) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(URL, APP_KEY, SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType(SMS_TYPE);


        req.setSmsFreeSignName("洪石软件");
        req.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"个人\"}");
        req.setRecNum(mobile);
        req.setSmsTemplateCode(TEMPLATE_CODE);
        req.setTimestamp(new Date().getTime());
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);


        JSONObject body = JSONObject.parseObject(rsp.getBody());
        LOG.info(JSON.toJSONString(body));
        if (body.containsKey("alibaba_aliqin_fc_sms_num_send_response")) {
            LOG.info(String.format("==>发送短信成功:手机号%s,发送返回信息:%s", mobile, rsp.getBody()));

            return  true;
        } else if (body.containsKey("error_response")) {
            JSONObject errorRps = body.getJSONObject("error_response");
            LOG.info(String.format("==>发送短信失败:手机号%s,发送返回信息:%s", mobile, rsp.getBody()));
            return false;
        } else {
            LOG.info(String.format("==>发送短信失败|未知异常:手机号%s,发送返回信息:%s", mobile, rsp.getBody()));
            return false;
        }


    }

    public static void main(String[] args) throws ApiException {
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.SMS_SIGNATURE_ILLEGAL","sub_msg":"短信签名不合法","request_id":"ej6ptuzf712n"}}
        //{"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"104623880315^1106450643788","success":true},"request_id":"yf8dnp04zc0"}}
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.MOBILE_NUMBER_ILLEGAL","sub_msg":"号码格式错误","request_id":"eonxf1ynynaa"}}
        //send("15902023897", "345345" );
//        System.out.println(responseCode);

    }


}

