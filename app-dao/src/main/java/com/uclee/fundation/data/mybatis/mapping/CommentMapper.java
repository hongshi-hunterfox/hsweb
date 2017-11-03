package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    Comment selectByOrderId(String orderSerialNum);

    List<Comment> selectAll();
}