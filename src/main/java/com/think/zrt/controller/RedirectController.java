/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月4日	| LPF 	| 	create the file                       
 */

package com.think.zrt.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.think.zrt.dao.UserInfoMapper;
import com.think.zrt.domain.ProcessResult;
import com.think.zrt.domain.UserInfo;
import com.think.zrt.service.MailService;
import com.think.zrt.util.ZrtConst;
import com.think.zrt.util.ZrtLog;

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

@Controller
public class RedirectController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserInfoMapper userInfoMapper;

	private ExecutorService threadPoole = Executors.newFixedThreadPool(1000);

	/**
	 * 登录重定向
	 * 
	 * @return
	 */
	@RequestMapping("/relogin")
	public String redirect() {

		return "redirect:/";
	}

	@RequestMapping("/admin")
	@ResponseBody
	public ProcessResult login(HttpServletRequest request,
			HttpServletResponse response, String userName, String password) {

		ProcessResult processResult = new ProcessResult();
		ZrtLog.debug(logger, " enter login ", null, " userName: " + userName
				+ " pwd : " + password);
		try {

			// 1、校验参数
			if (userName == null || password == null) {
				processResult.setRetCode(ZrtConst.PARAM_ERROR);
				processResult.setRetMsg(ZrtConst.PARAM_ERROR_MSG);
				return processResult;
			}

			// 2、查询用户信息
			UserInfo userInfo = userInfoMapper.getUserInfoByName(userName);
			if (userInfo == null || !password.equals(userInfo.getPassword())) {
				request.getSession().removeAttribute("user");
				processResult.setRetCode(ZrtConst.PARAM_ERROR);
				processResult.setRetMsg(ZrtConst.PARAM_ERROR_MSG);
			} else {
				request.getSession().setAttribute("user", userInfo);
				processResult.setRetCode(ProcessResult.SUCCESS);
				processResult.setRetMsg("ok");
			}

		} catch (Throwable t) {
			request.getSession().removeAttribute("user");
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "uploadProudct", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " finish login ", null, " processResult: "
				+ processResult);

		return processResult;

	}

	@Autowired
	private MailService mailService;

	@Value("${mail.sender.accout}")
	private String sender;

	@Value("${mail.receiver.account}")
	private String receiver;

	@RequestMapping("/mail")
	@ResponseBody
	public String mail() {

		threadPoole.submit(new Runnable() {

			@Override
			public void run() {
				mailService.sendMail(sender, receiver, "主题", "测试内容 qq邮箱");
			}
		});

		return "success";
	}
}
