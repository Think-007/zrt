/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月3日	| LPF 	| 	create the file                       
 */

package com.think.zrt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
@Configuration
public class SysBean {

	@Bean
	public RestTemplate getRestTemplate() {

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate;
	}

}
