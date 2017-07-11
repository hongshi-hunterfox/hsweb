package com.uclee.fundation.data.mybatis.mapping;

import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uclee.fundation.data.mybatis.model.UserInvitedLink;

public interface UserInvitedLinkMapper {
    int deleteByPrimaryKey(Integer linkId);

    int insert(UserInvitedLink record);

    int insertSelective(UserInvitedLink record);

    UserInvitedLink selectByPrimaryKey(Integer linkId);

    int updateByPrimaryKeySelective(UserInvitedLink record);

    int updateByPrimaryKey(UserInvitedLink record);

	List<UserInvitedLink> selectByUserId(Integer userId);
	
	HashSet<Integer> selectIdsByUserId(@Param("ids")HashSet<Integer> ids);

	UserInvitedLink selectByUserIdAndInvitedId(@Param("userId")Integer userId, @Param("invitedId")Integer invitedId);

	UserInvitedLink selectByInvitedId(Integer userId);

	List<UserInvitedLink> selectByUserIds(@Param("ids")HashSet<Integer> ids);
}