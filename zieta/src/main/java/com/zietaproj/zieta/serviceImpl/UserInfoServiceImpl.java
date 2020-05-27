package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;
import com.zietaproj.zieta.service.UserInfoService;



@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	UserInfoRepository userInfoRepositoryRepository;
	
	@Override
	public List<UserInfoDTO> getAllUserInfoDetails() {
		List<UserInfo> userInfoList= userInfoRepositoryRepository.findAll();
		List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
		UserInfoDTO userInfoDTO = null;
		for (UserInfo userInfo : userInfoList) {
			userInfoDTO = new UserInfoDTO();
			 userInfoDTO.setClient_id(userInfo.getClientId());
			 userInfoDTO.setUser_fname(userInfo.getUser_fname());
			 userInfoDTO.setEmail_id(userInfo.getEmail());
			 userInfoDTO.setPhone_no(userInfo.getPhone_no());
			 userInfoDTOs.add(userInfoDTO);
		}
		return userInfoDTOs;
	}

	@Override
	public UserInfoDTO findByEmail(String email) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(email);
		if(userInfo !=null) {
			 userInfoDTO.setClient_id(userInfo.getClientId());
			 userInfoDTO.setUser_fname(userInfo.getUser_fname());
			 userInfoDTO.setEmail_id(userInfo.getEmail());
			 userInfoDTO.setPhone_no(userInfo.getPhone_no());
			 userInfoDTO.setIs_active(userInfo.getIs_active());
			 userInfoDTO.setPassword(userInfo.getPassword());
			 userInfoDTO.setIs_active(userInfo.getIs_active());
		}
		return userInfoDTO;
		
	}

	@Override
	public UserDetailsResponse getUserData(String emailId) {
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(emailId);
		
		return fillUserData(userInfo);
	}

	
	private UserDetailsResponse fillUserData(UserInfo userInfo) {
		
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		userDetailsResponse.setClientId(userInfo.getClientId());
		userDetailsResponse.setFirstName(userInfo.getUser_fname());
		userDetailsResponse.setUserEmailId(userInfo.getEmail());
		userDetailsResponse.setStatus(userInfo.getIs_active());
		userDetailsResponse.setInfoMessage("User details after successful login");
		
		return userDetailsResponse;
	}
	
	
	public LoginResponse authenticateUser(String email, String password) {
		LoginResponse loginResponse = LoginResponse.builder().infoMessage("").build();
		
		UserInfoDTO dbUserInfo = findByEmail(email);
		if (dbUserInfo != null) {
			if (password.equals(dbUserInfo.getPassword())) {
				loginResponse.setActive(dbUserInfo.getIs_active() !=0);
				loginResponse.setInfoMessage("Valid credentials are provided !!");
				loginResponse.setLoginValid(true);
				
				return loginResponse;
				
			} else {
				loginResponse.setInfoMessage("Provided credentials are wrong... "
						+ email);
				return loginResponse;
			}
		}else {
			loginResponse.setInfoMessage("Invalid emailId provided: "+ email);
			return loginResponse;
		}
		
		
	}

	@Override
	public UserDetailsResponse findByClientId(Long client_id) {
		UserInfo userInfo = userInfoRepositoryRepository.findByClientId(client_id);
		return fillUserData(userInfo);
	}

	

	
}
