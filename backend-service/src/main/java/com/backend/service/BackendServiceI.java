package com.backend.service;
import java.text.ParseException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.backend.model.ProductForm;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.*;

public interface BackendServiceI {

	boolean updateConfig(ConfigPost configPost);

	List<Freight> selectAllFreight();

	boolean updateFreight(FreightPost freightPost);

	ProductForm getProductForm(Integer productId);

	List<RechargeConfig> selectAllRechargeConfig();
	
	List<RechargeConfig> selectMonPeiZhi();

	boolean updateRechargeConfig(FreightPost freightPost);

	List<LotteryDrawConfig> selectAllLotteryDrawConfig();

	boolean updateLottery(LotteryConfigPost post);

	List<ProductDto> selectAllProduct();

	List<HongShiOrder> getHongShiOrder(Boolean isEnd);

	List<UserProfile> getUserList(Integer pn);
	
	Double selectPageNums();
	
	List<UserProfile> getVipList(Date start,Date end) throws ParseException;

	ConfigPost getConfig();

	List<Banner> getBannerList();

	List<ProductGroupLink> getProductGroup(String groupName);

	boolean editBanner(BannerPost bannerPost);

	Banner getBanner(Integer id);

	int delBanner(Integer id);

	int delProductGroup(Integer groupId, Integer productId);

	boolean editProductGroup(ProductGroupPost productGroupPost);

	String selectStoreInfo();

	int updateStoreInfo(String description);

	List<UserProfile> getUserListForBirth(String start, String end);

	boolean sendBirthMsg(Integer userId, boolean sendVoucher);

	List<UserProfile> getUserListForUnBuy(Integer day);

	boolean sendUnbuyMsg(Integer userId);

	boolean delStore(Integer storeId);
	
    boolean editQiuckNavi(HomeQuickNavi homeQuickNavi);

	HomeQuickNavi getQuickNavi(Integer naviId);

	List<HomeQuickNavi> getQuickNaviList();

	int delQiuckNavi(Integer naviId);

	boolean editQuickNaviProduct(QuickNaviProductPost quickNaviProductPost);

	List<ProductDto> selectQuickNaviProduct(Integer naviId);

	int delQuickNaviProduct(Integer naviId, Integer productId);

    String getHongShiStoreName(String hsCode);

	List<ProductDto> selectAllProductByCatId(Integer categoryId);

	List<Category> getCategoryList();

	Map<String,Object> delCategory(Integer categoryId);

	Map<String,Object> editCategory(Category category);

	Category getCategoryById(Integer categoryId);

    List<Comment> getCommentList();

	Map<String,Object> commentBackHandler(Comment comment);

    boolean updateRechargeConfigNew(RechargeConfig rechargeConfig);

	List<RechargeConfig> getRechargeConfigList();

	boolean delRechargeConfig(Integer id);

	List<BirthVoucher> selectAllBirthVoucher();
	
	List<VipVoucher> selectAllVipVoucher();

	boolean updateBirthVoucher(BirthVoucherPost birthVoucherPost);
	
	boolean updateVipVoucher(VipVoucherPost vipVoucherPost);

    List<ShippingFullCut> selectAllShippingFullCut();

	boolean updateFullCutShipping(FreightPost freightPost);

	List<FullCut> selectAllFullCut();

	boolean updateFullCut(FreightPost freightPost);

	List<BindingRewards> selectAllBindingRewards();
	
	List<EvaluationGifts> selectAllEvaluationGifts();
	List<IntegralInGifts> selectAllIntegralInGifts();

	boolean updateBindingRewards(FreightPost freightPost);

	NapaStore getHongShiStore(String hsCode);

	Map<String,Object> isVoucherLimit(Integer amount);
	
	Map<String,Object> isCouponAmount(Integer amount);

	boolean delComment(Integer id);

	boolean truncateBirthVoucherHandler();

	boolean truncateVipVoucherHandler();
	
    boolean updateActivityConfig(ConfigPost configPost);

	boolean systemConfigHandler(ConfigPost configPost);

	boolean updateEvaluationGifts(FreightPost freightPost);
	boolean updateIntegralInGifts(FreightPost freightPost);

	//add by chiangpan for pickTime
	List<OrderSettingPick> selectAllOrderSettingPick();

	boolean updateOrderSettingPick(OrderSettingPick orderSettingPick);

    //add by chiangpan for sort
	int updateProductGroupPosition(Integer groupId, Integer productId, Integer position);

	List<Category> selectBybatchDiscount(String category);
	
	List<ProductParameters> getParameters(Integer productId);

	//add by chiangpan for operator Audit refund list
	List<AuditRefundDto> getRefundOrderList(String orderSerialNum);

	Order getOrderBySeialNum(String orderSerialNum);
	
	List<UserProfile> selectCardPhoneVips(String cartphone);
	
	List<UserProfile> selectAllVipList();

	boolean sendViphMsg(Integer userId, boolean sendVoucher);

	int insertGroupName(ProductGroup productGroup);
	
	int updateGroupName(ProductGroup productGroup);
	
	List<ProductGroup> selectAll();
	
	Map<String, Object> deleteGroupName(Integer groupId);
	
	boolean sendBargainhMsg(Integer userId);
	
	boolean sendLaunchBargainhMsg(Integer userId);
	
	boolean sendSucessMsg(Integer userId);
	
	Boolean getAccount(String account,String password);
	
	List<BargainSetting> selectBargain();
	
	BargainSetting selectBargainId(Integer id);
	
	BirthPush selectDay();
	
	int insert(ProductVoucher productVoucher);
	
	int updateByPrimaryKey(ProductVoucher productVoucher);
	
	int deleteByPrimaryKey(Integer id);
	
	List<ProductVoucher> selectAllProductVoucher();
	
	ProductVoucher selectByPrimaryKey(Integer id);
	
	int insertCouponsProductsLinks (ProductVoucherPost productVoucherPost);
	
	ProductVoucherPost selectInspectionAlreadyExists(Integer vid, Integer pid);
	
	List<ProductVoucherPost> selectSelectedProducts(Integer vid);
	
	int delCouponsProductsLinks(Integer vid, Integer pid);
}
