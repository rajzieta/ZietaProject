package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;

public interface UserInfoService {

	public List<UserInfoDTO> getAllUserInfoDetails();

	public UserInfoDTO findByEmail(String email);
	
	public UserDetailsResponse findByClientId(Long client_id);
	
	public LoginResponse authenticateUser(String email, String password);
	
	public UserDetailsResponse getUserData(String emailId);

}
