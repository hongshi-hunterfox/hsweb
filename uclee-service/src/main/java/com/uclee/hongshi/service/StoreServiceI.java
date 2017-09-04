package com.uclee.hongshi.service;

import com.uclee.fundation.data.mybatis.model.NapaStore;

import java.util.List;
import java.util.Map;

import com.uclee.fundation.data.web.dto.StoreDto;
import org.apache.ibatis.annotations.Param;

/**
 * Created by super13 on 5/20/17.
 */
public interface StoreServiceI {
    boolean addNapaStore(StoreDto store);

    List<NapaStore> selectAllNapaStore();

    List<NapaStore> selectNapaStoreByUserId(Integer userId);


    Map<String,Object> selectNapaStoreById(Integer storeId);


    boolean updateNapaStoreByStoreId(NapaStore store);

	NapaStore selectNapaStoreByCode(String hsCode, Integer userId);
}
