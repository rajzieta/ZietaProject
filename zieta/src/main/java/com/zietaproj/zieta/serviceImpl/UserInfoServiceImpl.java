package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.service.ActivityMasterService;
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
			 userInfoDTO.setClient_id(userInfo.getClient_id());
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
			 userInfoDTO.setClient_id(userInfo.getClient_id());
			 userInfoDTO.setUser_fname(userInfo.getUser_fname());
			 userInfoDTO.setEmail_id(userInfo.getEmail());
			 userInfoDTO.setPhone_no(userInfo.getPhone_no());
			 userInfoDTO.setIs_active(userInfo.getIs_active());
			 userInfoDTO.setPassword(userInfo.getPassword());
		}
		return userInfoDTO;
		
	}


	

	
}
