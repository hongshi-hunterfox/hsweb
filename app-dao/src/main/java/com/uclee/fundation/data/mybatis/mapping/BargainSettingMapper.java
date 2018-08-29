package com.uclee.fundation.data.mybatis.mapping;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.BargainLog;
import com.uclee.fundation.data.mybatis.model.BargainSetting;
import com.uclee.fundation.data.mybatis.model.LaunchBargain;
import com.uclee.fundation.data.mybatis.model.WxUser;
import com.uclee.fundation.data.mybatis.model.vipIdentity;
import com.uclee.fundation.data.web.dto.BargainPost;

public interface BargainSettingMapper {
	int insert(BargainPost record);
	int insertLaunchBargain(LaunchBargain record);
	int updateLaunchBargain(Integer vid);
	int insertBargainLog(BargainLog record);
	int deleteBargainId(Integer id);
	List<BargainSetting> selectBargain();
	List<BargainPost> getBargain();
	List<BargainPost> getBargainList();
	BargainSetting selectBargainId(Integer id);
	int updateBargainId(BargainPost record);
	vipIdentity selectVipIdentity(String oauthId);
	BargainSetting selectName(String name);
	BargainPost getValue(Integer valueId);
	List<WxUser> selectWxUser(String invitationCode);
	BigDecimal selectSumMoney(String invitationCode);
	LaunchBargain selectLaunch(Integer uid);
	LaunchBargain getbargainRecord(Integer uid);
	LaunchBargain selectLaunchLog(@Param("uid")Integer uid, @Param("invitationCode")String invitationCode);
	List<BargainLog> selectbargainLog(@Param("uid")Integer uid, @Param("invitationCode")String invitationCode);
	BargainSetting getPrice(Integer cartId);
	BargainLog getValueId(@Param("id")Integer id, @Param("uid")Integer uid);
	BargainPost getBargainOver(Integer id);
}
