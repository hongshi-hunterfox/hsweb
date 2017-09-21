package com.uclee.web.backend.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uclee.fundation.data.mybatis.model.Category;
import com.uclee.fundation.data.mybatis.model.Comment;
import com.uclee.fundation.data.mybatis.model.HomeQuickNavi;
import com.uclee.fundation.data.mybatis.model.RechargeConfig;
import com.uclee.fundation.data.web.dto.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.backend.model.ProductForm;
import com.backend.service.BackendServiceI;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-backend-web/")
public class BackendHandler {
	
	@Autowired
	private BackendServiceI backendService;
	
	@RequestMapping("/configHandler")
	public @ResponseBody boolean configHandler(HttpServletRequest request,@RequestBody ConfigPost configPost) {
		return backendService.updateConfig(configPost);
	}
	@RequestMapping("/sendBirthMsg")
	public @ResponseBody boolean sendBirthMsg(HttpServletRequest request,Integer userId,boolean sendVoucher) {
		return backendService.sendBirthMsg(userId,sendVoucher);
	}
	@RequestMapping("/isVoucherLimit")
	public @ResponseBody boolean isVoucherLimit(HttpServletRequest request,Integer amount) {
		return backendService.isVoucherLimit(amount);
	}
	@RequestMapping("/sendUnbuyMsg")
	public @ResponseBody boolean sendUnbuyMsg(HttpServletRequest request,Integer userId) {
		return backendService.sendUnbuyMsg(userId);
	}
	@RequestMapping("/delCategory")
	public @ResponseBody Map<String,Object> delCategory(HttpServletRequest request,Integer categoryId) {
		return backendService.delCategory(categoryId);
	}
	@RequestMapping("/editCategory")
	public @ResponseBody Map<String,Object> editCategory(HttpServletRequest request,@RequestBody Category category) {
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
	@RequestMapping("/birthVoucherHandler")
	public @ResponseBody boolean birthVoucherHandler(HttpServletRequest request,@RequestBody BirthVoucherPost birthVoucherPost) {
		return backendService.updateBirthVoucher(birthVoucherPost);
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
	@RequestMapping("/delQuickNaviProduct")
	public @ResponseBody int delQuickNaviProduct(HttpServletRequest request,Integer naviId,Integer productId) {
		return backendService.delQuickNaviProduct(naviId,productId);
	}
	@RequestMapping("/updateStoreInfo")
	public @ResponseBody int updateStoreInfo(HttpServletRequest request,@RequestBody ProductForm productForm) {
		return backendService.updateStoreInfo(productForm.getDescription());
	}
}
