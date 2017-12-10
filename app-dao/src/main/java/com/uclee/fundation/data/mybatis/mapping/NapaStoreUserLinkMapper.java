package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.NapaStoreUserLink;

import java.util.List;

public interface NapaStoreUserLinkMapper {
    int insert(NapaStoreUserLink record);

    int insertSelective(NapaStoreUserLink record);

    int deleteByUserId(Integer userId);

    List<Integer> getByUserId(Integer userId);
}