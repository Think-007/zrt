/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月4日	| LPF 	| 	create the file                       
 */

package com.think.zrt.service.impl;

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
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.think.zrt.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private Properties prop = new Properties();
	{
		prop.setProperty("mail.transport.protocol", "smtp");// 定义邮件发送协议
		prop.setProperty("mail.smtp.host", "smtp.163.com");// 声明邮件服务器地址
		prop.setProperty("mail.smtp.auth", "true");// 发送权限，为true时表示允许发送
		prop.setProperty("mail.debug", "true");// 设置为true时，调试的时候可以在控制台显示信息
	}

	@Value("${mail.sender.pwd}")
	private String password;

	@Override
	public void sendMail(String from, String to, String subject, String content) {
		try {
			// 建立了一条通信路线
			Session session = Session.getDefaultInstance(prop);
			Message msg = new MimeMessage(session);
			// 发件者邮箱
			msg.setFrom(new InternetAddress(from));
			// 收件者邮箱
			msg.setRecipient(RecipientType.TO, new InternetAddress(to));
			// 主题
			msg.setSubject(subject);
			// 内容
			msg.setText(content);

			Transport tran = session.getTransport("smtp");
			tran.connect(from, password);
			tran.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
