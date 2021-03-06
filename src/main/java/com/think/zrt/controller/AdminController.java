package com.think.zrt.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.think.zrt.domain.ProcessResult;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.domain.UserInfo;
import com.think.zrt.service.ProductInfoService;
import com.think.zrt.service.UserInfoService;
import com.think.zrt.util.IdUtil;
import com.think.zrt.util.ZrtConst;
import com.think.zrt.util.ZrtLog;

@RestController
@RequestMapping(value = "/auth")
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private UserInfoService userInfoService;

	public static Map<String, String> pathMap = new HashMap<String, String>();

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 * @param request
	 * @param templateId
	 *            模板id
	 * @param productName
	 *            产品名称
	 * @param productSimple
	 *            产品名
	 * @param productDesc
	 *            产品描述
	 * @param seriesName
	 *            系列名称
	 * @param videoTemplateId
	 *            视频id
	 * @param file0
	 *            产品图片
	 * @param file1
	 *            产品音频
	 * @param file2
	 *            视频图片
	 * @param file3
	 *            产品视频
	 * @param file4
	 *            防伪图片
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ProcessResult uploadProudct(HttpServletRequest request, String templateId, String productName,
			String productSimple, String productDesc, String seriesName, String videoTemplateId, MultipartFile file0,
			MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4) {

		ProcessResult processResult = new ProcessResult();
		List<String> delList = new ArrayList<String>();
		try {
			// 获取存储路径
			String webappsPath = pathMap.get("path");
			if (webappsPath == null) {
				String path = request.getServletContext().getRealPath("/");

				System.out.println("----------" + path);
				String[] s = path.split("/");
				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < s.length - 1; i++) {
					sb.append(s[i]);
					sb.append("/");
				}

				sb.append("upload/");
				webappsPath = sb.toString();
				pathMap.put("path", webappsPath);
			}

			ProductInfo productInfo = new ProductInfo();
			productInfo.setProductName(productName);
			if (templateId == null || "".equals(templateId.trim())) {
				productInfo.setTemplateId(0);
			} else {
				productInfo.setTemplateId(Integer.parseInt(templateId));
			}

			if (videoTemplateId == null || "".equals(videoTemplateId.trim())) {
				productInfo.setVideoTemplateId(0);
			} else {
				productInfo.setVideoTemplateId(Integer.parseInt(videoTemplateId));
			}
			productInfo.setProductDesc(productDesc);
			productInfo.setSeriesName(seriesName);
			productInfo.setProductSimple(productSimple);

			productInfo.setId(IdUtil.generateId());
			ZrtLog.debug(logger, " enter uploadProudct ", null, " productInfo: " + productInfo);

			// 存储图片
			if (file0 != null && !file0.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file0);
				productInfo.setProductPic(clienPath);
				delList.add(clienPath);
			}
			// 存储音频
			if (file1 != null && !file1.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file1);
				productInfo.setAudioUrl(clienPath);
				delList.add(clienPath);
			}
			// 存储视频图片
			if (file2 != null && !file2.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file2);
				productInfo.setVideoPic(clienPath);
				delList.add(clienPath);
			}
			// 存储视频
			if (file3 != null && !file3.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file3);
				productInfo.setVideoUrl(clienPath);
				delList.add(clienPath);
			}
			// 存储防伪图片
			if (file4 != null && !file4.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file4);
				productInfo.setCodePic(clienPath);
				delList.add(clienPath);
			}
			// 存储记录
			productInfoService.saveProductInfo(productInfo);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(null);

		} catch (DuplicateKeyException e) {
			processResult.setRetCode(-1);
			// 删除已上传的文件和图片信息
			productInfoService.deleteProductServerInfo(delList);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "uploadProudct", null, processResult, t);
			t.printStackTrace();
		} finally {
			delList = null;
		}
		ZrtLog.debug(logger, " finish  uploadProudct ", null, " processResult: " + processResult);
		return processResult;
	}

	/**
	 * 存储媒体信息
	 * 
	 * @param uploadPath
	 *            存储路劲
	 * @param pathId
	 *            子目录
	 * @param file
	 *            文件
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private String saveFile(String uploadPath, String pathId, MultipartFile file)
			throws IOException, FileNotFoundException {
		BufferedOutputStream stream;
		File path = new File(uploadPath + pathId + "/");
		String[] temp = file.getOriginalFilename().split("\\.");
		String serverFileName = IdUtil.generateId() + "." + temp[1];
		String clienPath = "/upload/" + pathId + "/" + serverFileName;
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

	/**
	 * 单个删除产品
	 * 
	 * @param request
	 * @param productName
	 *            产品名称
	 * @return
	 */
	@RequestMapping(value = "/delet_product")
	public ProcessResult delteProduct(HttpServletRequest request, String productName) {

		ProcessResult processResult = new ProcessResult();
		ZrtLog.debug(logger, " enter delteProduct ", null, " productName: " + productName);
		try {
			// productName = new String(productName.getBytes("8859_1"), "utf8");
			String webappsPath = pathMap.get("path");
			if (webappsPath == null) {
				String path = request.getServletContext().getRealPath("/");

				System.out.println("----------" + path);
				String[] s = path.split("/");
				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < s.length - 1; i++) {
					sb.append(s[i]);
					sb.append("/");
				}

				sb.append("upload/");
				webappsPath = sb.toString();
				pathMap.put("path", webappsPath);
			}
			productInfoService.deleteProductInfoByName(productName, webappsPath);
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
		ZrtLog.debug(logger, " enter listProductInfo ", null, " startPage: " + startPage);

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
	 * 
	 * 模糊查询接口
	 * 
	 * @param name
	 *            产品名称
	 * @param seriesName
	 *            系列名称
	 * @param startPage
	 *            页码
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
	 * @param file4
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ProcessResult updateProudct(HttpServletRequest request, String oldName, String templateId,
			String productName, String productSimple, String productDesc, String seriesName, String videoTemplateId,
			MultipartFile file0, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4) {
		ZrtLog.debug(logger, " enter  update ", null, " oldName: " + oldName + " templateId : " + templateId
				+ " productName: " + productName + " productDesc: " + productDesc);

		ProcessResult processResult = new ProcessResult();

		try {
			String webappsPath = pathMap.get("path");
			if (webappsPath == null) {
				String path = request.getServletContext().getRealPath("/");

				System.out.println("----------" + path);
				String[] s = path.split("/");
				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < s.length - 1; i++) {
					sb.append(s[i]);
					sb.append("/");
				}

				sb.append("upload/");
				webappsPath = sb.toString();
				pathMap.put("path", webappsPath);
			}
			ProductInfo productInfo = productInfoService.queryProductInfo(oldName);

			if ("undefined".equals(templateId) || templateId == null || "".equals(templateId.trim())) {
				productInfo.setTemplateId(-1);
			} else {
				productInfo.setTemplateId(Integer.parseInt(templateId));
			}
			if ("undefined".equals(videoTemplateId) || videoTemplateId == null || "".equals(videoTemplateId.trim())) {
				productInfo.setVideoTemplateId(-1);
			} else {
				productInfo.setVideoTemplateId(Integer.parseInt(videoTemplateId));
			}
			if ("undefined".equals(productName)) {
				productInfo.setProductName(null);
			} else {

				productInfo.setProductName(productName);
			}
			if ("undefined".equals(productSimple)) {
				productInfo.setProductSimple(null);
			} else {

				productInfo.setProductSimple(productSimple);
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
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file0);
				productInfo.setProductPic(clienPath);
			} else {
				productInfo.setProductPic(null);
			}
			if (file1 != null && !file1.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file1);
				productInfo.setAudioUrl(clienPath);
			} else {
				productInfo.setAudioUrl(null);
			}
			if (file2 != null && !file2.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file2);
				productInfo.setVideoPic(clienPath);
			} else {
				productInfo.setVideoPic(null);
			}
			if (file3 != null && !file3.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file3);
				productInfo.setVideoUrl(clienPath);
			} else {
				productInfo.setVideoUrl(null);
			}
			if (file4 != null && !file4.isEmpty()) {
				String clienPath = saveFile(webappsPath, productInfo.getId() + "", file4);
				productInfo.setCodePic(clienPath);
			} else {
				productInfo.setCodePic(null);
			}

			productInfoService.updateProductInfo(productInfo, oldName);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(null);

		} catch (DuplicateKeyException e) {
			processResult.setRetCode(-1);

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

	// **********************管理员增删*****************************

	/**
	 * 添加管理员
	 * 
	 * @param userInfo
	 *            管理员参数
	 * @return
	 */
	@RequestMapping(value = "/add_user")
	public ProcessResult addAdmin(String name, String password, Integer roleId) {
		ZrtLog.debug(logger, " enter  addAdmin ", null,
				" name: " + name + " password : " + password + " roleId : " + roleId);

		ProcessResult processResult = new ProcessResult();
		UserInfo userInfo = new UserInfo();
		userInfo.setName(name);
		userInfo.setPassword(password);
		userInfo.setRoleId(roleId);

		ZrtLog.debug(logger, " enter addAdmin ", null, " userInfo: " + userInfo);

		try {
			userInfoService.saveUserInfo(userInfo);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "addAdmin", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " finish addAdmin ", null, " processResult: " + processResult);
		return processResult;
	}

	/**
	 * 删除管理员信息
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/delete_user")
	public ProcessResult deleteAdmin(String userName) {

		ProcessResult processResult = new ProcessResult();
		ZrtLog.debug(logger, " enter deleteAdmin ", null, " userName: " + userName);
		try {
			userInfoService.delteUserInfo(userName);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "deleteAdmin", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " finish deleteAdmin ", null, " processResult: " + processResult);
		return processResult;
	}

	/**
	 * 更新管理员信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/update_user")
	public ProcessResult updateAdmin(String name, String password, int roleId, String oldName) {

		ZrtLog.debug(logger, " enter  updateAdmin ", null,
				" name: " + name + " password : " + password + " roleId : " + roleId + " oldName : " + oldName);
		ProcessResult processResult = new ProcessResult();
		UserInfo userInfo = new UserInfo();
		userInfo.setName(name);
		userInfo.setPassword(password);
		userInfo.setRoleId(roleId);
		ZrtLog.debug(logger, " enter  updateAdmin ", null, " userInfo: " + userInfo);
		try {
			userInfoService.updateUserInfo(userInfo, oldName);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "deleteAdmin", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " enter  updateAdmin ", null, " processResult: " + processResult);
		return processResult;
	}

	/**
	 * 获取所有管理员信息
	 * 
	 * @param startPage
	 *            起始页码
	 * @return
	 */
	@RequestMapping(value = "/all_admin")
	public ProcessResult listAllAdmin(int startPage) {

		ProcessResult processResult = new ProcessResult();
		ZrtLog.debug(logger, " enter  listAllAdmin ", null, " startPage: " + startPage);
		try {
			PageInfo<UserInfo> infoList = userInfoService.getAllUserInfo(startPage, 10);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(infoList);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "deleteAdmin", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " enter  listAllAdmin ", null, " processResult: " + processResult);

		return processResult;
	}

	@RequestMapping(value = "/edit_user")
	public ProcessResult editAdmin(String name) {

		ProcessResult processResult = new ProcessResult();
		ZrtLog.debug(logger, " enter  editAdmin ", null, " name: " + name);
		try {
			UserInfo userInfo = userInfoService.getUserInfo(name);
			userInfo.setPassword(null);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setObj(userInfo);

		} catch (Throwable t) {
			processResult.setRetCode(ZrtConst.EXCEPTION);
			processResult.setRetMsg(ZrtConst.EXCEPTION_MSG);
			processResult.setObj(t);
			ZrtLog.error(logger, "edit_user", null, processResult, t);
			t.printStackTrace();
		}
		ZrtLog.debug(logger, " finish  edit_user ", null, " processResult: " + processResult);

		return processResult;
	}

	public static void main(String[] args) {
		String a = "/usr/share/zrt/tomcat_zrt/webapps/zrtweb/";
		String[] s = a.split("/");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < s.length - 1; i++) {
			sb.append(s[i]);
			sb.append("/");
		}

		sb.append("upload/");

		System.out.println(sb.toString());

	}

}
