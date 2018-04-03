package com.think.zrt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.think.zrt.domain.ProcessResult;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.service.MailService;
import com.think.zrt.service.ProductInfoService;
import com.think.zrt.util.SecurityUtils;
import com.think.zrt.util.ZrtConst;
import com.think.zrt.util.ZrtLog;

@RestController
@RequestMapping("/zrt")
public class ProductController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 自动播放接口需要调用的微信的地址
	private static final String WEICHAT_URL = "http://weixin.solomochina.com/api/shareunit.aspx";

	// 自然堂的根据16位串码查询商品名称的接口地址
	private static final String GET_INFO_URL = "http://222.66.48.190:9080/chandoCode/qrcode/getProductInfo?params=";

	// 自然堂的16位串码校验接口地址
	private static final String GET_CHECK_URL = "http://222.66.48.190:9080/chandoCode/qrcode/checkQRCodeInfo?params=";

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MailService MailService;

	@Value("${mail.sender.accout}")
	private String sender;

	@Value("${mail.receiver.account}")
	private String receiver;

	/**
	 * 查询商品信息接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/product_info")
	public ProcessResult getProductInfo(String param) {

		ZrtLog.debug(logger, " enter getProductInfo ", null, " param: " + param);
		ProcessResult processResult = new ProcessResult();
		try {

			// 1、现根据16位串码查询产品的名称。
			String encode = SecurityUtils.encodeProductCode(param);
			// 工具类加密后每76字节会自动换行，需要去除空格
			encode = encode.replaceAll("[\\s*\t\n\r]", "");

			ZrtLog.debug(logger, "getProductInfo", null, " encode :" + encode);
			ResponseEntity<String> response = restTemplate.getForEntity(GET_INFO_URL + encode, String.class);
			if (response.getStatusCode() == HttpStatus.OK) {

				// 2、拿到名称，去数据库查询该名称对应的信息。
				String productName = response.getBody();
				ZrtLog.debug(logger, "getProductInfo", null, " productName :" + productName);
				ProductInfo productInfo = productInfoService.queryProductInfo(productName);
				processResult.setRetCode(ProcessResult.SUCCESS);
				processResult.setRetMsg("ok");
				processResult.setObj(productInfo);
			} else {
				processResult.setRetCode(ZrtConst.ERROR);
				processResult.setRetMsg(ZrtConst.ERROR_MSG);
				processResult.setObj(response.getBody());
			}
		} catch (ResourceAccessException e) {

			processResult.setRetCode(ZrtConst.TIME_OUT);
			processResult.setRetMsg(ZrtConst.TIME_OUT_MSG);
			ZrtLog.error(logger, "getProductInfo", null, processResult, e);
			// 发邮件
//			MailService.sendMail(sender, receiver, "主题", "内容");
		} catch (Throwable t) {

			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "getProductInfo", null, processResult, t);
			t.printStackTrace();
			// 发邮件
//			MailService.sendMail(sender, receiver, "主题", "内容");
		}
		ZrtLog.debug(logger, " finish getProductInfo ", null, " processResult: " + processResult);
		return processResult;

	}

	/**
	 * 校验商品是否为正品接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/auth_product")
	public ProcessResult authProductInfo(String code) {
		ZrtLog.debug(logger, " enter authProductInfo ", null, " code: " + code);

		ProcessResult processResult = new ProcessResult();

		try {
			// 1、直接调用自然堂接口进行校验即可。

			String encode = SecurityUtils.encodeProductCode(code);
			// 工具类加密后每76字节会自动换行，需要去除空格
			encode = encode.replaceAll("[\\s*\t\n\r]", "");

			ZrtLog.debug(logger, "authProductInfo", null, " encode :" + encode);

			ResponseEntity<String> response = restTemplate.getForEntity(GET_CHECK_URL + encode, String.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				processResult.setRetCode(ProcessResult.SUCCESS);
				processResult.setRetMsg("ok");
				processResult.setObj(response.getBody());
			} else {
				processResult.setRetCode(ZrtConst.ERROR);
				processResult.setRetMsg(ZrtConst.ERROR_MSG);
				processResult.setObj(response.getBody());
			}

		} catch (ResourceAccessException e) {

			processResult.setRetCode(ZrtConst.TIME_OUT);
			processResult.setRetMsg(ZrtConst.TIME_OUT_MSG);
			ZrtLog.error(logger, "authProductInfo", null, processResult, e);
			// 发邮件
//			MailService.sendMail(sender, receiver, "主题", "内容");
		} catch (Throwable t) {

			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "authProductInfo", null, processResult, t);
			t.printStackTrace();
			// 发邮件
//			MailService.sendMail(sender, receiver, "主题", "内容");
		}

		ZrtLog.debug(logger, " finish authProductInfo ", null, " processResult: " + processResult);

		return processResult;
	}

	/**
	 * 负责回调jssdk的一个接口
	 */

	@RequestMapping(value = "/to_weichat")
	public ProcessResult forwardToWeichat(String url) {

		ProcessResult processResult = new ProcessResult();
		try {

			ResponseEntity<String> response = restTemplate.getForEntity(WEICHAT_URL + "?url=" + url, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				processResult.setRetCode(ProcessResult.SUCCESS);
				processResult.setRetMsg("ok");
				processResult.setObj(response.getBody());
			} else {
				processResult.setRetCode(ZrtConst.ERROR);
				processResult.setRetMsg(ZrtConst.ERROR_MSG);
				processResult.setObj(response.getBody());
			}

		} catch (ResourceAccessException e) {

			processResult.setRetCode(ZrtConst.TIME_OUT);
			processResult.setRetMsg(ZrtConst.TIME_OUT_MSG);
			ZrtLog.error(logger, "forwardToWeichat", null, processResult, e);
		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "forwardToWeichat", null, processResult, t);
			t.printStackTrace();
		}

		return processResult;

	}

}
