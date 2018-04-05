package com.think.zrt.service.impl;

import java.io.File;
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
import com.think.zrt.util.ZrtConst;
import com.think.zrt.util.ZrtLog;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductMapper productMapper;

	private static final String DEFAULT_NAME = "默认产品";

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
	public int deleteProductInfoByName(String productName, String path) {

		// 保证默认数据不能删除
		if (DEFAULT_NAME.equals(productName)) {
			return 0;
		}
		// 找到该产品
		ProductInfo productInfo = productMapper.getProductInfoByName(productName);
		// 删除记录
		int result = productMapper.deleteProductInfoByName(productName);

		// 如果文件存在就删除文件
		File file = new File(path + productInfo.getId());
		if (file.exists()) {
			deleteDir(file);
		}
		return result;
	}

	// 删除文件
	private void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteDir(files[i]);
			}
		}
		dir.delete();
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

		if (allProductList.isEmpty()) {
			ProductInfo productInfo = productMapper.getProductInfoByName(DEFAULT_NAME);

			allProductList.add(productInfo);
		}

		PageInfo<ProductInfo> pageProductInfo = new PageInfo<ProductInfo>(allProductList);
		return pageProductInfo;

	}

	@Override
	public List<String> listSeriesInfo() {

		List<String> seriesList = productMapper.listProductSeries();
		return seriesList;
	}

	@Override
	public int updateProductInfo(ProductInfo productInfo, String oldName) {

		int result = productMapper.updateProductInfo(productInfo, oldName);

		return result;
	}

}
