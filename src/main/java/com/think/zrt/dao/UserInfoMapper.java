/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月4日	| LPF 	| 	create the file                       
 */

package com.think.zrt.dao;

import com.think.zrt.domain.UserInfo;

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

public interface UserInfoMapper {

	/**
	 * 根据姓名查找用户信息
	 * 
	 * @param name
	 * @return
	 */
	public UserInfo getUserInfoByName(String name);

}
