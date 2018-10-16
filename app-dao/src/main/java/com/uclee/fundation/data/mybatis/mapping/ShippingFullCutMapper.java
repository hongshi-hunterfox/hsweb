package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.ShippingFullCut;

import java.util.Date;
import java.util.List;

public interface ShippingFullCutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShippingFullCut record);

    int insertSelective(ShippingFullCut record);

    ShippingFullCut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShippingFullCut record);

    int updateByPrimaryKey(ShippingFullCut record);

    List<ShippingFullCut> selectAllShippingFullCut();

    int deleteAll();

    List<ShippingFullCut> selectAllShippingFullCutActive(Date date);
}