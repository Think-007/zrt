package com.think.zrt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.think.zrt.dao.ProductMapper;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.service.ProductInfoService;
import com.think.zrt.util.ZrtLog;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
	
	private Logger logger =LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductMapper productMapper;
	
	private static final String DEFAULT_NAME="默认值";

	@Override
	public ProductInfo queryProductInfo(String productName) {
		ZrtLog.debug(logger, " enter getProductInfo", null, " productName : "+productName);

		//1、根据名称查询
		ProductInfo productInfo = productMapper
				.getProductInfoByName(productName);
		//2、查不到就返回默认值
		if(productInfo==null){
			productInfo=productMapper.getProductInfoByName(DEFAULT_NAME);
		}
		
		ZrtLog.debug(logger, " finish getProductInfo", null, " productInfo : "+productInfo);

		return productInfo;
	}

}
