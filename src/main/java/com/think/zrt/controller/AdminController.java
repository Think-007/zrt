package com.think.zrt.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.think.zrt.domain.ProcessResult;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.service.ProductInfoService;
import com.think.zrt.util.ZrtConst;
import com.think.zrt.util.ZrtLog;

@RestController
@RequestMapping(value = "/auth")
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	// 客户端访问视频图片地址
	private static final String HOST = "http://47.97.41.50:9090/zrtfile/";

	@Autowired
	private ProductInfoService productInfoService;

	/**
	 * 
	 * @param request
	 * @param templateId
	 *            模板id
	 * @param productName
	 *            产品名称
	 * @param productDesc
	 *            产品描述
	 * @param seriesName
	 *            产品系列
	 * @return
	 */
	@RequestMapping(value = "/upload")
	public ProcessResult uploadProudct(HttpServletRequest request, String templateId, String productName,
			String productDesc, String seriesName) {

		ProcessResult processResult = new ProcessResult();

		try {
			ProductInfo productInfo = new ProductInfo();
			productInfo.setProductName(productName);
			productInfo.setTemplateId(Integer.parseInt(templateId));
			productInfo.setProductDesc(productDesc);
			productInfo.setSeriesName(seriesName);
			ZrtLog.debug(logger, " enter uploadProudct ", null, " productInfo: " + productInfo);
			List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
			MultipartFile file = null;
			BufferedOutputStream stream = null;
			for (int i = 0; i < files.size(); i++) {
				file = files.get(i);
				if (!file.isEmpty()) {
					File path = new File(ZrtConst.DATA_PATH + templateId + "/");
					String[] temp = file.getOriginalFilename().split("\\.");
					String serverFileName = i + "." + temp[1];
					String clienPath = HOST + templateId + "/" + serverFileName;
					File localFile = new File(path, serverFileName);
					if (!path.exists()) {
						path.mkdirs();
					}
					byte[] bytes = file.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(localFile));
					stream.write(bytes);
					stream.close();

					switch (i) {
					case 0:
						productInfo.setProductPic(clienPath);
						break;
					case 1:
						productInfo.setVideoUrl(clienPath);

						break;
					case 2:
						productInfo.setAudioPic(clienPath);
						break;
					case 3:
						productInfo.setAudioUrl(clienPath);
						break;
					default:
						break;
					}
				}
			}
			productInfoService.saveProductInfo(productInfo);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(null);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "uploadProudct", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " finish  uploadProudct ", null, " processResult: " + processResult);
		return processResult;
	}

	@RequestMapping(value = "/delet_product")
	public ProcessResult delteProduct(String productName) {

		ProcessResult processResult = new ProcessResult();
		ZrtLog.debug(logger, " enter delteProduct ", null, " productName: " + productName);
		try {

			productInfoService.deleteProductInfoByName(productName);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(null);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "delteProduct", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " finish delteProduct ", null, " processResult: " + processResult);
		return processResult;

	}

	/**
	 * 分页查询商品信息
	 * 
	 * @param startPage
	 *            起始页码
	 * @param pageSize
	 *            每页记录条数
	 * @return
	 */
	@RequestMapping(value = "/list_product")
	public ProcessResult listProductInfo(int startPage) {

		ProcessResult processResult = new ProcessResult();

		try {
			PageInfo<ProductInfo> pageInfo = productInfoService.listAllProductInfo(startPage, 10);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(pageInfo);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "delteProduct", null, processResult, t);
			t.printStackTrace();
		}

		return processResult;

	}

	/**
	 * 模糊查询接口
	 * 
	 * @param name
	 * @param startPage
	 * @return
	 */
	@RequestMapping(value = "/list_product_fuzzy")
	public ProcessResult listProductInfoFuzzy(String name, String seriesName, int startPage) {

		ProcessResult processResult = new ProcessResult();

		try {
			PageInfo<ProductInfo> pageInfo = productInfoService.listProductInfo(name, seriesName, startPage, 10);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(pageInfo);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "delteProduct", null, processResult, t);
			t.printStackTrace();
		}

		return processResult;

	}

//	/**
//	 * 系列查询接口
//	 * 
//	 * @param seriesName
//	 * @param startPage
//	 * @return
//	 */
//	@RequestMapping(value = "/list_series")
//	public ProcessResult listProductSeries(String seriesName, int startPage) {
//
//		ProcessResult processResult = new ProcessResult();
//
//		try {
//			PageInfo<ProductInfo> pageInfo = productInfoService.listProductInfoBySeries(seriesName, startPage, 10);
//			processResult.setRetCode(ProcessResult.SUCCESS);
//			processResult.setRetMsg("ok");
//			processResult.setObj(pageInfo);
//
//		} catch (Throwable t) {
//			processResult.setRetCode(ZrtConst.EXCEPTION);
//			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
//			processResult.setObj(t);
//			ZrtLog.error(logger, "delteProduct", null, processResult, t);
//			t.printStackTrace();
//		}
//
//		return processResult;
//
//	}

	/**
	 * 产品编辑接口
	 * 
	 * @param seriesName
	 * @param startPage
	 * @return
	 */
	@RequestMapping(value = "/edit_product")
	public ProcessResult editProductSeries(String productName) {

		ProcessResult processResult = new ProcessResult();

		try {
			ProductInfo productInfo = productInfoService.queryProductInfo(productName);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(productInfo);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "delteProduct", null, processResult, t);
			t.printStackTrace();
		}

		return processResult;

	}

}
