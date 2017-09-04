package com.uclee.web.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.data.mybatis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.service.BackendServiceI;
import com.uclee.date.util.DateUtils;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.data.mybatis.mapping.CategoryMapper;
import com.uclee.fundation.data.mybatis.mapping.FreightMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.web.dto.BannerPost;
import com.uclee.fundation.data.web.dto.ConfigPost;
import com.uclee.fundation.data.web.dto.MySelect;

import scala.collection.immutable.HashMap;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-backend-web/")
public class BackendController {
	@Autowired
	private BackendServiceI backendService;
	
	@RequestMapping("/freight")
	public @ResponseBody Map<String,Object> freight(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Double> map = new TreeMap<String,Double>();
		List<Freight> freight = backendService.selectAllFreight();
		int i = 0;
		for(Freight item : freight){
			map.put("myKey[" + i + "]", item.getCondition());
			map.put("myValue[" + i + "]", item.getMoney().doubleValue());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	@RequestMapping("/getHongShiStoreName")
	public @ResponseBody Map<String,Object> getHongShiStoreName(HttpServletRequest request,String hsCode) {
		Map<String,Object> map = new TreeMap<String,Object>();
		map.put("storeName",backendService.getHongShiStoreName(hsCode));

		return map;
	}
	@RequestMapping("/orderList")
	public @ResponseBody Map<String,Object> orderList(HttpServletRequest request,Boolean isEnd) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<HongShiOrder> orders = backendService.getHongShiOrder(isEnd);
		map.put("orders", orders);
		return map;
	}
	@RequestMapping("/storeInfo")
	public @ResponseBody Map<String,Object> storeInfo(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
 		map.put("text", backendService.selectStoreInfo());
		return map;
	}
	
	@RequestMapping("/productGroup")
	public @ResponseBody Map<String,Object> productGroup(HttpServletRequest request,String tag) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<ProductGroupLink> productGroup = backendService.getProductGroup(tag);
		map.put("productGroup", productGroup);
		return map;
	}
	
	@RequestMapping("/banner")
	public @ResponseBody Banner banner(HttpServletRequest request,Integer id) {
		return backendService.getBanner(id);
	}
	@RequestMapping("/bannerList")
	public @ResponseBody Map<String,Object> bannerList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<Banner> banner = backendService.getBannerList();
		map.put("banner", banner);
		return map;
	}
	@RequestMapping("/quickNavi")
	public @ResponseBody
	HomeQuickNavi quickNavi(HttpServletRequest request, Integer naviId) {
		HomeQuickNavi tmp = backendService.getQuickNavi(naviId);
		System.out.println(JSON.toJSONString(tmp));
		return tmp;
	}
	@RequestMapping("/quickNaviList")
	public @ResponseBody Map<String,Object> quickNaviList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<HomeQuickNavi> quickNavi = backendService.getQuickNaviList();
		map.put("quickNavi", quickNavi);
		return map;
	}
	@RequestMapping("/config")
	public @ResponseBody Map<String,Object> config(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		ConfigPost config = backendService.getConfig();
		map.put("config", config);
		return map;
	}
	@RequestMapping("/userList")
	public @ResponseBody Map<String,Object> userList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<UserProfile> users = backendService.getUserList();
		map.put("users", users);
		map.put("size", users.size());
		return map;
	}
	@RequestMapping("/userBirthList")
	public @ResponseBody Map<String,Object> userBirthList(HttpServletRequest request,Integer day) {
		Map<String,Object> map = new TreeMap<String,Object>();
		if(day==null){
			day=0;
		}
		List<UserProfile> users = backendService.getUserListForBirth(day);
		map.put("users", users);
		map.put("size", users.size());
		return map;
	}
	@RequestMapping("/userUnBuyList")
	public @ResponseBody Map<String,Object> userUnBuyList(HttpServletRequest request,Integer day) {
		Map<String,Object> map = new TreeMap<String,Object>();
		if(day==null){
			day=100000;
		}
		List<UserProfile> users = backendService.getUserListForUnBuy(day);
		map.put("users", users);
		map.put("size", users.size());
		return map;
	}
	
	@RequestMapping("/getLotteryConfig")
	public @ResponseBody Map<String,Object> lottery(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		List<LotteryDrawConfig> configs = backendService.selectAllLotteryDrawConfig();
		result.put("configs",configs);
		if(configs!=null&&configs.size()>0){
			String startTmp = DateUtils.format(configs.get(0).getTimeStart(), DateUtils.FORMAT_LONG);
			String[] tmp = startTmp.split(" ");
			if(tmp.length>0){
				result.put("dateStart", tmp[0]);
			}
			if(tmp.length>1){
				result.put("timeStart", tmp[1]);
			}
			String endTmp = DateUtils.format(configs.get(0).getTimeEnd(), DateUtils.FORMAT_LONG);
			String[] tmp2 = endTmp.split(" ");
			if(tmp2.length>0){
				result.put("dateEnd", tmp2[0]);
			}
			if(tmp2.length>1){
				result.put("timeEnd", tmp2[1]);
			}
			result.put("limits",configs.get(0).getLimits());
		}
		return result;
	}
	
	@RequestMapping("/rechargeConfig")
	public @ResponseBody Map<String,Object> rechargeConfig(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,String> map = new TreeMap<String,String>();
		List<RechargeConfig> rechargeConfig = backendService.selectAllRechargeConfig();
		List<MySelect> selectOptions = new ArrayList<MySelect>();
		int i = 0;
		for(RechargeConfig item : rechargeConfig){
			MySelect tmp = new MySelect();
			tmp.setValue(item.getType());
			map.put("myKey[" + i + "]", item.getMoney().toString());
			if(item.getType().equals(1)){
				map.put("myValue[" + i + "]", item.getRewards().toString());
				tmp.setText("卡余额");
			}else{
				tmp.setText("优惠券");
				map.put("myValue[" + i + "]", item.getVoucherCode());
			}
			map.put("mySelect[" + i + "]", item.getType().toString());
			i++;
			selectOptions.add(tmp);
		}
		result.put("selectOptions", selectOptions);
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	
}
