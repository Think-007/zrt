package com.think.zrt.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.think.zrt.dao.ProductMapper;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.service.ProductInfoService;
import com.think.zrt.util.ZrtLog;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductMapper productMapper;

	private static final String DEFAULT_NAME = "默认值";

	@Override
	public ProductInfo queryProductInfo(String productName) {
		ZrtLog.debug(logger, " enter getProductInfo", null, " productName : " + productName);
		// 1、根据名称查询
		ProductInfo productInfo = null;
		if (productName == null) {
			productInfo = productMapper.getProductInfoByName(DEFAULT_NAME);
			return productInfo;
		}
		productInfo = productMapper.getProductInfoByName(productName);
		// 2、查不到就返回默认值
		if (productInfo == null) {
			productInfo = productMapper.getProductInfoByName(DEFAULT_NAME);
		}

		ZrtLog.debug(logger, " finish getProductInfo", null, " productInfo : " + productInfo);

		return productInfo;
	}

	@Override
	public int saveProductInfo(ProductInfo productInfo) {

		int result = productMapper.saveProductInfo(productInfo);

		return result;
	}

	@Override
	public int deleteProductInfoByName(String productName) {

		// 保证默认数据不能删除
		if (DEFAULT_NAME.equals(productName)) {
			return 0;
		}
		int result = productMapper.deleteProductInfoByName(productName);
		return result;
	}

	@Override
	public PageInfo<ProductInfo> listAllProductInfo(int startPage, int pageSize) {

		PageHelper.startPage(startPage, pageSize);
		List<ProductInfo> allProductList = productMapper.listAllProductInfoByPage();
		PageInfo<ProductInfo> pageProductInfo = new PageInfo<ProductInfo>(allProductList);
		return pageProductInfo;
	}

	@Override
	public PageInfo<ProductInfo> listProductInfo(String name, String seriesName, int startPage, int pageSize) {

		PageHelper.startPage(startPage, pageSize);
		List<ProductInfo> allProductList = productMapper.listProductInfo(name, seriesName);
		PageInfo<ProductInfo> pageProductInfo = new PageInfo<ProductInfo>(allProductList);
		return pageProductInfo;

	}

	// @Override
	// public PageInfo<ProductInfo> listProductInfoBySeries(String seriesName,
	// int startPage, int pageSize) {
	//
	// PageHelper.startPage(startPage, pageSize);
	// List<ProductInfo> allProductList = null;
	// if (seriesName == null || "".equals(seriesName.trim())) {
	// allProductList = productMapper.listAllProductInfoByPage();
	// } else {
	// allProductList = productMapper.listProductInfo(seriesName);
	// }
	// PageInfo<ProductInfo> pageProductInfo = new
	// PageInfo<ProductInfo>(allProductList);
	// return pageProductInfo;
	// }

}
