package com.uclee.hongshi.service.impl;

import com.uclee.fundation.data.mybatis.mapping.*;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.fundation.data.web.dto.ProductDto;
import com.uclee.fundation.data.web.dto.StoreDto;
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
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductStoreLinkMapper productStoreLinkMapper;
    @Autowired
    private NapaStoreUserLinkMapper napaStoreUserLinkMapper;
    @Autowired
    private SpecificationValueMapper specificationValueMapper;

    @Override
    public boolean addNapaStore(StoreDto store) {
        napaStoreMapper.insertSelective(store);
        if(store.getLink()!=null&&store.getLink().equals("true")){
            productStoreLinkMapper.insertAll(store.getStoreId());
        }
        return true;
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
    public List<NapaStore> selectNapaStoreByUserId() {
        return napaStoreMapper.selectAllNapaStore();
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
	public NapaStore selectNapaStoreByCode(String hsCode) {
		return napaStoreMapper.selectNapaStoreByCode(hsCode);
	}

    @Override
    public boolean updateLink(Integer userId, List<Integer> storeIds) {
        napaStoreUserLinkMapper.deleteByUserId(userId);
        for(Integer item:storeIds){
            NapaStoreUserLink link = new NapaStoreUserLink();
            link.setStoreId(item);
            link.setUserId(userId);
            napaStoreUserLinkMapper.insertSelective(link);
        }
        return true;
    }

    @Override
    public List<Integer> getStoreLinkByUserId(Integer userId) {
        return napaStoreUserLinkMapper.getByUserId(userId);
    }
}
