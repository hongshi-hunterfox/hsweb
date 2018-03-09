package com.uclee.fundation.data.mybatis.mapping;
import com.uclee.fundation.data.mybatis.model.EvaluationGifts;
import java.util.List;
public interface EvaluationGiftsMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(EvaluationGifts record);

  int insertSelective(EvaluationGifts record);

  EvaluationGifts selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(EvaluationGifts record);

  int updateByPrimaryKey(EvaluationGifts record);

  List<EvaluationGifts> selectOne();

  int deleteAll();
}