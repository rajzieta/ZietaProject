package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.UserAccessType;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;

public interface UserAccessTypeService {

	List<UserAccessType> findByClientIdAndUserId(long clientId, long userId);
	List<UserAccessType> findAll();

}
