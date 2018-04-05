/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月5日	| LPF 	| 	create the file                       
 */

package com.think.zrt.util;

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

public class IdUtil {

	public static long generateId() {

		long time = System.currentTimeMillis();
		int a = (int) (Math.random() + 500);
		long id = time + a;

		return id;
	}

	public static void main(String[] args) {
		System.out.println(generateId());
	}

}
