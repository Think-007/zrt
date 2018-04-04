package com.think.zrt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.think.zrt.domain.ProductInfo;

public interface ProductMapper {

	/**
	 * 根据商品名称，查询商品信息
	 * 
	 * @param name
	 * @return
	 */
	public ProductInfo getProductInfoByName(@Param("name") String name);

	/**
	 * 存储商品信息
	 * 
	 * @param productInfo
	 * @return
	 */
	public int saveProductInfo(ProductInfo productInfo);

	/**
	 * 根据产品名称删除记录
	 * 
	 * @param name
	 * @return
	 */
	public int deleteProductInfoByName(@Param("name")String name);

	/**
	 * 分页查询所有记录
	 * 
	 * @param rowBounds
	 * @return
	 */
	public List<ProductInfo> listAllProductInfoByPage();

	/**
	 * 根据名称 模糊查询
	 * 
	 * @param name
	 * @return
	 */
	public List<ProductInfo> listProductInfo(@Param("name") String name, @Param("seriesName") String seriesName);

	// /**
	// * 根据产品系列查询
	// *
	// * @param name
	// * @return
	// */
	// public List<ProductInfo> listProductInfoBySeries(String seriesName);

}
