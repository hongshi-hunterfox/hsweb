package com.uclee.hongshi.service;

import com.uclee.fundation.data.mybatis.model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by super13 on 6/1/17.
 */
public interface HongShiServiceI {
    LinkedList<HongShiProduct> getHongShiProduct(Integer productId);
    List<HongShiStore> getHongShiStore();
    List<HongShiCoupon> getHongShiCoupon(String cWeiXinCode);
    List<HongShiOrderItem> getHongShiOrderItems(Integer id);
    HongShiGoods getHongShiGoods(String code);
    HongShiStore getHongShiStoreById();
    List<HongShiProduct> getHongShiProduct();
   
}
