package com.think.zrt.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Encoder;

/**
 * Created by mashenwei on 2018/3/23. 对等加密方案
 */
@SuppressWarnings("restriction")
public class SecurityUtils {
	private static final String MODE_AES = "AES";
	/** 自然堂可视化加密解密规则 */
	private static String ZRT_KEY_RULE = "B1PHCqNzDUCIDW9k";

	private static String encodeInfo(String data, String keyRules) throws Exception {
		// 根据当前日期生成规则
		KeyGenerator keyGen = KeyGenerator.getInstance(MODE_AES);
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(keyRules.getBytes());
		keyGen.init(128, secureRandom);
		SecretKey original_key = keyGen.generateKey();
		byte[] raw = original_key.getEncoded();
		SecretKey key = new SecretKeySpec(raw, MODE_AES);
		Cipher cipher = Cipher.getInstance(MODE_AES);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byte_encode = data.getBytes("utf-8");
		byte[] byte_AES = cipher.doFinal(byte_encode);
		String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
		return AES_encode;
	}

	public static String encodeProductCode(String data) throws Exception {
		// 根据当前日期生成规则
		String keyRules = ZRT_KEY_RULE + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return encodeInfo(data, keyRules);
	}
//
//	public static JSONObject getJsonObject(String data) throws Exception {
//		try {
//			String keyRules = ZRT_KEY_RULE + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//			KeyGenerator keygen = KeyGenerator.getInstance(MODE_AES);
//			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//			secureRandom.setSeed(keyRules.getBytes());
//			keygen.init(128, secureRandom);
//			SecretKey original_key = keygen.generateKey();
//			byte[] raw = original_key.getEncoded();
//			SecretKey key = new SecretKeySpec(raw, MODE_AES);
//			Cipher cipher = Cipher.getInstance(MODE_AES);
//			cipher.init(Cipher.DECRYPT_MODE, key);
//			byte[] byte_content = new BASE64Decoder.decodeBuffer(data);
//			byte[] byte_decode = cipher.doFinal(byte_content);
//			String AES_decode = new String(byte_decode, "utf-8");
//			JSONObject object = JSON.parseObject(AES_decode);
//			return object;
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//		} catch (BadPaddingException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 自然堂可视化解密后，获取二维码
	 */
//	public static String getPackCodeForZRT(String data) throws Exception {
//		try {
//			JSONObject object = getJsonObject(data);
//			if (object != null && object.get("code") != null) {
//				return object.getString("code");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//		try {
//			String dataContent1 = "{ \"code\":\"1017690787578426\",\"TimeStamp\":152143525223 }";
//			String a1 = SecurityUtils.encodeProductCode(dataContent1);
//			System.out.println("===============");
//			System.out.println("加密后：" + a1);
//			String b1 = SecurityUtils.getPackCodeForZRT(a1);
//			System.out.println("===============");
//			System.out.println("解密后：" + b1);
//			System.out.println("===============");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
}
