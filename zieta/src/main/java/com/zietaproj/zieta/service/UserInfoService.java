package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.request.PasswordEditRequest;
import com.zietaproj.zieta.request.UserInfoEditRequest;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;

public interface UserInfoService {

	public List<UserInfoDTO> getAllUserInfoDetails();

	public UserInfoDTO findByEmail(String email);
	
	public List<UserInfoDTO> findByClientId(Long client_id);
	
	public LoginResponse authenticateUser(String email, String password);
	
	public UserDetailsResponse getUserData(String emailId);

	public void addUsersInfo(@Valid UserInfo userinfo);

	public void editUsersById(@Valid UserInfoEditRequest userinfoeditRequest) throws Exception;

	public void EditPasswordByEmailId(@Valid PasswordEditRequest passwordeditRequest) throws Exception;

	public void deleteUsersById(Long id, String modifiedBy) throws Exception;
}
