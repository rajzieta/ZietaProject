package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.UserAccessType;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.UserAccessMappingRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;
import com.zietaproj.zieta.service.UserAccessTypeService;
import com.zietaproj.zieta.service.UserInfoService;



@Service
public class UserAccessTypeServiceImpl implements UserAccessTypeService {

	@Autowired
	UserAccessMappingRepository  userAccessMappingRepository;

	@Override
	public List<UserAccessType> findByClientIdAndUserId(long clientId, long userId) {
		// TODO Auto-generated method stub
		return userAccessMappingRepository.findByClientIdAndUserId(clientId, userId);
	}
	
	
	public List<UserAccessType> findAll(){
		return userAccessMappingRepository.findAll();
	}
	
	

	
}
