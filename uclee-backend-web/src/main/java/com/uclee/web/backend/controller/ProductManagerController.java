package com.uclee.web.backend.controller;

import com.backend.model.ProductForm;
import com.backend.service.BackendServiceI;
import com.uclee.fundation.data.mybatis.mapping.CategoryMapper;
import com.uclee.fundation.data.mybatis.mapping.NapaStoreMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductImageLinkMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductMapper;
import com.uclee.fundation.data.mybatis.model.Category;
import com.uclee.fundation.data.mybatis.model.HongShiProduct;
import com.uclee.fundation.data.mybatis.model.HongShiStore;
import com.uclee.fundation.data.mybatis.model.NapaStore;
import com.uclee.fundation.data.mybatis.model.Product;
import com.uclee.fundation.data.web.dto.ProductDto;
import com.uclee.fundation.oss.OssUtil;
import com.uclee.hongshi.service.HongShiServiceI;
import com.uclee.web.backend.vo.FileVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@EnableAutoConfiguration
@RequestMapping("/uclee-product-web")
public class ProductManagerController {
	
	private static final Logger logger = Logger.getLogger(ProductManagerController.class);
	
	
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private HongShiServiceI hongShiService;
	@Autowired
	private ProductImageLinkMapper productImageLinkMapper;
	@Autowired
	private BackendServiceI backendService;
	@Autowired
	private NapaStoreMapper napaStoreMapper;

	@Autowired
	private OssUtil ossUtil;
	
	@RequestMapping(value="addProduct")
	public @ResponseBody Map<String,Object> addProduct(HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Category> cat = categoryMapper.selectAll();
		map.put("cat", cat);
		List<HongShiProduct> hongShiProduct = hongShiService.getHongShiProduct();
		map.put("hongShiProduct", hongShiProduct);
		List<NapaStore> tmp = napaStoreMapper.selectAllNapaStore();
    	HashSet<String> hsCode = new HashSet<String>();
    	List<NapaStore> ret = new ArrayList<NapaStore>();
    	for(NapaStore item : tmp){
    		if(!hsCode.contains(item.getHsCode())){
    			ret.add(item);
    			hsCode.add(item.getHsCode());
    		}
    	}
		map.put("store", ret);
		return map;
	}
	
	@RequestMapping(value="productList")
	public @ResponseBody Map<String,Object> productList(HttpSession session,Integer productId){
		Map<String,Object> map = new HashMap<String,Object>();
		List<ProductDto> productDto = backendService.selectAllProduct();
		ProductDto product = productMapper.getProductById(productId);
		if(product!=null){
			map.put("text", product.getTitle());
		}
		map.put("products", productDto);
		return map;
	}
	
	@RequestMapping(value="getCategory")
	public @ResponseBody List<Category>  getCategory(ModelMap model,HttpSession session,Integer parentId){
		if(parentId==null){
			parentId = 0;
		}
		List<Category> cat = categoryMapper.selectByParentId(parentId);
		return cat;
	}
	
	@RequestMapping(value="getAddProductData")
	public @ResponseBody Map<String,Object>  getAddProductData(ModelMap model,HttpSession session){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Category> cat = categoryMapper.selectByParentId(0);
		result.put("cat", cat);
		List<HongShiProduct> proudctList = hongShiService.getHongShiProduct();
		result.put("proudctList", proudctList);
		List<HongShiStore> storeList = hongShiService.getHongShiStore();
		result.put("storeList", storeList);
		return result;
	}
	@RequestMapping(value="getUpdateProductData")
	public @ResponseBody Map<String,Object>  getUpdateProductData(ModelMap model,HttpSession session){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Category> cat = categoryMapper.selectByParentId(0);
		result.put("cat", cat);
		List<HongShiProduct> proudctList = hongShiService.getHongShiProduct();
		result.put("proudctList", proudctList);
		List<HongShiStore> storeList = hongShiService.getHongShiStore();
		result.put("storeList", storeList);
		return result;
	}
	
	@RequestMapping(value="updateProduct")
	public @ResponseBody Map<String,Object> updateProduct(HttpSession session,Integer productId){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Category> cat = categoryMapper.selectAll();
		map.put("cat", cat);
		List<HongShiProduct> hongShiProduct = hongShiService.getHongShiProduct();
		map.put("hongShiProduct", hongShiProduct);
		List<NapaStore> tmp = napaStoreMapper.selectAllNapaStore();
    	HashSet<String> hsCode = new HashSet<String>();
    	List<NapaStore> ret = new ArrayList<NapaStore>();
    	for(NapaStore item : tmp){
    		if(!hsCode.contains(item.getHsCode())){
    			ret.add(item);
    			hsCode.add(item.getHsCode());
    		}
    	}
		map.put("store", ret);
		ProductForm productForm = backendService.getProductForm(productId);
		map.put("productForm", productForm);
		return map;
	}


	@RequestMapping(value="doUploadFile")
	public @ResponseBody String doUploadFile(FileVo fileVo,HttpSession session){
		String ss=ossUtil.uploadFile(fileVo.getFile(),"file"+System.currentTimeMillis()+new Random().nextInt(1000)+".jpg");
		return ss;
	}

}
