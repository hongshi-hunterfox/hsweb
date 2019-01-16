package com.uclee.fundation.data.mybatis.mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.Balance;
import com.uclee.fundation.data.mybatis.model.Category;
import com.uclee.fundation.data.mybatis.model.CreateOrderItemResult;
import com.uclee.fundation.data.mybatis.model.CreateOrderResult;
import com.uclee.fundation.data.mybatis.model.HongShiCommonResult;
import com.uclee.fundation.data.mybatis.model.HongShiCoupon;
import com.uclee.fundation.data.mybatis.model.HongShiCreateOrder;
import com.uclee.fundation.data.mybatis.model.HongShiCreateOrderItem;
import com.uclee.fundation.data.mybatis.model.HongShiGoods;
import com.uclee.fundation.data.mybatis.model.HongShiOrder;
import com.uclee.fundation.data.mybatis.model.HongShiOrderItem;
import com.uclee.fundation.data.mybatis.model.HongShiProduct;
import com.uclee.fundation.data.mybatis.model.HongShiStore;
//import com.uclee.fundation.data.mybatis.model.ProductPromotion;
import com.uclee.fundation.data.mybatis.model.CouponSales;
import com.uclee.fundation.data.web.dto.MobileItem;
import com.uclee.fundation.data.web.dto.BossCenterItem;

public interface HongShiMapper {

	List<HongShiProduct> getHongShiProduct();
	List<HongShiStore> getHongShiStore();
	List<HongShiCoupon> getHongShiCoupon(String cWeiXinCode);
	List<HongShiCoupon> getHongShiCouponByCode(String voucherCode);
	List<HongShiOrder> getHongShiOrder(@Param("cWeiXinCode")String cWeiXinCode,@Param("isEnd")Boolean isEnd);
	HongShiOrder getOrderByOutCode(@Param("code")String code);
	CreateOrderResult createOrder(HongShiCreateOrder createOrder);
	List<HongShiOrderItem> getHongShiOrderItems(Integer id);
	HongShiGoods getHongShiGoods(String code);
	CreateOrderItemResult createOrderItem(HongShiCreateOrderItem createOrderItem);
	HongShiCommonResult signInAddPoint(@Param("oauthId")String oauthId, @Param("point")Integer point,@Param("tag") String tag);
	HongShiCommonResult lotteryPoint(@Param("oauthId")String oauthId, @Param("point")Integer point);
	int recoverVoucher(@Param("goodsCode")String goodsCode,@Param("orderId")Integer orderId,@Param("voucherCode")String voucherCode,@Param("remark")String remark,@Param("amount") BigDecimal amount);
	int saleVoucher(@Param("voucherCode")String voucherCode,@Param("goodsCode")String goodsCode,@Param("oauthId")String oauthId,@Param("SaleType")String SaleType);
	List<HongShiCoupon> getHongShiCouponByGoodsCode(@Param("goodsCode")String goodsCode);
	List<BossCenterItem> selectBossCenter(@Param("hsCode")String hsCode,@Param("userId")Integer userId);
	List<MobileItem>selectMobile(@Param("hsCode")String hsCode,@Param("userId")Integer userId);
	List<Map<String, Object>> getmobJect(@Param("QueryName")String QueryName,@Param("hsCode")String hsCode,@Param("userId")Integer userId);
	HongShiCoupon getCouponName(String productNumber);
	List<Map<String, Object>> getObjectName(@Param("objectName")String objectName);
	List<CouponSales> CouponSales(String salesTime,String serialNumber);
	CouponSales getCouponDenomination(String numbers);
}
