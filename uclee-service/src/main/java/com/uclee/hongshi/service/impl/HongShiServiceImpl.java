package com.uclee.hongshi.service.impl;

import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationValueMapper;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.hongshi.service.HongShiServiceI;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by super13 on 6/1/17.
 */
public class HongShiServiceImpl implements HongShiServiceI{

    @Autowired
    private HongShiMapper hongShiMapper;
    @Autowired
    private SpecificationValueMapper specificationValueMapper;


    @Override
    public LinkedList<HongShiProduct> getHongShiProduct(Integer productId) {
        List<HongShiProduct> hongShiProducts = hongShiMapper.getHongShiProduct();
        List<String> hsCodes = specificationValueMapper.selectHsCodeByProductId(productId);
        List<HongShiProduct> notSelected = new ArrayList<HongShiProduct>();
        LinkedList<HongShiProduct> ret = new LinkedList<HongShiProduct>();
        for(HongShiProduct item : hongShiProducts){
            if(hsCodes.contains(item.getCode())){
                ret.add(item);
            }else{
                notSelected.add(item);
            }
        }
        for(HongShiProduct item : notSelected){
            ret.add(item);
        }
        return ret;
    }
    @Override
    public List<HongShiProduct> getHongShiProduct() {
        List<HongShiProduct> hongShiProducts = hongShiMapper.getHongShiProduct();
        return hongShiProducts;
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
