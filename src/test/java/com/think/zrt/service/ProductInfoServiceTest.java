/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月3日	| LPF 	| 	create the file                       
 */

package com.think.zrt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;
import com.think.zrt.domain.ProductInfo;

/**
 * 
 * 类简要描述
 * 
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author LPF
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceTest {

	@Autowired
	ProductInfoService productInfoService;

	@Test
	public void queryProductInfoTest() {

		ProductInfo p = productInfoService.queryProductInfo("冰川水s");

		System.out.println(p);

	}

	@Test
	public void saveProductInfoTest() {

		ProductInfo p = new ProductInfo();
		p.setAudioPic("sfsdfsdf11servi");
		p.setProductName("afdaf");
		p.setAudioUrl("fdsfasdfasfaf");
		productInfoService.saveProductInfo(p);
	}

	@Test
	public void listAllProductInfoTest() {

		PageInfo<ProductInfo> list = productInfoService.listAllProductInfo(0, 3);

		System.out.println(list);

	}

}
