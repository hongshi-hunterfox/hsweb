package com.uclee.web.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.config.links.WeiXinInfo;
import com.uclee.fundation.data.mybatis.mapping.VarMapper;
import com.uclee.fundation.data.mybatis.model.OauthLogin;
import com.uclee.fundation.data.mybatis.model.User;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.mybatis.model.Var;
import com.uclee.user.model.WeixinLoginResult;
import com.uclee.user.scribe.OAuthServiceProvider;
import com.uclee.user.service.DuobaoServiceI;
import com.uclee.user.util.JwtUtil;
import com.uclee.weixin.util.EmojiFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

/**
 * @author Ming
 *	社会化登陆
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-user-web")
public class WeiXinLogin extends CommonUserHandler{

	@Autowired
	@Qualifier("weixinServiceProvider")
	private OAuthServiceProvider weixinServiceProvider;
	
	@Autowired
	private DuobaoServiceI duobaoService;
	
	@Autowired
	private VarMapper varMapper;
	
	private static final Token EMPTY_TOKEN = null;
	private static final Logger logger = Logger.getLogger(WeiXinLogin.class);
	public static String OAUTH_WEIXIN="weixin";
	

	public WeixinLoginResult autoLogin(String account,String pwsd,WeixinLoginResult result,Integer userId,HttpSession session){
		UsernamePasswordToken token = new UsernamePasswordToken(account, pwsd);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			initUseMessageInSession(account,userId,session);
			result.setResult(true);
			logger.info("微信登陆成功"+JSON.toJSONString(result));
			return result;
		} catch (IncorrectCredentialsException ice) {
			logger.info("对用户["  + "]进行登录验证..验证未通过,错误的凭证");
			result.setResult(false);
			return result;
		}
	}
	
	//通过appid 和 secret 获取全局的token
	public static String getGolbalAccessToken(){
		String url_to_golbal_get_access_token = 
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ WeiXinInfo.appid +
			"&secret=" + WeiXinInfo.appSecret;
		
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(url_to_golbal_get_access_token);
        CloseableHttpResponse response = null;
        String content ="";  
        try {  
            //执行get方法  
            response = httpclient.execute(httpget);  
            if(response.getStatusLine().getStatusCode()==200){  
                content = EntityUtils.toString(response.getEntity(),"utf-8");  
                return content;
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return content; 
	}
	
	
	public static String filterEmoji(String source) {  
        if(source != null)
        {
            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find()) 
            {
                source = emojiMatcher.replaceAll("*");
                return source ; 
            }
        return source;
       }
       return source;  
    }

	
	/**
	 *return
	 *	key:result
	 *		firstLoginSuccess    第一次登陆成功，跳转到绑定手机页面,没有手机号
	 *		success				   登陆成功 
	 *		fail  			                登陆失败
	 */
	/** 
	* @Title: facebooklogin 
	* @Description: 发起微信登陆请求
	* @param @param request
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value={"/weiXinLogin"}, method = RequestMethod.GET)
	public String facebooklogin(WebRequest request,ModelMap model,HttpSession session) {
		OAuthService service = weixinServiceProvider.getService();
		request.setAttribute("ATTR_OAUTH_REQUEST_TOKEN", EMPTY_TOKEN, SCOPE_SESSION);
		String url = service.getAuthorizationUrl(EMPTY_TOKEN);
		url =url.replaceAll("snsapi_userinfo", "snsapi_base");
		logger.info("请求的Url 是 "+url);
		return "redirect:" + url;		 
	}
	/**
	 * Require 
	 * 	code
	 */
	/** 
	* @Title: weiXinCallback 
	* @Description: 微信登陆回调请求处理 
	* @param @param oauthVerifier
	* @param @param request
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return WeixinLoginResult    返回类型 
	* @throws 
	*/
	@RequestMapping(value={"/weiXinCallback"}, method = RequestMethod.GET)
	public  @ResponseBody WeixinLoginResult weiXinCallback(@RequestParam(value="code", required=false) String code,
			WebRequest request,ModelMap model,HttpSession session) {
		WeixinLoginResult result = new WeixinLoginResult();
		
		long a = System.currentTimeMillis();
		Integer uid = (Integer)session.getAttribute(GlobalSessionConstant.USER_ID);
		if(uid!=null){
			result.setResult(true);
			logger.info("userId:"+uid+"已经登录");
			result.setToken(JwtUtil.genToken(uid));
			return result;
		}
		//根据微信回掉的code 获取openId
		logger.info("微信回掉的code 是"+ code);
		OAuthService service = weixinServiceProvider.getService();
		Token accessToken = service.getAccessToken(null, new Verifier(code));
		Object accessTokenObject = JSON.parse(accessToken.getRawResponse());
		JSONObject jsonObject = JSON.parseObject(accessToken.getRawResponse());  
		String openid=jsonObject.getString("openid");
		
		logger.info("获取的  opendId 是"+openid);
		if(StringUtils.isEmpty(openid)){
			logger.info("获取 openId  失败 ,授权 失败,get 到的信息为：\n"+JSON.toJSONString(accessTokenObject));
			System.out.println("获取 openId  失败 ,授权 失败,get 到的信息为：\n"+JSON.toJSONString(accessTokenObject));
			result.setResult(false);
			return result;
		}
		long b = System.currentTimeMillis();
		logger.info("b-a: " + (b-a));
		//通过 openId  检查是否是第一次使用 微信登陆
		OauthLogin oauthLogin = userService.getOauthLoginInfoByOauthId(openid);
		
		if(oauthLogin != null ){
			UserProfile userProfile = userProfileServiceI.getUserProfileByUserId(oauthLogin.getUserId());
			User user = userService.getUserById(oauthLogin.getUserId());
			String name = user.getSerialNum();
			String pswd = userService.getUserById(userProfile.getUserId()).getPassword().substring(0, 4);
			Var var = varMapper.selectByPrimaryKey(new Integer(1));
			//获取微信用户的name 作为account
			String weiXinUserInfo = userService.getWeiXinUserInfo(var.getValue(), openid);
			JSONObject weiXinUserInfoJsonData = JSON.parseObject(weiXinUserInfo);
			logger.info(weiXinUserInfo);
			String headimgurl="";
			String weiXinNickName = "";
			String errcode = weiXinUserInfoJsonData.getString("errcode");
			logger.info("erroCode: " + errcode);
			if(errcode!=null){
				duobaoService.getGolbalAccessToken();
				var = varMapper.selectByPrimaryKey(new Integer(1));
				//获取微信用户的name 作为account
				weiXinUserInfo = userService.getWeiXinUserInfo(var.getValue(), openid);
				weiXinUserInfoJsonData = JSON.parseObject(weiXinUserInfo);
			}
			long c = System.currentTimeMillis();
			logger.info("c-b: " + (c-b));
			String isSubcribe = weiXinUserInfoJsonData.getString("subscribe");
			if (isSubcribe!=null&&isSubcribe.equals("1")) {
				weiXinNickName = EmojiFilter.filterEmoji(weiXinUserInfoJsonData.getString("nickname"));
				//headimgurl = headimgurl.replaceAll("\\", "");
				logger.info(weiXinNickName);
				if (Strings.isNullOrEmpty(weiXinNickName)) {
					logger.info("获取微信用户名失败");
					result.setResult(false);
					return result;
				}
				headimgurl = weiXinUserInfoJsonData.getString("headimgurl");
				logger.info(headimgurl);
				oauthLogin.setIsSubcribe(true);
				oauthLogin.setIsSubscribeShow(false);
				oauthLoginMapper.updateByPrimaryKeySelective(oauthLogin);
			} else {
				oauthLogin.setIsSubcribe(false);
				oauthLogin.setIsSubscribeShow(true);
				oauthLoginMapper.updateByPrimaryKeySelective(oauthLogin);
				headimgurl = "http://120.25.193.220/group1/M00/00/91/eBnB3FggfCeAYi8BAAAXscnEGhw05.file";
			}
			userProfile.setImage(headimgurl);
			if(!weiXinNickName.equals("")){
				userProfile.setName(weiXinNickName);
				userProfile.setNickName(weiXinNickName);
			}
			userProfileServiceI.updateUserProfile(userProfile);
			Integer userId = userProfile.getUserId();
			result = autoLogin(name,pswd,result,userId,session);
			result.setToken(JwtUtil.genToken(userId));
			long d = System.currentTimeMillis();
			logger.info("d-c: " + (d-c));
			return result;
 		}else{
 			
 			Var var = varMapper.selectByPrimaryKey(new Integer(1));
 	        if(StringUtils.isEmpty(var.getValue())){
 	        	logger.info("获取全局access_token 失败");
 	        	result.setResult(false);
 				return result;
 	        }
 	        
 	        OauthLogin newOauthLogin = new OauthLogin();
 	        String expires_in="7200";
 	        newOauthLogin.setOauthAccessToken(var.getValue());    //存储的是全局 的token
 	        newOauthLogin.setOauthExpires(expires_in);
 	        newOauthLogin.setOauthId(openid);
 	        newOauthLogin.setOauthType(OAUTH_WEIXIN);
 	        newOauthLogin.setOauthName(OAUTH_WEIXIN);
 	        long c = System.currentTimeMillis();
			logger.info("c-b: " + (c-b));
 	        //获取微信用户的name 作为account
 	        String weiXinUserInfo = userService.getWeiXinUserInfo(var.getValue(),openid);
 	        JSONObject weiXinUserInfoJsonData = JSON.parseObject(weiXinUserInfo);
 	        String errcode = weiXinUserInfoJsonData.getString("errcode");
			if(errcode!=null){
				duobaoService.getGolbalAccessToken();
				var = varMapper.selectByPrimaryKey(new Integer(1));
				//获取微信用户的name 作为account
				weiXinUserInfo = userService.getWeiXinUserInfo(var.getValue(), openid);
				weiXinUserInfoJsonData = JSON.parseObject(weiXinUserInfo);
			}
			logger.info("erroCode: " + errcode);
 	        logger.info(weiXinUserInfo);
 	        String isSubcribe = weiXinUserInfoJsonData.getString("subscribe");
 	        String headimgurl;
			if (isSubcribe!=null&&isSubcribe.equals("1")) {
				String weiXinNickName;
				weiXinNickName = EmojiFilter.filterEmoji(weiXinUserInfoJsonData.getString("nickname"));
				//headimgurl = headimgurl.replaceAll("\\", "");
				logger.info(weiXinNickName);
				if (Strings.isNullOrEmpty(weiXinNickName)) {
					logger.info("获取微信用户名失败");
					result.setResult(false);
					return result;
				}
				newOauthLogin.setOauthUserName(weiXinNickName);
				newOauthLogin.setIsSubcribe(true);
				newOauthLogin.setIsSubscribeShow(false);
				headimgurl = weiXinUserInfoJsonData.getString("headimgurl");
				logger.info(headimgurl);
			}else{
				newOauthLogin.setIsSubcribe(false);
//				newOauthLogin.setOauthUserName("用户" + new Date().getTime());
				newOauthLogin.setOauthUserName("游客");
				headimgurl = "http://120.25.193.220/group1/M00/00/91/eBnB3FggfCeAYi8BAAAXscnEGhw05.file";
			}
			long d = System.currentTimeMillis();
			logger.info("d-c: " + (d-c));
			userService.createNewAccount(newOauthLogin,headimgurl);
 			OauthLogin oauthLoginTmp = userService.getOauthLoginInfoByOauthId(openid);
 			UserProfile userProfile = userProfileServiceI.getUserProfileByUserId(oauthLoginTmp.getUserId());
 			User user = userService.getUserById(oauthLoginTmp.getUserId());
			String name = user.getSerialNum();
			String pswd = userService.getUserById(userProfile.getUserId()).getPassword().substring(0, 4);
			
			Integer userId = userProfile.getUserId();
 			autoLogin(name,pswd,result,userId,session);
 			result.setResult(true);
 			result.setToken(JwtUtil.genToken(userId));
 			long e = System.currentTimeMillis();
			logger.info("e-d: " + (e-d));
 			return result;
 		}
		
		
	}
}
