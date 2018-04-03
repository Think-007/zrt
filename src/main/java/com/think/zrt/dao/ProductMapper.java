package com.think.zrt.dao;

import com.think.zrt.domain.ProductInfo;

public interface ProductMapper {

	/**
	 * 根据商品名称，查询商品信息
	 * 
	 * @param name
	 * @return
	 */
	public ProductInfo queryProductInfoByName(String name);

}
