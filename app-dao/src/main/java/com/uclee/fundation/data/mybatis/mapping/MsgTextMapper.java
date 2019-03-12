package com.uclee.fundation.data.mybatis.mapping;

import java.util.List;

import com.uclee.fundation.data.mybatis.model.MsgText;

public interface MsgTextMapper {
	List<MsgText> selectAll();
	int updateBymsg(MsgText msgtext);
	MsgText selectByPayType(String payType);
}
