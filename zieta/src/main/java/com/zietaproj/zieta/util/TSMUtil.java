package com.zietaproj.zieta.util;

import org.springframework.util.StringUtils;

import com.zietaproj.zieta.model.UserInfo;

public class TSMUtil {
	
	private final static String SPACE = " ";
	
	public static String getFullName(UserInfo userInfo) {

		StringBuilder userName = new StringBuilder(userInfo.getUser_fname());
		if (!StringUtils.isEmpty(userInfo.getUser_mname())) {
			userName.append(SPACE).append(userInfo.getUser_mname());
		}
		userName.append(SPACE).append(userInfo.getUser_lname());
		return userName.toString();
	}

}
