package com.think.zrt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.think.zrt.domain.ProcessResult;



@RestController
@RequestMapping("/zrt")
public class ProductController {

	/**
	 * 查询商品信息接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productinfo")
	@ResponseBody
	public ProcessResult getProductInfo() {

		ProcessResult processResult = new ProcessResult();
		try {

			// 1、现根据16位串码查询产品的名称。

			// 2、拿到名称后，去数据库查询该名称对应的信息。
			Map<String, String> map = new HashMap<String, String>();

			map.put("productName", "冰川面膜");
			map.put("seriesName", "面膜");
			map.put("productPic", "localhostff.jpg");
			map.put("videoUrl", "www.baidu.com");
			map.put("audioUrl", "www.google.com");
			map.put("audioPic", "wwwwww.pic");
			map.put("productDesc", "最新款面膜");
			map.put("templateId", "1");

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(map);
		} catch (Throwable t) {

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(t);
			t.printStackTrace();
		}

		return processResult;

	}

	/**
	 * 校验商品是否为正品接口
	 * 
	 * @return
	 */
	public ProcessResult authProductInfo() {

		ProcessResult processResult = new ProcessResult();

		try {

		} catch (Throwable t) {

		}

		return processResult;
	}

}
