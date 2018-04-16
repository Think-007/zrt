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
	ProductInfo getProductInfoByName(@Param("name") String name);

	/**
	 * 存储商品信息
	 * 
	 * @param productInfo
	 * @return
	 */
	int saveProductInfo(ProductInfo productInfo);

	/**
	 * 根据产品名称删除记录
	 * 
	 * @param name
	 * @return
	 */
	int deleteProductInfoByName(@Param("name") String name);

	/**
	 * 产品批量删除
	 * 
	 * @param names
	 * @return
	 */
	int deleteProductInfoList(@Param("names") List<String> names);

	/**
	 * 分页查询所有记录
	 * 
	 * @param rowBounds
	 * @return
	 */
	List<ProductInfo> listAllProductInfoByPage();

	/**
	 * 根据名称 模糊查询
	 * 
	 * @param name
	 * @return
	 */
	List<ProductInfo> listProductInfo(@Param("name") String name, @Param("seriesName") String seriesName);

	/**
	 * 查询系列
	 * 
	 * @return
	 */

	List<String> listProductSeries();

	/**
	 * 更新商品信息
	 * 
	 * @param productInfo
	 * @return
	 */
	int updateProductInfo(@Param("productInfo") ProductInfo productInfo, @Param("oldName") String oldName);

}
