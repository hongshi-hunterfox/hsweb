package com.uclee.backend.service.impl;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.backend.model.ProductForm;
import com.backend.service.ProductManageServiceI;
import com.uclee.date.util.DateUtils;
import com.uclee.file.util.FileUtil;
import com.uclee.fundation.data.mybatis.mapping.ProductCategoryLinkMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductImageLinkMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductSaleMapper;
import com.uclee.fundation.data.mybatis.mapping.ProductsSpecificationsValuesLinkMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationValueMapper;
import com.uclee.fundation.data.mybatis.mapping.SpecificationValueStoreLinkMapper;
import com.uclee.fundation.data.mybatis.model.ProductCategoryLink;
import com.uclee.fundation.data.mybatis.model.ProductCategoryLinkKey;
import com.uclee.fundation.data.mybatis.model.ProductImageLink;
import com.uclee.fundation.data.mybatis.model.ProductSale;
import com.uclee.fundation.data.mybatis.model.ProductsSpecificationsValuesLink;
import com.uclee.fundation.data.mybatis.model.SpecificationValue;
import com.uclee.fundation.data.mybatis.model.SpecificationValueStoreLink;
import com.uclee.fundation.data.web.dto.ValuePost;
import com.uclee.fundation.dfs.fastdfs.FDFSFileUpload;

public class ProductManageServiceImpl implements ProductManageServiceI{

	private static final Logger logger = Logger.getLogger(ProductManageServiceImpl.class);
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private FDFSFileUpload fDFSFileUpload;
	@Autowired
	private ProductImageLinkMapper productImageLinkMapper;
	@Autowired
	private ProductCategoryLinkMapper productCategoryLinkMapper;
	@Autowired
	private SpecificationValueMapper specificationValueMapper;
	@Autowired
	private ProductsSpecificationsValuesLinkMapper productsSpecificationsValuesLinkMapper;
	@Autowired
	private SpecificationValueStoreLinkMapper specificationValueStoreLinkMapper;
	@Autowired
	private ProductSaleMapper productSaleMapper;
	@Override
	public boolean addProduct(ProductForm product) {	
		//description 
		product.setIsActive(true);
		descriptionHandler(product);
		if(productMapper.insertSelective(product)>0){
			if(product.getSale()!=null){
				ProductSale productSale = productSaleMapper.selectByProductId(product.getProductId());
				if(productSale!=null){
					productSale.setCount(product.getSale());
					productSaleMapper.updateByPrimaryKeySelective(productSale);
				}else{
					ProductSale tmp = new ProductSale();
					tmp.setCount(product.getSale());
					tmp.setProductId(product.getProductId());
					productSaleMapper.insertSelective(tmp);
				}
			}
			ProductSale tmp = productSaleMapper.selectByProductId(product.getProductId());
			if(tmp==null){
			ProductSale sale = new ProductSale();
			sale.setCount(0);
			sale.setProductId(product.getProductId());
			productSaleMapper.insertSelective(sale);
			}
			logger.info("productId:" + product.getProductId());
			// 插入
			categoryHandler(product);
			
			//image 
			imageHandler(product);
			
			specificationHandler(product);
			
			return true;
		}
		return false;
	}

	private void specificationHandler(ProductForm product) {
		for(ValuePost item:product.getValuePost()){
			SpecificationValue value = new SpecificationValue();
			value.setHsGoodsCode(item.getCode());
			value.setHsGoodsPrice(item.getHsPrice());
			value.setHsStock(item.getHsStock());
			value.setPrePrice(item.getPrePrice());
			value.setPromotionPrice(item.getPromotionPrice());
			value.setStartTime(item.getStartTime());
			value.setEndTime(item.getEndTime());
			value.setStartTimeStr(DateUtils.format(item.getStartTime(), DateUtils.FORMAT_LONG));
			value.setEndTimeStr(DateUtils.format(item.getEndTime(), DateUtils.FORMAT_LONG));
			value.setSpecificationId(1);
			value.setValue(item.getName());
			if(specificationValueMapper.insertSelective(value)>0){
				ProductsSpecificationsValuesLink link = new ProductsSpecificationsValuesLink();
				link.setProductId(product.getProductId());
				link.setSpecificationId(1);
				link.setValueId(value.getValueId());
				productsSpecificationsValuesLinkMapper.insertSelective(link);
				for(Integer storeId:item.getStoreIds()){
					SpecificationValueStoreLink storeLink = new SpecificationValueStoreLink();
					storeLink.setStoreId(storeId);
					storeLink.setValueId(value.getValueId());
					specificationValueStoreLinkMapper.insertSelective(storeLink);
				}
			}
		}
		SpecificationValue value = specificationValueMapper.selectByProductIdLimit(product.getProductId());
		if(value!=null){
			product.setPrice(value.getHsGoodsPrice());
			productMapper.updateByPrimaryKeySelective(product);
		}
	}
	
