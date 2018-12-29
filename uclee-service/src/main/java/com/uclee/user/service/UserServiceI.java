package com.uclee.user.service;
import com.github.pagehelper.PageInfo;
//import com.uclee.fundation.config.links.WebConfig;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.BargainPost;
import com.uclee.fundation.data.web.dto.CartDto;
import com.uclee.fundation.data.web.dto.OrderPost;
import com.uclee.fundation.data.web.dto.ProductDto;
import com.uclee.fundation.data.web.dto.Stock;
import com.uclee.fundation.data.web.dto.StockPost;
import com.uclee.payment.exception.PaymentHandlerException;
import com.uclee.payment.exception.RefundHandlerException;
import com.uclee.user.model.PaymentStrategyResult;
import com.uclee.user.model.RefundStrategyResult;
import com.uclee.user.model.UserForm;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author 
 *
 */
public interface UserServiceI {

	/*
	 * 验证用户密码
	 */
	boolean hasMatchUserPwd(User user, String inputPassword);

	int  regUser(UserForm userForm);
	
	int regUserWithoutPassword(UserForm userForm);
	
	User getUserById(Integer userId);
	
	boolean isExistAccount(String account);
	
	OauthLogin getOauthLoginInfoByOauthId(String oauthId);
	
	int socialRegister(OauthLogin oauthLogin);

	boolean updatePassword(User user);
	
	boolean addChiledUser(UserForm userForm);

	Integer addPhoneUser(String name,String phone);

	List<UserProfile> phoneUserList();
	
	List<UserProfile> getSubUserList(Integer parentInd);
	
	boolean isSettingPassword(Integer userId);
	
	int deleByUserId(Integer userId);
	
	int updateUser(User user);
	int updateLoginUserMsg(UserProfile userProfile, String IP);
	 
	String getIp(HttpServletRequest request);
	 
	User getUserByChild(Integer userId);

	List<Permission> getUserPermission(Integer userId);

	UserProfile getBasicUserProfile(Integer userId);

	String getRandomNum();

	boolean createNewAccount(OauthLogin oauthLogin,String headimgurl);

	List<Province> getAllProvince();

	List<City> getCities(Integer stateId);

	List<Region> getRegions(Integer cityId);

	User getUserBySerialNum(String serialNum);

	String getIpAddr(HttpServletRequest request);

	PaymentStrategyResult getWCPayment(String openId, String paymentSerialNum, BigDecimal bigDecimal, String title) throws PaymentHandlerException;
	
	Map<String, String> WCScan(String string, HttpServletRequest request);

	String getJSSDKAccessToken();

	String getJSSDKTicket(String access_token);
	
	boolean isWC(HttpServletRequest request);

	void updateWXInfo();

	String getWeiXinUserInfo(String token, String openId);

	OauthLogin getOauthLoginInfoByUserId(Integer userId);

	void updateOauthLogin(OauthLogin oauthLogin);

	boolean getIsSubScribe(Integer userId);
	
	FinancialAccount getFinancialAccount(Integer userId);
	
	PageInfo<UserInvitedLink> getInvitation(Integer userId, Integer pageNum, Integer pageSize);
	
	HashSet<Integer> getInvitationId(Integer userId);

	String alipayNotifyHandle(HttpServletRequest request);
	
	boolean updateProfile(Integer userId, UserProfile userProfile);

	List<Payment> selectPaymentForRecharge();

	Payment getPaymentMethodById(Integer paymentId);

	int insertPaymentOrder(PaymentOrder paymentOrder);

	boolean WechatNotifyHandle(String out_trade_no, String transaction_id, String attach);

	boolean paymentSuccessHandler(PaymentOrder paymentOrder, OauthLogin oauthLogin);

	Boolean sendWXMessage(String openId,String templateId,String url ,String firstData,String[] key,String[] value,String remarkData);

	ProductDto getProductDtoById(Integer productId);

	Integer addToCart(Cart cart);

	Product getProductById(Integer productId);

	SpecificationValue getSpecificationValue(Integer productId, Integer specificationValueId);

