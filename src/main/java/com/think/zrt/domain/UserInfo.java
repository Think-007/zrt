/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2018年4月4日	| LPF 	| 	create the file                       
 */

package com.think.zrt.domain;

import java.io.Serializable;

/**
 * 
 * 
 * 管理员模型
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author LPF
 * 
 */

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户名
	private String name;
	// 密码
	private String password;

	// 用户角色
	private int roleId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", password=" + password + ", roleId=" + roleId + "]";
	}

}
