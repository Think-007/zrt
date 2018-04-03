package com.think.zrt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.think.zrt.domain.ProcessResult;

@RestController
@RequestMapping("/auth")
public class AdminController {

	@RequestMapping("/admin")
	public ProcessResult login(String userName, String password) {

		ProcessResult processResult = new ProcessResult();

		try {

		} catch (Throwable t) {

		}

		return processResult;

	}

	public ProcessResult uploadProudct() {

		ProcessResult processResult = new ProcessResult();

		try {
			
			

		} catch (Throwable t) {

		}

		return processResult;

	}

}