	private void updateSpecificationHandler(ProductForm product) {
		delPreSpecificationValue(product.getProductId());
		delPreProSpeValueLink(product.getProductId());
		delPreStoreValueLink(product.getProductId());
		for(ValuePost item:product.getValuePost()){
			SpecificationValue value = new SpecificationValue();
			value.setHsGoodsCode(item.getCode());
			value.setHsGoodsPrice(item.getHsPrice());
			value.setHsStock(item.getHsStock());
			value.setPrePrice(item.getPrePrice());
			value.setPromotionPrice(item.getPromotionPrice());
			//提交时转换类型
			value.setStartTime(DateUtils.parse(item.getStartTimeStr()));
			value.setEndTime(DateUtils.parse(item.getEndTimeStr()));
			value.setSpecificationId(1);
			value.setValue(item.getName());
			if(specificationValueMapper.insertSelective(value)>0){
				ProductsSpecificationsValuesLink link = new ProductsSpecificationsValuesLink();
				link.setProductId(product.getProductId());
				link.setSpecificationId(1);
				link.setValueId(value.getValueId());
				productsSpecificationsValuesLinkMapper.insertSelective(link);
				for(Integer storeId:item.getStoreIds()){
					SpecificationValueStoreLink storeLink = new SpecificationValueStoreLink();
					storeLink.setStoreId(storeId);
					storeLink.setValueId(value.getValueId());
					specificationValueStoreLinkMapper.insertSelective(storeLink);
				}
			}
		}
		SpecificationValue value = specificationValueMapper.selectByProductIdLimit(product.getProductId());
		if(value!=null){
			product.setPrice(value.getHsGoodsPrice());
			productMapper.updateByPrimaryKeySelective(product);
		}
	}
	
	private void SpdatespecificationHandler(ProductForm product) {
		delPreStoreValueLink(product.getProductId());
		delPreSpecificationValue(product.getProductId());
		delPreProSpeValueLink(product.getProductId());
		for(ValuePost item:product.getValuePost()){
			SpecificationValue value = new SpecificationValue();
			value.setHsGoodsCode(item.getCode());
			value.setHsGoodsPrice(item.getHsPrice());
			value.setHsStock(item.getHsStock());
			value.setSpecificationId(1);
			value.setValue(item.getName());
			if(specificationValueMapper.insertSelective(value)>0){
				ProductsSpecificationsValuesLink link = new ProductsSpecificationsValuesLink();
				link.setProductId(product.getProductId());
				link.setSpecificationId(1);
				link.setValueId(value.getValueId());
				productsSpecificationsValuesLinkMapper.insertSelective(link);
				for(Integer storeId:item.getStoreIds()){
					SpecificationValueStoreLink storeLink = new SpecificationValueStoreLink();
					storeLink.setStoreId(storeId);
					storeLink.setValueId(value.getValueId());
					specificationValueStoreLinkMapper.insertSelective(storeLink);
				}
			}
		}
		
	}

	private void delPreProSpeValueLink(Integer productId) {
		productsSpecificationsValuesLinkMapper.delByProductId(productId);
	}

	private void delPreSpecificationValue(Integer productId) {
		specificationValueMapper.delByProductId(productId);
	}

	private void delPreStoreValueLink(Integer productId) {
		specificationValueStoreLinkMapper.delByProductId();
	}

