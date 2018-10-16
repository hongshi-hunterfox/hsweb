package com.uclee.web.backend.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.data.mybatis.mapping.RechargeConfigMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductGroupMapper;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.AuditRefundDto;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.service.BackendServiceI;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uclee.date.util.DateUtils;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.data.mybatis.mapping.CategoryMapper;
import com.uclee.fundation.data.mybatis.mapping.FreightMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.mapping.UserProfileMapper;
import com.uclee.fundation.data.web.dto.BannerPost;
import com.uclee.fundation.data.web.dto.ConfigPost;
import com.uclee.fundation.data.web.dto.FreightPost;
import com.uclee.fundation.data.web.dto.MySelect;
import com.uclee.fundation.data.mybatis.mapping.BargainSettingMapper;
import scala.collection.immutable.HashMap;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-backend-web/")
public class BackendController {
	@Autowired
	private BackendServiceI backendService;
	@Autowired
	private RechargeConfigMapper rechargeConfigMapper;
	@Autowired
	private ProductGroupMapper productGroupMapper;
	@Autowired
	private BargainSettingMapper bargainSettingMapper;
	
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
	@RequestMapping("/fullCutShipping")
	public @ResponseBody Map<String,Object> fullCutShipping(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new TreeMap<String,Object>();
		List<ShippingFullCut> shippingFullCut = backendService.selectAllShippingFullCut();
		int i = 0;
		if(shippingFullCut.size()==0){
			ShippingFullCut shippingFullCut1 = new ShippingFullCut();
			shippingFullCut1.setsLimit(0);
			shippingFullCut1.setsLimit(0);
			shippingFullCut1.setCondition(new BigDecimal(0));
			shippingFullCut.add(shippingFullCut1);
			map.put("startTime",new Date());
			map.put("endTime",new Date());
			String startTimeStr= DateUtils.format(new Date(),DateUtils.FORMAT_LONG);
			String endTimeStr= DateUtils.format(DateUtils.addDay(new Date(),30),DateUtils.FORMAT_LONG);
			map.put("startTimeStr",startTimeStr);
			map.put("endTimeStr",endTimeStr);
			try {
				map.put("startDateTmp",startTimeStr.split(" ")[0]);
				map.put("startTimeTmp",startTimeStr.split(" ")[1]);
				map.put("endDateTmp",endTimeStr.split(" ")[0]);
				map.put("endTimeTmp",endTimeStr.split(" ")[1]);
			}catch (Exception e){}
		}else{
			map.put("startTime",shippingFullCut.get(0).getStartTime());
			map.put("endTime",shippingFullCut.get(0).getEndTime());
			String startTimeStr= DateUtils.format(shippingFullCut.get(0).getStartTime(),DateUtils.FORMAT_LONG);
			String endTimeStr= DateUtils.format(shippingFullCut.get(0).getEndTime(),DateUtils.FORMAT_LONG);
			map.put("startTimeStr",startTimeStr);
			map.put("endTimeStr",endTimeStr);
			try {
				map.put("startDateTmp",startTimeStr.split(" ")[0]);
				map.put("startTimeTmp",startTimeStr.split(" ")[1]);
				map.put("endDateTmp",endTimeStr.split(" ")[0]);
				map.put("endTimeTmp",endTimeStr.split(" ")[1]);
			}catch (Exception e){}
		}
		for(ShippingFullCut item : shippingFullCut){
			map.put("myKey[" + i + "]", item.getsLimit());
			map.put("myValue[" + i + "]", item.getuLimit());
			map.put("myValue1[" + i + "]", item.getCondition().doubleValue());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	@RequestMapping("/bindMember")
	public @ResponseBody Map<String,Object> bindMember(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new TreeMap<String,Object>();
		List<BindingRewards> bindingRewardss = backendService.selectAllBindingRewards();
		int i = 0;
		if(bindingRewardss.size()==0){
			BindingRewards bindingReward = new BindingRewards();
			bindingReward.setPoint(0);
			bindingReward.setVoucherCode("");
			bindingReward.setAmount(0);
			bindingRewardss.add(bindingReward);
		}
		for(BindingRewards item : bindingRewardss){
			map.put("myKey[" + i + "]", item.getPoint());
			map.put("myValue[" + i + "]", item.getVoucherCode());
			map.put("myValue1[" + i + "]", item.getAmount());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	@RequestMapping("/evaluationConfiguration")
	public @ResponseBody Map<String,Object> evaluationConfiguration(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new TreeMap<String,Object>();
		List<EvaluationGifts> evaluationGifts = backendService.selectAllEvaluationGifts();
		int i = 0;
		if(evaluationGifts.size()==0){
			EvaluationGifts evaluationGift = new EvaluationGifts();
			evaluationGift.setPoint(0);
			evaluationGift.setMoney(new BigDecimal(0));
			evaluationGift.setVoucherCode("");
			evaluationGift.setAmount(0);
			evaluationGifts.add(evaluationGift);
		}
		for(EvaluationGifts item : evaluationGifts){
			map.put("myKey[" + i + "]", item.getPoint());
			map.put("myValue0[" + i +"]", item.getMoney());
			map.put("myValue[" + i + "]", item.getVoucherCode());
			map.put("myValue1[" + i + "]", item.getAmount());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	@RequestMapping("/integralinConfiguration")
	public @ResponseBody Map<String,Object> integralinConfiguration(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new TreeMap<String,Object>();
		List<IntegralInGifts> integralinGifts = backendService.selectAllIntegralInGifts();
		int i = 0;
		if(integralinGifts.size()==0){
			IntegralInGifts integralinGift = new IntegralInGifts();
			integralinGift.setDay(0);
			integralinGift.setMoney(new BigDecimal(0));
			integralinGift.setVoucherCode("");
			integralinGift.setAmount(0);
			integralinGifts.add(integralinGift);
		}
		for(IntegralInGifts item : integralinGifts){
			map.put("myKey[" + i + "]", item.getDay());
			map.put("myValue0[" + i +"]", item.getMoney());
			map.put("myValue[" + i + "]", item.getVoucherCode());
			map.put("myValue1[" + i + "]", item.getAmount());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	@RequestMapping("/fullCut")
	public @ResponseBody Map<String,Object> fullCut(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new TreeMap<String,Object>();
		List<FullCut> fullCut = backendService.selectAllFullCut();
		int i = 0;
		if(fullCut.size()==0){
			FullCut fullCut1 = new FullCut();
			fullCut1.setCondition(new BigDecimal(0));
			fullCut1.setCondition(new BigDecimal(0));
			map.put("startTime",new Date());
			map.put("endTime",new Date());
			String startTimeStr= DateUtils.format(new Date(),DateUtils.FORMAT_LONG);
			String endTimeStr= DateUtils.format(new Date(),DateUtils.FORMAT_LONG);
			map.put("startTimeStr",DateUtils.format(new Date(),DateUtils.FORMAT_LONG));
			map.put("endTimeStr",DateUtils.format(DateUtils.addDay(new Date(),30),DateUtils.FORMAT_LONG));
			try {
				map.put("startDateTmp",startTimeStr.split(" ")[0]);
				map.put("startTimeTmp",startTimeStr.split(" ")[1]);
				map.put("endDateTmp",endTimeStr.split(" ")[0]);
				map.put("endTimeTmp",endTimeStr.split(" ")[1]);
			}catch (Exception e){}
		}else{
			map.put("startTime",fullCut.get(0).getStartTime());
			map.put("endTime",fullCut.get(0).getEndTime());
			String startTimeStr= DateUtils.format(fullCut.get(0).getStartTime(),DateUtils.FORMAT_LONG);
			String endTimeStr= DateUtils.format(fullCut.get(0).getEndTime(),DateUtils.FORMAT_LONG);
			map.put("startTimeStr",startTimeStr);
			map.put("endTimeStr",endTimeStr);
			try {
				map.put("startDateTmp",startTimeStr.split(" ")[0]);
				map.put("startTimeTmp",startTimeStr.split(" ")[1]);
				map.put("endDateTmp",endTimeStr.split(" ")[0]);
				map.put("endTimeTmp",endTimeStr.split(" ")[1]);
			}catch (Exception e){}
		}
		for(FullCut item : fullCut){
			map.put("myKey[" + i + "]", item.getCondition());
			map.put("myValue[" + i + "]", item.getCut());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	@RequestMapping("/birthVoucher")
	public @ResponseBody Map<String,Object> birthVoucher(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new LinkedMap();
		BirthPush birthPush = backendService.selectDay();
		result.put("day", birthPush.getDay());
		List<BirthVoucher> birthVoucher = backendService.selectAllBirthVoucher();
		int i = 0;
		for(BirthVoucher item : birthVoucher){
			map.put("myKey[" + i + "]", item.getVoucherCode());
			map.put("myValue[" + i + "]", item.getAmount());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		return result;
	}
	@RequestMapping("/vipVoucher")
	public @ResponseBody Map<String,Object> vipVoucher(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new LinkedMap();
		List<VipVoucher> vipVoucher = backendService.selectAllVipVoucher();
		int i = 0;
		for(VipVoucher item : vipVoucher){
			map.put("myKey[" + i + "]", item.getVoucher());
			map.put("myValue[" + i + "]", item.getAmount());
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
		map.put("store",backendService.getHongShiStore(hsCode));

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
	public @ResponseBody Map<String,Object> productGroup(HttpServletRequest request,String groupName) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<ProductGroupLink> productGroup = backendService.getProductGroup(groupName);
		map.put("productGroup", productGroup);
		return map;
	}
	
	@RequestMapping("/getGroupName")
	public @ResponseBody Map<String,Object> getGroupName(HttpServletRequest request,Integer groupId) {
		Map<String,Object> map = new TreeMap<String,Object>();
		if(groupId!=null){
		System.out.println("groupId=="+ groupId);
		ProductGroup productGroup = productGroupMapper.selectByPrimaryKey(groupId);
		map.put("groupName",productGroup.getGroupName());
		map.put("displayType",productGroup.getDisplayType());
		map.put("image",productGroup.getImage());
		}
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
	@RequestMapping("/rechargeConfigList")
	public @ResponseBody Map<String,Object> rechargeConfigList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<RechargeConfig> rechargeConfigs = backendService.getRechargeConfigList();
		map.put("rechargeConfigs", rechargeConfigs);
		return map;
	}
	@RequestMapping("/getRechargeConfig")
	public @ResponseBody RechargeConfig getRechargeConfig(HttpServletRequest request,Integer id) {
		Map<String,Object> map = new TreeMap<String,Object>();
		RechargeConfig rechargeConfig = rechargeConfigMapper.selectByPrimaryKey(id);
		if(rechargeConfig!=null){
			rechargeConfig.setEndTimeStr(DateUtils.format(rechargeConfig.getEndTime(),DateUtils.FORMAT_LONG));
			rechargeConfig.setStartTimeStr(DateUtils.format(rechargeConfig.getStartTime(),DateUtils.FORMAT_LONG));
			try{
				rechargeConfig.setStartDateTmp(rechargeConfig.getStartTimeStr().split(" ")[0]);
				rechargeConfig.setStartTimeTmp(rechargeConfig.getStartTimeStr().split(" ")[1]);
				rechargeConfig.setEndDateTmp(rechargeConfig.getEndTimeStr().split(" ")[0]);
				rechargeConfig.setEndTimeTmp(rechargeConfig.getEndTimeStr().split(" ")[1]);
			}catch (Exception e){

			}
		}else{
			rechargeConfig = new RechargeConfig();
			rechargeConfig.setEndTimeStr(DateUtils.format(new Date(),DateUtils.FORMAT_LONG));
			rechargeConfig.setStartTimeStr(DateUtils.format(new Date(),DateUtils.FORMAT_LONG));
			try{
				rechargeConfig.setStartDateTmp(rechargeConfig.getStartTimeStr().split(" ")[0]);
				rechargeConfig.setStartTimeTmp("00:01");
				rechargeConfig.setEndDateTmp(rechargeConfig.getEndTimeStr().split(" ")[0]);
				rechargeConfig.setEndTimeTmp("23:59");
			}catch (Exception e){

			}
		}
		return rechargeConfig;
	}
	@RequestMapping("/quickNaviList")
	public @ResponseBody Map<String,Object> quickNaviList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<HomeQuickNavi> quickNavi = backendService.getQuickNaviList();
		map.put("quickNavi", quickNavi);
		return map;
	}
	@RequestMapping("/commentList")
	public @ResponseBody Map<String,Object> commentList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<Comment> comment = backendService.getCommentList();
		map.put("comment", comment);
		return map;
	}
	@RequestMapping("/config")
	public @ResponseBody Map<String,Object> config(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		ConfigPost config = backendService.getConfig();
		String notice = config.getNotice();
		System.out.println("notice==="+notice.length());
		map.put("notice", notice.length());
		map.put("qq", config.getQq());
		map.put("config", config);
		return map;
	}
	@RequestMapping("/category")
	public @ResponseBody Category category(HttpServletRequest request,Integer categoryId) {
		Category category = backendService.getCategoryById(categoryId);
		return category;
	}
	@SuppressWarnings("unused")
	@RequestMapping("/userList")
	public @ResponseBody Map<String,Object> userList(HttpServletRequest request, Integer pn) {
		Map<String,Object> map = new TreeMap<String,Object>();		
		List<UserProfile> users;
		if(pn==null){
			 users = backendService.getUserList(0);
			 map.put("pagenum",1);
		}else{
			 map.put("pagenum",pn);
			 pn = pn-1;
			 users = backendService.getUserList(pn*15);
		}
		Double pagenums = backendService.selectPageNums();
		Double pagesize = (pagenums/15);
		//小数点后值不为0进1
		int size = (int)Math.ceil(pagesize);
		map.put("size", size);
		map.put("users", users);
		map.put("pagenums", pagenums);
		return map;
	}
	/*
	 * 取得所有会员信息和根据会员注册时间查询会员信息
	 */
	@RequestMapping("/vipList")
	public @ResponseBody Map<String,Object> vipList(HttpServletRequest request, String start, String end) throws ParseException {
		Map<String,Object> map = new TreeMap<String,Object>();	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("start============="+start);
			System.out.println("end============="+end);
			if(!start.equals("undefined")&&!end.equals("undefined")){
				Date start1=sdf.parse(start);
				Date end1=sdf.parse(end);
				List<UserProfile> vips = backendService.getVipList(start1,end1);
				for(UserProfile item:vips){
					//用于转换会员注册日期数据类型
					item.setRegistTimeStr(DateUtils.format(item.getRegistTime(), DateUtils.FORMAT_LONG));
				}
				map.put("start", DateUtils.format(new Date(),DateUtils.FORMAT_SHORT));
				map.put("end", DateUtils.format(DateUtils.addDay(new Date(),30),DateUtils.FORMAT_SHORT));
				map.put("vips", vips);
				map.put("size", vips.size());
			}
			
		return map;
	}
	/*
	 * 根据手机号和卡号查询会员信息
	 */
	@RequestMapping("/vipPhone")
	public @ResponseBody Map<String,Object> vipPhone(HttpServletRequest request,String cartphone) {
		Map<String,Object> map = new TreeMap<String,Object>();
		System.out.println("phone================"+cartphone);
		List<UserProfile> vips = backendService.selectCardPhoneVips(cartphone);
		map.put("vips", vips);
		map.put("size", vips.size());
		return map;
	}
	/*
	 * 取所有会员信息----skx
	 */
	@RequestMapping("/getAllvip")
	public @ResponseBody Map<String,Object> getAllvip(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<UserProfile> vips = backendService.selectAllVipList();
		for(UserProfile item:vips){
			//用于转换会员注册日期数据类型
			item.setRegistTimeStr(DateUtils.format(item.getRegistTime(), DateUtils.FORMAT_LONG));
			 System.out.println(item);
		}
		map.put("vips", vips);
		map.put("size", vips.size());
		return map;
	}
	@RequestMapping("/categoryList")
	public @ResponseBody Map<String,Object> categoryList(HttpServletRequest request) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<Category> categories = backendService.getCategoryList();
		map.put("categories", categories);
		return map;
	}
	@RequestMapping("/userBirthList")
	public @ResponseBody Map<String,Object> userBirthList(HttpServletRequest request,String start,String end) {
		Map<String,Object> map = new TreeMap<String,Object>();
		List<UserProfile> users = backendService.getUserListForBirth(start,end);
		map.put("users", users);
		map.put("start", DateUtils.format(new Date(),DateUtils.FORMAT_SHORT));
		map.put("end", DateUtils.format(DateUtils.addDay(new Date(),30),DateUtils.FORMAT_SHORT));
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
		}else{
			String startTmp = DateUtils.format(new Date(), DateUtils.FORMAT_LONG);
			String endTmp = DateUtils.format(DateUtils.addDay(new Date(),30), DateUtils.FORMAT_LONG);
			String[] tmp = startTmp.split(" ");
			String[] tmp2 = endTmp.split(" ");
			result.put("dateStart", tmp[0]);
			result.put("timeStart", tmp[1]);
			result.put("dateEnd", tmp2[0]);
			result.put("timeEnd", tmp2[1]);
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

	/*
		copy by chiangpan
	*/
	@RequestMapping("/orderSettingPick")
	public @ResponseBody Map<String,Object> orderSettingPick(HttpServletRequest request) {
		Map<String,Object> result = new TreeMap<String,Object>();
		Map<String,Object> map = new TreeMap<String,Object>();
		List<OrderSettingPick> orderSettingPick = backendService.selectAllOrderSettingPick();

		if(orderSettingPick!=null && orderSettingPick.size()>0){
			map.put("closeStartDateStr",DateUtils.format(orderSettingPick.get(0).getCloseStartDate(),DateUtils.FORMAT_SHORT));
			map.put("closeEndDateStr",DateUtils.format(orderSettingPick.get(0).getCloseEndDate(),DateUtils.FORMAT_SHORT));
			map.put("businessStartTime", orderSettingPick.get(0).getBusinessStartTime());
			map.put("businessEndTime",orderSettingPick.get(0).getBusinessEndTime());
			result.put("size",1);
		}else{
			//以下是默认日期
			String year=DateUtils.getYear(new Date());
			map.put("closeStartDateStr",year+"-01-01");
			map.put("closeEndDateStr",year+"-01-01");
			map.put("businessStartTime", "00:00");
			map.put("businessEndTime","23:59");
			result.put("size",0);
		}
		result.put("data", map);
		return result;
	}

	@RequestMapping("/refundList")
	public @ResponseBody  Map<String,Object> refundList(HttpServletRequest Request){

		Map<String,Object> map = new TreeMap<String,Object>();
		List<AuditRefundDto> refundList = backendService.getRefundOrderList("");

		map.put("refunds", refundList);
		map.put("size", refundList.size());
		return map;
	}

	//审核退款单时的订单详情
	@RequestMapping(value="/getAduitRefundDetail")
	public @ResponseBody Map<String,Object> getAduitRefundDetail(HttpServletRequest request,String orderSerialNum){
		Map<String,Object> orderMap=new TreeMap<String,Object>();
		//为了不影响页面效果而死命的查询，牺牲性能
		AuditRefundDto auditRefundDto=new AuditRefundDto();
		List<AuditRefundDto> refundList = backendService.getRefundOrderList(orderSerialNum);
		if(refundList!=null && refundList.size()>0){
			 auditRefundDto=refundList.get(0);
		}
		Order order=backendService.getOrderBySeialNum(orderSerialNum);
		orderMap.put("order",order);
		orderMap.put("refundDesc",auditRefundDto.getRefundDesc());
		return orderMap;
	}
	/**
	 * 用来校验登陆账户和密码---凯鑫
	 * @param account
	 * @param password
	 * @param Request
	 * @return
	 */
	@RequestMapping("/getAccount")
	public @ResponseBody  Boolean getAccount(HttpServletRequest Request,String account,String password){
		HttpSession session = Request.getSession();
		System.out.println("--------------"+account);
		System.out.println("--------------"+password);
		return backendService.getAccount(account,password);
	}
	/**
	 * 获取砍价活动列表
	 * @return
	 */
	@RequestMapping("/getBargainList")
	public @ResponseBody Map<String,Object> getBargainList(HttpServletRequest Request){
		HttpSession session = Request.getSession();
		Map<String,Object> map=new TreeMap<String,Object>();
		List<BargainSetting> bargainList = backendService.selectBargain();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for(BargainSetting item:bargainList){
			item.setStarts(sdf.format(item.getStart()));
			item.setEnds(sdf.format(item.getClosure()));
		}
		System.out.println("bargainlist=="+JSON.toJSONString(bargainList));
		map.put("bargainList", bargainList);
		return map;
	}
	/**
	 * 获取砍价活动设置
	 * @param id
	 */
	@RequestMapping("/getBargain")
	public @ResponseBody Map<String,Object> getBargain(HttpServletRequest Request,Integer id){
		HttpSession session = Request.getSession();
		Map<String,Object> map = new TreeMap<String,Object>();
		if(id!=null){
			BargainSetting bargain = backendService.selectBargainId(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			bargain.setStarts(sdf.format(bargain.getStart()));
			bargain.setEnds(sdf.format(bargain.getClosure()));
			SimpleDateFormat times = new SimpleDateFormat("HH:mm");
			bargain.setStartTime(times.format(bargain.getStart()));
			bargain.setEndTime(times.format(bargain.getClosure()));
			map.put("bargain", bargain);
			
		}
		return map;
	}

	@RequestMapping("/getAll")
	public @ResponseBody  Map<String, Object> getAll(HttpServletRequest Request){
		Map<String,Object> map = new TreeMap<String,Object>();
		List<ProductGroup> all= backendService.selectAll();
		map.put("all",all);
		 return map;
	}
}
