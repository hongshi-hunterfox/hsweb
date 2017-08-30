package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.QuickNaviProductLink;
import org.apache.ibatis.annotations.Param;

public interface QuickNaviProductLinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuickNaviProductLink record);

    int insertSelective(QuickNaviProductLink record);

    QuickNaviProductLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuickNaviProductLink record);

    int updateByPrimaryKey(QuickNaviProductLink record);

    QuickNaviProductLink selectByNaviIdAndProductId(@Param("naviId") Integer naviId, @Param("productId")Integer productId);

    int deleteByNIdAndPId(@Param("naviId")Integer naviId, @Param("productId")Integer productId);
}