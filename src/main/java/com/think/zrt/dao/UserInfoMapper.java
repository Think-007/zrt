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
	UserInfo getUserInfoByName(String name);
	
	/**
	 * 添加管理员
	 * 
	 * @param name
	 * @return
	 */
	void saveUserInfo(UserInfo userInfo);
	
	/**
	 * 删除管理员
	 * 
	 * @param name
	 * @return
	 */
	void deleteUserInfo(String userName);
	
	/**
	 * 更改管理员信息
	 * 
	 * @param name
	 * @return
	 */
	void updateUserInfo(String userName);

}
