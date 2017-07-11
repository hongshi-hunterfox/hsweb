package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.MsgRecord;

public interface MsgRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MsgRecord record);

    int insertSelective(MsgRecord record);

    MsgRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MsgRecord record);

    int updateByPrimaryKey(MsgRecord record);
}