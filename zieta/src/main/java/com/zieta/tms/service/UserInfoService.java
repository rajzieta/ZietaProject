package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.UserDetailsResponse;

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
