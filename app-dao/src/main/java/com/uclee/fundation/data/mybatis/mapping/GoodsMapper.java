package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.CreateOrderItemResult;
import com.uclee.fundation.data.mybatis.model.CreateOrderResult;
import com.uclee.fundation.data.mybatis.model.Goods;
import com.uclee.fundation.data.mybatis.model.GoodsCart;
import com.uclee.fundation.data.mybatis.model.GoodsCategoriesLink;
import com.uclee.fundation.data.mybatis.model.GoodsOrderItem;
import com.uclee.fundation.data.mybatis.model.GoodsSpecifications;
import com.uclee.fundation.data.mybatis.model.GoodsSpecificationsStoreLink;
import com.uclee.fundation.data.mybatis.model.HsVip;
import com.uclee.fundation.data.web.dto.GoodsOrder;

public interface GoodsMapper {
	int insert(Goods goods);
	List<Goods> selectAll();
	int insertGoodsAndCategories(GoodsCategoriesLink goodsCategoriesLink);
	int insertGoodsSpecifications(GoodsSpecifications goodsSpecifications);
	int insertGoodsSpecificationsStoreLink(GoodsSpecificationsStoreLink goodsSpecificationsStoreLink);
	List<String> selectByGoodsId(Integer productId);
	Goods selectByPrimaryKey(Integer id);
	GoodsCategoriesLink selectByGoodsAndCategories(Integer id);
	List<GoodsSpecifications> selectByGoodsSpecifications(Integer id);
	int updateGoodsById(Goods goods);
	int updateGoodsAndCategories(GoodsCategoriesLink goodsCategoriesLink);
	int deleteBylink(Integer id);
	int deleteGoods(Integer id);
	List<Goods> selectGoodsList();
	List<Goods> selectGoodsAndCatList(@Param("goodscategory") Integer goodscategory,@Param("storeId")String storeId);
	List<Goods> selectGoodsAndSpecification(Integer id);
	int insertGoodsCart(GoodsCart goodsCart);
	List<GoodsCart> selectGoodsCart(Integer userId);
	int selectIsVip(Integer userId);
	int deleteGoodsCart(Integer id);
	GoodsCart selectIsCart(@Param("userId")Integer userId,@Param("specId")Integer specId,@Param("flavorname") String flavorname);
	GoodsCart selectIsCarts(@Param("userId")Integer userId,@Param("specId")Integer specId);
	int updateGoodsCart(@Param("amount")Integer amount,@Param("id")Integer id);
	HsVip selectVipInfo(Integer userId);
	Integer selectSumCart(Integer userId);
	CreateOrderResult createGoodsOrder(GoodsOrder goodsOrder);
	int deleteUserGoodsCart(Integer userId);
	CreateOrderItemResult createGoodsOrderItem(GoodsOrderItem goodsOrderItem);
	List<GoodsSpecifications> selectByStoreAndGoods();
}
