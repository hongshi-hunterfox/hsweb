package com.uclee.web.backend.controller;

import com.uclee.datasource.service.DataSourceInfoServiceI;
import com.uclee.fundation.data.mybatis.model.DataSourceInfo;
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
@RequestMapping("/uclee-backend-web")
public class SuperAdminController {

    private static final Logger logger = Logger.getLogger(SuperAdminController.class);

    @Autowired
    private DataSourceInfoServiceI dataSourceInfoService;


    @RequestMapping("dataSourceList")
    public @ResponseBody
    List<DataSourceInfo>
    dataSourceList(HttpServletRequest request){
        return dataSourceInfoService.getAllDataSourceInfo();

    }
    @RequestMapping("getDataSourceInfo")
    public @ResponseBody
    DataSourceInfo getDataSourceInfo(Integer id,HttpServletRequest request){
        return dataSourceInfoService.getDataSourceInfoById(id);

    }





}
