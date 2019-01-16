package com.uclee.backend.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.uclee.fundation.data.mybatis.mapping.*;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.*;

import org.apache.http.entity.ContentType;
import org.apache.poi.hssf.record.formula.functions.Na;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.model.ProductForm;
import com.backend.service.BackendServiceI;
import com.github.pagehelper.PageHelper;
import com.uclee.date.util.DateUtils;
import com.uclee.file.util.DownloadPicFromURL;
import com.uclee.file.util.FileUtil;
import com.uclee.fundation.config.links.WebConfig;
import com.uclee.fundation.dfs.fastdfs.FDFSFileUpload;
import com.uclee.number.util.NumberUtil;
import com.youzan.open.sdk.util.http.DefaultHttpClient;
import com.youzan.open.sdk.util.http.HttpClient;


public class BackendServiceImpl implements BackendServiceI {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private YouZanVarMapper youZanVarMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private HongShiVipMapper hongShiVipMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ShippingFullCutMapper shippingFullCutMapper;
	@Autowired
	private FullCutMapper fullCutMapper;
	@Autowired
	private BindingRewardsMapper bindingRewardsMapper;
	@Autowired
	private SendCouponMapper sendCouponMapper;
	@Autowired
	private LinkCouponMapper linkCouponMapper;
	@Autowired
	private EvaluationGiftsMapper evaluationGiftsMapper;
	@Autowired
	private IntegralInGiftsMapper integralinGiftsMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private HomeQuickNaviMapper homeQuickNaviMapper;
	@Autowired
	private QuickNaviProductLinkMapper quickNaviProductLinkMapper;
	@Autowired
	private BannerMapper bannerMapper;
	@Autowired
	private MsgRecordMapper msgRecordMapper;
	@Autowired
	private FDFSFileUpload fDFSFileUpload;
	@Autowired
	private ProductGroupLinkMapper productGroupLinkMapper;
	@Autowired
	private BirthVoucherMapper birthVoucherMapper;
	@Autowired
	private ConsumerVoucherMapper consumerVoucherMapper;
	@Autowired
	private OauthLoginMapper oauthLoginMapper;
	@Autowired
	private ProductGroupMapper productGroupMapper;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private FreightMapper freightMapper;
	@Autowired
	private ProductCategoryLinkMapper productCategoryLinkMapper;
	@Autowired
	private ProductImageLinkMapper productImageLinkMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private StoreInfoMapper storeInfoMapper;
	@Autowired
	private SpecificationValueMapper specificationValueMapper;
	@Autowired
	private SpecificationValueStoreLinkMapper specificationValueStoreLinkMapper;
	@Autowired
	private NapaStoreMapper napaStoreMapper;
	@Autowired
	private RechargeConfigMapper rechargeConfigMapper;
	@Autowired
	private LotteryDrawConfigMapper lotteryDrawConfigMapper;
	@Autowired
	private HongShiMapper hongShiMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private VarMapper varMapper;
	@Autowired
	private BargainSettingMapper bargainSettingMapper;
	@Autowired
	private OrderSettingPickMapper orderSettingPickMapper;
	@Autowired
	private RefundOrderMapper refundOrderMapper;
	@Autowired
	private ProductVoucherMapper productVoucherMapper;
	@Autowired
	private MarketingEntranceMapper marketingEntranceMapper;
	
