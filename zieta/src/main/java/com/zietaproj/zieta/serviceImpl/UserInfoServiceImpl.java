package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.ScreensMaster;
//import com.zietaproj.zieta.model.UserAccessType;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.AccessTypeMasterRepository;
import com.zietaproj.zieta.repository.AccessTypeScreenMappingRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.request.PasswordEditRequest;
import com.zietaproj.zieta.request.UserInfoEditRequest;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;
import com.zietaproj.zieta.service.AccessTypeMasterService;
import com.zietaproj.zieta.service.ScreensMasterService;
//import com.zietaproj.zieta.service.UserAccessTypeService;
import com.zietaproj.zieta.service.UserInfoService;
import com.zietaproj.zieta.util.PasswordUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

	
	@Autowired
	UserInfoRepository userInfoRepositoryRepository;
	
	@Autowired
	AccessTypeScreenMappingRepository accessControlConfigRepository;
	
	
	@Autowired
	ScreensMasterService screensMasterService;
	
	@Autowired
	AccessTypeMasterService accessTypeMasterService;
	
	@Autowired
	AccessTypeMasterRepository accessTypeMasterRepo;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	ClientInfoRepository clientInfoRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
//	@Autowired
//    private PasswordEncoder passwordEncoder;
//	
//	@Autowired
//    private BCrypt bcrypt;
	
	@Override
	public List<UserInfoDTO> getAllUserInfoDetails() {
		short notDeleted = 0;
		List<UserInfo> userInfoList= userInfoRepositoryRepository.findByIsDelete(notDeleted);
		List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
		mapUserInfoModelToDTO(userInfoList, userInfoDTOs);
		return userInfoDTOs;
	}

	private void mapUserInfoModelToDTO(List<UserInfo> userInfoList, List<UserInfoDTO> userInfoDTOs) {
		UserInfoDTO userInfoDTO = null;
		for (UserInfo userInfo : userInfoList) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
			userInfoDTO.setPassword("Welcome1");
			userInfoDTO.setClientCode(clientInfoRepo.findById(userInfo.getClientId()).get().getClientCode());
			userInfoDTO.setClientDescription(clientInfoRepo.findById(userInfo.getClientId()).get().getClientName());
			userInfoDTO.setAccessType(accessTypeMasterRepo.findById(userInfo.getAccessTypeId()).get().getAccessType());
			
			 userInfoDTOs.add(userInfoDTO);
		}
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
		
