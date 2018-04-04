///*      						
// * Copyright 2012 LPF  All rights reserved.
// * 
// * History:
// * ------------------------------------------------------------------------------
// * Date    	|  Who  		|  What  
// * 2018年4月4日	| LPF 	| 	create the file                       
// */
//
//package com.think.zrt.controller;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * 
// * 类简要描述
// * 
// * <p>
// * 类详细描述
// * </p> 
//* 
// * @author LPF
// * 
// */
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import com.think.zrt.domain.ProductInfo;
//import com.think.zrt.service.ProductInfoService;
//import com.think.zrt.util.ZrtConst;
//
//@Controller
//public class FileController {
//
//	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
//
//	private static String host = "http://47.97.41.50:9090/zrtfile/";
//
//	@Autowired
//	ProductInfoService productInfoService;
//
//	// 多文件上传
//	@RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
//	@ResponseBody
//	public String handleFileUpload(HttpServletRequest request, String templateId, String productName,
//			String productDesc, String seriesName, String audioPic, String productPic) {
//
//		System.out.println(templateId);
//
//		ProductInfo productInfo = new ProductInfo();
//		productInfo.setProductName(productName);
//		productInfo.setTemplateId(Integer.parseInt(templateId));
//		productInfo.setProductDesc(productDesc);
//		productInfo.setSeriesName(seriesName);
//		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
//		MultipartFile file = null;
//		BufferedOutputStream stream = null;
//		for (int i = 0; i < files.size(); i++) {
//			file = files.get(i);
//			if (!file.isEmpty()) {
//				try {
//					File path = new File(ZrtConst.DATA_PATH + templateId + "/");
//					String[] temp = file.getOriginalFilename().split("\\.");
//					String serverFileName = i +"."+ temp[1];
//					String clienPath = host + templateId + "/" + serverFileName;
//					File localFile = new File(path, serverFileName);
//					if (!path.exists()) {
//						path.mkdirs();
//					}
//					byte[] bytes = file.getBytes();
//					stream = new BufferedOutputStream(new FileOutputStream(localFile));
//					stream.write(bytes);
//					stream.close();
//
//					switch (i) {
//					case 0:
//						productInfo.setProductPic(clienPath);
//						break;
//					case 1:
//						productInfo.setVideoUrl(clienPath);
//
//						break;
//					case 2:
//						productInfo.setAudioPic(clienPath);
//						break;
//					case 3:
//						productInfo.setAudioUrl(clienPath);
//						break;
//					default:
//						break;
//					}
//
//				} catch (Exception e) {
//					stream = null;
//					return "You failed to upload " + i + " => " + e.getMessage();
//				}
//			} else {
//				return "You failed to upload " + i + " because the file was empty.";
//			}
//		}
//		productInfoService.saveProductInfo(productInfo);
//		return "upload successful";
//	}
//
//}
