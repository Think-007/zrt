/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月4日	| LPF 	| 	create the file                       
 */

package com.think.zrt.service;

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

public interface MailService {

	/**
	 * 发送邮件接口
	 * 
	 * @param from
	 *            发送者
	 * @param to
	 *            接受者
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public void sendMail(String from, String to, String subject, String content);

}
