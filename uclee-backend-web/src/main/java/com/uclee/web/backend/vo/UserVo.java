package com.uclee.web.backend.vo;

import com.uclee.fundation.data.mybatis.model.NapaStore;

import java.util.List;
import java.util.Map;

/**
 * Created by super13 on 5/31/17.
 */
public class UserVo {
    private Integer userId;

    private String phone;

    private String name;

    private List<Integer> storeIds;

    private Map<Integer,Integer> ids;

    public Map<Integer, Integer> getIds() {
        return ids;
    }

    public void setIds(Map<Integer, Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<Integer> storeIds) {
        this.storeIds = storeIds;
    }

    public List<NapaStore> getStores() {
        return stores;
    }

    public void setStores(List<NapaStore> stores) {
        this.stores = stores;
    }

    private List<NapaStore> stores;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
