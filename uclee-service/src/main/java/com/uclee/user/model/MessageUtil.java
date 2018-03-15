package com.uclee.user.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消息工具类
 * 
@author xjany
 * @date 2013-05-19
 */
public class MessageUtil {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	
	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	/**
	 * 事件类型：扫描带参二维码
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
	
	
	/**
	 * String 转成XML
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXmlByStr(String str) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 读取输入流
		Document document = DocumentHelper.parseText(str);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		return map;
	}

	/**
	 * 微支付对象转换成xml
	 * 
	 * @return xml
	 */
	public static String wxUnifiedRequestToXml(WxUnifiedRequest wxUnifiedRequest) {
		xstream.alias("xml", wxUnifiedRequest.getClass());
		return xstream.toXML(wxUnifiedRequest);
	}
	/**
	 * 微支付对象转换成xml
	 *
	 * @return xml
	 */
	public static String requestToXml(String success) {
		xstream.alias("xml", String.class);
		return xstream.toXML(success);
	}

	public static void main(String[] args) {
		String xml = "<xml><_fm;0yV,*U><![CDATA[i=rT[kU]]></_fm;0yV,*U>\n" +
				"<appid><![CDATA[wxe32c0663202d345a]]></appid>\n" +
				"<bank_type><![CDATA[CFT]]></bank_type>\n" +
				"<cash_fee><![CDATA[16800]]></cash_fee>\n" +
				"<fee_type><![CDATA[CNY]]></fee_type>\n" +
				"<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
				"<mch_id><![CDATA[1490049552]]></mch_id>\n" +
				"<nonce_str><![CDATA[URCU!9s2H;zV_]]></nonce_str>\n" +
				"<openid><![CDATA[oEXIU0_oz4z3Jvqs8aH5HXHqfp6I]]></openid>\n" +
				"<out_trade_no><![CDATA[17111108590323060356]]></out_trade_no>\n" +
				"<result_code><![CDATA[SUCCESS]]></result_code>\n" +
				"<return_code><![CDATA[SUCCESS]]></return_code>\n" +
				"<sign><![CDATA[27F8276DE33A17A2F1851E7C3C6C6575]]></sign>\n" +
				"<time_end><![CDATA[20171111085912]]></time_end>\n" +
				"<total_fee>16800</total_fee>\n" +
				"<trade_type><![CDATA[APP]]></trade_type>\n" +
				"<transaction_id><![CDATA[4200000036201711113855246887]]></transaction_id>\n" +
				"</xml>";
		try {
//			String errorNode = (xml.substring(5,xml.indexOf("<appid>")));
//			String result = xml.replace(errorNode, "");
			System.out.println(MessageUtil.parseXml(xml));
		} catch (DocumentException de) {
			String errorNode = (xml.substring(5,xml.indexOf("<appid>")));
			String result = xml.replace(errorNode, "");
			try {
				System.out.println(MessageUtil.parseXml(result));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("catcg————————————");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, Class clazz) {
					super.startNode(name);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 解析微信发来的请求（XML）
	 *
	 * @param callBackData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(String callBackData) throws Exception {
		Map<String, String> map = new HashMap<>();// 将解析结果存储在HashMap中

		Document document = null;
		try {
			document = DocumentHelper.parseText(callBackData);
		} catch (DocumentException e) {
			e.printStackTrace();
			String errorNode = (callBackData.substring(5,callBackData.indexOf("<appid>")));
			String result = callBackData.replace(errorNode, "");
			document = DocumentHelper.parseText(result);

		}finally {
			Element root = document.getRootElement();// 得到xml根元素
			List<Element> elementList = root.elements();// 得到根元素的所有子节点
			for (Element e : elementList) { // 遍历所有子节点
				map.put(e.getName(), e.getText());
			}
			return map;
		}

	}

	public static Map<String, Object> parseMultiXml(String callBackData) throws Exception {
		return xmltoMap(callBackData);
	}

	/**
	 * xml转成map
	 * @param xmlstr xml报文
	 * @return
	 */
	public static Map<String, Object> xmltoMap(String xmlstr){
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlstr);
		} catch (DocumentException e) {
			//logger.error("parse text error : " + e);
		}
		Element rootElement = doc.getRootElement();
		Map<String,Object> mapXml = new HashMap<String,Object>();
		elementToMap(mapXml,rootElement);
		return mapXml;
	}

	/**
	 * 遍历子节点
	 * @param map
	 * @param rootElement
	 */
	public static void elementToMap(Map<String, Object> map, Element rootElement){
		//获得当前节点的子节点
		List<Element> elements = rootElement.elements();
		Map<String,Object> childMap = new HashMap<String,Object>();
		//如果还存在子节点，就考虑list情况，继续递归
		for (Element element : elements) {
			List<Element> es = element.elements();
			if(es.size()>0){
				//获取当前节点下的子节点
				ArrayList<Map> list = new ArrayList<Map>();
				for(Element e:es){
					elementChildToList(list,e);
				}
				map.put(element.getName(), list);
			}else{
				map.put(element.getName(),element.getText());
			}

		}
	}

	/**
	 * 递归子节点
	 * @param arrayList
	 * @param rootElement
	 */
	public static void elementChildToList(ArrayList<Map> arrayList, Element rootElement){
		//获得当前节点的子节点
		List<Element> elements = rootElement.elements();
		if(elements.size()>0){
			ArrayList<Map> list = new ArrayList<Map>();
			Map<String,Object> sameTempMap = new HashMap<String,Object>();
			for(Element element:elements){
				elementChildToList(list,element);
				sameTempMap.put(element.getName(), element.getText());
			}
			arrayList.add(sameTempMap);
		}

	}
	public static Map<String, String> jsonToMap(String source) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		try{
			map = mapper.readValue(source, new TypeReference<HashMap<String,String>>(){});
			return map;
		}catch(Exception e){
			e.printStackTrace();
		}
		return new HashMap<>();
	}

}