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

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
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

	@Value("${mail.sender.pwd}")
	private String password;

	// 定义邮件发送协议
	@Value("${mail.transport.protocol}")
	private String protocol;
	// 声明邮件服务器地址
	@Value("${mail.smtp.host}")
	private String host;
	// 端口
	@Value("${mail.smtp.port}")
	private String port;
	// 发送权限，为true时表示允许发送
	@Value("${mail.smtp.auth}")
	private String isSSL;
	// 设置为true时，调试的时候可以在控制台显示信息
	@Value("${mail.debug}")
	private String isDebug;

	@Override
	public void sendMail(String from, String to, String subject, String content) {
		try {

			prop.setProperty("mail.transport.protocol", protocol);
			prop.setProperty("mail.smtp.host", host);
			prop.setProperty("mail.smtp.auth", isSSL);
			prop.setProperty("mail.debug", isDebug);
			prop.setProperty("mail.smtp.port", port);

			Authenticator authenticator = new Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			};
			// 建立了一条通信路线
			Session session = Session.getInstance(prop, authenticator);
			Message msg = new MimeMessage(session);
			// 发件者邮箱
			msg.setFrom(new InternetAddress(from));
			// 收件者邮箱
			msg.setRecipient(RecipientType.TO, new InternetAddress(to));
			// 主题
			msg.setSubject(subject);
			// 内容
			msg.setText(content);

			Transport tran = session.getTransport(protocol);
			tran.connect(from, password);
			tran.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
