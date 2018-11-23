package com.uclee.fundation.data.mybatis.mapping;
import com.uclee.fundation.data.mybatis.model.YouZanVar;

public interface YouZanVarMapper {
	int insert(YouZanVar youZanVar);
	int updateByPrimaryKey(YouZanVar youZanVar);
	YouZanVar selectByPrimaryKey(int id);
}
