package com.uclee.fundation.data.mybatis.mapping;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.HsVip;
import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.mybatis.model.UserProfile;
public interface HsVipMapper {
	HsVip updateVip(String vCode);
	int updateVips(HsVip record);
    int updateVip(HsVip record);
	List<HsVip> selecthsVip(String vCode);
	HsVip selectupdateVips(String vCode);
	List<HsVip> selectVips(String vNumber);
	Integer updateRecharge(Integer id);
	HsVip getVips(Integer id);
}