	List<CartDto> getUserCart(Integer userId, Integer selectedStoreId);

	List<DeliverAddr> getDeliverAddrList(Integer userId);

	String editAddrHandler(DeliverAddr deliverAddr);

	String delAddrHandler(DeliverAddr deliverAddr);

	String setDefaultAddr(DeliverAddr deliverAddr);

	boolean getInvitationHandler(Integer userId, String serialNum);

	List<UserInvitedLink> getInvitation(Integer userId);

	DeliverAddr getDefaultAddrByUserId(Integer userId);

	Map<String, Object> orderHandler(OrderPost orderPost, Integer userId, String OrderSerialNum);

	List<Order> getUnpayOrderListByUserId(Integer userId);

	List<UserInvitedLink> getInvitationList(Integer userId);

	List<Order> getInvitationOrder(Integer userId);

	List<ProductGroup> getTermGroups(String[] tags);

	PageInfo<DeliverAddr> getAddrList(Integer userId, Integer pageNum, Integer pageSize);

	List<CartDto> selectCartByIds(Integer userId, List<CartDto> cart);

	DeliverAddr selectAddrById(Integer addrId);

	List<Payment> selectAllPayment();

	List<Order> selectOrderByPaymentSerialNum(Integer userId, String paymentSerialNum);

	PaymentOrder selectPaymentOrderBySerialNum(String paymentSerialNum);

	HongShiVip getHongShiVip(Integer userId);

	int updatePaymentOrder(PaymentOrder paymentOrder);

	List<HongShiOrder> getHongShiOrder(Integer userId,Boolean isEnd);

	List<HongShiCoupon> selectCouponById(Integer userId);

	Map<String,Object> signInHandler(Integer userId);

	Integer getChoujiangResult(Integer userId);

	String getVipImage(String getcVipCode, Integer userId);

	Map<String, Object> distCenter(Integer userId);

	Balance selectBalanceByUserId(Integer userId);

	PaymentStrategyResult getAlipayForFastPay(PaymentOrder paymentOrder, String title, String string);

	PaymentOrder getPaymentOrderBySerialNum(String paymentSerialNum);

	List<LotteryDrawConfig> getLotteryConfig();

	PaymentStrategyResult memberCardPaymentHandler(PaymentOrder paymentOrder);

	List<Freight> getAllFreightConfig();

	Map<String, Object> lotteryHandler(Integer userId, String configCode);

	Config getLotteryWebConfig();

	Map<String, Object> cardAddHandler(Integer userId,Integer cartId, Integer amount, Integer activityMarkers);

	Map<String, Object> cardDelHandler(Integer userId, Integer cartId);

	Map<String, Object> tranferBalance(Integer userId);

	DeliverAddr getAddrInfo(Integer userId, Integer deliverAddrId);

	List<City> getCitiesByStr(String province);

	List<Region> getRegionsByStr(String city);

	String getVipJbarcode(String oauthId, Integer userId);

	String getStoreAddr(Integer storeId);

	NapaStore getNapaStore(Integer storeId);

	List<Banner> selectAllBanner();

	List<LotteryRecord> getUserLotteryRecord(Integer userId);

	int delOrder(String orderSerialNum);

	Boolean getNapaStoreByPhone(String phone);

	Map<String,String> getWeixinConfig();
	
	Map<String, Object> getShakeRecord();

	boolean shakeHandler(Integer userId);

	List<Payment> selectMemberPayment();

	Map<String, Object> getBossCenter(String phone, String hsCode);
	
	Map<String, Object> getShakePageData();

	Map<String, Object> firstDrawHandler();

	Map<String, Object> secondDrawHandler();

	Map<String, Object> thirdDrawHandler();

	boolean alipayNotifyHandle(String out_trade_no, String transaction_id);

	boolean resetDraw();

	Map<String, String> getAlipayConfig();

	Map<String, String> getSMSConfig();

	Map<String, Object> stockCheck(StockPost stockPost, Integer userId);

	String getAppId(String merchantCode);

