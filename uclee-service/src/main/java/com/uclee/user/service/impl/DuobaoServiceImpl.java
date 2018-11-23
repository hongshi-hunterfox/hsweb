package com.uclee.user.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.InetSocketAddress;
import java.net.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uclee.fundation.config.links.WechatMerchantInfo;
import com.uclee.fundation.data.mybatis.mapping.CategoryMapper;
import com.uclee.fundation.data.mybatis.mapping.ConfigMapper;
import com.uclee.fundation.data.mybatis.mapping.OauthLoginMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductImageLinkMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductSaleMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationValueMapper;
import com.uclee.fundation.data.mybatis.mapping.UserMapper;
import com.uclee.fundation.data.mybatis.mapping.UserProfileMapper;
import com.uclee.fundation.data.mybatis.mapping.UserRoleLinkMapper;
import com.uclee.fundation.data.mybatis.mapping.VarMapper;
import com.uclee.fundation.data.mybatis.mapping.YouZanVarMapper;
import com.uclee.fundation.data.mybatis.model.Category;
import com.uclee.fundation.data.mybatis.model.Config;
import com.uclee.fundation.data.mybatis.model.OauthLogin;
import com.uclee.fundation.data.mybatis.model.ProductImageLink;
import com.uclee.fundation.data.mybatis.model.ProductSale;
import com.uclee.fundation.data.mybatis.model.SpecificationValue;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.mybatis.model.Var;
import com.uclee.fundation.data.mybatis.model.YouZanVar;
import com.uclee.fundation.data.web.dto.ProductDto;
import com.uclee.fundation.dfs.fastdfs.FDFSFileUpload;
import com.uclee.user.service.DuobaoServiceI;
import com.youzan.open.sdk.util.http.DefaultHttpClient;
import com.youzan.open.sdk.util.http.HttpClient;

public class DuobaoServiceImpl implements DuobaoServiceI {
	private final static Logger logger = Logger.getLogger(DuobaoServiceImpl.class);
	
	@Autowired
	private VarMapper varMapper;
	@Autowired
	private YouZanVarMapper youZanVarMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private UserRoleLinkMapper userRoleLinkMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ProductSaleMapper productSaleMapper;
	@Autowired
	private FDFSFileUpload fDFSFileUpload;
	
	@Autowired
	private OauthLoginMapper oauthLoginMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductImageLinkMapper productImageLinkMapper;
	@Autowired
	private SpecificationMapper specificationMapper;
	@Autowired
	private SpecificationValueMapper specificationValueMapper;
//	private String wc_notify_url = "http://cooka.vicp.cc/seller/WCNotifyHandler";
//	private String wc_general_order = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//	private String alipay_notify_url = "http://cooka.vicp.cc/cooka-finance-web/alipayNotifyHandler";
//	private String alipay_return_url = "http://cooka.vicp.cc/fastpaysuccess/";
	
	@Override
	public List<Category> getAllCat() {
		return categoryMapper.selectAll();
	}
	
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	@Override
	public String sendWXMessageSuccess(Integer userId,Integer successCount,String orderSerialNum) {
		OauthLogin oauthLogin = oauthLoginMapper.selectByUserId(userId);
		UserProfile profile = userProfileMapper.selectByUserId(userId);
		Map<String,Object> map1 = new LinkedHashMap<String,Object>();
		if(oauthLogin!=null){
			map1.put("touser", oauthLogin.getOauthId());
			map1.put("template_id", "OnZuzHm3q4n2kuc3Fq-VJv6e_3hs5vkXlJAviqb44uA");
			map1.put("topcolor", "#FF0000");
			map1.put("url", "m.uclee.com/recordDetail?orderSerialNum=" +orderSerialNum);
			Map<String,Object> map2 = new TreeMap<String,Object>();
			Map<String,Object> mapFirst = new TreeMap<String,Object>();
			if(profile!=null){
				mapFirst.put("value", "亲爱的"+ profile.getNickName() + "：您的订单已处理完成，共计成功购得夺宝号" + successCount +  "个。");
			}else{
				mapFirst.put("value", "您的订单已处理完成，共计成功购得夺宝号" + successCount +  "个。");
			}
			map2.put("first", mapFirst);
			Map<String,Object> mapKeyword1 = new TreeMap<String,Object>();
			if(profile!=null){
				mapKeyword1.put("value", profile.getNickName());
			}else{
				mapKeyword1.put("value", "");
			}
			map2.put("keyword1", mapKeyword1);
			Map<String,Object> mapKeyword2 = new TreeMap<String,Object>();
			mapKeyword2.put("value", orderSerialNum);
			map2.put("keyword2", mapKeyword2);
			Map<String,Object> mapKeyword3 = new TreeMap<String,Object>();
			mapKeyword3.put("value", "1元");
			map2.put("keyword3", mapKeyword3);
			Map<String,Object> mapKeyword4 = new TreeMap<String,Object>();
			mapKeyword4.put("value", "测试标题");
			map2.put("keyword4", mapKeyword4);
			Map<String,Object> mapRemark = new TreeMap<String,Object>();
			mapRemark.put("value", "点击详情查看夺宝详情，如有问题请致电客服手机号15902023879或直接在微信留言，客服在线时间为工作日10:00-18:00.客服人员将第一时间为您服务。");
			mapRemark.put("color", "#173177");
			map2.put("remark", mapRemark);
			map1.put("data", map2);
			logger.info(JSON.toJSONString(map1));
			try {
 	        Var var = varMapper.selectByPrimaryKey(new Integer(1));
            URL url = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + var.getValue());// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url  
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
            out.append(JSON.toJSONString(map1));  
            out.flush();  
            out.close();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
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
		}
		return null;
	}
	
	
	
