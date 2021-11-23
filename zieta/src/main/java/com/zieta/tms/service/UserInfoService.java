package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.UsersInfoDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.response.ResponseMessage;

public interface UserInfoService {

	public List<UserInfoDTO> getAllUserInfoDetails();

	public UserInfoDTO findByEmail(String email);//findUserDetailsByUserId
	
	public UserInfoDTO findByUserId(long userId);
	
	public UsersInfoDTO findByUsersId(long userId);
	
	public UserDetailsDTO findUserDetailsByUserId(long userId);
	
	public List<UserInfoDTO> findByClientId(Long client_id);
	
	public LoginResponse authenticateUser(String email, String password);
	
	public UserDetailsResponse getUserData(String emailId);

	//public void addUsersInfo(@Valid UserInfo userinfo) throws Exception;
	public AddUserResponse addUsersInfo(@Valid UserInfo userinfo) throws Exception;
	
	public UserDetails addUserDetails(@Valid UserDetails userDetails) throws Exception;

	public void editUsersById(@Valid UserInfoEditRequest userinfoeditRequest) throws Exception;//
	
	public ResponseMessage editUserDetailsByUserId(@Valid UserDetailsEditRequest userDetailsEditRequest) throws Exception;
	
	public void EditPasswordByEmailId(@Valid PasswordEditRequest passwordeditRequest) throws Exception;

	public void deleteUsersById(Long id, String modifiedBy) throws Exception;

	public List<UserInfo> getUsersByIds(List<Long> teamList);
	
	public void uploadUsersData(MultipartFile multipartFile, long clientId);
	
	
	//temp use only
	//public List<Object> getAllBYDProject();
	
}
