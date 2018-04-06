/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月3日	| LPF 	| 	create the file                       
 */

package com.think.zrt.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.think.zrt.dao.ProductMapper;
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
public class ProductMapperTest {

	@Autowired
	private ProductMapper productMapper;

	@Test
	public void queryProductInfoByName() {

		ProductInfo p = productMapper.getProductInfoByName("冰川水s");

		System.out.println(p);

	}

	@Test
	public void saveProductInfoTest() {

		ProductInfo p = new ProductInfo();
		p.setId(123124);
		p.setVideoPic("sfsdfsdf11");
		p.setVideoUrl("videourl");
		p.setProductName("afdaf444");
		p.setAudioUrl("fdsfasdfasfaf");

		productMapper.saveProductInfo(p);
	}

	@Test
	public void deleteProductInfoByNameTest() {

		int result = productMapper.deleteProductInfoByName("afdaf");

		System.out.println(result);
	}

	@Test
	public void getAllProductInfoByPageTest() {

		List<ProductInfo> list = productMapper.listAllProductInfoByPage();
		System.out.println(list);

	}


	@Test
	public void listProductInfoTest() {

		List<ProductInfo> list = productMapper.listProductInfo("44","");
		System.out.println(list);
	}
	
	@Test
	
	public void listSeriesTest(){
		
		
		List l = productMapper.listProductSeries();
		
		System.out.println(l);
	}
	
	@Test
	public void updateProductInfoTest(){
		
		ProductInfo productInfo = new ProductInfo(); 
		productInfo.setId(20);
		productInfo.setProductName("产品22354555555");
		productInfo.setVideoPic("00000");
		
		productMapper.updateProductInfo(productInfo,"产品223545");
		
	}
	
	@Test
	public void deleteProductInfoListTest(){
		
		List<String> list = new ArrayList<String>();
		
		list.add(1+"");
		list.add(2+"");
		list.add(3+"");
		
		int re = productMapper.deleteProductInfoList(list);
		System.out.println(re);
		
	}

}
