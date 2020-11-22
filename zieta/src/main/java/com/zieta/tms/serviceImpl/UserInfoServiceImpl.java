package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.common.MessagesConstants;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.AccessTypeScreenMappingRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.MessageMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.service.AccessTypeMasterService;
import com.zieta.tms.service.ScreensMasterService;
import com.zieta.tms.service.UserInfoService;

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
	MessageMasterRepository messageMasterRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
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
			userInfoDTO.setClientStatus(clientInfoRepo.findById(userInfo.getClientId()).get().getClientStatus());
			userInfoDTO.setAccessType(accessTypeMasterRepo.findById(userInfo.getAccessTypeId()).get().getAccessType());
			
			 userInfoDTOs.add(userInfoDTO);
		}
	}

	@Override
	public UserInfoDTO findByEmail(String email) {
		UserInfoDTO userInfoDTO = null;
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(email);
		if(userInfo !=null) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
		}
		return userInfoDTO;
		
	}

	@Override
	public UserDetailsResponse getUserData(String emailId) {
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(emailId);
		
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
			ClientInfo clientInfo = clientInfoRepo.findById(dbUserInfo.getClientId()).get();
			Long clientStatus = clientInfo.getClientStatus();
			Boolean active = Boolean.FALSE;
			loginResponse.setActive(active);
			loginResponse.setLoginValid(active);

			if (clientStatus == 0) {
				loginResponse
						.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E103).getMsgDesc());
				return loginResponse;
			} else if (dbUserInfo.getIsActive() == 0) {
				loginResponse
						.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E102).getMsgDesc());
				return loginResponse;
			}
			active = ((dbUserInfo.getIsActive() != 0) && (clientStatus != 0));
			if (password.equals(dbUserInfo.getPassword())) {
				loginResponse.setIsSuperAdmin(clientInfo.getSuperAdmin());
				loginResponse.setActive(active);
				loginResponse.setLoginValid(active);
				return loginResponse;

			} else {
				loginResponse
						.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E101).getMsgDesc());
				return loginResponse;
			}
		} else {
			loginResponse.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E101).getMsgDesc());
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
			throw new Exception("Old Password does not match with the existing password : ");
			
		}
		}
		else {
			throw new Exception("User does not exist : ");
			
		}
	}
	
	@Override
	public List<UserInfo> getUsersByIds(List<Long> teamList){
		
		return userInfoRepositoryRepository.findAllById(teamList);
		
	}
	
}
