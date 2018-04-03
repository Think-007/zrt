/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月3日	| LPF 	| 	create the file                       
 */

package com.think.zrt.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	public void queryProductInfoByName(){
		
		ProductInfo p =  productMapper.getProductInfoByName("冰川水s");
		
		System.out.println(p);
		
	}

}
