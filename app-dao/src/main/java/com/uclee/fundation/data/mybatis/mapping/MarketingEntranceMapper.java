package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.MarketingEntrance;

public interface MarketingEntranceMapper {
	int insert(MarketingEntrance marketingEntrance);
	List<MarketingEntrance> selectAllMarketingEntrance();
	MarketingEntrance getMarketingEntrance(Integer id);
	int deleteMarketingEntrance(Integer id);
	int updateMarketingEntrance(MarketingEntrance marketingEntrance);
}
