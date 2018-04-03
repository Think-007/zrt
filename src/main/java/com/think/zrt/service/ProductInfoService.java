package com.think.zrt.service;

import com.think.zrt.domain.ProductInfo;

public interface ProductInfoService {

	/**
	 * 查询商品信息业务逻辑
	 * 
	 * @param productName
	 * @return
	 */
	public ProductInfo queryProductInfo(String productName);

}
