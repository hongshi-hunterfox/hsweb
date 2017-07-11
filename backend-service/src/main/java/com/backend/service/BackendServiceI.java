package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.backend.model.ProductForm;
import com.uclee.fundation.data.mybatis.model.Banner;
import com.uclee.fundation.data.mybatis.model.Config;
import com.uclee.fundation.data.mybatis.model.Freight;
import com.uclee.fundation.data.mybatis.model.HongShiOrder;
import com.uclee.fundation.data.mybatis.model.LotteryDrawConfig;
import com.uclee.fundation.data.mybatis.model.ProductGroupLink;
import com.uclee.fundation.data.mybatis.model.RechargeConfig;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.web.dto.BannerPost;
import com.uclee.fundation.data.web.dto.ConfigPost;
import com.uclee.fundation.data.web.dto.FreightPost;
import com.uclee.fundation.data.web.dto.LotteryConfigPost;
import com.uclee.fundation.data.web.dto.ProductDto;
import com.uclee.fundation.data.web.dto.ProductGroupPost;

public interface BackendServiceI {

	boolean updateConfig(ConfigPost configPost);

	List<Freight> selectAllFreight();

	boolean updateFreight(FreightPost freightPost);

	ProductForm getProductForm(Integer productId);

	List<RechargeConfig> selectAllRechargeConfig();

	boolean updateRechargeConfig(FreightPost freightPost);

	List<LotteryDrawConfig> selectAllLotteryDrawConfig();

	boolean updateLottery(LotteryConfigPost post);

	List<ProductDto> selectAllProduct();

	List<HongShiOrder> getHongShiOrder(Boolean isEnd);

	List<UserProfile> getUserList();

	ConfigPost getConfig();

	List<Banner> getBannerList();

	List<ProductGroupLink> getProductGroup(String tag);

	boolean editBanner(BannerPost bannerPost);

	Banner getBanner(Integer id);

	int delBanner(Integer id);

	int delProductGroup(Integer groupId, Integer productId);

	boolean editProductGroup(ProductGroupPost productGroupPost);

	String selectStoreInfo();

	int updateStoreInfo(String description);

	List<UserProfile> getUserListForBirth(Integer day);

	boolean sendBirthMsg(Integer userId);

	List<UserProfile> getUserListForUnBuy(Integer day);

	boolean sendUnbuyMsg(Integer userId);

	boolean delStore(Integer storeId);


}
