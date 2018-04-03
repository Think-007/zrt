/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月4日	| LPF 	| 	create the file                       
 */

package com.think.zrt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
public class MailServiceImplTest {

	@Autowired
	MailService mailService;
	@Value("${mail.sender.accout}")
	private String sender;

	@Value("${mail.receiver.account}")
	private String receiver;

	@Test
	public void sendMainTest() {

		mailService.sendMail(sender, "wylipengfei@163.com", "主题测试", "测试内容sssss");

	}

}
