package com.uclee.web.user.controller;

import com.uclee.fundation.data.mybatis.model.NapaStore;
import com.uclee.hongshi.service.StoreServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    List<NapaStore> storeList(HttpServletRequest request){

        return storeService.selectAllNapaStore();
    }

}
