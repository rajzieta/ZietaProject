package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.UserInfoDTO;

public interface UserInfoService {

	public List<UserInfoDTO> getAllUserInfoDetails();

	public UserInfoDTO findByEmail(String email);

}
