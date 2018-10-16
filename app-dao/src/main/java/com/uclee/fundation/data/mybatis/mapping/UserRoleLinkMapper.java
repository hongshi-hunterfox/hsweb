package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.UserRoleLinkKey;

import java.util.List;
import java.util.Map;

public interface UserRoleLinkMapper {
    int deleteByPrimaryKey(UserRoleLinkKey key);

    int insert(UserRoleLinkKey record);

    int insertSelective(UserRoleLinkKey record);
    
    int batchAddUserRoles(List<UserRoleLinkKey> record);
    
    int batachDeleteUserRoles(Map<String,Object> map);
    
    List<UserRoleLinkKey> selectByUserId(Integer userId);

}