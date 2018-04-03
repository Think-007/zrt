package com.think.zrt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.think.zrt.dao.ProductMapper;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.service.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductMapper productMapper;

	@Override
	public ProductInfo getProductInfo(String productName) {

		ProductInfo productInfo = productMapper
				.queryProductInfoByName(productName);

		return null;
	}

}
