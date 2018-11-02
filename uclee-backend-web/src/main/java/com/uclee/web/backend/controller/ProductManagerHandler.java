package com.uclee.web.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.backend.model.ProductForm;
import com.backend.model.RoleForm;
import com.backend.model.UserRoleForm;
import com.backend.service.ProductManageServiceI;
import com.backend.service.UserManageServiceI;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uclee.fundation.config.links.GlobalSessionConstant;
import com.uclee.fundation.data.mybatis.model.Permission;
import com.uclee.fundation.data.mybatis.model.Role;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.web.dto.ValuePost;
import com.uclee.model.CookieVelocity;
import com.uclee.page.util.PageUtils;
import com.uclee.user.service.UserProfileServiceI;
import com.uclee.user.service.UserServiceI;
import com.uclee.web.backend.vo.FileVo;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-product-web")
public class ProductManagerHandler {
	
	private static final Logger logger = Logger.getLogger(ProductManagerHandler.class);
	@Autowired
	private ProductManageServiceI productManageService;
	
	@RequestMapping(value = "/doAddProductHandler", method = RequestMethod.POST)
	public @ResponseBody Boolean addProductHandler(@RequestBody ProductForm productForm,HttpSession session) throws Exception {
		return productManageService.addProduct(productForm);
		
	}
	
	@RequestMapping(value = "/delProduct")
	public @ResponseBody Boolean delProduct(Integer productId,HttpSession session) throws Exception {
	
		return productManageService.delProduct(productId);
		
	}
	
	@RequestMapping(value = "/doUpdateProductHandler", method = RequestMethod.POST)
	public @ResponseBody Boolean doUpdateProductHandler(@RequestBody ProductForm productForm,HttpSession session) throws Exception {
		return productManageService.updateProduct(productForm);
		
	}
	
	@RequestMapping(value = "/doUploadImage", method = RequestMethod.POST)
	public @ResponseBody String uploadImage(FileVo fileVo) {
		String url=null;
		if(fileVo.getFile() != null){
			url = productManageService.uploadImage(fileVo.getFile());
			logger.info(url);
    	}
		return url;
	}

}
