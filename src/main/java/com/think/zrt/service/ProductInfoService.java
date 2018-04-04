package com.think.zrt.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.think.zrt.domain.ProductInfo;

public interface ProductInfoService {

	/**
	 * 查询商品信息业务逻辑
	 * 
	 * @param productName
	 * @return
	 */
	public ProductInfo queryProductInfo(String productName);

	/**
	 * 存储产品信息
	 * 
	 * @param productInfo
	 * @return
	 */
	public int saveProductInfo(ProductInfo productInfo);

	/**
	 * 根据名称删除记录
	 * 
	 * @param productName
	 * @return
	 */
	public int deleteProductInfoByName(String productName);

	/**
	 * 查询所有产品
	 * 
	 * @return
	 */
	public PageInfo<ProductInfo> listAllProductInfo(int startPage, int pageSize);

	/**
	 * 模糊查询产品
	 * 
	 * @return
	 */
	public PageInfo<ProductInfo> listProductInfo(String name, String seriesName, int startPage, int pageSize);

//	/**
//	 * 根据系列名称查询产品
//	 * 
//	 * @return
//	 */
//	public PageInfo<ProductInfo> listProductInfoBySeries(String seriesName, int startPage, int pageSize);
}
