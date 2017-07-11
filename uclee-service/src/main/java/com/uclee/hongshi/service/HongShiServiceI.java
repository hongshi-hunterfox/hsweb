package com.uclee.hongshi.service;

import com.uclee.fundation.data.mybatis.model.*;

import java.util.List;

/**
 * Created by super13 on 6/1/17.
 */
public interface HongShiServiceI {
    List<HongShiProduct> getHongShiProduct();
    List<HongShiStore> getHongShiStore();
    List<HongShiCoupon> getHongShiCoupon(String cWeiXinCode);
    List<HongShiOrderItem> getHongShiOrderItems(Integer id);
    HongShiGoods getHongShiGoods(String code);

    HongShiStore getHongShiStoreById();
}
