package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;
import java.util.Map;

import com.uclee.fundation.data.mybatis.model.Goods;
import com.uclee.fundation.data.mybatis.model.GoodsCart;
import com.uclee.fundation.data.mybatis.model.GoodsCategoriesLink;
import com.uclee.fundation.data.mybatis.model.GoodsSpecifications;
import com.uclee.fundation.data.mybatis.model.GoodsSpecificationsStoreLink;

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
	List<Goods> selectGoodsAndSpecification(Integer id);
	int insertGoodsCart(GoodsCart goodsCart);
	List<GoodsCart> selectGoodsCart(Integer userId);
	int selectIsVip(Integer userId);
}
