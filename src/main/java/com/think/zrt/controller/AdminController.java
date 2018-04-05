package com.think.zrt.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.think.zrt.domain.ProcessResult;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.service.ProductInfoService;
import com.think.zrt.util.IdUtil;
import com.think.zrt.util.ZrtConst;
import com.think.zrt.util.ZrtLog;

@RestController
@RequestMapping(value = "/auth")
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(getClass());

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
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ProcessResult uploadProudct(HttpServletRequest request, String templateId, String productName,
			String productDesc, String seriesName, MultipartFile file0, MultipartFile file1, MultipartFile file2,
			MultipartFile file3) {

		ProcessResult processResult = new ProcessResult();

		try {
			ProductInfo productInfo = new ProductInfo();
			productInfo.setProductName(productName);
			if (templateId == null || "".equals(templateId.trim())) {
				productInfo.setTemplateId(0);
			} else {
				productInfo.setTemplateId(Integer.parseInt(templateId));
			}
			productInfo.setTemplateId(Integer.parseInt(templateId));
			productInfo.setProductDesc(productDesc);
			productInfo.setSeriesName(seriesName);
			productInfo.setId(IdUtil.generateId());
			ZrtLog.debug(logger, " enter uploadProudct ", null, " productInfo: " + productInfo);
			// List<MultipartFile> files = ((MultipartHttpServletRequest)
			// request).getFiles("file");

			if (file0 != null && !file0.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file0, 0);
				productInfo.setProductPic(clienPath);
			}
			if (file1 != null && !file1.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file1, 1);
				productInfo.setVideoUrl(clienPath);
			}
			if (file2 != null && !file2.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file2, 2);
				productInfo.setAudioPic(clienPath);
			}
			if (file3 != null && !file3.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file3, 3);
				productInfo.setAudioUrl(clienPath);
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

	private String saveFile(String templateId, MultipartFile file, int i) throws IOException, FileNotFoundException {
		BufferedOutputStream stream;
		File path = new File(ZrtConst.DATA_PATH + templateId + "/");
		String[] temp = file.getOriginalFilename().split("\\.");
		String serverFileName = i + "." + temp[1];
		String clienPath = ZrtConst.DATA_PATH + templateId + "/" + serverFileName;
		File localFile = new File(path, serverFileName);
		if (!path.exists()) {
			path.mkdirs();
		}
		byte[] bytes = file.getBytes();
		stream = new BufferedOutputStream(new FileOutputStream(localFile));
		stream.write(bytes);
		stream.close();
		return clienPath;
	}

	@RequestMapping(value = "/delet_product")
	public ProcessResult delteProduct(String productName) {

		ProcessResult processResult = new ProcessResult();
		ZrtLog.debug(logger, " enter delteProduct ", null, " productName: " + productName);
		try {
//			productName = new String(productName.getBytes("8859_1"), "utf8");
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
			productName = new String(productName.getBytes("8859_1"), "utf8");
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

	/**
	 * 更新商品信息
	 * 
	 * @param request
	 * @param templateId
	 * @param productName
	 * @param productDesc
	 * @param seriesName
	 * @param file0
	 * @param file1
	 * @param file2
	 * @param file3
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ProcessResult updateProudct(HttpServletRequest request, String oldName, String templateId,
			String productName, String productDesc, String seriesName, MultipartFile file0, MultipartFile file1,
			MultipartFile file2, MultipartFile file3) {
		ZrtLog.debug(logger, " enter  update ", null, " oldName: " + oldName + " templateId : " + templateId
				+ " productName: " + productName + " productDesc: " + productDesc);

		ProcessResult processResult = new ProcessResult();

		try {
//			ProductInfo productInfo = new ProductInfo();
			 ProductInfo productInfo =
			 productInfoService.queryProductInfo(oldName);

			if ("undefined".equals(templateId) || templateId == null || "".equals(templateId.trim())) {
				productInfo.setTemplateId(-1);
			} else {
				productInfo.setTemplateId(Integer.parseInt(templateId));
			}
			if ("undefined".equals(productName)) {
				productInfo.setProductName(null);
			} else {

				productInfo.setProductName(productName);
			}
			if ("undefined".equals(productDesc)) {

				productInfo.setProductDesc(null);
			} else {
				productInfo.setProductDesc(productDesc);

			}
			if ("undefined".equals(seriesName)) {

				productInfo.setSeriesName(null);
			} else {
				productInfo.setSeriesName(seriesName);
			}
			ZrtLog.debug(logger, " enter uploadProudct ", null, " productInfo: " + productInfo);
			if (file0 != null && !file0.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file0, 0);
				productInfo.setProductPic(clienPath);
			} else {
				productInfo.setProductPic(null);
			}
			if (file1 != null && !file1.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file1, 1);
				productInfo.setVideoUrl(clienPath);
			} else {
				productInfo.setVideoUrl(null);
			}
			if (file2 != null && !file2.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file2, 2);
				productInfo.setAudioPic(clienPath);
			} else {
				productInfo.setAudioPic(null);
			}
			if (file3 != null && !file3.isEmpty()) {
				String clienPath = saveFile(productInfo.getId() + "", file3, 3);
				productInfo.setAudioUrl(clienPath);
			} else {
				productInfo.setAudioUrl(null);
			}

			productInfoService.updateProductInfo(productInfo, oldName);
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

	/**
	 * 查询系列
	 * 
	 * @return
	 */
	@RequestMapping(value = "/series")
	public ProcessResult listSeries() {
		ProcessResult processResult = new ProcessResult();

		try {
			List<String> series = productInfoService.listSeriesInfo();
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(series);

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
