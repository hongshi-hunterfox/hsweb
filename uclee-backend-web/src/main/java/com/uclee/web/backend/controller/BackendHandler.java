package com.uclee.web.backend.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.uclee.fundation.data.mybatis.mapping.BargainSettingMapper;
import com.uclee.fundation.data.mybatis.mapping.BirthVoucherMapper;
import com.uclee.fundation.data.mybatis.mapping.ConsumerVoucherMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiVipMapper;
import com.uclee.fundation.data.mybatis.mapping.OauthLoginMapper;
import com.alibaba.fastjson.JSON;
import com.backend.model.ProductForm;
import com.backend.service.BackendServiceI;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-backend-web/")
public class BackendHandler {
	
	@Autowired
	private BackendServiceI backendService;
	@Autowired
	private BargainSettingMapper bargainSettingMapper;
	@Autowired
	private OauthLoginMapper oauthLoginMapper;
	@Autowired
	private HongShiVipMapper hongShiVipMapper;
	@Autowired
	private HongShiMapper hongShiMapper;
	@Autowired
	private BirthVoucherMapper birthVoucherMapper;
	@Autowired
	private ConsumerVoucherMapper consumerVoucherMapper;
	
	private BargainSetting bargaintinting;

	
	@RequestMapping("/configHandler")
	public @ResponseBody boolean configHandler(HttpServletRequest request,@RequestBody ConfigPost configPost) {
		return backendService.updateConfig(configPost);
	}
	@RequestMapping("/activityConfigHandler")
	public @ResponseBody boolean activityConfigHandler(HttpServletRequest request,@RequestBody ConfigPost configPost) {
		System.out.println(JSON.toJSON(configPost));
		return backendService.updateActivityConfig(configPost);
	}
	@RequestMapping("/systemConfigHandler")
	public @ResponseBody boolean systemConfigHandler(HttpServletRequest request,@RequestBody ConfigPost configPost) {
		return backendService.systemConfigHandler(configPost);
	}
	@RequestMapping("/sendBirthMsg")
	public @ResponseBody boolean sendBirthMsg(HttpServletRequest request,String userList,boolean sendVoucher) {
		System.out.println("userlist====="+userList);
		String[] userarr = userList.split(","); // 用,分割
		boolean ret=true;
		for(String item:userarr) {
			Integer userId = Integer.parseInt(item);
			if(!sendVoucher) {
				return ret = backendService.sendBirthMsg(userId,sendVoucher);
			}
			OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
			if(login!=null && !login.equals("")) {
				ret = backendService.sendBirthMsg(userId,sendVoucher);
				List<BirthVoucher> birthVouchers = birthVoucherMapper.selectAll();//获取礼券赠送配置
				for(int j=0; j<birthVouchers.size();j++) {
					List<HongShiCoupon> cp= hongShiMapper.getHongShiCouponByGoodsCode(birthVouchers.get(j).getVoucherCode());
					for(int i=0; i<birthVouchers.get(j).getAmount();i++) {
						System.out.println("ooo=="+cp.get(i).getVouchersCode()+"ooo=="+cp.get(i).getGoodsCode());
						ret = backendService.sendCoupon(cp.get(i).getGoodsCode(),cp.get(i).getVouchersCode(),login.getOauthId(),"生日祝福送券");
					}
				}
			}else{
				return false;
			}
		}
		return ret;
	}
	@RequestMapping("/sendVipMsg")
	public @ResponseBody boolean sendViphMsg(HttpServletRequest request,String userList,boolean sendVoucher) {
		System.out.println("userlist====="+userList);
		String[] userarr = userList.split(","); // 用,分割
		boolean ret=true;
		for(String item:userarr) {
			Integer userId = Integer.parseInt(item);
			if(!sendVoucher) {
				return ret = backendService.sendViphMsg(userId,sendVoucher);
			}
			OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
			if(login!=null && !login.equals("")) {
				ret = backendService.sendViphMsg(userId,sendVoucher);
				List<VipVoucher> vipVouchers = hongShiVipMapper.selectAll();//获取礼券赠送配置
				for(int j=0; j<vipVouchers.size();j++) {
					List<HongShiCoupon> cp= hongShiMapper.getHongShiCouponByGoodsCode(vipVouchers.get(j).getVoucher());
					for(int i=0; i<vipVouchers.get(j).getAmount();i++) {
						System.out.println("ooo=="+cp.get(i).getVouchersCode()+"ooo=="+cp.get(i).getGoodsCode());
						ret = backendService.sendCoupon(cp.get(i).getVouchersCode(),cp.get(i).getGoodsCode(),login.getOauthId(),"定向送券");
					}
				}
			}else{
				return false;
			}
		}		
		return ret;
	}
	@RequestMapping("/isVoucherLimit")
	public @ResponseBody Map<String,Object> isVoucherLimit(HttpServletRequest request,Integer amount) {
		return backendService.isVoucherLimit(amount);
	}
	@RequestMapping("/isCouponAmount")
	public @ResponseBody Map<String,Object> isCouponAmount(HttpServletRequest request,Integer amount) {
		return backendService.isCouponAmount(amount);
	}
	@RequestMapping("/sendUnbuyMsg")
	public @ResponseBody boolean sendUnbuyMsg(HttpServletRequest request,String userList,boolean sendVoucher) {
		System.out.println("userlist====="+userList);
		String[] userarr = userList.split(","); // 用,分割
		boolean ret=true;
		for(String item:userarr) {
			Integer userId = Integer.parseInt(item);
			if(!sendVoucher) {
				return ret = backendService.sendUnbuyMsg(userId,sendVoucher);
			}
			OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
			if(login!=null && !login.equals("")) {
				ret = backendService.sendUnbuyMsg(userId,sendVoucher);
				List<ConsumerVoucher> consumerVouchers = consumerVoucherMapper.selectAll();//获取礼券赠送配置
				for(int j=0; j<consumerVouchers.size();j++) {
					List<HongShiCoupon> cp= hongShiMapper.getHongShiCouponByGoodsCode(consumerVouchers.get(j).getVoucherCode());
					for(int i=0; i<consumerVouchers.get(j).getAmount();i++) {
						System.out.println("ooo=="+cp.get(i).getVouchersCode()+"ooo=="+cp.get(i).getGoodsCode());
						ret = backendService.sendCoupon(cp.get(i).getGoodsCode(),cp.get(i).getVouchersCode(),login.getOauthId(),"消费信息送券");
					}
				}
			}else{
				return false;
			}
			
		}		
		return ret;
	}
	@RequestMapping("/delCategory")
	public @ResponseBody Map<String,Object> delCategory(HttpServletRequest request,Integer categoryId) {
		return backendService.delCategory(categoryId);
	}
	@RequestMapping("/editCategory")
	public @ResponseBody Map<String,Object> editCategory(HttpServletRequest request,@RequestBody Category category) {
		System.out.println("666666==========="+JSON.toJSONString(category));
		return backendService.editCategory(category);
	}
	@RequestMapping("/freightHandler")
	public @ResponseBody boolean freightHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateFreight(freightPost);
	}
	@RequestMapping("/fullCutShippingHandler")
	public @ResponseBody boolean fullCutShippingHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateFullCutShipping(freightPost);
	}
	@RequestMapping("/fullCutHandler")
	public @ResponseBody boolean fullCutHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateFullCut(freightPost);
	}
	@RequestMapping("/bindMemberHandler")
	public @ResponseBody boolean bindMemberHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateBindingRewards(freightPost);
	}
	@RequestMapping("/evaluationConfigurationHandler")
	public @ResponseBody boolean evaluationConfigurationHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateEvaluationGifts(freightPost);
	}
	@RequestMapping("/integralinConfigurationHandler")
	public @ResponseBody boolean integralinConfigurationHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateIntegralInGifts(freightPost);
	}
	@RequestMapping("/fullDiscountHandler")
	public @ResponseBody boolean fullDiscountHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateSendCoupon(freightPost);
	}
	@RequestMapping("/linkCouponHandler")
	public @ResponseBody boolean linkCouponHandler(HttpServletRequest request,@RequestBody VipVoucherPost vipVoucherPost) {
		return backendService.updateLinkCoupon(vipVoucherPost);
	}
	@RequestMapping("/birthVoucherHandler")
	public @ResponseBody boolean birthVoucherHandler(HttpServletRequest request,@RequestBody BirthVoucherPost birthVoucherPost) {
		System.out.println("day=============="+birthVoucherPost.getDay());
		return backendService.updateBirthVoucher(birthVoucherPost);
	}
	@RequestMapping("/truncateBirthVoucherHandler")
	public @ResponseBody boolean truncateBirthVoucherHandler(HttpServletRequest request) {
		return backendService.truncateBirthVoucherHandler();
	}
	@RequestMapping("/vipVoucherHandler")
	public @ResponseBody boolean vipVoucherHandler(HttpServletRequest request,@RequestBody VipVoucherPost vipVoucherPost) {
		return backendService.updateVipVoucher(vipVoucherPost);
	}
	@RequestMapping("/truncateVipVoucherHandler")
	public @ResponseBody boolean truncateVipVoucherHandler(HttpServletRequest request) {
		return backendService.truncateVipVoucherHandler();
	}
	
	@RequestMapping("/lotteryHandler")
	public @ResponseBody boolean lotteryHandler(HttpServletRequest request,@RequestBody LotteryConfigPost post) {
		System.out.println(JSON.toJSONString(post));
		return backendService.updateLottery(post);
	}
	@RequestMapping("/rechargeConfigNewHandler")
	public @ResponseBody boolean rechargeConfigHandler(HttpServletRequest request,@RequestBody RechargeConfig rechargeConfig) {
		return backendService.updateRechargeConfigNew(rechargeConfig);
	}
	@RequestMapping("/delRechargeConfig")
	public @ResponseBody boolean delRechargeConfig(HttpServletRequest request,Integer id) {
		return backendService.delRechargeConfig(id);
	}
	@RequestMapping("/rechargeConfigHandler")
	public @ResponseBody boolean rechargeConfigHandler(HttpServletRequest request,@RequestBody FreightPost freightPost) {
		return backendService.updateRechargeConfig(freightPost);
	}
	@RequestMapping("/delStore")
	public @ResponseBody boolean delStore(HttpServletRequest request,Integer storeId) {
		return backendService.delStore(storeId);
	}
	@RequestMapping("/editBanner")
	public @ResponseBody boolean editBanner(HttpServletRequest request,@RequestBody BannerPost bannerPost) {
		System.out.println(JSON.toJSONString(bannerPost));
		return backendService.editBanner(bannerPost);
	}
	@RequestMapping("/editQiuckNavi")
	public @ResponseBody boolean editQiuckNavi(HttpServletRequest request,@RequestBody HomeQuickNavi homeQuickNavi) {
		System.out.println(JSON.toJSONString(homeQuickNavi));
		return backendService.editQiuckNavi(homeQuickNavi);
	}
	@RequestMapping("/delBanner")
	public @ResponseBody int delBanner(HttpServletRequest request,Integer id) {
		return backendService.delBanner(id);
	}
	@RequestMapping("/delQuickNavi")
	public @ResponseBody int delQiuckNavi(HttpServletRequest request,Integer naviId) {
		return backendService.delQiuckNavi(naviId);
	}
	@RequestMapping("/editProductGroup")
	public @ResponseBody boolean editProductGroup(HttpServletRequest request,@RequestBody ProductGroupPost productGroupPost) {
		return backendService.editProductGroup(productGroupPost);
	}
	@RequestMapping("/insertGroupName")
	public @ResponseBody int insertGroupName(HttpServletRequest request,@RequestBody ProductGroup productGroup){
		return backendService.insertGroupName(productGroup);
	}
	@RequestMapping("/updateGroupName")
	public @ResponseBody int updateGroupName(HttpServletRequest request,@RequestBody ProductGroup productGroup){
		return backendService.updateGroupName(productGroup);
	}
	@RequestMapping("/deleteGroupName")
	public @ResponseBody Map<String, Object> deleteGroupName(HttpServletRequest request,Integer groupId){
		return backendService.deleteGroupName(groupId);
	}
	@RequestMapping("/editQuickNaviProduct")
	public @ResponseBody boolean editQuickNaviProduct(HttpServletRequest request,@RequestBody QuickNaviProductPost quickNaviProductPost) {
		return backendService.editQuickNaviProduct(quickNaviProductPost);
	}
	@RequestMapping("/delProductGroup")
	public @ResponseBody int delProductGroup(HttpServletRequest request,Integer groupId,Integer productId) {
		return backendService.delProductGroup(groupId,productId);
	}
	@RequestMapping(value="/commentBackHandler", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> commentBackHandler(HttpServletRequest request,@RequestBody Comment comment) {
		return backendService.commentBackHandler(comment);
	}
	@RequestMapping(value="/delComment")
	public @ResponseBody boolean delComment(HttpServletRequest request,Integer id) {
		return backendService.delComment(id);
	}
	@RequestMapping("/delQuickNaviProduct")
	public @ResponseBody int delQuickNaviProduct(HttpServletRequest request,Integer naviId,Integer productId) {
		return backendService.delQuickNaviProduct(naviId,productId);
	}
	@RequestMapping("/updateStoreInfo")
	public @ResponseBody int updateStoreInfo(HttpServletRequest request,@RequestBody ProductForm productForm) {
		return backendService.updateStoreInfo(productForm.getDescription());
	}
	@RequestMapping("/insertBargainSetting")
	public @ResponseBody int insertBargainSetting(HttpServletRequest request,@RequestBody BargainPost bargainPost) throws ParseException {
		//转换时间格式插入数据库
		String StartTimes = bargainPost.getStarts()+" "+bargainPost.getStartTime();
		String EndTimes = bargainPost.getEnds()+" "+bargainPost.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date StartDate = sdf.parse(StartTimes);
		Date EndDate = sdf.parse(EndTimes);
		bargainPost.setStart(StartDate);
		bargainPost.setClosure(EndDate);
		System.out.println("bargainPost="+JSON.toJSONString(bargainPost));
		return bargainSettingMapper.insert(bargainPost);
	}
	
	@RequestMapping("/delBargainId")
	public @ResponseBody int delBargainId(HttpServletRequest request,Integer id) {
		System.out.println("id="+id);
		return bargainSettingMapper.deleteBargainId(id);
	}
	
	@RequestMapping("/updateBargainSetting")
	public @ResponseBody int updateBargainSetting(HttpServletRequest request,@RequestBody BargainPost bargainPost) throws ParseException {
		System.out.println("bargainPost="+JSON.toJSONString(bargainPost));
		//转换时间格式更新数据库
		String StartTimes = bargainPost.getStarts()+" "+bargainPost.getStartTime();
		String EndTimes = bargainPost.getEnds()+" "+bargainPost.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date StartDate = sdf.parse(StartTimes);
		Date EndDate = sdf.parse(EndTimes);
		bargainPost.setStart(StartDate);
		bargainPost.setClosure(EndDate);
		return bargainSettingMapper.updateBargainId(bargainPost);
	}
	
	@RequestMapping("/orderSettingPickHandler")
	public  @ResponseBody boolean orderSettingPickHandler(HttpServletRequest request, @RequestBody OrderSettingPick  orderSettingPick){
		return backendService.updateOrderSettingPick(orderSettingPick);
	}

	@RequestMapping("/productGroupSortPosition")
	public @ResponseBody int productGroupSortPosition(HttpServletRequest request,Integer groupId,Integer productId,Integer position){
		return backendService.updateProductGroupPosition(groupId,productId,position);
	}
	
	@RequestMapping("/insertProductVoucher")
	public @ResponseBody int insertProductVoucher(HttpServletRequest request,@RequestBody ProductVoucher productVoucher){
		System.out.println(JSON.toJSON(productVoucher));
		return backendService.insert(productVoucher);
	}
	
	@RequestMapping("/updateProductVoucher")
	public @ResponseBody int updateProductVoucher(HttpServletRequest request,@RequestBody ProductVoucher productVoucher){
		System.out.println(JSON.toJSON(productVoucher));
		return backendService.updateByPrimaryKey(productVoucher);
	}
	
	@RequestMapping("/delProductVoucher")
	public @ResponseBody int delProductVoucher(HttpServletRequest request, Integer id){
		System.out.println("id:"+id);
		return backendService.deleteByPrimaryKey(id);
	}
	
	/**
	 * @author 申凯鑫
	 * 关联券和指定产品
	 * @param request
	 * @param vid
	 * @param pid
	 * @return
	 */
	@RequestMapping("/insertCouponsProductsLinks")
	public @ResponseBody ProductVoucherPost insertCouponsProductsLinks(HttpServletRequest request,Integer vid, Integer[] pid) {
		ProductVoucherPost productVoucherPost = new ProductVoucherPost();
		System.out.println(pid.length);
		for(int i=0; i<pid.length; i++) {
			productVoucherPost.setVid(vid);
			productVoucherPost.setPid(pid[i]);
			ProductVoucherPost pvp = backendService.selectInspectionAlreadyExists(productVoucherPost.getVid(), pid[i]);
			//没有相同数据才运行
			if(pvp == null) {
				backendService.insertCouponsProductsLinks(productVoucherPost);
			}
		}
		return productVoucherPost;
	}
	
	/**
	 * @author 申凯鑫
	 * 删除单个产品与购物赠券设置的关联
	 * @param request
	 * @param vid
	 * @param pid
	 * @return
	 */
	@RequestMapping("/delCouponsProductsLinks")
	public @ResponseBody int delCouponsProductsLinks(HttpServletRequest request,Integer vid, Integer pid) {
		return backendService.delCouponsProductsLinks(vid, pid);
	}
	
	@RequestMapping("/consumerVoucherHandler")
	public @ResponseBody boolean consumerVoucherHandler(HttpServletRequest request,@RequestBody ConsumerVoucherPost consumerVoucherPost) {
		return backendService.updateConsumerVoucher(consumerVoucherPost);
	}
	@RequestMapping("/truncateConsumerVoucherHandler")
	public @ResponseBody boolean truncateConsumerVoucherHandler(HttpServletRequest request) {
		return backendService.truncateConsumerVoucherHandler();
	}
	
	/**
	 * @author Administrator
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertMarketingEntrance")
	public @ResponseBody int insertMarketingEntrance(HttpServletRequest request, @RequestBody MarketingEntrance marketingEntrance) {
		System.out.println(JSON.toJSON(marketingEntrance));
		return backendService.insert(marketingEntrance);
	}
	
	@RequestMapping("/deleteMarketingEntrance")
	public @ResponseBody int deleteMarketingEntrance(HttpServletRequest request, Integer id) {
		return backendService.deleteMarketingEntrance(id);
	}

	@RequestMapping("/updateMarketingEntrance")
	public @ResponseBody int updateMarketingEntrance(HttpServletRequest request, @RequestBody MarketingEntrance marketingEntrance) {
		return backendService.updateMarketingEntrance(marketingEntrance);
	}
}
