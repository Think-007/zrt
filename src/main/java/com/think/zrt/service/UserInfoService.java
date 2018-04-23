package com.think.zrt.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.think.zrt.domain.UserInfo;

public interface UserInfoService {

	/**
	 * 查询用户身份信息
	 * 
	 * @param name
	 * @return
	 */
	UserInfo getUserInfo(String name);

	/**
	 * 删除用户信息
	 * 
	 * @param name
	 */
	void delteUserInfo(String name);

	/**
	 * 存储用户信息
	 * 
	 * @param userInfo
	 */
	void saveUserInfo(UserInfo userInfo);

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @param oldName
	 */

	void updateUserInfo(UserInfo userInfo, String oldName);

	/**
	 * 分页展示用户信息
	 * 
	 * @param startPage
	 *            起始页码
	 * @param pageSize
	 *            每页记录数
	 * @return
	 */
	PageInfo<UserInfo> getAllUserInfo(int startPage, int pageSize);

}
