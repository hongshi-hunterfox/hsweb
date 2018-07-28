package com.uclee.web.backend.controller;

import com.uclee.datasource.service.DataSourceInfoServiceI;
import com.uclee.fundation.data.mybatis.model.DataSourceInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by super13 on 5/20/17.
 */

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-backend-web")
public class SuperAdminHandler {

    private static final Logger logger = Logger.getLogger(SuperAdminHandler.class);

    @Autowired
    private DataSourceInfoServiceI dataSourceInfoService;


    @RequestMapping("doAddDataSourceInfo")
    public @ResponseBody
    Boolean
    dataSourceList(@RequestBody DataSourceInfo dataSourceInfo,HttpServletRequest request){
        return dataSourceInfoService.addDataSourceInfo(dataSourceInfo);

    }
    @RequestMapping("doUpdateDataSourceInfo")
    public @ResponseBody
    Boolean getDataSourceInfo(@RequestBody DataSourceInfo dataSourceInfo,HttpServletRequest request){
        return dataSourceInfoService.updateDataSourceInfo(dataSourceInfo);

    }





}