    List<HomeQuickNavi> getQuickNavis();

    int getUnpayOrderCountByUserId(Integer userId);

    List<Message> getUnSendMesg();

    Config getConfigByTag(String supportDeliver);

    int getUnCommentCount(Integer userId);

	Map<String, Object> selectMobile(String phone, String hsCode);
	
	Map<String, Object> getVersion(String version);

	Map<String, Object> getMobile(String phone, String hsCode);
	
    //Map<String, Object> getMobJect(String QueryName);
	//根据微商城订单号取得订单的所有信息by chiangpan
	Order getOrderListSerailNum(String outerOrderCode);

	Map<String, Object> getMobJect(String QueryName,String phone,String hsCode);
	List<PaymentOrder> selectForTimer();

	Map<String,String> wxInitiativeCheck(PaymentOrder paymentOrder);
	
	List<HsVip> selecthsVip(String vCode);

	int updateVips(String vCode, HsVip hsVip);
	
	List<HsVip> selectVips(String vNumber);
	
	List<UserProfile> selectAllProfileLists(Integer userId);
	
	List<HongShiVip> selectVip(String cMobileNumber);
	
	List<Lnsurance> getUsers(String oauthId);
	
	ProductParameters obtainParameters(Integer id);
	
	SignRecord selectAccumulation(Integer userId);
	
	SignRecord getAccumulation(Integer userId);

	//退款 by chiangpan
	Map<String,Object> applyRefund(String outerOrderCode, Integer userId);

	RefundOrder selectRefundOrderBySerialNum(String refundSerialNum);

	RefundStrategyResult getWCRefund(String openId, RefundOrder refundOrder) throws RefundHandlerException;

	//获得微信证书配置
	Map<String,String> getWeixinZhengshuConfig();

	//支付宝退款
	RefundStrategyResult getAlipayForRefund(RefundOrder refundOrder);

	int updateRefundOrder(RefundOrder refundOrder);
	//调用存储过程插入到线下表order_trace表
	int insertOrderTrace(Map pramMap);
	
	List<SpecificationValue> selectByHsGoods(Integer valueId);
	
	Cart selectValueId(Integer userId, Integer cartId);
	
	SpecificationValue selectGoods(Integer valueId);
	
	List<BargainSetting> selectBargain();
	
	List<BargainPost> getBargain();
	
	int insertLaunchBargain(LaunchBargain launchBargain);
	
	int insertBargainLog(BargainLog bargainLog);
	
	vipIdentity selectVipIdentity(String oauthId);
	
	BargainSetting selectName(String name);
	
	BargainPost getValue(Integer valueId);
	
	List<WxUser> selectWxUser(String  invitationCode);
	
	BigDecimal selectSumMoney(String  invitationCode);
	
	LaunchBargain selectLaunch(Integer  uid);
	
	LaunchBargain selectLaunchLog(Integer uid, String invitationCode);
	
	List<BargainLog> selectbargainLog(Integer uid, String invitationCode);
	
	BargainSetting getPrice(Integer cartId);
	
	LaunchBargain getbargainRecord(Integer uid);
	
	BargainLog getValueId(Integer id, Integer uid);
	
	List<BargainPost> getBargainList();
	
	int updateLaunchBargain(Integer uid);
	
	BargainPost getBargainOver(Integer id);
	
	List<Order> getOrderListByStatus();
	
	Order selectBySerialNum(String orderSerialNum);
	
	int updateByInvalid(String orderSerialNum);
	
	List<LinkCoupon> selectByPrimaryKey();
	
	int insertLinkCouponLog(LinkCouponLogs record);
	
	List<LinkCouponLogs> selectLinkCoponLog(@Param("name") String name, @Param("oauthId") String oauthId);
	
	List<MarketingEntrance> selectAllMarketingEntrance();
	
	LaunchBargain getLaunchUser(String invitationCode);
	
	List<BargainStatistics> getBargainLog(Integer id);
	
	int removeStock(Stock stock);
	
	Stock selectStock(Integer valueId);
}