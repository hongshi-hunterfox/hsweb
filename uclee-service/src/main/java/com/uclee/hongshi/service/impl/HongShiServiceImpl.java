package com.uclee.hongshi.service.impl;

import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.hongshi.service.HongShiServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by super13 on 6/1/17.
 */
public class HongShiServiceImpl implements HongShiServiceI{

    @Autowired
    private HongShiMapper hongShiMapper;


    @Override
    public List<HongShiProduct> getHongShiProduct() {
        return hongShiMapper.getHongShiProduct();
    }

    @Override
    public List<HongShiStore> getHongShiStore() {
        return hongShiMapper.getHongShiStore();
    }

    @Override
    public List<HongShiCoupon> getHongShiCoupon(String cWeiXinCode) {
        return hongShiMapper.getHongShiCoupon(cWeiXinCode);
    }

    @Override
    public List<HongShiOrderItem> getHongShiOrderItems(Integer id) {
        return null;
    }

    @Override
    public HongShiGoods getHongShiGoods(String code) {
        return null;
    }

    @Override
    public HongShiStore getHongShiStoreById() {
        return null;
    }
}