//		List<UserAccessType> userAccessTypeList = userAccessTypeService.
//				findByClientIdAndUserId(userInfo.getClientId(), userInfo.getId());
//		List<Long> accessIdList = userAccessTypeList.stream().map(UserAccessType::getAccessTypeId)
//										.collect(Collectors.toList());
				
		 List<Long> accessControlConfigList = accessControlConfigRepository.
				 findByClientIdANDAccessTypeId(userInfo.getClientId(), userInfo.getAccessTypeId());
		 List<ScreensMaster> screensListByClientId = screensMasterService.getScreensByIds(accessControlConfigList);
		 List<String> accessTypes = accessTypeMasterService.findByClientIdANDAccessTypeId(userInfo.getClientId(), userInfo.getAccessTypeId());
		 UserDetailsResponse userDetails = fillUserData(userInfo);
		 userDetails.setClientCode(clientInfoRepo.findById(userInfo.getClientId()).get().getClientCode());
		 userDetails.setClientDescription(clientInfoRepo.findById(userInfo.getClientId()).get().getClientName());
		 userDetails.setScreensByClient(screensListByClientId);
		 userDetails.setAccessTypesByClient(accessTypes);
		
		return userDetails;
	}

	
	private UserDetailsResponse fillUserData(UserInfo userInfo) {
		
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		userDetailsResponse.setClientId(userInfo.getClientId());
		userDetailsResponse.setFirstName(userInfo.getUserFname());
		userDetailsResponse.setMiddleName(userInfo.getUserMname());
		userDetailsResponse.setLastName(userInfo.getUserLname());
		userDetailsResponse.setUserEmailId(userInfo.getEmail());
		userDetailsResponse.setEmpId(userInfo.getEmpId());
		userDetailsResponse.setAccessTypeId(userInfo.getAccessTypeId());
		userDetailsResponse.setStatus(userInfo.getIsActive());
		userDetailsResponse.setUserId(userInfo.getId());
		userDetailsResponse.setInfoMessage("User details after successful login");
		
		return userDetailsResponse;
	}
	
	
	public LoginResponse authenticateUser(String email, String password) {
		LoginResponse loginResponse = LoginResponse.builder().infoMessage("").build();
		
		UserInfoDTO dbUserInfo = findByEmail(email);
		if (dbUserInfo != null) {
			if (password.equals(dbUserInfo.getPassword())) {
				loginResponse.setActive(dbUserInfo.getIsActive() !=0);
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
	public List<UserInfoDTO> findByClientId(Long client_id) {
		short notDeleted = 0;
		List<UserInfo> userInfoList = userInfoRepositoryRepository.findByClientIdAndIsDelete(client_id, notDeleted);
		List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
		mapUserInfoModelToDTO(userInfoList, userInfoDTOs);
		return userInfoDTOs;
	}

	
	
	@Override
	public void addUsersInfo(UserInfo userinfo) {
		userInfoRepositoryRepository.save(userinfo);
	}


	public void editUsersById(@Valid UserInfoEditRequest userinfoeditRequest) throws Exception {
		
		Optional<UserInfo> userinfoEntity = userInfoRepositoryRepository.findById(userinfoeditRequest.getId());
		if(userinfoEntity.isPresent()) {
			UserInfo userinfosave = userinfoEntity.get();
			userinfosave.setClientId(userinfoeditRequest.getClientId());
			userinfosave.setUserFname(userinfoeditRequest.getUserFname());
			userinfosave.setUserMname(userinfoeditRequest.getUserMname());
			userinfosave.setUserLname(userinfoeditRequest.getUserLname());
			userinfosave.setEmail(userinfoeditRequest.getEmail());
			userinfosave.setEmpId(userinfoeditRequest.getEmpId());
			userinfosave.setAccessTypeId(userinfoeditRequest.getAccessTypeId());
			userinfosave.setPhoneNo(userinfoeditRequest.getPhoneNo());
			userinfosave.setIsActive(userinfoeditRequest.getIsActive());
			userinfosave.setModifiedBy(userinfoeditRequest.getModifiedBy());
			userinfosave.setModifiedDate(userinfoeditRequest.getModifiedDate());
			userinfosave.setIsDelete(userinfoeditRequest.getIsDelete());
			userInfoRepositoryRepository.save(userinfosave);
			
		}else {
			throw new Exception("User not found with the provided ID : "+userinfoeditRequest.getId());
		}
		
	}
	
	public void deleteUsersById(Long id, String modifiedBy) throws Exception {
	Optional<UserInfo> userinfo = userInfoRepositoryRepository.findById(id);
	if (userinfo.isPresent()) {
		UserInfo userinfoEntitiy = userinfo.get();
		short delete = 1;
		userinfoEntitiy.setIsDelete(delete);
		userinfoEntitiy.setModifiedBy(modifiedBy);
		userInfoRepositoryRepository.save(userinfoEntitiy);

	}else {
		log.info("No User Info found with the provided ID{} in the DB",id);
		throw new Exception("No UserInfo found with the provided ID in the DB :"+id);
	}
	
	
}
	
	
	
	
	@Override
	public void EditPasswordByEmailId(@Valid PasswordEditRequest passwordeditRequest) throws Exception {
		
		Optional<UserInfo> userinfoEntit = userInfoRepositoryRepository.findById(passwordeditRequest.getId());
		UserInfo useremail = userInfoRepositoryRepository.findByEmail(passwordeditRequest.getEmail());
		//String salt = PasswordUtil.getSalt();
		if(useremail!= null && userinfoEntit.isPresent()) {
			
			UserInfo userPassSave = userinfoEntit.get();
		 //if (PasswordUtil.verifyUserPassword((passwordeditRequest.getOldPassword()), (userPassSave.getPassword()), (salt))) {
		//if ((PasswordUtil.getSecurePassword(passwordeditRequest.getOldPassword())).equals(userPassSave.getPassword())) {
			
			if ((passwordeditRequest.getOldPassword()).equals(userPassSave.getPassword())) {
			userPassSave.setPassword(passwordeditRequest.getNewPassword());
			userPassSave.setPassword(passwordeditRequest.getConfirmPassword());
			if(passwordeditRequest.getNewPassword().equals(passwordeditRequest.getConfirmPassword())) {
			
				//	String salt = PasswordUtil.getSalt();
				//userPassSave.setPassword(PasswordUtil.getSecurePassword(userPassSave.getPassword()));
				//userPassSave.setPassword(userPassSave);
			
				userInfoRepositoryRepository.save(userPassSave);
			}
			
			else {
				throw new Exception("New Password and confirm password doesn't match: ");
				
			}
		}
		else {
			throw new Exception("Old Password doesnt match with the current password : ");
			
		}
		}
		else {
			throw new Exception("User doesnt exist : ");
			
		}
	}
	
	
	
}
