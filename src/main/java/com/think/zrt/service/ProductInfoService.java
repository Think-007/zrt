package com.think.zrt.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.think.zrt.domain.ProductInfo;

public interface ProductInfoService {

	/**
	 * 查询商品信息业务逻辑
	 * 
	 * @param productName
	 *            产品名称
	 * @return
	 */
	ProductInfo queryProductInfo(String productName);

	/**
	 * 存储产品信息
	 * 
	 * @param productInfo
	 *            产品信息
	 * @return
	 */
	int saveProductInfo(ProductInfo productInfo);

	/**
	 * 
	 * 根据名称删除记录
	 * 
	 * @param productName
	 *            记录名称
	 * @param path
	 *            信息路径
	 * @return
	 */
	int deleteProductInfoByName(String productName, String path);

	/**
	 * 批量删除指定目录的文件信息
	 * 
	 * @param pathList
	 *            文件路径
	 */
	void deleteProductServerInfo(List<String> pathList);

	/**
	 * 查询所有产品
	 * 
	 * @return
	 */
	PageInfo<ProductInfo> listAllProductInfo(int startPage, int pageSize);

	/**
	 * 模糊查询产品信息，没有的话返回默认值
	 * 
	 * @param productName
	 *            产品名称
	 * @param seriesName
	 *            系列名称
	 * @param startPage
	 *            起始页码
	 * @param pageSize
	 *            每页数目
	 * @return
	 */
	PageInfo<ProductInfo> listProductInfo(String productName, String seriesName, int startPage, int pageSize);

	/**
	 * 查询系列数目
	 * 
	 * @return
	 */
	List<String> listSeriesInfo();

	/**
	 * 更新产品信息
	 * 
	 * @param productInfo
	 *            更改后的产品信息
	 * @param oldName
	 *            旧名称
	 * @return
	 */
	int updateProductInfo(ProductInfo productInfo, String oldName);

	/**
	 * 根据名称批量删除产品
	 * 
	 * @param nameList
	 *            名称列表
	 * @return
	 */
	int delteProductList(List nameList);
}
