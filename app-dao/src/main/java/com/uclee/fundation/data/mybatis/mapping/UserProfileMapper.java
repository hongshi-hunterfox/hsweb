package com.uclee.fundation.data.mybatis.mapping;

 
import java.util.List;

import com.uclee.fundation.data.mybatis.model.UserProfile;
import org.apache.ibatis.annotations.Param;

public interface UserProfileMapper {
    int deleteByPrimaryKey(Integer profileId);

    int insert(UserProfile record);

    int insertSelective(UserProfile record);

    UserProfile selectByPrimaryKey(Integer profileId);

    int updateByPrimaryKeySelective(UserProfile record);

    int updateByPrimaryKey(UserProfile record);
    
    UserProfile selectByUserProfile(UserProfile userProfile);
    
    List<UserProfile> selectUserByRoleType(Integer roleId);
    
    List<UserProfile>  selectUserListByParentId(Integer userId );

	UserProfile selectByUserId(Integer userId);
	
	List<UserProfile> selectListByUserProfile(UserProfile userProfile);

	List<UserProfile> selectAllProfileList();

	List<UserProfile> getUserListForBirth(@Param("start") String start, @Param("end")String end);

	List<UserProfile> getUserListForUnBuy(Integer day);

    List<UserProfile> selectByName(String name);
}