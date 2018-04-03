package com.think.zrt.domain;

import java.io.Serializable;


public class ProcessResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 成功状态码
	public static final int SUCCESS = 0;

	// 返回码，0成功，其他失败
	private int retCode = -1;

	// 返回状态信息描述
	private String retMsg;

	// 返回的数据信息
	private Object obj;

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "ProcessRersult [retCode=" + retCode + ", retMsg=" + retMsg
				+ ", obj=" + obj + "]";
	}

}
