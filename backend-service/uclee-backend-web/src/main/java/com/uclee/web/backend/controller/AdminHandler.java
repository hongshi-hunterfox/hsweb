package com.uclee.web.backend.controller;

import com.alibaba.fastjson.JSON;
import com.uclee.fundation.data.mybatis.mapping.UserProfileMapper;
import com.uclee.fundation.data.mybatis.model.NapaStore;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.web.dto.StoreDto;
import com.uclee.hongshi.service.HongShiServiceI;
import com.uclee.hongshi.service.StoreServiceI;
import com.uclee.user.service.UserServiceI;
import com.uclee.web.backend.vo.UserVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by super13 on 5/20/17.
 */

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-backend-web")
public class AdminHandler {

    private static final Logger logger = Logger.getLogger(AdminHandler.class);

    @Autowired
    private StoreServiceI storeService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private HongShiServiceI hongShiService;

    @RequestMapping("doAddStore")
    public @ResponseBody
    Map<String,Object> doAddStore(@RequestBody StoreDto store, HttpServletRequest request){

        Map<String,Object> map = new TreeMap<String,Object>();
        HttpSession session = request.getSession();
        logger.info("store:"+JSON.toJSONString(store));
        map.put("result","fail");
        if(store!=null){
        	NapaStore tmp = storeService.selectNapaStoreByCode(store.getHsCode());
        	if(tmp!=null){
        		map.put("reason", "existed");
        		return map;
        	}
            storeService.addNapaStore(store);
            map.put("result","success");
        }

        return map;
    }
    @RequestMapping("doAddPhoneUser")
    public @ResponseBody
    Map<String,Object> doAddPhoneUser(@RequestBody UserVo user, HttpServletRequest request){

        Map<String,Object> map = new TreeMap<String,Object>();
        HttpSession session = request.getSession();
        logger.info("user:"+JSON.toJSONString(user));

        map.put("result","fail");
        if(user!=null){
            UserProfile userProfile = userProfileMapper.selectByName(user.getName());
            if(userProfile!=null){
                map.put("reason","该名称已注册，请重新填写");
                return map;
            }
            Integer b = userService.addPhoneUser(user.getName(), user.getPhone());
            if(b!=null) {
                storeService.updateLink(b,user.getStoreIds());
                map.put("result", "success");
            }else{
                map.put("reason","添加失败！手机已存在");
            }
        }

        return map;
    }
    @RequestMapping("doUpdatePhoneUser")
    public @ResponseBody
    Map<String,Object> doUpdatePhoneUser(@RequestBody UserVo user, HttpServletRequest request){

        Map<String,Object> map = new TreeMap<String,Object>();
        logger.info("user:"+JSON.toJSONString(user));
        map.put("result","fail");
        if(user!=null){
            if(user.getUserId()!=null) {
                UserProfile userProfile = userProfileMapper.selectByName(user.getName());
                if(userProfile!=null&&!userProfile.getUserId().equals(user.getUserId())){
                    map.put("reason","该名称已注册，请重新填写");
                    return map;
                }
                UserProfile up=new UserProfile();
                up.setUserId(user.getUserId());
                up.setName(user.getName());
                up.setPhone(user.getPhone());
                boolean b = userService.updateProfile(user.getUserId(),up);
                if (b) {
                    storeService.updateLink(user.getUserId(),user.getStoreIds());
                    map.put("result", "success");
                }else{
                    map.put("reason","添加失败！手机已存在");
                }
            }
        }

        return map;
    }

    @RequestMapping("doDeletePhoneUser")
    public @ResponseBody
    Map<String,Object> doDeletePhoneUser(Integer userId, HttpServletRequest request){
        Map<String, Object> map=new HashMap<>();
        map.put("result","fail");
        if(userId!=null){
            map.put("result","success");
            userService.deleByUserId(userId);
        }

        return map;
    }
    @RequestMapping("doUpdateStore")
    public @ResponseBody
    Map<String,Object> doUpdateStore(@RequestBody NapaStore store, HttpServletRequest request){

        Map<String,Object> map = new TreeMap<String,Object>();
        HttpSession session = request.getSession();
        logger.info("store:"+JSON.toJSONString(store));
        map.put("result","fail");
        if(store!=null&&store.getStoreId()!=null){
            storeService.updateNapaStoreByStoreId(store);
            map.put("result","success");
        }

        return map;
    }

}
