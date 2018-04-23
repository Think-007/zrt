package com.think.zrt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.think.zrt.dao.UserInfoMapper;
import com.think.zrt.domain.ProductInfo;
import com.think.zrt.domain.UserInfo;
import com.think.zrt.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo getUserInfo(String name) {

		UserInfo userInfo = userInfoMapper.getUserInfoByName(name);

		return userInfo;
	}

	@Override
	public void delteUserInfo(String name) {

		userInfoMapper.deleteUserInfoByName(name);

	}

	@Override
	public void saveUserInfo(UserInfo userInfo) {
		userInfoMapper.saveUserInfo(userInfo);

	}

	@Override
	public void updateUserInfo(UserInfo userInfo, String oldName) {
		userInfoMapper.updateUserInfo(userInfo, oldName);

	}

	@Override
	public PageInfo<UserInfo> getAllUserInfo(int startPage, int pageSize) {

		PageHelper.startPage(startPage, pageSize);

		List<UserInfo> userInfoList = userInfoMapper.listAllUserInfo();

		PageInfo<UserInfo> pageUserInfo = new PageInfo<UserInfo>(userInfoList);

		return pageUserInfo;
	}

}
