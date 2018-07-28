package com.uclee.web.user.controller;

import com.uclee.fundation.config.links.WebConfig;
import com.uclee.fundation.data.mybatis.mapping.ConfigMapper;
import com.uclee.fundation.data.mybatis.model.Config;
import com.uclee.hongshi.service.StoreServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by super13 on 5/20/17.
 */

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-user-web")
public class StoreController {

    private static final Logger logger = Logger.getLogger(StoreController.class);

    @Autowired
    private StoreServiceI storeService;
    @Autowired
    private ConfigMapper configMapper;


    /** 
    * @Title: storeList 
    * @Description: 得到全部加盟店列表
    * @param @param request
    * @param @return    设定文件 
    * @return List<NapaStore>    返回类型 
    * @throws 
    */
    @RequestMapping("storeList")
    public @ResponseBody
    Map<String,Object> storeList(HttpServletRequest request){
        Map<String,Object> ret = new TreeMap<String,Object>();
        ret.put("storeList",storeService.selectAllNapaStore());
        Config config1 = configMapper.getByTag(WebConfig.logoUrl);
        if(config1!=null) {
            ret.put("logoUrl",config1.getValue() );
        }
        Config config2 = configMapper.getByTag(WebConfig.brand);
        if(config2!=null) {
            ret.put("brand",config2.getValue());
        }
        Config config3 = configMapper.getByTag(WebConfig.qq);
        if(config3!=null) {
            ret.put("numbers",config3.getValue());
        }
        return ret;
    }

    @RequestMapping("storeLogo")
    public @ResponseBody
    Map<String,Object> storeLogo(HttpServletRequest request){
        Map<String,Object> ret = new TreeMap<String,Object>();
        Config config1 = configMapper.getByTag(WebConfig.logoUrl);
        if(config1!=null) {
            ret.put("logoUrl",config1.getValue() );
        }
        Config config2 = configMapper.getByTag(WebConfig.signName);
        if(config2!=null) {
            ret.put("signName",config2.getValue());
        }

        return ret;
    }

}