	@SuppressWarnings("unused")
	@Override
	public boolean updateConfig(ConfigPost configPost) { 
		
		boolean flag = true;
		if(!(configMapper.updateByTag(WebConfig.drawPoint, configPost.getDrawPoint())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.firstDis, configPost.getFirstDis())>0))   flag=false;
		if(!(configMapper.updateByTag(WebConfig.registPoint, configPost.getRegistPoint())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.secondDis, configPost.getSecondDis())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.signInPoint, configPost.getSignInPoint())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.APPID, configPost.getAppId())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.AppSecret, configPost.getAppSecret())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.APPKEY, configPost.getAppKey())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.MERCHANT_CODE, configPost.getMerchantCode())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.NOTIFY_URL, configPost.getNotifyUrl())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.FIRST_PRIZE, configPost.getFirstPrize())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.SECOND_PRIZE, configPost.getSecondPrize())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.THIRD_PRIZE, configPost.getThirdPrize())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.FIRST_COUNT, configPost.getFirstCount())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.SECOND_COUNT, configPost.getSecondCount())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.THIRD_COUNT, configPost.getThirdCount())>0)) flag=false;
		if(!(configMapper.updateByTag(WebConfig.THIRD_COUNT, configPost.getRestrictedDistance())>0)) flag=false;
		if (configPost.getAlipayNotifyUrl()!=null) {
			configMapper.updateByTag(WebConfig.alipayNotifyUrl, configPost.getAlipayNotifyUrl());
		}else{
			configMapper.updateByTag(WebConfig.alipayNotifyUrl, "");
		}
		if (configPost.getSellerId()!=null) {
			configMapper.updateByTag(WebConfig.sellerId, configPost.getSellerId());
		}else{
			configMapper.updateByTag(WebConfig.sellerId, "");
		}
		if (configPost.getKey()!=null) {
			configMapper.updateByTag(WebConfig.key, configPost.getKey());
		}else{
			configMapper.updateByTag(WebConfig.key, "");
		}
		if (configPost.getPartner()!=null) {
			configMapper.updateByTag(WebConfig.partner, configPost.getPartner());
		}else{
			configMapper.updateByTag(WebConfig.partner, "");
		}
		if (configPost.getAliAppkey()!=null) {
			configMapper.updateByTag(WebConfig.aliAppkey, configPost.getAliAppkey());
		}else{
			configMapper.updateByTag(WebConfig.aliAppkey, "");
		}
		if (configPost.getAliAppSecret()!=null) {
			configMapper.updateByTag(WebConfig.aliAppSecret, configPost.getAliAppSecret());
		}else{
			configMapper.updateByTag(WebConfig.aliAppSecret, "");
		}
		if (configPost.getTemplateCode()!=null) {
			configMapper.updateByTag(WebConfig.templateCode, configPost.getTemplateCode());
		}else{
			configMapper.updateByTag(WebConfig.templateCode, "");
		}
		if (configPost.getSignName()!=null) {
			configMapper.updateByTag(WebConfig.signName, configPost.getSignName());
		}else{
			configMapper.updateByTag(WebConfig.signName, "");
		}
		if (configPost.getBirthTemId()!=null) {
			configMapper.updateByTag(WebConfig.birthTmpId, configPost.getBirthTemId());
		}else{
			configMapper.updateByTag(WebConfig.birthTmpId, "");
		}
		if (configPost.getBuyTemId()!=null) {
			configMapper.updateByTag(WebConfig.buyTmpId, configPost.getBuyTemId());
		}else{
			configMapper.updateByTag(WebConfig.buyTmpId, "");
		}
		if (configPost.getPayTemId()!=null) {
			configMapper.updateByTag(WebConfig.payTmpId, configPost.getPayTemId());
		}else{
			configMapper.updateByTag(WebConfig.payTmpId, "");
		}
		if (configPost.getRechargeTemId()!=null) {
			configMapper.updateByTag(WebConfig.rechargeTmpId, configPost.getRechargeTemId());
		}else{
			configMapper.updateByTag(WebConfig.rechargeTmpId, "");
		}
		if (configPost.getBindText()!=null) {
			configMapper.updateByTag(WebConfig.bindText, configPost.getBindText());
		}else{
			configMapper.updateByTag(WebConfig.bindText, "");
		}
		if (configPost.getCommentText()!=null) {
			configMapper.updateByTag(WebConfig.commentText, configPost.getCommentText());
		}else{
			configMapper.updateByTag(WebConfig.commentText, "");
		}
		if (configPost.getSupportDeliver()!=null) {
			configMapper.updateByTag(WebConfig.supportDeliver, configPost.getSupportDeliver());
		}else{
			configMapper.updateByTag(WebConfig.supportDeliver, "");
		}
		if (configPost.getDomain()!=null) {
			configMapper.updateByTag(WebConfig.domain, configPost.getDomain());
		}else{
			configMapper.updateByTag(WebConfig.domain, "");
		}
		if (configPost.getHsMerchantCode()!=null) {
			configMapper.updateByTag(WebConfig.hsMerchatCode, configPost.getHsMerchantCode());
		}else{
			configMapper.updateByTag(WebConfig.hsMerchatCode, "");
		}
		if (configPost.getLogoUrl()!=null) {
			configMapper.updateByTag(WebConfig.logoUrl, configPost.getLogoUrl());
		}else{
			configMapper.updateByTag(WebConfig.logoUrl, "");
		}
		if (configPost.getUcenterImg()!=null) {
			configMapper.updateByTag(WebConfig.ucenterImg, configPost.getUcenterImg());
		}else{
			configMapper.updateByTag(WebConfig.ucenterImg, "");
		}
		if (configPost.getRestrictedDistance()!=null) {
			configMapper.updateByTag(WebConfig.restrictedDistance, configPost.getRestrictedDistance());
		}else{
			configMapper.updateByTag(WebConfig.restrictedDistance, "");
		}
		if (configPost.getStartUp()!=null) {
			configMapper.updateByTag(WebConfig.startUp, configPost.getStartUp());
		}else{
			configMapper.updateByTag(WebConfig.startUp, "");
		}
		if (configPost.getSignText()!=null) {
			configMapper.updateByTag(WebConfig.signText, configPost.getSignText());
		}else{
			configMapper.updateByTag(WebConfig.signText, "");
		}
		if (configPost.getForce()!=null) {
			configMapper.updateByTag(WebConfig.force, configPost.getForce());
		}else{
			configMapper.updateByTag(WebConfig.force, "");
		}if (configPost.getQq()!=null) {
			configMapper.updateByTag(WebConfig.qq, configPost.getQq());
		}else{
			configMapper.updateByTag(WebConfig.qq, "");
		}if(configPost.getBargainText()!=null){
			configMapper.updateByTag(WebConfig.bargainText, configPost.getBargainText());
		}else{
			configMapper.updateByTag(WebConfig.bargainText, "");
		}if (configPost.getLoss()!=null) {
			configMapper.updateByTag(WebConfig.loss, configPost.getLoss());
		}else{
			configMapper.updateByTag(WebConfig.loss, "");
		}if (configPost.getNotice()!=null) {
			configMapper.updateByTag(WebConfig.notice, configPost.getNotice());
		}else{
			configMapper.updateByTag(WebConfig.notice, "");
		}if(configPost.getPerfectBirthText()!=null){
			configMapper.updateByTag(WebConfig.perfectBirthText, configPost.getPerfectBirthText());
		}else{
			configMapper.updateByTag(WebConfig.perfectBirthText, "");
		}if(configPost.getVoucherSendInformation()!=null){
			configMapper.updateByTag(WebConfig.voucherSendInformation, configPost.getVoucherSendInformation());
		}else{
			configMapper.updateByTag(WebConfig.voucherSendInformation, "");
		}if(configPost.getWhetherEnableAlipay()!=null){
			configMapper.updateByTag(WebConfig.whetherEnableAlipay, configPost.getWhetherEnableAlipay());
		}else{
			configMapper.updateByTag(WebConfig.whetherEnableAlipay, "");
		}if(configPost.getVoucherSendInformation()!=null){
			configMapper.updateByTag(WebConfig.linkCouponText, configPost.getLinkCouponText());
		}else{
			configMapper.updateByTag(WebConfig.linkCouponText, "");
		}if(configPost.getCartLogo()!=null){
			configMapper.updateByTag(WebConfig.CartLogo, configPost.getCartLogo());
		}else{
			configMapper.updateByTag(WebConfig.CartLogo, "");
		}if(configPost.getCartBgUrl()!=null){
			configMapper.updateByTag(WebConfig.CartBgUrl, configPost.getCartBgUrl());
		}else{
			configMapper.updateByTag(WebConfig.CartBgUrl, "");
		}if(configPost.getCartBrandName()!=null){
			configMapper.updateByTag(WebConfig.CartBrandName, configPost.getCartBrandName());
		}else{
			configMapper.updateByTag(WebConfig.CartBrandName, "");
		}if(configPost.getCartTitle()!=null){
			configMapper.updateByTag(WebConfig.CartTitle, configPost.getCartTitle());
		}else{
			configMapper.updateByTag(WebConfig.CartTitle, "");
		}if(configPost.getCartNotice()!=null){
			configMapper.updateByTag(WebConfig.CartNotice, configPost.getCartNotice());
		}else{
			configMapper.updateByTag(WebConfig.CartNotice, "");
		}if(configPost.getCartServicePhone()!=null){
			configMapper.updateByTag(WebConfig.CartServicePhone, configPost.getCartServicePhone());
		}else{
			configMapper.updateByTag(WebConfig.CartServicePhone, "");
		}if(configPost.getCartDescription()!=null){
			configMapper.updateByTag(WebConfig.CartDescription, configPost.getCartDescription());
		}else{
			configMapper.updateByTag(WebConfig.CartDescription, "");
		}if(configPost.getCartCustomUrl()!=null){
			configMapper.updateByTag(WebConfig.CartCustomUrl, configPost.getCartCustomUrl());
		}else{
			configMapper.updateByTag(WebConfig.CartCustomUrl, "");
		}if(configPost.getCartPromotionUrl()!=null){
			configMapper.updateByTag(WebConfig.CartPromotionUrl, configPost.getCartPromotionUrl());
		}else{
			configMapper.updateByTag(WebConfig.CartPromotionUrl, "");
		}if(configPost.getCartCenterUrl()!=null){
			configMapper.updateByTag(WebConfig.CartCenterUrl, configPost.getCartCenterUrl());
		}else{
			configMapper.updateByTag(WebConfig.CartCenterUrl, "");
		}if(configPost.getCartBonusUrl()!=null){
			configMapper.updateByTag(WebConfig.CartBonusUrl, configPost.getCartBonusUrl());
		}else{
			configMapper.updateByTag(WebConfig.CartBonusUrl, "");
		}if(configPost.getCartCustomField1Url()!=null){
			configMapper.updateByTag(WebConfig.CartCustomField1Url, configPost.getCartCustomField1Url());
		}else{
			configMapper.updateByTag(WebConfig.CartCustomField1Url, "");
		}if(configPost.getCartPrerogative()!=null){
			configMapper.updateByTag(WebConfig.CartPrerogative, configPost.getCartPrerogative());
		}else{
			configMapper.updateByTag(WebConfig.CartPrerogative, "");
		}if(configPost.getPriceCuttingPoster()!=null){
			configMapper.updateByTag(WebConfig.priceCuttingPoster, configPost.getPriceCuttingPoster());
		}else{
			configMapper.updateByTag(WebConfig.priceCuttingPoster, "");
		}if(configPost.getNoBirthdayMessagePush()!=null){
			configMapper.updateByTag(WebConfig.noBirthdayMessagePush, configPost.getNoBirthdayMessagePush());
		}else{
			configMapper.updateByTag(WebConfig.noBirthdayMessagePush, "");
		}
		
		return true;
	}
	@Override
	public boolean updateActivityConfig(ConfigPost configPost) {

		boolean flag = true;
		if (configPost.getDrawPoint()!=null) {
			configMapper.updateByTag(WebConfig.drawPoint, configPost.getDrawPoint());
		}
		if (configPost.getFirstDis()!=null) {
			configMapper.updateByTag(WebConfig.firstDis, configPost.getFirstDis());
		}
		if (configPost.getRegistPoint()!=null) {
			configMapper.updateByTag(WebConfig.registPoint, configPost.getRegistPoint());
		}
		if (configPost.getSecondDis()!=null) {
			configMapper.updateByTag(WebConfig.secondDis, configPost.getSecondDis());
		}
		if (configPost.getSignInPoint()!=null) {
			configMapper.updateByTag(WebConfig.signInPoint, configPost.getSignInPoint());
		}
		if (configPost.getAppId()!=null) {
			configMapper.updateByTag(WebConfig.APPID, configPost.getAppId());
		}
		if (configPost.getAppSecret()!=null) {
			configMapper.updateByTag(WebConfig.AppSecret, configPost.getAppSecret());
		}
		if (configPost.getAppKey()!=null) {
			configMapper.updateByTag(WebConfig.APPKEY, configPost.getAppKey());
		}
		if (configPost.getMerchantCode()!=null) {
			configMapper.updateByTag(WebConfig.MERCHANT_CODE, configPost.getMerchantCode());
		}
		if (configPost.getNotifyUrl()!=null) {
			configMapper.updateByTag(WebConfig.NOTIFY_URL, configPost.getNotifyUrl());
		}
		if (configPost.getFirstPrize()!=null) {
			configMapper.updateByTag(WebConfig.FIRST_PRIZE, configPost.getFirstPrize());
		}
		if (configPost.getSecondPrize()!=null) {
			configMapper.updateByTag(WebConfig.SECOND_PRIZE, configPost.getSecondPrize());
		}
		if (configPost.getThirdPrize()!=null) {
			configMapper.updateByTag(WebConfig.THIRD_PRIZE, configPost.getThirdPrize());
		}
		if (configPost.getFirstCount()!=null) {
			configMapper.updateByTag(WebConfig.FIRST_COUNT, configPost.getFirstCount());
		}
		if (configPost.getSecondCount()!=null) {
			configMapper.updateByTag(WebConfig.SECOND_COUNT, configPost.getSecondCount());
		}
		if (configPost.getThirdCount()!=null) {
			configMapper.updateByTag(WebConfig.THIRD_COUNT, configPost.getThirdCount());
		}
		if (configPost.getAlipayNotifyUrl()!=null) {
			configMapper.updateByTag(WebConfig.alipayNotifyUrl, configPost.getAlipayNotifyUrl());
		}
		if (configPost.getSellerId()!=null) {
			configMapper.updateByTag(WebConfig.sellerId, configPost.getSellerId());
		}
		if (configPost.getKey()!=null) {
			configMapper.updateByTag(WebConfig.key, configPost.getKey());
		}
		if (configPost.getPartner()!=null) {
			configMapper.updateByTag(WebConfig.partner, configPost.getPartner());
		}
		if (configPost.getAliAppkey()!=null) {
			configMapper.updateByTag(WebConfig.aliAppkey, configPost.getAliAppkey());
		}
		if (configPost.getAliAppSecret()!=null) {
			configMapper.updateByTag(WebConfig.aliAppSecret, configPost.getAliAppSecret());
		}
		if (configPost.getTemplateCode()!=null) {
			configMapper.updateByTag(WebConfig.templateCode, configPost.getTemplateCode());
		}
		if (configPost.getSignName()!=null) {
			configMapper.updateByTag(WebConfig.signName, configPost.getSignName());
		}
		if (configPost.getBirthTemId()!=null) {
			configMapper.updateByTag(WebConfig.birthTmpId, configPost.getBirthTemId());
		}
		if (configPost.getBuyTemId()!=null) {
			configMapper.updateByTag(WebConfig.buyTmpId, configPost.getBuyTemId());
		}
		if (configPost.getPayTemId()!=null) {
			configMapper.updateByTag(WebConfig.payTmpId, configPost.getPayTemId());
		}
		if (configPost.getRechargeTemId()!=null) {
			configMapper.updateByTag(WebConfig.rechargeTmpId, configPost.getRechargeTemId());
		}
		if (configPost.getBindText()!=null) {
			configMapper.updateByTag(WebConfig.bindText, configPost.getBindText());
		}
		if (configPost.getCommentText()!=null) {
			configMapper.updateByTag(WebConfig.commentText, configPost.getCommentText());
		}
		if (configPost.getSupportDeliver()!=null) {
			configMapper.updateByTag(WebConfig.supportDeliver, configPost.getSupportDeliver());
		}
		if (configPost.getDomain()!=null) {
			configMapper.updateByTag(WebConfig.domain, configPost.getDomain());
		}
		if (configPost.getHsMerchantCode()!=null) {
			configMapper.updateByTag(WebConfig.hsMerchatCode, configPost.getHsMerchantCode());
		}
		if (configPost.getLogoUrl()!=null) {
			configMapper.updateByTag(WebConfig.logoUrl, configPost.getLogoUrl());
		}
		if (configPost.getUcenterImg()!=null) {
			configMapper.updateByTag(WebConfig.ucenterImg, configPost.getUcenterImg());
		}
		if (configPost.getBirthText()!=null) {
			configMapper.updateByTag(WebConfig.birthText, configPost.getBirthText());
		}
		if (configPost.getSalesText()!=null) {
			configMapper.updateByTag(WebConfig.salesText, configPost.getSalesText());
		}
		if (configPost.getRestrictedDistance()!=null) {
			configMapper.updateByTag(WebConfig.restrictedDistance, configPost.getRestrictedDistance());
		}
		if (configPost.getStartUp()!=null) {
			configMapper.updateByTag(WebConfig.startUp, configPost.getStartUp());
		}
		if (configPost.getFull()!=null) {
			configMapper.updateByTag(WebConfig.full, configPost.getFull());
		}
		if (configPost.getUnbundling()!=null) {
			configMapper.updateByTag(WebConfig.unbundling, configPost.getUnbundling());
		}
		if(configPost.getLoss()!=null){
			configMapper.updateByTag(WebConfig.loss, configPost.getLoss());
		}
		if (configPost.getSignText()!=null) {
			configMapper.updateByTag(WebConfig.signText, configPost.getSignText());
		}
		if (configPost.getVoucherSendInformation()!=null) {
			configMapper.updateByTag(WebConfig.voucherSendInformation, configPost.getVoucherSendInformation());
		}
		if (configPost.getForce()!=null) {
			configMapper.updateByTag(WebConfig.force, configPost.getForce());
		}
		if (configPost.getBrand()!=null) {
			configMapper.updateByTag(WebConfig.brand, configPost.getBrand());
		}
		if (configPost.getQq()!=null) {
			configMapper.updateByTag(WebConfig.qq, configPost.getQq());
		}
		if(configPost.getBargainText()!=null){
			configMapper.updateByTag(WebConfig.bargainText, configPost.getBargainText());
		}
		if (configPost.getNotice()!=null) {
			configMapper.updateByTag(WebConfig.notice, configPost.getNotice());
		}
		if (configPost.getPerfectBirthText()!=null) {
			configMapper.updateByTag(WebConfig.perfectBirthText, configPost.getPerfectBirthText());
		}
		if (configPost.getWhetherEnableAlipay()!=null) {
			configMapper.updateByTag(WebConfig.whetherEnableAlipay, configPost.getWhetherEnableAlipay());
		}
		if (configPost.getCartLogo()!=null) {
			configMapper.updateByTag(WebConfig.CartLogo, configPost.getCartLogo());
		}
		if (configPost.getCartBgUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartBgUrl, configPost.getCartBgUrl());
		}
		if (configPost.getCartBrandName()!=null) {
			configMapper.updateByTag(WebConfig.CartBrandName, configPost.getCartBrandName());
		}
		if (configPost.getCartTitle()!=null) {
			configMapper.updateByTag(WebConfig.CartTitle, configPost.getCartTitle());
		}
		if (configPost.getCartNotice()!=null) {
			configMapper.updateByTag(WebConfig.CartNotice, configPost.getCartNotice());
		}
		if (configPost.getCartServicePhone()!=null) {
			configMapper.updateByTag(WebConfig.CartServicePhone, configPost.getCartServicePhone());
		}
		if (configPost.getCartDescription()!=null) {
			configMapper.updateByTag(WebConfig.CartDescription, configPost.getCartDescription());
		}
		if (configPost.getCartCustomUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartCustomUrl, configPost.getCartCustomUrl());
		}
		if (configPost.getCartPromotionUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartPromotionUrl, configPost.getCartPromotionUrl());
		}
		if (configPost.getCartCenterUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartCenterUrl, configPost.getCartCenterUrl());
		}
		if (configPost.getCartBonusUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartBonusUrl, configPost.getCartBonusUrl());
		}
		if (configPost.getCartCustomField1Url()!=null) {
			configMapper.updateByTag(WebConfig.CartCustomField1Url, configPost.getCartCustomField1Url());
		}
		if (configPost.getCartPrerogative()!=null) {
			configMapper.updateByTag(WebConfig.CartPrerogative, configPost.getCartPrerogative());
		}
		if (configPost.getPriceCuttingPoster()!=null) {
			configMapper.updateByTag(WebConfig.priceCuttingPoster, configPost.getPriceCuttingPoster());
		}
		if (configPost.getNoBirthdayMessagePush()!=null) {
			configMapper.updateByTag(WebConfig.noBirthdayMessagePush, configPost.getNoBirthdayMessagePush());
		}
		return true;
	}
	@Override
	public boolean systemConfigHandler(ConfigPost configPost) {

		boolean flag = true;
		if (configPost.getDrawPoint()!=null) {
			configMapper.updateByTag(WebConfig.drawPoint, configPost.getDrawPoint());
		}
		if (configPost.getFirstDis()!=null) {
			configMapper.updateByTag(WebConfig.firstDis, configPost.getFirstDis());
		}
		if (configPost.getRegistPoint()!=null) {
			configMapper.updateByTag(WebConfig.registPoint, configPost.getRegistPoint());
		}
		if (configPost.getSecondDis()!=null) {
			configMapper.updateByTag(WebConfig.secondDis, configPost.getSecondDis());
		}
		if (configPost.getSignInPoint()!=null) {
			configMapper.updateByTag(WebConfig.signInPoint, configPost.getSignInPoint());
		}
		if (configPost.getAppId()!=null) {
			configMapper.updateByTag(WebConfig.APPID, configPost.getAppId());
		}
		if (configPost.getAppSecret()!=null) {
			configMapper.updateByTag(WebConfig.AppSecret, configPost.getAppSecret());
		}
		if (configPost.getAppKey()!=null) {
			configMapper.updateByTag(WebConfig.APPKEY, configPost.getAppKey());
		}
		if (configPost.getMerchantCode()!=null) {
			configMapper.updateByTag(WebConfig.MERCHANT_CODE, configPost.getMerchantCode());
		}
		if (configPost.getNotifyUrl()!=null) {
			configMapper.updateByTag(WebConfig.NOTIFY_URL, configPost.getNotifyUrl());
		}
		if (configPost.getFirstPrize()!=null) {
			configMapper.updateByTag(WebConfig.FIRST_PRIZE, configPost.getFirstPrize());
		}
		if (configPost.getSecondPrize()!=null) {
			configMapper.updateByTag(WebConfig.SECOND_PRIZE, configPost.getSecondPrize());
		}
		if (configPost.getThirdPrize()!=null) {
			configMapper.updateByTag(WebConfig.THIRD_PRIZE, configPost.getThirdPrize());
		}
		if (configPost.getFirstCount()!=null) {
			configMapper.updateByTag(WebConfig.FIRST_COUNT, configPost.getFirstCount());
		}
		if (configPost.getSecondCount()!=null) {
			configMapper.updateByTag(WebConfig.SECOND_COUNT, configPost.getSecondCount());
		}
		if (configPost.getThirdCount()!=null) {
			configMapper.updateByTag(WebConfig.THIRD_COUNT, configPost.getThirdCount());
		}
		if (configPost.getAlipayNotifyUrl()!=null) {
			configMapper.updateByTag(WebConfig.alipayNotifyUrl, configPost.getAlipayNotifyUrl());
		}
		if (configPost.getSellerId()!=null) {
			configMapper.updateByTag(WebConfig.sellerId, configPost.getSellerId());
		}
		if (configPost.getKey()!=null) {
			configMapper.updateByTag(WebConfig.key, configPost.getKey());
		}
		if (configPost.getPartner()!=null) {
			configMapper.updateByTag(WebConfig.partner, configPost.getPartner());
		}
		if (configPost.getAliAppkey()!=null) {
			configMapper.updateByTag(WebConfig.aliAppkey, configPost.getAliAppkey());
		}
		if (configPost.getAliAppSecret()!=null) {
			configMapper.updateByTag(WebConfig.aliAppSecret, configPost.getAliAppSecret());
		}
		if (configPost.getTemplateCode()!=null) {
			configMapper.updateByTag(WebConfig.templateCode, configPost.getTemplateCode());
		}
		if (configPost.getSignName()!=null) {
			configMapper.updateByTag(WebConfig.signName, configPost.getSignName());
		}
		if (configPost.getBirthTemId()!=null) {
			configMapper.updateByTag(WebConfig.birthTmpId, configPost.getBirthTemId());
		}
		if (configPost.getBuyTemId()!=null) {
			configMapper.updateByTag(WebConfig.buyTmpId, configPost.getBuyTemId());
		}
		if (configPost.getPayTemId()!=null) {
			configMapper.updateByTag(WebConfig.payTmpId, configPost.getPayTemId());
		}
		if (configPost.getRechargeTemId()!=null) {
			configMapper.updateByTag(WebConfig.rechargeTmpId, configPost.getRechargeTemId());
		}
		if (configPost.getBindText()!=null) {
			configMapper.updateByTag(WebConfig.bindText, configPost.getBindText());
		}
		if (configPost.getCommentText()!=null) {
			configMapper.updateByTag(WebConfig.commentText, configPost.getCommentText());
		}
		if (configPost.getSupportDeliver()!=null) {
			configMapper.updateByTag(WebConfig.supportDeliver, configPost.getSupportDeliver());
		}
		if (configPost.getDomain()!=null) {
			configMapper.updateByTag(WebConfig.domain, configPost.getDomain());
		}
		if (configPost.getHsMerchantCode()!=null) {
			configMapper.updateByTag(WebConfig.hsMerchatCode, configPost.getHsMerchantCode());
		}
		if (configPost.getLogoUrl()!=null) {
			configMapper.updateByTag(WebConfig.logoUrl, configPost.getLogoUrl());
		}
		if (configPost.getUcenterImg()!=null) {
			configMapper.updateByTag(WebConfig.ucenterImg, configPost.getUcenterImg());
		}
		if (configPost.getBirthText()!=null) {
			configMapper.updateByTag(WebConfig.birthText, configPost.getBirthText());
		}
		if (configPost.getSalesText()!=null) {
			configMapper.updateByTag(WebConfig.salesText, configPost.getSalesText());
		}
		if (configPost.getRestrictedDistance()!=null) {
			configMapper.updateByTag(WebConfig.restrictedDistance, configPost.getRestrictedDistance());
		}
		if (configPost.getStartUp()!=null) {
			configMapper.updateByTag(WebConfig.startUp, configPost.getStartUp());
		}
		if (configPost.getFull()!=null) {
			configMapper.updateByTag(WebConfig.full, configPost.getFull());
		}
		if (configPost.getUnbundling()!=null) {
			configMapper.updateByTag(WebConfig.unbundling, configPost.getUnbundling());
		}
		if (configPost.getLoss()!=null) {
			configMapper.updateByTag(WebConfig.loss, configPost.getLoss());
		}
		if (configPost.getSignText()!=null) {
			configMapper.updateByTag(WebConfig.signText, configPost.getSignText());
		}
		if (configPost.getVoucherSendInformation()!=null) {
			configMapper.updateByTag(WebConfig.voucherSendInformation, configPost.getVoucherSendInformation());
		}
		if (configPost.getForce()!=null) {
			configMapper.updateByTag(WebConfig.force, configPost.getForce());
		}
		if (configPost.getBrand()!=null) {
			configMapper.updateByTag(WebConfig.brand, configPost.getBrand());
		}
		if(configPost.getBargainText()!=null){
			configMapper.updateByTag(WebConfig.bargainText, configPost.getBargainText());
		}
		if (configPost.getNotice()!=null) {
			configMapper.updateByTag(WebConfig.notice, configPost.getNotice());
		}
		if (configPost.getPerfectBirthText()!=null) {
			configMapper.updateByTag(WebConfig.perfectBirthText, configPost.getPerfectBirthText());
		}
		if (configPost.getWhetherEnableAlipay()!=null) {
			configMapper.updateByTag(WebConfig.whetherEnableAlipay, configPost.getWhetherEnableAlipay());
		}
		if (configPost.getLinkCouponText()!=null) {
			configMapper.updateByTag(WebConfig.linkCouponText, configPost.getLinkCouponText());
		}
		if (configPost.getCartLogo()!=null) {
			configMapper.updateByTag(WebConfig.CartLogo, configPost.getCartLogo());
		}
		if (configPost.getCartBgUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartBgUrl, configPost.getCartBgUrl());
		}
		if (configPost.getCartBrandName()!=null) {
			configMapper.updateByTag(WebConfig.CartBrandName, configPost.getCartBrandName());
		}
		if (configPost.getCartTitle()!=null) {
			configMapper.updateByTag(WebConfig.CartTitle, configPost.getCartTitle());
		}
		if (configPost.getCartNotice()!=null) {
			configMapper.updateByTag(WebConfig.CartNotice, configPost.getCartNotice());
		}
		if (configPost.getCartServicePhone()!=null) {
			configMapper.updateByTag(WebConfig.CartServicePhone, configPost.getCartServicePhone());
		}
		if (configPost.getCartDescription()!=null) {
			configMapper.updateByTag(WebConfig.CartDescription, configPost.getCartDescription());
		}
		if (configPost.getCartCustomUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartCustomUrl, configPost.getCartCustomUrl());
		}
		if (configPost.getCartPromotionUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartPromotionUrl, configPost.getCartPromotionUrl());
		}
		if (configPost.getCartCenterUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartCenterUrl, configPost.getCartCenterUrl());
		}
		if (configPost.getCartBonusUrl()!=null) {
			configMapper.updateByTag(WebConfig.CartBonusUrl, configPost.getCartBonusUrl());
		}
		if (configPost.getCartCustomField1Url()!=null) {
			configMapper.updateByTag(WebConfig.CartCustomField1Url, configPost.getCartCustomField1Url());
		}
		if (configPost.getCartPrerogative()!=null) {
			configMapper.updateByTag(WebConfig.CartPrerogative, configPost.getCartPrerogative());
		}
		if (configPost.getPriceCuttingPoster()!=null) {
			configMapper.updateByTag(WebConfig.priceCuttingPoster, configPost.getPriceCuttingPoster());
		}
		if (configPost.getNoBirthdayMessagePush()!=null) {
			configMapper.updateByTag(WebConfig.noBirthdayMessagePush, configPost.getNoBirthdayMessagePush());
		}
		return true;
	}
	@Override
	public List<Freight> selectAllFreight() {

		return freightMapper.selectAllAsc();
	}
	@Override
	public List<FullCut> selectAllFullCut() {

		return fullCutMapper.selectAll();
	}
	@Override
	public List<BindingRewards> selectAllBindingRewards() {

		return bindingRewardsMapper.selectOne();
	}
	
	@Override
	public List<EvaluationGifts> selectAllEvaluationGifts() {

		return evaluationGiftsMapper.selectOne();
	}

	@Override
	public List<IntegralInGifts> selectAllIntegralInGifts() {

		return integralinGiftsMapper.selectOne();
	}

	@Override
	public boolean updateFreight(FreightPost freightPost) {
		int delAll = freightMapper.deleteAll();
		if(freightPost.getMyKey()==null||freightPost.getMyValue()==null||freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			Freight freight = new Freight();
			freight.setCondition(entry.getValue());
			freight.setMoney(new BigDecimal(freightPost.getMyValue().get(entry.getKey())));
			freightMapper.insertSelective(freight);
		}
		return true;
	}
	@Override
	public boolean updateFullCutShipping(FreightPost freightPost) {
		int delAll = shippingFullCutMapper.deleteAll();
		if(freightPost.getMyKey()==null||freightPost.getMyValue()==null||freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0||freightPost.getMyValue1()==null||freightPost.getMyValue1().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			ShippingFullCut shippingFullCut1 = new ShippingFullCut();
			shippingFullCut1.setsLimit(entry.getValue());
			shippingFullCut1.setuLimit(Double.parseDouble(freightPost.getMyValue().get(entry.getKey())));
			shippingFullCut1.setCondition(new BigDecimal(freightPost.getMyValue1().get(entry.getKey())));
			shippingFullCut1.setStartTime(DateUtils.parse(freightPost.getStartTimeStr(),DateUtils.FORMAT_LONG));
			shippingFullCut1.setEndTime(DateUtils.parse(freightPost.getEndTimeStr(),DateUtils.FORMAT_LONG));
			shippingFullCutMapper.insertSelective(shippingFullCut1);
		}
		return true;
	}
	@Override
	public boolean updateBindingRewards(FreightPost freightPost) {
		int delAll = bindingRewardsMapper.deleteAll();
		if(freightPost.getMyKey()==null||freightPost.getMyValue()==null||freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0||freightPost.getMyValue1()==null||freightPost.getMyValue1().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			BindingRewards bindingRewards = new BindingRewards();
			bindingRewards.setPoint(entry.getValue().intValue());
			bindingRewards.setVoucherCode(freightPost.getMyValue().get(entry.getKey()));
			bindingRewards.setAmount(Integer.parseInt(freightPost.getMyValue1().get(entry.getKey())));
			bindingRewardsMapper.insertSelective(bindingRewards);
		}
		return true;
	}
	@Override
	public boolean updateEvaluationGifts(FreightPost freightPost) {
		int delAll = evaluationGiftsMapper.deleteAll();
		if(freightPost.getMyKey()==null||freightPost.getMyValue()==null||freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0||freightPost.getMyValue1()==null||freightPost.getMyValue1().size()==0||freightPost.getMyValue0()==null||freightPost.getMyValue0().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			EvaluationGifts evaluationGifts = new EvaluationGifts();
			evaluationGifts.setPoint(entry.getValue().intValue());
			evaluationGifts.setMoney(new BigDecimal(freightPost.getMyValue0().get(entry.getKey())));
			evaluationGifts.setVoucherCode(freightPost.getMyValue().get(entry.getKey()));
			evaluationGifts.setAmount(Integer.parseInt(freightPost.getMyValue1().get(entry.getKey())));
			evaluationGiftsMapper.insertSelective(evaluationGifts);
		}
		return true;
	}
	@Override
	public boolean updateIntegralInGifts(FreightPost freightPost) {
		int delAll = integralinGiftsMapper.deleteAll();
		if(freightPost.getMyKey()==null||freightPost.getMyValue()==null||freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0||freightPost.getMyValue1()==null||freightPost.getMyValue1().size()==0||freightPost.getMyValue0()==null||freightPost.getMyValue0().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			IntegralInGifts integralinGifts = new IntegralInGifts();
			integralinGifts.setDay(entry.getValue().intValue());
			integralinGifts.setMoney(new BigDecimal(freightPost.getMyValue0().get(entry.getKey())));
			integralinGifts.setVoucherCode(freightPost.getMyValue().get(entry.getKey()));
			integralinGifts.setAmount(Integer.parseInt(freightPost.getMyValue1().get(entry.getKey())));
			integralinGiftsMapper.insertSelective(integralinGifts);
		}
		return true;
	}
	@Override
	public boolean updateFullCut(FreightPost freightPost) {
		int delAll = fullCutMapper.deleteAll();
		if(freightPost.getMyKey()==null||freightPost.getMyValue()==null||freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			FullCut fullCut1 = new FullCut();
			fullCut1.setCondition(new BigDecimal(entry.getValue()));
			fullCut1.setCut(new BigDecimal(freightPost.getMyValue().get(entry.getKey())));
			fullCut1.setStartTime(DateUtils.parse(freightPost.getStartTimeStr(),DateUtils.FORMAT_LONG));
			fullCut1.setEndTime(DateUtils.parse(freightPost.getEndTimeStr(),DateUtils.FORMAT_LONG));
			fullCutMapper.insertSelective(fullCut1);
		}
		return true;
	}
	@Override
	public boolean updateBirthVoucher(BirthVoucherPost birthVoucherPost) {
		int delAll = birthVoucherMapper.deleteAll();
		if(birthVoucherPost.getMyKey()==null||birthVoucherPost.getMyValue()==null||birthVoucherPost.getMyKey().size()==0||birthVoucherPost.getMyValue().size()==0){
			return false;
		}
		for(Map.Entry<Integer, String> entry : birthVoucherPost.getMyKey().entrySet()){
			if(entry.getValue()==null||birthVoucherPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, String> entry : birthVoucherPost.getMyKey().entrySet()){
			BirthVoucher tmp = new BirthVoucher();
			tmp.setAmount(Integer.parseInt(birthVoucherPost.getMyValue().get(entry.getKey())));
			tmp.setVoucherCode(entry.getValue().toString());
			birthVoucherMapper.insertSelective(tmp);
		}
		birthVoucherMapper.updateBrithPush(birthVoucherPost.getDay());
		return true;
	}
	@Override
	public boolean truncateBirthVoucherHandler() {
		int delAll = birthVoucherMapper.deleteAll();

		return true;
	}

	@Override
	public boolean updateVipVoucher(VipVoucherPost vipVoucherPost) {
		@SuppressWarnings("unused")
		int delAll = hongShiVipMapper.deleteAll();
		if(vipVoucherPost.getMyKey()==null||vipVoucherPost.getMyValue()==null||vipVoucherPost.getMyKey().size()==0||vipVoucherPost.getMyValue().size()==0){
			return false;
		}
		for(Map.Entry<Integer, String> entry : vipVoucherPost.getMyKey().entrySet()){
			if(entry.getValue()==null||vipVoucherPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, String> entry : vipVoucherPost.getMyKey().entrySet()){
			VipVoucher tmp = new VipVoucher();
			tmp.setAmount(Integer.parseInt(vipVoucherPost.getMyValue().get(entry.getKey())));
			tmp.setVoucher(entry.getValue().toString());
			hongShiVipMapper.insertSelective(tmp);
		}
		return true;
	}
	@Override
	public boolean truncateVipVoucherHandler() {
		int delAll = hongShiVipMapper.deleteAll();

		return true;
	}

	@Override
	public List<ShippingFullCut> selectAllShippingFullCut() {
		return shippingFullCutMapper.selectAllShippingFullCut();
	}

	@Override
	@CacheEvict(value = "cookaCache", key = "'config'")
	public boolean updateLottery(LotteryConfigPost post) {
		int delAll = lotteryDrawConfigMapper.deleteAll();
		if(post.getValue()==null||post.getKey()==null){
			return false;
		}
		int maxSize = 8;
		for(int i=0;i<=maxSize-1;i++){
			LotteryDrawConfig config = new LotteryDrawConfig();
			config.setCode(NumberUtil.generateSerialNum());
			if(post.getValue().get(i)==1){
				config.setVoucherCode(post.getKey().get(i));
			}else{
				try {
					config.setMoney(new BigDecimal(post.getKey().get(i)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			config.setCount(post.getCount().get(i));
			config.setRate(post.getRate().get(i));
			config.setTimeEnd(DateUtils.parse(post.getDateEnd()+" "+post.getTimeEnd()+":00", DateUtils.FORMAT_LONG));
			config.setTimeStart(DateUtils.parse(post.getDateStart()+" "+post.getTimeStart()+":00", DateUtils.FORMAT_LONG));
			config.setLimits(post.getLimits());
			lotteryDrawConfigMapper.insertSelective(config);
		}
		return true;
	}
	@Override
	public boolean updateRechargeConfig(FreightPost freightPost) {
		if(freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null||freightPost.getMySelect().get(entry.getKey())==null){
				return false;
			}
		}
		int delAll = rechargeConfigMapper.deleteAll();
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			RechargeConfig config = new RechargeConfig();
			if(freightPost.getMySelect().get(entry.getKey()).equals(1)){
				config.setRewards(new BigDecimal(freightPost.getMyValue().get(entry.getKey())));
			}else{
				config.setVoucherCode(freightPost.getMyValue().get(entry.getKey()));
			}
			config.setMoney(new BigDecimal(entry.getValue()));
			config.setType(freightPost.getMySelect().get(entry.getKey()));
			rechargeConfigMapper.insertSelective(config);
		}
		return true;
	}

	@Override
	public ProductForm getProductForm(Integer productId) {
		ProductForm productForm = new ProductForm();
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product!=null){
			productForm.setIsShow(product.getIsShow());
			productForm.setParameter(product.getParameter());
			productForm.setExplain(product.getExplain());
			productForm.setFrequency(product.getFrequency());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
			if(product.getShelfTime()!=null && product.getDownTime()!=null){
				productForm.setShelfDate(sdf.format(product.getShelfTime()));
				productForm.setShelfTimes(sdf1.format(product.getShelfTime()));
				productForm.setDownDate(sdf.format(product.getDownTime()));
				productForm.setDownTimes(sdf1.format(product.getDownTime()));
			}			
			
			productForm.setAppointedTime(product.getAppointedTime());
			productForm.setShippingFree(product.getShippingFree());
			productForm.setTitle(product.getTitle());
			
			if(product.getPickUpTime() != null && product.getPickEndTime() != null){
				productForm.setPickUpTimes(sdf.format(product.getPickUpTime()));
				productForm.setPickEndTimes(sdf.format(product.getPickEndTime()));
			}
			productForm.setDescription(FileUtil.UrlRequest(product.getDescription()));
		}
		ProductCategoryLinkKey link = productCategoryLinkMapper.selectByProductId(productId);
		if(link!=null){
			productForm.setCategoryId(link.getCategoryId());
		}
		List<ProductImageLink> imageLink = productImageLinkMapper.selectByProductId(productId);
		String images[] = new String[3];
		for(int i=0;i<imageLink.size();i++){
			images[i] = imageLink.get(i).getImageUrl();
		}
		productForm.setImages(images);
		productForm.setProductId(productId);
		List<ValuePost> valuePost = new ArrayList<ValuePost>();
		List<SpecificationValue> values = specificationValueMapper.selectByProductId(productId);
		for(SpecificationValue value:values){
			ValuePost tmp = new ValuePost();
			tmp.setValueId(value.getValueId());
			tmp.setCode(value.getHsGoodsCode());
			tmp.setHsStock(value.getHsStock());
			tmp.setHsPrice(value.getHsGoodsPrice());
			tmp.setVipPrice(value.getVipPrice());
			tmp.setName(value.getValue());
			tmp.setPrePrice(value.getPrePrice());
			tmp.setPromotionPrice(value.getPromotionPrice());
			tmp.setStartTime(value.getStartTime());
			tmp.setEndTime(value.getEndTime());
			tmp.setStartTimeStr(DateUtils.format(value.getStartTime(), DateUtils.FORMAT_LONG));
			tmp.setEndTimeStr(DateUtils.format(value.getEndTime(), DateUtils.FORMAT_LONG));	
			List<Integer> storeIds = new ArrayList<Integer>();
			List<NapaStore> stores = napaStoreMapper.selectByValueId(value.getValueId());
			for(NapaStore store:stores){
				storeIds.add(store.getStoreId());
			}
			tmp.setStoreIds(storeIds);
			valuePost.add(tmp);
		}
		productForm.setValuePost(valuePost);
		return productForm;
	}

	@Override
	public List<RechargeConfig> selectAllRechargeConfig() {
		List<RechargeConfig> configs = rechargeConfigMapper.selectAll();
		return configs;
	}

	@Override
	public List<LotteryDrawConfig> selectAllLotteryDrawConfig() {
		return lotteryDrawConfigMapper.selectAll();
	}

	@Override
	public List<ProductDto> selectAllProduct() {
		List<ProductDto> product = productMapper.getAllProduct(null,null,null,null, null);
		for(ProductDto item:product){
			ProductImageLink link = productImageLinkMapper.selectByProductIdLimit(item.getProductId());
			if(link!=null){
				item.setImage(link.getImageUrl());
			}
			Category category = categoryMapper.selectByPId(item.getProductId());
			if(category!=null){
				item.setCategory(category.getCategory());
			}
		}
		return product;
	}

	@Override
	public List<HongShiOrder> getHongShiOrder(Boolean isEnd) {
		List<HongShiOrder> orders = hongShiMapper.getHongShiOrder(null,isEnd);
		for (HongShiOrder order : orders) {
			List<HongShiOrderItem> orderItems = hongShiMapper.getHongShiOrderItems(order.getId());
			for (HongShiOrderItem item : orderItems) {
				HongShiGoods goods = hongShiMapper.getHongShiGoods(item.getCode());
				if (goods != null) {
					ProductImageLink link = productImageLinkMapper.selectByHongShiGoodsCodeLimit(goods.getCode());
					if (link != null) {
						goods.setImage(link.getImageUrl());
					}
				}
				item.setHongShiGoods(goods);
			}
			order.setOrderItems(orderItems);
		}
		return orders;
	}

	@Override
	public List<UserProfile> getUserList(Integer pn) {
		List<UserProfile> userProfile = userProfileMapper.selectAllProfileList(pn);
		for(UserProfile item:userProfile){
			item.setRegistTimeStr(DateUtils.format(item.getRegistTime(), DateUtils.FORMAT_LONG));
		}
		return userProfile;
	}
	
	@Override
	public Double selectPageNums() {
		return userProfileMapper.selectPageNums();
	}
	
	@Override
	public List<UserProfile> getVipList(Date start, Date end) throws ParseException {
		return userProfileMapper.selectByVips(start, end);
	}
	@Override
	public List<UserProfile> selectCardPhoneVips(String cartphone) {
		if(cartphone==null){
			return new ArrayList<UserProfile>();
		}
		List<UserProfile> viplist = userProfileMapper.selectCardPhoneVips(cartphone);
		for(UserProfile item:viplist){
			item.setRegistTimeStr(DateUtils.format(item.getRegistTime(), DateUtils.FORMAT_LONG));
			item.setLastBuyStr(DateUtils.format(item.getLastBuy(), DateUtils.FORMAT_LONG));
		}
		return viplist;
	}
	
	@Override
	public List<UserProfile> getUserListForBirth(String start,String end) {
		if(start==null||end==null){
			return new ArrayList<UserProfile>();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		List<UserProfile> userProfile = userProfileMapper.getUserListForBirth(start,end,sdf.format(date));
		for(UserProfile item : userProfile){
			item.setBirthStr(DateUtils.format(item.getBirth(), DateUtils.FORMAT_SHORT));
		}
		return userProfile;
	}
	@Override
	public List<UserProfile> getUserListForUnBuy(Integer day) {
		List<UserProfile> userProfile = userProfileMapper.getUserListForUnBuy(day);
		for(UserProfile item : userProfile){
			item.setLastBuyStr(DateUtils.format(item.getLastBuy(), DateUtils.FORMAT_SHORT));
		}
		return userProfile;
	}

	@Override
	public ConfigPost getConfig() {
		ConfigPost configPost = new ConfigPost();
		List<Config> configs = configMapper.selectAll();
		for(Config config:configs){
			if(config.getTag().equals(WebConfig.APPID)){
				configPost.setAppId(config.getValue());
			}else if(config.getTag().equals(WebConfig.APPKEY)){
				configPost.setAppKey(config.getValue());
			}else if(config.getTag().equals(WebConfig.AppSecret)){
				configPost.setAppSecret(config.getValue());
			}else if(config.getTag().equals(WebConfig.drawPoint)){
				configPost.setDrawPoint(config.getValue());
			}else if(config.getTag().equals(WebConfig.firstDis)){
				configPost.setFirstDis(config.getValue());
			}else if(config.getTag().equals(WebConfig.MERCHANT_CODE)){
				configPost.setMerchantCode(config.getValue());
			}else if(config.getTag().equals(WebConfig.NOTIFY_URL)){
				configPost.setNotifyUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.registPoint)){
				configPost.setRegistPoint(config.getValue());
			}else if(config.getTag().equals(WebConfig.secondDis)){
				configPost.setSecondDis(config.getValue());
			}else if(config.getTag().equals(WebConfig.signInPoint)){
				configPost.setSignInPoint(config.getValue());
			}else if(config.getTag().equals(WebConfig.FIRST_PRIZE)){
				configPost.setFirstPrize(config.getValue());
			}else if(config.getTag().equals(WebConfig.SECOND_PRIZE)){
				configPost.setSecondPrize(config.getValue());
			}else if(config.getTag().equals(WebConfig.THIRD_PRIZE)){
				configPost.setThirdPrize(config.getValue());
			}else if(config.getTag().equals(WebConfig.FIRST_COUNT)){
				configPost.setFirstCount(config.getValue());
			}else if(config.getTag().equals(WebConfig.SECOND_COUNT)){
				configPost.setSecondCount(config.getValue());
			}else if(config.getTag().equals(WebConfig.THIRD_COUNT)){
				configPost.setThirdCount(config.getValue());
			}else if(config.getTag().equals(WebConfig.sellerId)){
				configPost.setSellerId(config.getValue());
			}else if(config.getTag().equals(WebConfig.alipayNotifyUrl)){
				configPost.setAlipayNotifyUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.key)){
				configPost.setKey(config.getValue());
			}else if(config.getTag().equals(WebConfig.partner)){
				configPost.setPartner(config.getValue());
			}else if(config.getTag().equals(WebConfig.aliAppkey)){
				configPost.setAliAppkey(config.getValue());
			}else if(config.getTag().equals(WebConfig.aliAppSecret)){
				configPost.setAliAppSecret(config.getValue());
			}else if(config.getTag().equals(WebConfig.templateCode)){
				configPost.setTemplateCode(config.getValue());
			}else if(config.getTag().equals(WebConfig.signName)){
				configPost.setSignName(config.getValue());
			}else if(config.getTag().equals(WebConfig.birthTmpId)){
				configPost.setBirthTemId(config.getValue());
			}else if(config.getTag().equals(WebConfig.buyTmpId)){
				configPost.setBuyTemId(config.getValue());
			}else if(config.getTag().equals(WebConfig.payTmpId)){
				configPost.setPayTemId(config.getValue());
			}else if(config.getTag().equals(WebConfig.rechargeTmpId)){
				configPost.setRechargeTemId(config.getValue());
			}else if(config.getTag().equals(WebConfig.bindText)){
				configPost.setBindText(config.getValue());
			}else if(config.getTag().equals(WebConfig.commentText)){
				configPost.setCommentText(config.getValue());
			}else if(config.getTag().equals(WebConfig.supportDeliver)){
				configPost.setSupportDeliver(config.getValue());
			}else if(config.getTag().equals(WebConfig.domain)){
				configPost.setDomain(config.getValue());
			}else if(config.getTag().equals(WebConfig.hsMerchatCode)){
				configPost.setHsMerchantCode(config.getValue());
			}else if(config.getTag().equals(WebConfig.logoUrl)){
				configPost.setLogoUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.ucenterImg)){
				configPost.setUcenterImg(config.getValue());
			}else if(config.getTag().equals(WebConfig.birthText)){
				configPost.setBirthText(config.getValue());
			}else if(config.getTag().equals(WebConfig.salesText)){
				configPost.setSalesText(config.getValue());
			}else if(config.getTag().equals(WebConfig.restrictedDistance)){
				configPost.setRestrictedDistance(config.getValue());
			}else if(config.getTag().equals(WebConfig.startUp)){
				configPost.setStartUp(config.getValue());
			}else if(config.getTag().equals(WebConfig.full)){
				configPost.setFull(config.getValue());
			}else if(config.getTag().equals(WebConfig.unbundling)){
				configPost.setUnbundling(config.getValue());
			}else if(config.getTag().equals(WebConfig.loss)){
				configPost.setLoss(config.getValue());				
			}else if(config.getTag().equals(WebConfig.signText)){
				configPost.setSignText(config.getValue());
			}else if(config.getTag().equals(WebConfig.voucherSendInformation)){
				configPost.setVoucherSendInformation(config.getValue());
			}else if(config.getTag().equals(WebConfig.force)){
				configPost.setForce(config.getValue());
			}else if(config.getTag().equals(WebConfig.brand)){
				configPost.setBrand(config.getValue());
			}else if(config.getTag().equals(WebConfig.qq)){
				configPost.setQq(config.getValue());
			}else if(config.getTag().equals(WebConfig.bargainText)){
				configPost.setBargainText(config.getValue());
			}else if(config.getTag().equals(WebConfig.notice)){
				configPost.setNotice(config.getValue());
			}else if(config.getTag().equals(WebConfig.perfectBirthText)){
				configPost.setPerfectBirthText(config.getValue());
			}else if(config.getTag().equals(WebConfig.whetherEnableAlipay)){
				configPost.setWhetherEnableAlipay(config.getValue());
			}else if(config.getTag().equals(WebConfig.linkCouponText)){
				configPost.setLinkCouponText(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartLogo)){
				configPost.setCartLogo(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartBgUrl)){
				configPost.setCartBgUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartBrandName)){
				configPost.setCartBrandName(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartTitle)){
				configPost.setCartTitle(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartNotice)){
				configPost.setCartNotice(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartServicePhone)){
				configPost.setCartServicePhone(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartDescription)){
				configPost.setCartDescription(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartCustomUrl)){
				configPost.setCartCustomUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartPromotionUrl)){
				configPost.setCartPromotionUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartCenterUrl)){
				configPost.setCartCenterUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartBonusUrl)){
				configPost.setCartBonusUrl(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartCustomField1Url)){
				configPost.setCartCustomField1Url(config.getValue());
			}else if(config.getTag().equals(WebConfig.CartPrerogative)){
				configPost.setCartPrerogative(config.getValue());
			}else if(config.getTag().equals(WebConfig.priceCuttingPoster)){
				configPost.setPriceCuttingPoster(config.getValue());
			}
			else if(config.getTag().equals(WebConfig.noBirthdayMessagePush)){
				configPost.setNoBirthdayMessagePush(config.getValue());
			}
			
		}
		return configPost;
	}

	@Override
	public List<Banner> getBannerList() {
		return bannerMapper.selectAll();
	}

	@Override
	public List<ProductGroupLink> getProductGroup(String groupName) {
		List<ProductGroupLink> group = null; 
		if(groupName!=null&&!groupName.equals("")){
			group = productGroupLinkMapper.selectByTag(groupName);
		}else{
			group = productGroupLinkMapper.selectAll();
		}
		for(ProductGroupLink item:group){
			ProductDto productDto = productMapper.getProductById(item.getProductId());

			ProductImageLink link = productImageLinkMapper.selectByProductIdLimit(item.getProductId());
			if(link!=null){
				item.setImage(link.getImageUrl());
			}
			ProductGroup productGroup = productGroupMapper.selectByPrimaryKey(item.getGroupId());
			if(productGroup!=null){
				item.setGroupName(productGroup.getGroupName());
			}
		}
		return group;
	}

	@Override
	public boolean editBanner(BannerPost bannerPost) {
		if(bannerPost.getId()!=null){
			Banner banner = bannerMapper.selectByPrimaryKey(bannerPost.getId());
			banner.setImageUrl(bannerPost.getImage());
			banner.setLink(bannerPost.getLink());
			bannerMapper.updateByPrimaryKeySelective(banner);
		}else{
			Banner banner = new Banner();
			banner.setImageUrl(bannerPost.getImage());
			banner.setLink(bannerPost.getLink());
			bannerMapper.insertSelective(banner);
		}
		return true;
	}

	@Override
	public Banner getBanner(Integer id) {
		return bannerMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delBanner(Integer id) {
		return bannerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int delProductGroup(Integer groupId, Integer productId) {
		return productGroupLinkMapper.del(groupId,productId);
	}

	@Override
	public boolean editProductGroup(ProductGroupPost productGroupPost) {
		ProductGroup productGroup = productGroupMapper.selectByGroupName(productGroupPost.getGroupName());
		System.out.println("groupId"+productGroup.getGroupId());
		ProductGroupLink isExisted = productGroupLinkMapper.selectByGroupIdAndProductId(productGroup.getGroupId(), productGroupPost.getProductId());
		if(isExisted!=null){
			return false;
		}
		productGroupLinkMapper.del(productGroupPost.getPreGroupId(),productGroupPost.getPreProductId());
		ProductGroupLink linkTmp = new ProductGroupLink();
		linkTmp.setGroupId(productGroup.getGroupId());
		linkTmp.setProductId(productGroupPost.getProductId());
		//新增时设定该产品的postion值 by chiangpan
//		int maxPosition=productGroupLinkMapper.getMaxPosition(productGroup.getGroupId());
		int maxPosition = 1;
		linkTmp.setPosition(maxPosition);
		productGroupLinkMapper.insertSelective(linkTmp);
		return true;
	}

	@Override
	public String selectStoreInfo() {
		StoreInfo storeInfo = storeInfoMapper.selectOne();
		if(storeInfo!=null){
			return FileUtil.UrlRequest(storeInfo.getDescription());
		}
		return "";
	}

	@Override
	public int updateStoreInfo(String description) {
    	File file = FileUtil.convertToFile(description);
        String url = uploadFile(file);
        StoreInfo tmp = storeInfoMapper.selectByPrimaryKey(1);
        if(tmp!=null){
        	tmp.setDescription(url);
        	storeInfoMapper.updateByPrimaryKeySelective(tmp);
        }else{
        	StoreInfo storeInfo = new StoreInfo();
        	storeInfo.setDescription(url);
        	storeInfoMapper.insertSelective(storeInfo);
        }
        file.delete();
		return 0;
	}
	public String uploadFile(File file){
		String url = null;
		url = fDFSFileUpload.getFileId(file);
		return url;
	}

	@Override
	public boolean sendBirthMsg(Integer userId,boolean sendVoucher) {
		OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
		if(login!=null){
			String nickName="";
			UserProfile profile = userProfileMapper.selectByUserId(userId);
			if(profile!=null){
				nickName = profile.getNickName();
			}
			String[] key = {"keyword1","keyword2","keyword3"};
			String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"生日提醒"};
			Config config = configMapper.getByTag("birthTmpId");
			Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
			Config config2 = configMapper.getByTag(WebConfig.domain);
			Config config3 = configMapper.getByTag(WebConfig.birthText);
			if(config!=null){
				//EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew
				sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"?merchantCode="+config1.getValue(), config3.getValue(), key,value, "");
				MsgRecord msgRecord = new MsgRecord();
				msgRecord.setType(1);
				msgRecord.setUserId(userId);
				msgRecordMapper.insertSelective(msgRecord);
			}
			return true;
		}
		return false;
	}
	@Override
	public boolean sendUnbuyMsg(Integer userId, boolean sendVoucher) {
		OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
		if(login!=null){
			String nickName="";
			UserProfile profile = userProfileMapper.selectByUserId(userId);
			if(profile!=null){
				nickName = profile.getNickName();
			}
			String[] key = {"keyword1","keyword2","keyword3"};
			String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"消费提醒"};
			Config config = configMapper.getByTag("buyTmpId");
			Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
			Config config2 = configMapper.getByTag(WebConfig.domain);
			Config config3 = configMapper.getByTag(WebConfig.salesText);
			if(config!=null){
				//EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew
				sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"?merchantCode="+config1.getValue(), config3.getValue(), key,value, "");
				MsgRecord msgRecord = new MsgRecord();
				msgRecord.setType(2);
				msgRecord.setUserId(userId);
				msgRecordMapper.insertSelective(msgRecord);
			}
		
			return true;
		}
		return false;
	}
	@Override
	public boolean sendViphMsg(Integer userId, boolean sendVoucher) {
		
		OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
		if(login!=null){
			String nickName="";
			UserProfile profile = userProfileMapper.selectByUserId(userId);
			if(profile!=null){
				nickName = profile.getNickName();
			}
			String[] key = {"keyword1","keyword2","keyword3"};
			String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"优惠券派送提醒"};
			//使用生日短信模板
			Config config = configMapper.getByTag("birthTmpId");
			Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
			Config config2 = configMapper.getByTag(WebConfig.domain);
			Config config3 = configMapper.getByTag(WebConfig.voucherSendInformation);
			if(config!=null){
				sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"/coupon?merchantCode="+config1.getValue(), config3.getValue(), key,value, "");
				MsgRecord msgRecord = new MsgRecord();
				msgRecord.setType(1);
				msgRecord.setUserId(userId);
				msgRecordMapper.insertSelective(msgRecord);
			}
			return true;
		}
	
		return false;
	}
	
	public boolean sendCoupon(String vouchersCode,String GoodsCode,String oauthId, String typeText) {
			int n=hongShiMapper.saleVoucher(vouchersCode,GoodsCode, oauthId, typeText);
			return true;
	}
	
	@Override
	public boolean sendBargainhMsg(Integer userId) {
		OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
		if(login!=null){
			String nickName="";
			UserProfile profile = userProfileMapper.selectByUserId(userId);
			if(profile!=null){
				nickName = profile.getNickName();
			}
			String[] key = {"keyword1","keyword2","keyword3"};
			String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"砍价活动完成通知"};
			Config config = configMapper.getByTag("birthTmpId");
			Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
			Config config2 = configMapper.getByTag(WebConfig.domain);
//			Config config3 = configMapper.getByTag(WebConfig.VoucherSendInformation);
			if(config!=null){
				//EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew
				sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"/order-list?merchantCode="+config1.getValue(), "恭喜你，砍价完成通知", key,value, "");
				MsgRecord msgRecord = new MsgRecord();
				msgRecord.setType(1);
				msgRecord.setUserId(userId);
				msgRecordMapper.insertSelective(msgRecord);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean sendLaunchBargainhMsg(Integer userId) {
		OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
		if(login!=null){
			String nickName="";
			UserProfile profile = userProfileMapper.selectByUserId(userId);
			if(profile!=null){
				nickName = profile.getNickName();
			}
			String[] key = {"keyword1","keyword2","keyword3"};
			String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"砍价活动--发起成功通知"};
			Config config = configMapper.getByTag("birthTmpId");
			Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
			Config config2 = configMapper.getByTag(WebConfig.domain);
//			Config config3 = configMapper.getByTag(WebConfig.VoucherSendInformation);
			if(config!=null){
				//EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew
				sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"/bargain?merchantCode="+config1.getValue(), "恭喜你，砍价活动发起成功", key,value, "");
				MsgRecord msgRecord = new MsgRecord();
				msgRecord.setType(1);
				msgRecord.setUserId(userId);
				msgRecordMapper.insertSelective(msgRecord);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean sendSucessMsg(Integer userId) {
		OauthLogin login = oauthLoginMapper.getOauthLoginInfoByUserId(userId);
		if(login!=null){
			String nickName="";
			UserProfile profile = userProfileMapper.selectByUserId(userId);
			if(profile!=null){
				nickName = profile.getNickName();
			}
			String[] key = {"keyword1","keyword2","keyword3"};
			String[] value = {nickName,DateUtils.format(new Date(), DateUtils.FORMAT_LONG).toString(),"砍价成功通知"};
			Config config = configMapper.getByTag("birthTmpId");
			Config config1 = configMapper.getByTag(WebConfig.hsMerchatCode);
			Config config2 = configMapper.getByTag(WebConfig.domain);
//			Config config3 = configMapper.getByTag(WebConfig.VoucherSendInformation);
			if(config!=null){
				//EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew
				sendWXMessage(login.getOauthId(), config.getValue(), config2.getValue()+"/bargain?merchantCode="+config1.getValue(), "恭喜你，砍价完成，马上点击下单吧", key,value, "");
				MsgRecord msgRecord = new MsgRecord();
				msgRecord.setType(1);
				msgRecord.setUserId(userId);
				msgRecordMapper.insertSelective(msgRecord);
			}
			return true;
		}
		return false;
	}


	public String sendWXMessage(String openId,String templateId,String url, String firstData,String[] key,String[] value,String remarkData) {
		Map<String,Object> sendData = new LinkedHashMap<String,Object>();
		sendData.put("touser", openId);
		sendData.put("template_id", templateId);
		sendData.put("topcolor", "#FF0000");
		sendData.put("url", url);
		Map<String,Object> mainData = new TreeMap<String,Object>();
		
		Map<String,Object> mapFirst = new TreeMap<String,Object>();
		mapFirst.put("value", firstData);
		mainData.put("first", mapFirst);
		
		for(int i=0;i<key.length;i++){
			Map<String,Object> Keyword = new TreeMap<String,Object>();
			Keyword.put("value", value[i]);
			mainData.put(key[i], Keyword);
		}
		
		Map<String,Object> mapRemark = new TreeMap<String,Object>();
		mapRemark.put("value", remarkData);
		mapRemark.put("color", "#173177");
		mainData.put("remark", mapRemark);
		
		sendData.put("data", mainData);
		try {
        Var var = varMapper.selectByPrimaryKey(new Integer(1));
        URL urlPost = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + var.getValue());// 创建连接  
        HttpURLConnection connection = (HttpURLConnection) urlPost  
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
        out.append(JSON.toJSONString(sendData));  
        out.flush();  
        out.close();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
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
		return null;
	}

	@Override
	public boolean delStore(Integer storeId) {
		napaStoreMapper.deleteByPrimaryKey(storeId);
		specificationValueStoreLinkMapper.delByStoreId(storeId);
		return true;
	}

	@Override
	public boolean editQiuckNavi(HomeQuickNavi homeQuickNavi) {
		if(homeQuickNavi.getNaviId()!=null){
			HomeQuickNavi tmp = homeQuickNaviMapper.selectByPrimaryKey(homeQuickNavi.getNaviId());
			tmp.setImageUrl(homeQuickNavi.getImageUrl());
			tmp.setTitle(homeQuickNavi.getTitle());
			homeQuickNaviMapper.updateByPrimaryKeySelective(tmp);
		}else{
			HomeQuickNavi tmp = new HomeQuickNavi();
			tmp.setImageUrl(homeQuickNavi.getImageUrl());
			tmp.setTitle(homeQuickNavi.getTitle());
			homeQuickNaviMapper.insertSelective(tmp);
		}
		return true;
	}

	@Override
	public HomeQuickNavi getQuickNavi(Integer naviId) {
		return homeQuickNaviMapper.selectByPrimaryKey(naviId);
	}

	@Override
	public List<HomeQuickNavi> getQuickNaviList() {
		return homeQuickNaviMapper.selectAll();
	}

	@Override
	public int delQiuckNavi(Integer naviId) {
		return homeQuickNaviMapper.deleteByPrimaryKey(naviId);
	}

	@Override
	public boolean editQuickNaviProduct(QuickNaviProductPost quickNaviProductPost) {
		System.out.println(JSON.toJSONString(quickNaviProductPost));
		for(Integer productId:quickNaviProductPost.getProductIds()){
			if(quickNaviProductLinkMapper.selectByNaviIdAndProductId(quickNaviProductPost.getNaviId(),productId)==null){
				QuickNaviProductLink link = new QuickNaviProductLink();
				link.setProductId(productId);
				link.setNaviId(quickNaviProductPost.getNaviId());
				quickNaviProductLinkMapper.insertSelective(link);
			}
		}
		return true;
	}

	@Override
	public List<ProductDto> selectQuickNaviProduct(Integer naviId) {
		List<ProductDto> product = productMapper.selectQuickNaviProduct(naviId);
		for(ProductDto item:product){
			ProductImageLink link = productImageLinkMapper.selectByProductIdLimit(item.getProductId());
			if(link!=null){
				item.setImage(link.getImageUrl());
			}
		}
		return product;
	}

	@Override
	public int delQuickNaviProduct(Integer naviId, Integer productId) {
		return quickNaviProductLinkMapper.deleteByNIdAndPId(naviId,productId);
	}

	@Override
	public String getHongShiStoreName(String hsCode) {
		List<NapaStore> napaStores = napaStoreMapper.selectByHsCode(hsCode);
		if(napaStores!=null&&napaStores.size()>=1){
			return napaStores.get(0).getStoreName();
		}
		return null;
	}
	@Override
	public NapaStore getHongShiStore(String hsCode) {
		List<NapaStore> napaStores = napaStoreMapper.selectByHsCode(hsCode);
		if(napaStores!=null&&napaStores.size()>=1){
			return napaStores.get(0);
		}
		return null;
	}

	@Override
	public boolean delComment(Integer id) {
		Comment comment = commentMapper.selectByPrimaryKey(id);
		if(comment!=null){
			comment.setBackTitle("");
			comment.setBackTime(null);
			return commentMapper.updateByPrimaryKeySelective(comment)>0;
		}
		return false;
	}

	@Override
	public Map<String,Object> isVoucherLimit(Integer amount) {
		Map<String,Object> ret = new TreeMap<String,Object>();
		if(amount==null){
			amount=1;
		}
		try {
			List<BirthVoucher> birthVouchers = birthVoucherMapper.selectAll();
			for(BirthVoucher birthVoucher:birthVouchers) {
				List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(birthVoucher.getVoucherCode());
				if(coupon!=null&&coupon.size()<(birthVoucher.getAmount()*amount)){
					ret.put("result",false);
					if(coupon.size()>0) {
						ret.put("text", "券（商品券号：" + coupon.get(0).getGoodsCode() + "），剩余张数为" + coupon.size() + "还差" + (birthVoucher.getAmount() * amount-coupon.size()) + "张");
					}else{
						ret.put("text", "券（商品券号：" + birthVoucher.getVoucherCode() + "），剩余张数为0张" + "还差" + (birthVoucher.getAmount() * amount) + "张");
					}
					return ret;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			ret.put("result",false);
			return ret;
		}
		ret.put("result",true);
		return ret;
	}
	
	@Override
	public Map<String,Object> isCouponAmount(Integer amount) {
		Map<String,Object> ret = new TreeMap<String,Object>();
		if(amount==null){
			amount=1;
		}
		try {
			List<VipVoucher> vipVouchers = hongShiVipMapper.selectAll();
			for(VipVoucher vipVoucher:vipVouchers) {
				List<HongShiCoupon> coupon = hongShiMapper.getHongShiCouponByGoodsCode(vipVoucher.getVoucher());
				if(coupon!=null&&coupon.size()<(vipVoucher.getAmount()*amount)){
					ret.put("result",false);
					if(coupon.size()>0) {
						ret.put("text", "券（商品券号：" + coupon.get(0).getGoodsCode() + "），剩余张数为" + coupon.size() + "还差" + (vipVoucher.getAmount() * amount-coupon.size()) + "张");
					}else{
						ret.put("text", "券（商品券号：" + vipVoucher.getVoucher() + "），剩余张数为0张" + "还差" + (vipVoucher.getAmount() * amount) + "张");
					}
					return ret;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			ret.put("result",false);
			return ret;
		}
		ret.put("result",true);
		return ret;
	}

	@Override
	public List<ProductDto> selectAllProductByCatId(Integer categoryId) {
		List<ProductDto> product =  productMapper.getAllProductByCatId(categoryId);
		for(ProductDto item:product){
			ProductImageLink link = productImageLinkMapper.selectByProductIdLimit(item.getProductId());
			if(link!=null){
				item.setImage(link.getImageUrl());
			}
			Category category = categoryMapper.selectByPId(item.getProductId());
			if(category!=null){
				item.setCategory(category.getCategory());
			}
		}
		return product;
	}

	@Override
	public List<Category> getCategoryList() {
		return categoryMapper.selectAll();
	}

	@Override
	public Map<String,Object> delCategory(Integer categoryId) {
		Map<String,Object> ret = new TreeMap<String,Object>();
		List<ProductCategoryLinkKey> productCategoryLinkKeys = productCategoryLinkMapper.selectByCId(categoryId);
		if(productCategoryLinkKeys!=null&&productCategoryLinkKeys.size()>0){
			ret.put("result",false);
			ret.put("reason","不可删除已经有下属产品的类别");
			return ret;
		}
		if(categoryMapper.deleteByPrimaryKey(categoryId)>0){
			ret.put("result",true);
		}else{
			ret.put("result",false);
			ret.put("reason","网络繁忙");
		}
		return ret;
	}

	@Override
	public Map<String,Object> editCategory(Category category) {
		Map<String,Object> ret = new TreeMap<String,Object>();
		if(category.getCategoryId()!=null){
			Category tmp = categoryMapper.selectByPrimaryKey(category.getCategoryId());
			Category tmp2 = categoryMapper.selectByName(category.getCategory());
			List<Category> tmp3 = categoryMapper.selectBybatchDiscount(category.getCategory());
			List<ProductGe> productguige = productMapper.selectProductsCategories(category.getCategoryId());
			
			if(productguige!=null){
				for(int i=0;i<productguige.size();i++){
					SpecificationValue Value = specificationValueMapper.selectByPrimaryKey(productguige.get(i).getValueId());
				    System.out.println("在售价============"+ Value.getHsGoodsPrice());
				    BigDecimal prePrice = Value.getHsGoodsPrice().multiply(category.getBatchDiscount());	
					Value.setPromotionPrice(prePrice);
					Value.setStartTimeStr(category.getStartTimeStrs());
					Value.setEndTimeStr(category.getEndTimeStrs());
					
					specificationValueMapper.updateGuiGe(Value);
					
				}
			}
			
			if(tmp2!=null&&tmp2.getCategoryId()!=category.getCategoryId()){
				ret.put("result",false);
				ret.put("reason","该类别已经存在，不可重复添加");
				return ret;
			}
			if(tmp!=null){
				tmp.setCategory(category.getCategory());
				if(categoryMapper.updateByPrimaryKeySelective(category)>0){
					ret.put("result",true);
				}else{
					ret.put("result",false);
					ret.put("reason","网络繁忙");
				}
				return ret;
			}else{
				ret.put("result",false);
				ret.put("reason","网络繁忙");
				return ret;
			}
		}else{
			if(category.getCategory()!=null){
				category.setParentId(0);
				Category tmp2 = categoryMapper.selectByName(category.getCategory());
				if(tmp2!=null){
					ret.put("result",false);
					ret.put("reason","该类别已经存在，不可重复添加");
					return ret;
				}
				System.out.println("9999===="+JSON.toJSONString(category));
				if(categoryMapper.insert(category)>0){
					ret.put("result",true);
				}else{
					ret.put("result",false);
					ret.put("reason","网络繁忙");
				}
				return ret;
			}
		}
		return ret;
	}

	@Override
	public Category getCategoryById(Integer categoryId) {
		return categoryMapper.selectByPrimaryKey(categoryId);
	}
	
	@Override
	public List<Comment> getCommentList(Integer pn) {
		List<Comment> comments = commentMapper.selectAll(pn);
		for (Comment comment:comments){
			Order order  = orderMapper.getOrderListByOrderSerailNum(comment.getOrderSerialNum());
			comment.setOrder(order);
			UserProfile userProfile = userProfileMapper.selectByUserId(comment.getUserId());
			comment.setUserProfile(userProfile);
			comment.setTimeStr(DateUtils.format(comment.getTime(),DateUtils.FORMAT_LONG));
			comment.setBackTimeStr(DateUtils.format(comment.getBackTime(),DateUtils.FORMAT_LONG));
		}
		return comments;
	}

	@Override
	public Map<String,Object> commentBackHandler(Comment comment) {
		Map<String,Object> ret = new TreeMap<String,Object>();
		if(comment.getId()==null||comment.getBackTitle()==null){
			ret.put("result",false);
			ret.put("reason","参数错误");
		}
		Comment tmp = commentMapper.selectByPrimaryKey(comment.getId());
		if(tmp!=null){
			tmp.setBackTime(new Date());
			tmp.setBackTitle(comment.getBackTitle());
			if(commentMapper.updateByPrimaryKeySelective(tmp)>0){
				ret.put("result",true);
			}else{
				ret.put("result",false);
				ret.put("reason","网络繁忙，请稍后重试");
			}
		}else{
			ret.put("result",false);
			ret.put("reason","参数错误");
		}
		return ret;
	}

	@Override
	public boolean updateRechargeConfigNew(RechargeConfig rechargeConfig) {
		if(rechargeConfig.getStartTimeStr()!=null){
			rechargeConfig.setStartTime(DateUtils.parse(rechargeConfig.getStartTimeStr(),DateUtils.FORMAT_LONG));
		}
		if(rechargeConfig.getEndTimeStr()!=null){
			rechargeConfig.setEndTime(DateUtils.parse(rechargeConfig.getEndTimeStr(),DateUtils.FORMAT_LONG));
		}
		if(rechargeConfig.getId()!=null){
			return rechargeConfigMapper.updateByPrimaryKeySelective(rechargeConfig)>0;
		}else{
			return rechargeConfigMapper.insertSelective(rechargeConfig)>0;
		}
	}

	@Override
	public List<RechargeConfig> getRechargeConfigList() {
		List<RechargeConfig> rechargeConfigs = rechargeConfigMapper.selectAll();
		for(RechargeConfig item:rechargeConfigs){
			item.setEndTimeStr(DateUtils.format(item.getEndTime(),DateUtils.FORMAT_LONG));
			item.setStartTimeStr(DateUtils.format(item.getStartTime(),DateUtils.FORMAT_LONG));
		}
		return rechargeConfigs;
	}

	@Override
	public boolean delRechargeConfig(Integer id) {
		return rechargeConfigMapper.deleteByPrimaryKey(id)>0;
	}

	@Override
	public List<BirthVoucher> selectAllBirthVoucher() {
		return birthVoucherMapper.selectAll();
	}
	
	@Override
	public List<VipVoucher> selectAllVipVoucher() {
		return hongShiVipMapper.selectAll();
	}
	
	@Override
	public List<OrderSettingPick> selectAllOrderSettingPick() {
		return orderSettingPickMapper.selectAll();
	}

	@Override
	public boolean updateOrderSettingPick(OrderSettingPick  orderSettingPick) {
		int delAll =orderSettingPickMapper.deleteAll();
		orderSettingPick.setCloseStartDate(DateUtils.parse(orderSettingPick.getCloseStartDateStr(), DateUtils.FORMAT_SHORT));
		orderSettingPick.setCloseEndDate(DateUtils.parse(orderSettingPick.getCloseEndDateStr(), DateUtils.FORMAT_SHORT));
		orderSettingPickMapper.insertSelective(orderSettingPick);

		return true;

	}

	@Override
	public int updateProductGroupPosition(Integer groupId, Integer productId, Integer position) {
		return productGroupLinkMapper.updateProductGroupPosition(groupId,productId,position);
	}
	@Override
	public List<ProductParameters> getParameters(Integer productId) {
		return productMapper.getParameters(productId);
	}

	@Override
	public List<AuditRefundDto> getRefundOrderList(String orderSerialNum) {
		return refundOrderMapper.getRefundOrderList(orderSerialNum);

	}

	@Override
	public Order getOrderBySeialNum(String orderSerialNum) {
		Order order=orderMapper.getOrderListByOrderSerailNum(orderSerialNum);
		return order;
	}
	
	@Override
	public List<Category> selectBybatchDiscount(String category) {
		return  categoryMapper.selectBybatchDiscount(category);
	}
	
	@Override
	public List<UserProfile> selectAllVipList() {
		return userProfileMapper.selectAllVipList();
	}
	@Override
	public Boolean getAccount(String account,String password) {
		List<Account> Account = accountMapper.getAccount(account);
		if(Account!=null&&Account.size()>0){
			List<Account> Password = accountMapper.getPassword(password);
				if(Password!=null&&Password.size()>0){
					return true;
				}
		}
		return false;
	}

	//插入groupName
	@Override
	public int insertGroupName(ProductGroup productGroup) {	
		productGroup.setTag("hotProduct");
		productGroup.getDisplayType();
		productGroup.setIsActive(true);
		return productGroupMapper.insert(productGroup);
	}
	//更新groupName
	@Override
	public int updateGroupName(ProductGroup productGroup) {
		return productGroupMapper.updateByPrimaryKey(productGroup);

	}
	//查询groupName
	@Override
	public List<ProductGroup> selectAll() {
		return productGroupMapper.selectAll();
	}
	
	@Override
	public Map<String,Object> deleteGroupName(Integer groupId) {
    Map<String,Object> ret = new TreeMap<String,Object>();	
    if(productGroupMapper.deleteByPrimaryKey(groupId)>0){
		ret.put("result",true);
	}else{
		ret.put("result",false);
		ret.put("reason","网络繁忙");
	}
	return ret;
}
	@Override
	public List<BargainSetting> selectBargain() {
		List<BargainSetting> bargainList = bargainSettingMapper.selectBargain();
		return bargainList;
	}
	
	@Override
	public BargainSetting selectBargainId(Integer id) {
		return bargainSettingMapper.selectBargainId(id);
	}
	
	@Override
	public List<RechargeConfig> selectMonPeiZhi() {
		List<RechargeConfig> configs = rechargeConfigMapper.selectMonPeiZhi();
		return configs;
	}
	
	@Override
	public BirthPush selectDay() {
		BirthPush birthPush = birthVoucherMapper.selectDay();
		return birthPush;
	}
	
	@Override
	public int insert(ProductVoucher productVoucher) {
		return productVoucherMapper.insert(productVoucher);
	}
	
	@Override
	public List<ProductVoucher> selectAllProductVoucher() {
		//获取所有购买商品赠送礼券设置
		return productVoucherMapper.selectAllProductVoucher();
	}
	
	@Override
	public ProductVoucher selectByPrimaryKey(Integer id) {
		return productVoucherMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(ProductVoucher productVoucher) {
		return productVoucherMapper.updateByPrimaryKey(productVoucher);
	}
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		productVoucherMapper.deleteByLinks(id);//删除产品与赠送券关联
		return productVoucherMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insertCouponsProductsLinks(ProductVoucherPost productVoucherPost) {
		return productVoucherMapper.insertCouponsProductsLinks(productVoucherPost);
	}
	
	@Override
	public ProductVoucherPost selectInspectionAlreadyExists(Integer vid, Integer pid) {
		return productVoucherMapper.selectInspectionAlreadyExists(vid, pid);
	}
	
	@Override
	public List<ProductVoucherPost> selectSelectedProducts(Integer vid) {
		return productVoucherMapper.selectSelectedProducts(vid);
	}
	
	@Override
	public int delCouponsProductsLinks(Integer vid, Integer pid) {
		return productVoucherMapper.delCouponsProductsLinks(vid, pid);
	}
	@Override
	public Double selectPageNum() {
		return commentMapper.selectPageNum();
	}
	@Override
	public List<SendCoupon> selectAllSendCoupon() {
		return sendCouponMapper.selectOne();
	}
	
	@Override
	public boolean updateSendCoupon(FreightPost freightPost) {
		int delAll = sendCouponMapper.deleteAll();
		if(freightPost.getMyKey()==null||freightPost.getMyValue()==null||freightPost.getMyKey().size()==0||freightPost.getMyValue().size()==0||freightPost.getMyValue1()==null||freightPost.getMyValue1().size()==0){
			return false;
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			if(entry.getValue()==null||freightPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, Double> entry : freightPost.getMyKey().entrySet()){
			SendCoupon sendCoupon = new SendCoupon();
			sendCoupon.setMoney(new BigDecimal(entry.getValue().intValue()));
			sendCoupon.setVoucher(freightPost.getMyValue().get(entry.getKey()));
			sendCoupon.setAmount(Integer.parseInt(freightPost.getMyValue1().get(entry.getKey())));
			sendCouponMapper.insertSelective(sendCoupon);
		}
		return true;
	}
	@Override
	public boolean updateLinkCoupon(VipVoucherPost vipVoucherPost) {
		linkCouponMapper.deleteAll();
		if(vipVoucherPost.getMyKey()==null||vipVoucherPost.getMyValue()==null||vipVoucherPost.getMyKey().size()==0||vipVoucherPost.getMyValue().size()==0||vipVoucherPost.getMyValue1()==null||vipVoucherPost.getMyValue1().size()==0){
			return false;
			
		}
		
		for(Map.Entry<Integer, String> entry : vipVoucherPost.getMyKey().entrySet()){
			if(entry.getValue()==null||vipVoucherPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, String> entry : vipVoucherPost.getMyKey().entrySet()){
			LinkCoupon linkCoupon = new LinkCoupon();
			linkCoupon.setVoucher(entry.getValue().toString());
			linkCoupon.setAmount(Integer.parseInt(vipVoucherPost.getMyValue().get(entry.getKey())));
			linkCoupon.setName(vipVoucherPost.getMyValue1().get(entry.getKey()));
			linkCouponMapper.insertSelective(linkCoupon);
		}
		return true;
	}
	@Override
	public List<LinkCoupon> selectAllLinkCoupon() {
		return linkCouponMapper.selectOne();
	}
	
	@Override
	public List<ConsumerVoucher> selectAllConsumerVoucher() {
		return consumerVoucherMapper.selectAll();
	}

    @Override
	public boolean updateConsumerVoucher(ConsumerVoucherPost consumerVoucherPost) {
		int delAll  = consumerVoucherMapper.deleteAll();
		if(consumerVoucherPost.getMyKey()==null||consumerVoucherPost.getMyValue()==null||consumerVoucherPost.getMyKey().size()==0||consumerVoucherPost.getMyValue().size()==0){
			return false;
		}
		for(Map.Entry<Integer, String> entry : consumerVoucherPost.getMyKey().entrySet()){
			if(entry.getValue()==null||consumerVoucherPost.getMyValue().get(entry.getKey())==null){
				return false;
			}
		}
		for(Map.Entry<Integer, String> entry : consumerVoucherPost.getMyKey().entrySet()){
			ConsumerVoucher tmp = new ConsumerVoucher();
			tmp.setAmount(Integer.parseInt(consumerVoucherPost.getMyValue().get(entry.getKey())));
			tmp.setVoucherCode(entry.getValue().toString());
			consumerVoucherMapper.insertSelective(tmp);
		}
		return true;
	}
	@Override
	public boolean truncateConsumerVoucherHandler() {
		int delAll = consumerVoucherMapper.deleteAll();	
		return true;
	}
	@Override
	public String CreatWxVip() throws IOException {
		Var var = varMapper.selectByPrimaryKey(new Integer(1));
		int count = 2;
		String res = "";
		String ress = "";
		String res2 = "";
		String filepath = "";
		for(int i=1; i<=count; i++){
			if(i==1){
				//图片路径
				filepath = getConfig().getCartLogo();
			}else{
				//图片路径
				filepath = getConfig().getCartBgUrl();
			}
			
			String path = filepath.substring(filepath.lastIndexOf("/")+1);
			path = "d:/hs/img/"+path;
			DownloadPicFromURL.downloadPicture(filepath, path);
			// 上传文件请求路径
	         String action = "http://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="
	                + var.getValue() + "&type=path";

			 URL url = new URL(action);
		     File file = new File(path);
		     if (!file.exists() || !file.isFile()) {
		         throw new IOException("上传的文件不存在");
		     }
		     HttpURLConnection con = (HttpURLConnection) url.openConnection();
		       con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		       con.setDoInput(true);
		       con.setDoOutput(true);
		       con.setUseCaches(false); // post方式不能使用缓存
		
		       // 设置请求头信息
		       con.setRequestProperty("Connection", "Keep-Alive");
		       con.setRequestProperty("Charset", "UTF-8");
		       // 设置边界
		       String BOUNDARY = "----------" + System.currentTimeMillis();
		       con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
		               + BOUNDARY);
		
		       // 请求正文信息
		       // 第一部分：
		       StringBuilder sb = new StringBuilder();
		       sb.append("--"); // 必须多两道线
		       sb.append(BOUNDARY);
		       sb.append("\r\n");
		       sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
		               + file.getName() + "\"\r\n");
		       sb.append("Content-Type:application/octet-stream\r\n\r\n");
		       byte[] head = null;
			try {
				head = sb.toString().getBytes("utf-8");
			} catch (UnsupportedEncodingException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}
		       // 获得输出流
		       OutputStream out = null;
			try {
				out = new DataOutputStream(con.getOutputStream());
			} catch (IOException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}
		
		       // 输出表头
		       try {
				out.write(head);
			} catch (IOException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}
		       // 文件正文部分
		       // 把文件已流文件的方式 推入到url中
		       DataInputStream in = null;
			try {
				in = new DataInputStream(new FileInputStream(file));
			} catch (FileNotFoundException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
		       int bytes = 0;
		       byte[] bufferOut = new byte[1024];
		       try {
				while ((bytes = in.read(bufferOut)) != -1) {
				       try {
						out.write(bufferOut, 0, bytes);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   }
			} catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
		       try {
				in.close();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		
		       // 结尾部分
		       byte[] foot = null;
			try {
				foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}// 定义最后数据分隔线
		       try {
				out.write(foot);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		       try {
				out.flush();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		       out.close();
		       StringBuffer buffer = new StringBuffer();
		       BufferedReader reader = null;
		
		       try {
		           // 定义BufferedReader输入流来读取URL的响应
		           reader = new BufferedReader(new InputStreamReader(con
		                   .getInputStream()));
		           String line = null;
		           while ((line = reader.readLine()) != null) {
		               buffer.append(line);
		           }
		           res = buffer.toString();
		           System.out.println(res.toString()+"----SKX");
		       } catch (IOException e) {
		           System.out.println("发送POST请求出现异常！" + e);
		           e.printStackTrace();
		           try {
					throw new IOException("数据读取异常");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		       } finally {
		           if (reader != null) {
		               try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
	       if(i == 1){
	    	   ress = res.substring(res.indexOf(":")+1,res.indexOf("}")).trim(); 
	    	   
	       }else if(i == 2) {
	    	   res2 = res.substring(res.indexOf(":")+1,res.indexOf("}")).trim();
	       }
		}
		final String result = ress;
		final String result1 = res2;
		System.out.println("sds==="+result);
		System.out.println("sds==="+result1);
		String jsonc = "{\"card\": {"
    			+ "\"card_type\": \"MEMBER_CARD\","
    			+ "\"member_card\": {"
    			+ "\"background_pic_url\": "+result1+","
    					+ "\"base_info\": {"
    					+ "\"logo_url\": "+result+","
    					+ "\"brand_name\":\""+getConfig().getCartBrandName()+"\","
    					//code_type卡二维码
    					+ "\"code_type\": \"CODE_TYPE_NONE\","
    					+ "\"title\":\""+getConfig().getCartTitle()+"\","
    					//背景色
    					+ "\"color\":"+"\"Color010\""+","
    					+ "\"notice\":\""+getConfig().getCartNotice()+"\","
    					+ "\"service_phone\":\""+getConfig().getCartServicePhone()+"\","
    					+"\"description\":\""+getConfig().getCartDescription()+"\","
    					+ "\"date_info\": {"
    					//有效日期
    					+ "\"type\": "+"\"DATE_TYPE_PERMANENT\""
    					+ "},"
    					+ "\"sku\": {"
    					+ "\"quantity\": "+50000000
    					+"},"
    					//会员卡每人限一张
    					+ "\"get_limit\":"+ 1 +","
    					+ "\"use_custom_code\":"+ false+","
    					+ "\"can_give_friend\":"+ true +","
    					+ "\"location_id_list\": ["
    					+ 123+","
    					+ 12321+"],"
    					+ "\"custom_url_name\": "+"\"消费记录\""+","
    					+ "\"custom_url\": \""+getConfig().getCartCustomUrl()+"\","
    					+ "\"custom_url_sub_title\": "+"\"查看消费记录\""+","
    					+ "\"promotion_url_name\": "+"\"微商城\""+","
    					+ "\"promotion_url\": \""+getConfig().getCartPromotionUrl()+"\","
    					+ "\"need_push_on_view\": "+true+","
    					+ "\"center_title\": "+"\"出示付款码\""+","
//    					+ "\"center_sub_title\": "+"\"查看付款码\""+","
    					+ "\"center_url\": \""+getConfig().getCartCenterUrl()
    					+"\"},"
    					//设置积分
    					+ "\"supply_bonus\": "+true+","
    					//设置跳转外链查看积分详情
    					+ "\"bonus_url\": \""+getConfig().getCartBonusUrl()+"\","
    					+ "\"supply_balance\":"+ false+","
    					+ "\"prerogative\": \""+getConfig().getCartPrerogative()+"\","
    					//会员卡是否自动激活
    					+ "\"auto_activate\": "+true+","
//    					+ "\"custom_field1\": {"
//    					+ "\"name\": "+"\"余额\""+","
//    					+ "\"url\": "+"\"http://www.qq.com\""
//    					+ "},"
    					+ "\"custom_field1\": {"
    					+ "\"name\": "+"\"余额\""+","
    					+ "\"url\": \""+getConfig().getCartCustomField1Url()
    					+ "\"}"
    					+ "}}}";
	       
		System.out.println(jsonc.toString());
		String lines = null;
		try {
		URL urlPost = new URL("https://api.weixin.qq.com/card/create?access_token=" + var.getValue());// 创建连接  
        HttpURLConnection connection = (HttpURLConnection) urlPost  
                .openConnection();  
        connection.setDoOutput(true);  
        connection.setDoInput(true);  
        connection.setUseCaches(false);  
        connection.setInstanceFollowRedirects(true);  
        connection.setRequestMethod("POST"); // 设置请求方式  
        connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
        connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
        connection.connect();  
        OutputStreamWriter out1 = new OutputStreamWriter(  
                connection.getOutputStream(), "UTF-8"); // utf-8编码  
        out1.append(jsonc);  
        out1.flush();  
        out1.close();
        
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        
        StringBuffer res1 = new StringBuffer("");
        while ((lines = reader1.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            res1.append(lines);
        }
        System.err.println(res1);
        reader1.close();
        // 断开连接
        connection.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}	
	
	@Override
	public String yzPost(String code, String redirect_uri) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpClient.Params params = HttpClient.Params.custom()
				.add("client_id", "01e3d14da80e4e77e6") //填写您的client_id
				.add("client_secret", "bbb8efa2c6f017c3e9edee2ca74f2d21") //填写您的client_secret
				.add("grant_type", "authorization_code") //默认值请勿修改
				.add("code",code)
				.add("redirect_uri",redirect_uri)
				.setContentType(ContentType.APPLICATION_FORM_URLENCODED).build();
		String resp = httpClient.post("https://open.youzan.com/oauth/token", params);
		JSONObject jsonObject = JSONObject.parseObject(resp);
		String access_token = jsonObject.getString("access_token");
		Integer expires_in = Integer.parseInt(jsonObject.getString("expires_in"));
		String refresh_token = jsonObject.getString("refresh_token");
		if(access_token!=null){
			YouZanVar youZanVar = new YouZanVar();
			youZanVar.setName("access_token");
			youZanVar.setPlatform("YZ");
			youZanVar.setStorageTime(new Date());
			youZanVar.setValue(access_token);
			youZanVar.setExpiresIn(expires_in);
			youZanVar.setRefreshToken(refresh_token);
			//判断是否已有授权记录
			YouZanVar record = youZanVarMapper.selectByPrimaryKey(new Integer(1));
			if(record!=null){
				youZanVar.setId(new Integer(1));
				youZanVarMapper.updateByPrimaryKey(youZanVar);
			}else{
				youZanVarMapper.insert(youZanVar);
			}
			return "authorize_ok： "+access_token;
		}
		return  "authorize_err";
	}

	@Override
	public List<UserProfile> selectAllVipLists() {
		return userProfileMapper.selectAllVipLists();
	}
	@Override
	public int insert(MarketingEntrance marketingEntrance) {
		
		return marketingEntranceMapper.insert(marketingEntrance);
	}
	@Override
	public List<MarketingEntrance> selectAllMarketingEntrance() {
		return marketingEntranceMapper.selectAllMarketingEntrance();
	}
	@Override
	public MarketingEntrance getMarketingEntrance(Integer id) {
		return marketingEntranceMapper.getMarketingEntrance(id);
	}
	@Override
	public int deleteMarketingEntrance(Integer id) {
		return marketingEntranceMapper.deleteMarketingEntrance(id);
	}
	@Override
	public int updateMarketingEntrance(MarketingEntrance marketingEntrance) {
		System.out.println(JSON.toJSON(marketingEntrance));
		return marketingEntranceMapper.updateMarketingEntrance(marketingEntrance);
	}

}
