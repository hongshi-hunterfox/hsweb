package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.Comment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    Comment selectByOrderId(String orderSerialNum);

    List<Comment> selectAll(@Param("pn") Integer pn);
    
    Double selectPageNum();
}