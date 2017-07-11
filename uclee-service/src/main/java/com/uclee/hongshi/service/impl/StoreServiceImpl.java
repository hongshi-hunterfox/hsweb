package com.uclee.hongshi.service.impl;

import com.uclee.fundation.data.mybatis.mapping.CityMapper;
import com.uclee.fundation.data.mybatis.mapping.NapaStoreMapper;
import com.uclee.fundation.data.mybatis.mapping.ProvinceMapper;
import com.uclee.fundation.data.mybatis.mapping.RegionMapper;
import com.uclee.fundation.data.mybatis.model.City;
import com.uclee.fundation.data.mybatis.model.NapaStore;
import com.uclee.fundation.data.mybatis.model.Province;
import com.uclee.hongshi.service.StoreServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by super13 on 5/20/17.
 */
@Service
public class StoreServiceImpl implements StoreServiceI {

    @Autowired
    private NapaStoreMapper napaStoreMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private RegionMapper regionMapper;

    @Override
    public boolean addNapaStore(NapaStore store) {
        return napaStoreMapper.insertSelective(store)>0;
    }

    @Override
    public List<NapaStore> selectAllNapaStore() {
    	List<NapaStore> tmp = napaStoreMapper.selectAllNapaStore();
    	HashSet<String> hsCode = new HashSet<String>();
    	List<NapaStore> ret = new ArrayList<NapaStore>();
    	for(NapaStore item : tmp){
    		if(!hsCode.contains(item.getHsCode())){
    			ret.add(item);
    			hsCode.add(item.getHsCode());
    		}
    	}
        return ret;
    }

    @Override
    public List<NapaStore> selectNapaStoreByUserId(Integer userId) {
        return napaStoreMapper.selectByUserId(userId);
    }

    @Override
    public Map<String,Object> selectNapaStoreById(Integer storeId) {
    	Map<String,Object> map = new HashMap<String, Object>();
    	NapaStore store = napaStoreMapper.selectByPrimaryKey(storeId);
    	map.put("napaStore", store);
    	if(store!=null){
    		Province ret = provinceMapper.selectByProvince(store.getProvince());
    		if(ret!=null){
    			map.put("city", cityMapper.selectByProvinceId(ret.getProvinceId()));
    		}
    		City tmp = cityMapper.selectByCity(store.getCity());
    		if(tmp!=null){
    			map.put("region", regionMapper.selectByCityId(tmp.getCityId()));
    		}
    	}
        return  map;
    }

    @Override
    public boolean updateNapaStoreByStoreId(NapaStore store) {
        return napaStoreMapper.updateByPrimaryKeySelective(store)>0;
    }

	@Override
	public NapaStore selectNapaStoreByCode(String hsCode,Integer userId) {
		return napaStoreMapper.selectNapaStoreByCode(hsCode,userId);
	}
}
