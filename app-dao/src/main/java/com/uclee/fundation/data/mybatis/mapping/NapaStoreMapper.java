package com.uclee.fundation.data.mybatis.mapping;

import com.uclee.fundation.data.mybatis.model.NapaStore;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NapaStoreMapper {
    int deleteByPrimaryKey(Integer storeId);

    int insert(NapaStore record);

    int insertSelective(NapaStore record);

    NapaStore selectByPrimaryKey(Integer storeId);


    List<NapaStore> selectAllNapaStore ();

    int updateByPrimaryKeySelective(NapaStore record);

    int updateByPrimaryKey(NapaStore record);

	List<NapaStore> selectByValueId(Integer valueId);

    List<NapaStore> selectByUserId(Integer userId);

	List<NapaStore> selectByPhone(String phone);

	NapaStore selectNapaStoreByCode(@Param("hsCode")String hsCode);

    List<NapaStore> selectByHsCode(String hsCode);
}