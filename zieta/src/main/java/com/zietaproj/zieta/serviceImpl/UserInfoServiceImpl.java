package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.model.UserAccessType;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.AccessControlConfigRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;
import com.zietaproj.zieta.service.AccessTypeMasterService;
import com.zietaproj.zieta.service.ScreensMasterService;
import com.zietaproj.zieta.service.UserAccessTypeService;
import com.zietaproj.zieta.service.UserInfoService;



@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	UserInfoRepository userInfoRepositoryRepository;
	
	@Autowired
	AccessControlConfigRepository accessControlConfigRepository;
	
	@Autowired
	UserAccessTypeService userAccessTypeService;
	
	@Autowired
	ScreensMasterService screensMasterService;
	
	@Autowired
	AccessTypeMasterService accessTypeMasterService;
	
	@Autowired
	ClientInfoRepository clietinfoRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<UserInfoDTO> getAllUserInfoDetails() {
		List<UserInfo> userInfoList= userInfoRepositoryRepository.findAll();
		List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
		UserInfoDTO userInfoDTO = null;
		for (UserInfo userInfo : userInfoList) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
			userInfoDTO.setPassword("********");
			userInfoDTO.setClientCode(clietinfoRepo.findById(userInfo.getClientId()).get().getClient_code());
			 userInfoDTOs.add(userInfoDTO);
		}
		return userInfoDTOs;
	}

	@Override
	public UserInfoDTO findByEmail(String email) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(email);
		if(userInfo !=null) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
		}
		return userInfoDTO;
		
	}

	@Override
	public UserDetailsResponse getUserData(String emailId) {
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(emailId);
		
		List<UserAccessType> userAccessTypeList = userAccessTypeService.
				findByClientIdAndUserId(userInfo.getClientId(), userInfo.getId());
		List<Long> accessIdList = userAccessTypeList.stream().map(UserAccessType::getAccessTypeId)
										.collect(Collectors.toList());
				
		 List<Long> accessControlConfigList = accessControlConfigRepository.
				 findByClientIdANDAccessTypeId(userInfo.getClientId(), accessIdList);
		 List<ScreensMaster> screensListByClientId = screensMasterService.getScreensByIds(accessControlConfigList);
		 List<String> accessTypes = accessTypeMasterService.findByClientIdANDAccessTypeId(userInfo.getClientId(), accessIdList);
		 UserDetailsResponse userDetails = fillUserData(userInfo);
		 userDetails.setClientCode(clietinfoRepo.findById(userInfo.getClientId()).get().getClient_code());
		 userDetails.setScreensByClient(screensListByClientId);
		 userDetails.setAccessTypesByClient(accessTypes);
		
		return userDetails;
	}

	
	private UserDetailsResponse fillUserData(UserInfo userInfo) {
		
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		userDetailsResponse.setClientId(userInfo.getClientId());
		userDetailsResponse.setFirstName(userInfo.getUser_fname());
		userDetailsResponse.setUserEmailId(userInfo.getEmail());
		userDetailsResponse.setStatus(userInfo.getIs_active());
		userDetailsResponse.setUserId(userInfo.getId());
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
