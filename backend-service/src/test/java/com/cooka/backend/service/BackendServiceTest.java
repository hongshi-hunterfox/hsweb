package com.cooka.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.entity.ContentType;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.backend.model.ProductForm;
import com.backend.service.BackendServiceI;
import com.backend.service.ProductManageServiceI;
import com.uclee.fundation.data.mybatis.mapping.CategoryMapper;
import com.uclee.fundation.data.mybatis.mapping.ConfigMapper;
import com.uclee.fundation.data.mybatis.mapping.HongShiMapper;
import com.uclee.fundation.data.mybatis.model.Category;
import com.uclee.fundation.data.mybatis.model.Config;
import com.uclee.fundation.data.mybatis.model.HongShiProduct;
import com.uclee.fundation.data.mybatis.model.HongShiStore;
import com.uclee.fundation.data.mybatis.model.LotteryDrawConfig;
import com.uclee.fundation.data.mybatis.model.NapaStore;
import com.uclee.fundation.data.mybatis.model.UserProfile;
import com.uclee.fundation.data.mybatis.model.Var;
import com.uclee.fundation.data.web.dto.ValuePost;


public class BackendServiceTest  extends AbstractServiceTests {

	private static final Logger logger = Logger.getLogger(BackendServiceTest.class);

	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private HongShiMapper hongShiMapper;
	@Autowired
	private ProductManageServiceI productManageService;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private BackendServiceI backendService;

	@Test
	public void testCat() {
		logger.info(JSON.toJSONString(categoryMapper.selectByParentId(2)));
	}

	@Test
	public void testbirth() {
		logger.info(JSON.toJSONString(backendService.getUserListForUnBuy(1)));
	}

	@Test
	public void testUserList() {
		logger.info(JSON.toJSONString(backendService.getUserList(1)));
	}

	@Test
	public void testHongShiProduct() {
		logger.info(JSON.toJSONString(hongShiMapper.getHongShiProduct()));
	}

	@Test
	public void testHongShiStore() {
		logger.info(JSON.toJSONString(hongShiMapper.getHongShiStore()));
	}

	@Test
	public void testGetAddProductDataController() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Category> cat = categoryMapper.selectByParentId(0);
		result.put("cat", cat);
		List<HongShiProduct> proudctList = hongShiMapper.getHongShiProduct();
		result.put("proudctList", proudctList);
		List<HongShiStore> storeList = hongShiMapper.getHongShiStore();
		result.put("storeList", storeList);
		logger.info(JSON.toJSONString(result));
	}

	@Test
	public void testGenProductForm() {
		generateProductForm();
	}

	public ProductForm generateProductForm() {
		ProductForm product = new ProductForm();
		product.setCategoryId(1);
		product.setDescription("hsdaslkgjaslkg");
		String[] images = {"http://120.25.193.220/group1/M00/2C/AE/eBnB3FhKpfSAEgYRAACTnTHS0sE83.file", "http://120.25.193.220/group1/M00/2C/AE/eBnB3FhKpfmAGkGmAAB3G4LPDvY38.file", "http://120.25.193.220/group1/M00/2D/0E/eBnB3FhL_TWAM97FAAClmKmbu8s17.file"};
		product.setImages(images);
		product.setTitle("测试产品");
		List<ValuePost> valuePost = new ArrayList<ValuePost>();
		ValuePost item1 = new ValuePost();
		item1.setCode("111");
		item1.setHsStock(10);
		item1.setHsPrice(new BigDecimal(10));
		List<Integer> storeIds = new ArrayList<>();
		storeIds.add(1);
		item1.setStoreIds(storeIds);
		item1.setName("测试规格");
		valuePost.add(item1);
		ValuePost item2 = new ValuePost();
		item2.setCode("111");
		item2.setHsStock(20);
		item2.setHsPrice(new BigDecimal(20));
		List<Integer> storeIds2 = new ArrayList<>();
		storeIds.add(4);
		item2.setStoreIds(storeIds2);
		item2.setName("测试规格");
		valuePost.add(item2);
		product.setValuePost(valuePost);
		logger.info(JSON.toJSONString(product));
		return product;
	}

	@Test
	public void testAddProduct() {
		ProductForm product = generateProductForm();
		productManageService.addProduct(product);
	}

	@Test
	public void testUpdateProductData() {
		System.out.println(JSON.toJSONString(backendService.getProductForm(1)));
	}

	@Test
	public void testLottery() {
		Map<String, Object> result = new TreeMap<String, Object>();
		Map<String, Object> map = new TreeMap<String, Object>();
		List<LotteryDrawConfig> configs = backendService.selectAllLotteryDrawConfig();
		int i = 0;
		for (LotteryDrawConfig item : configs) {
			map.put("myKey[" + i + "]", item.getVoucherCode());
			map.put("myValue[" + i + "]", item.getMoney());
			i++;
		}
		result.put("data", map);
		result.put("size", i++);
		System.out.println(JSON.toJSONString(result));
	}

	@Test
	public void testUpdateProduct() {
		Map<String, Object> map = new HashMap<String, Object>();
		ProductForm productForm = backendService.getProductForm(1);
		map.put("productForm", productForm);
		System.out.println(JSON.toJSONString(productForm));
	}
}