	@Override
	public  String getGolbalAccessToken(){
		Map<String,String> weixinConfig = getWeixinConfig();
		logger.info(JSON.toJSONString(weixinConfig));
		String url_to_golbal_get_access_token = 
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ weixinConfig.get(WechatMerchantInfo.APPID_CONFIG) +
			"&secret=" + weixinConfig.get(WechatMerchantInfo.AppSecret_CONFIG);
		
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(url_to_golbal_get_access_token);
        CloseableHttpResponse response = null;
        String content ="";  
        try {  
            //执行get方法  
            response = httpclient.execute(httpget);  
            if(response.getStatusLine().getStatusCode()==200){  
                content = EntityUtils.toString(response.getEntity(),"utf-8");  
                com.alibaba.fastjson.JSONObject data = JSON.parseObject(content);
                String access_token=data.getString("access_token");
                Var var = varMapper.selectByPrimaryKey(new Integer(1));
                var.setValue(access_token);
                var.setStorageTime(new Date());
                varMapper.updateByPrimaryKeySelective(var);
                return content;
            }  
            
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	    
        return content; 
	}
	
	@Override
	public  String getYouZanAccessToken(){
		YouZanVar youZanVar = youZanVarMapper.selectByPrimaryKey(new Integer(1));
		HttpClient httpClient = new DefaultHttpClient();
		HttpClient.Params params = HttpClient.Params.custom()

				.add("grant_type", "refresh_token") //填写您的client_id
				.add("refresh_token", youZanVar.getRefreshToken()) //填写您的client_secret
				.add("client_id", "01e3d14da80e4e77e6") //默认值请勿修改
				.add("client_secret","bbb8efa2c6f017c3e9edee2ca74f2d21")
				.setContentType(ContentType.APPLICATION_FORM_URLENCODED).build();
		String resp = httpClient.post("https://open.youzan.com/oauth/token", params);
		JSONObject jsonObject = JSONObject.parseObject(resp);
		String access_token = jsonObject.getString("access_token");
		Integer expires_in = Integer.parseInt(jsonObject.getString("expires_in"));
		String refresh_token = jsonObject.getString("refresh_token");

		youZanVar.setName("access_token");
		youZanVar.setPlatform("YZ");
		youZanVar.setStorageTime(new Date());
		youZanVar.setValue(access_token);
		youZanVar.setExpiresIn(expires_in);
		youZanVar.setRefreshToken(refresh_token);		
		youZanVarMapper.updateByPrimaryKey(youZanVar);
		System.out.println("更新有赞Token成功");
		return refresh_token;
		
		
//		Map<String,String> weixinConfig = getWeixinConfig();
//		logger.info(JSON.toJSONString(weixinConfig));
//		String url_to_golbal_get_access_token = 
//					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ weixinConfig.get(WechatMerchantInfo.APPID_CONFIG) +
//			"&secret=" + weixinConfig.get(WechatMerchantInfo.AppSecret_CONFIG);
//		
//        CloseableHttpClient httpclient = HttpClients.createDefault();  
//        HttpGet httpget = new HttpGet(url_to_golbal_get_access_token);
//        CloseableHttpResponse response = null;
//        String content ="";  
//        try {  
//            //执行get方法  
//            response = httpclient.execute(httpget);  
//            if(response.getStatusLine().getStatusCode()==200){  
//                content = EntityUtils.toString(response.getEntity(),"utf-8");  
//                com.alibaba.fastjson.JSONObject data = JSON.parseObject(content);
//                String access_token=data.getString("access_token");
//                Var var = varMapper.selectByPrimaryKey(new Integer(1));
//                var.setValue(access_token);
//                var.setStorageTime(new Date());
//                varMapper.updateByPrimaryKeySelective(var);
//                return content;
//            }  
//            
//        } catch (ClientProtocolException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//	    
//        return content; 
	}

	
	private Map<String, String> getWeixinConfig() {
		Map<String,String> map = new HashMap<String,String>();
		List<Config> configs = configMapper.getWeixinConfig();
		for(Config config:configs){
			map.put(config.getTag(), config.getValue());
		}
		return map;
	}

	public static int getRandom(int min,int max){
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	@Override
	public List<ProductDto> getAllProduct(Integer categoryId, Boolean isSaleDesc, Boolean isPriceDesc, String keyword, Integer naviId) {
		List<ProductDto> products = productMapper.getAllProduct(categoryId,isSaleDesc,isPriceDesc,keyword,naviId);
		Iterator<ProductDto> iter = products.iterator();
		while(iter.hasNext()){
			ProductDto productDto =iter.next();
			if(productDto!=null && productDto.getIsShow() == false){
				iter.remove();
			}
		}
		for(ProductDto item:products){
			if(new Date().compareTo(item.getShelfTime())<0 || new Date().compareTo(item.getDownTime())>0) {
				System.out.println(item.getTitle()+"不在上架时间范围内");
			}else{
				ProductImageLink productImageLink = productImageLinkMapper.selectByProductIdLimit(item.getProductId());
				if(productImageLink!=null){
					item.setImage(productImageLink.getImageUrl());
				}
				SpecificationValue value = specificationValueMapper.selectByProductIdLimit(item.getProductId());
				if(value!=null){
					item.setPrice(value.getHsGoodsPrice());
					item.setPrePrice(value.getPrePrice());
					item.setVipPrice(value.getVipPrice());
				}
				ProductSale sale = productSaleMapper.selectByProductId(item.getProductId());
				if(sale!=null){
					item.setSalesAmount(sale.getCount());
				}else{
					item.setSalesAmount(0);
				}
			}
		}
		return products;
	}
}
