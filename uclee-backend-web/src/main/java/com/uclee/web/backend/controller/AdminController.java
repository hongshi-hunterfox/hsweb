package com.uclee.web.backend.controller;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.data.mybatis.model.*;
import com.uclee.hongshi.service.HongShiServiceI;
import com.uclee.hongshi.service.StoreServiceI;
import com.uclee.user.service.UserServiceI;
import com.uclee.web.backend.vo.UserVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by super13 on 5/20/17.
 */

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-backend-web")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private StoreServiceI storeService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private HongShiServiceI hongShiService;




    @RequestMapping("phoneUserList")
    public @ResponseBody List<UserProfile>
     phoneUserList(HttpServletRequest request){

        HttpSession session = request.getSession();

        return userService.phoneUserList();

    }

    @RequestMapping("getPhoneUser")
    public @ResponseBody UserVo getPhoneUser(Integer userId, HttpServletRequest request){
        UserVo ret=new UserVo();
        UserProfile u = userService.getBasicUserProfile(userId);
        logger.info(JSON.toJSONString(u));
        if(u!=null){
            List<Integer> links = storeService.getStoreLinkByUserId(u.getUserId());
            Map<Integer,Integer> map = new HashMap<Integer,Integer>();
            for(Integer item:links){
                map.put(item,item);
            }
            ret.setIds(map);
            ret.setStoreIds(links);
            ret.setUserId(u.getUserId());
            ret.setName(u.getName());
            ret.setPhone(u.getPhone());
            ret.setStores(storeService.selectAllNapaStore());
        }
        return ret;
    }
    @RequestMapping("getStore")
    public @ResponseBody UserVo getStore(HttpServletRequest request){
        UserVo ret=new UserVo();
        ret.setStores(storeService.selectAllNapaStore());
        return ret;
    }

    @RequestMapping("getHongShiStore")
    public @ResponseBody Map<String,Object> getHongShiStore(){
    	Map<String,Object> map = new TreeMap<String,Object>();
    	List<HongShiStore> stores = hongShiService.getHongShiStore();
    	List<Province> province = userService.getAllProvince();
		map.put("province", province);
		map.put("stores", stores);
        return map;
    }
    @RequestMapping("getNapaStoreById")
    public @ResponseBody Map<String,Object> getNapaStoreById(Integer storeId){
    	System.out.println("store===="+storeId);
         return storeService.selectNapaStoreById(storeId);
    }

    @RequestMapping("napaStoreList")
    public @ResponseBody
    List<NapaStore> napaStoreList(HttpServletRequest request){
        return storeService.selectAllNapaStore();
    }
}
