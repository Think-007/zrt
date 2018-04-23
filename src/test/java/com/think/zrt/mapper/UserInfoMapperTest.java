/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月4日	| LPF 	| 	create the file                       
 */

package com.think.zrt.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.think.zrt.dao.UserInfoMapper;
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
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {

	@Autowired
	UserInfoMapper userInfoMapper;

	@Test
	public void getUserInfoByNameTest() {

		UserInfo u = userInfoMapper.getUserInfoByName("admin");

		System.out.println(u);

	}

	@Test
	public void saveUserInfo() {

		UserInfo u = new UserInfo();
		u.setName("test");
		u.setPassword("33333");
		u.setRoleId(0);

		userInfoMapper.saveUserInfo(u);

		System.out.println(u);

	}

	@Test
	public void listAllUserInfo() {

		List<UserInfo> l = userInfoMapper.listAllUserInfo();
		System.out.println(l);
	}

}