	public String uploadFile(File file){
		String url = null;
		url = fDFSFileUpload.getFileId(file);
		return url;
	}
	
	private boolean imageHandler(ProductForm product) {
		if(product.getImages()!=null){
			int i=0;
			for(String url : product.getImages()){
				i++;
				if(i>6)break;//
				if(!url.isEmpty()){
					ProductImageLink productImage = new ProductImageLink();
					productImage.setProductId(product.getProductId());
					productImage.setImageUrl(url);
					fDFSFileUpload.getSlaveImage(url, 512, 512);
					productImageLinkMapper.insertSelective(productImage);
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean updateImageHandler(ProductForm product) {
		List<ProductImageLink> links = productImageLinkMapper.selectByProductId(product.getProductId());
		for(ProductImageLink link : links){
			productImageLinkMapper.deleteByPrimaryKey(link.getLinkId());
		}
		if(product.getImages()!=null){
			int i=0;
			for(String url : product.getImages()){
				i++;
				if(i>3)break;//
				if(url!=null&&!url.isEmpty()){
					ProductImageLink productImage = new ProductImageLink();
					productImage.setProductId(product.getProductId());
					productImage.setImageUrl(url);
					fDFSFileUpload.getSlaveImage(url, 512, 512);
					productImageLinkMapper.insertSelective(productImage);
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean categoryHandler(ProductForm product) {
		ProductCategoryLink productCategoryLink = new ProductCategoryLink();
		productCategoryLink.setCategoryId(product.getCategoryId());
		productCategoryLink.setProductId(product.getProductId());
		//fix:null value handle
		productCategoryLinkMapper.insertSelective(productCategoryLink);
		return true;
	}
	
	private boolean updateCategoryHandler(ProductForm product) {
		ProductCategoryLinkKey tmp = productCategoryLinkMapper.selectByProductId(product.getProductId());
		productCategoryLinkMapper.deleteByPrimaryKey(tmp);
		ProductCategoryLink productCategoryLink = new ProductCategoryLink();
		productCategoryLink.setCategoryId(product.getCategoryId());
		productCategoryLink.setProductId(product.getProductId());
		//fix:null value handle
		productCategoryLinkMapper.insertSelective(productCategoryLink);
		return true;
	}

	private boolean descriptionHandler(ProductForm product) {
		if(product.getDescription()!=null){ 
        	File file = FileUtil.convertToFile(product.getDescription());
            String url = uploadFile(file);
            product.setDescription(url);
            file.delete();
		}
		return true;
	}
	@Override
	public String uploadImage(MultipartFile file) {
		String url = null;
		url = fDFSFileUpload.getFileId(file);
	
		return url;
	}

	/*@Override
	public boolean updateProduct(ProductForm productForm) {
		descriptionHandler(productForm);
		if(productMapper.updateByPrimaryKeySelective(productForm)>0){
			logger.info("productId:" + productForm.getProductId());
			// category link
			updateCategoryHandler(productForm);
			
			//image 
			updateImageHandler(productForm);
			
			return true;
		}
		return false;
	}*/
	@Override
	public boolean updateProduct(ProductForm product) {	
		//description 
		product.setIsActive(true);
		descriptionHandler(product);
		if(productMapper.updateByPrimaryKeySelective(product)>0){
			if(product.getSale()!=null){
				ProductSale productSale = productSaleMapper.selectByProductId(product.getProductId());
				if(productSale!=null){
					productSale.setCount(product.getSale());
					productSaleMapper.updateByPrimaryKeySelective(productSale);
				}else{
					ProductSale tmp = new ProductSale();
					tmp.setCount(product.getSale());
					tmp.setProductId(product.getProductId());
					productSaleMapper.insertSelective(tmp);
				}
			}
			logger.info("productId:" + product.getProductId());
			// 插入
			updateCategoryHandler(product);
			
			//image 
			updateImageHandler(product);
			
			updateSpecificationHandler(product);
			
			return true;
		}
		return false;
	}

	@Override
	public Boolean delProduct(Integer productId) {
		return productMapper.deleteByPrimaryKey(productId)>0;
	}

}
