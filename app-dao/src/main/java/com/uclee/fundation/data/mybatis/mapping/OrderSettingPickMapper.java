package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.OrderSettingPick;

import java.util.List;

/**
 * Created by jiang on 2018/3/16.
 */
public interface OrderSettingPickMapper {
    List<OrderSettingPick> selectAll();

    int insertSelective(OrderSettingPick record);

    int deleteAll();




}
