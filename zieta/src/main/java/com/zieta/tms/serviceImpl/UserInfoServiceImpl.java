package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.common.MessagesConstants;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.AccessTypeScreenMappingRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.MessageMasterRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.repository.UserDetailsRepository;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.service.AccessTypeMasterService;
import com.zieta.tms.service.ScreensMasterService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

	
	@Autowired
	UserInfoRepository userInfoRepositoryRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
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
	OrgInfoRepository orgInfoRepository;
	
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
			if(userInfo.getOrgNode() !=null) {
				userInfoDTO.setOrgNodeName(orgInfoRepository.findById(userInfo.getOrgNode()).get().getOrgNodeName());
			}
		
			String prjMgrName = StringUtils.EMPTY;
			String rempId = StringUtils.EMPTY;
			if(userInfo.getReportingMgr() !=null) {
				Optional<UserInfo> userInfos = userInfoRepositoryRepository.findById(userInfo.getReportingMgr());
				if (userInfos.isPresent()) {
					prjMgrName = TSMUtil.getFullName(userInfos.get());
					 rempId= userInfos.get().getEmpId();
					// otherdetails
					userInfoDTO.setReportingMgrName(prjMgrName);
					userInfoDTO.setReportingMgrEmpId(rempId);
				}
			}
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
		String reportingManager = StringUtils.EMPTY;
		
		if(userInfo.getReportingMgr() != null) {
			UserInfo rmInfo = userInfoRepositoryRepository.findById(userInfo.getReportingMgr()).get();
			reportingManager = TSMUtil.getFullName(rmInfo);
		}
		
		 List<Long> accessControlConfigList = accessControlConfigRepository.
				 findByClientIdANDAccessTypeId(userInfo.getClientId(), userInfo.getAccessTypeId());
		 List<ScreensMaster> screensListByClientId = screensMasterService.getScreensByIds(accessControlConfigList);
		 List<String> accessTypes = accessTypeMasterService.findByClientIdANDAccessTypeId(userInfo.getClientId(), userInfo.getAccessTypeId());
		 UserDetailsResponse userDetails = fillUserData(userInfo);
		 userDetails.setReportingManagerName(reportingManager);
		 userDetails.setClientCode(clientInfoRepo.findById(userInfo.getClientId()).get().getClientCode());
		 userDetails.setClientDescription(clientInfoRepo.findById(userInfo.getClientId()).get().getClientName());
		 if(userInfo.getOrgNode() !=null) {
			 userDetails.setOrgNodeName(orgInfoRepository.findById(userInfo.getOrgNode()).get().getOrgNodeName());
		 }
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
		userDetailsResponse.setEmpId(userInfo.getExtId());
		if(userInfo.getOrgNode() !=null) {
			userDetailsResponse.setOrgNode(userInfo.getOrgNode());
		}
		if(userInfo.getReportingMgr() !=null) {
			userDetailsResponse.setReportingMgr(userInfo.getReportingMgr());
		}
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
	public AddUserResponse addUsersInfo(UserInfo userinfo) throws Exception {
		
		short notDeleted = 0;
		AddUserResponse addUserResponse = new AddUserResponse();		
		UserInfo userinfoEntities = userInfoRepositoryRepository.findByEmailAndIsDelete(userinfo.getEmail(),notDeleted);		
		if(userinfoEntities !=null) {
			//set email id already exist validation
			addUserResponse.setInfoMessage("EmailId "+userinfoEntities.getEmail()+" already in use ");
			addUserResponse.setStatus("501");
			//log.info("EmailId is already in use ",userinfoEntities.getEmail());
			return addUserResponse;
			//throw new Exception("Emailid is already in use " +userinfoEntities.getEmail());
		}else{
			
			 addUserResponse.setInfoMessage("User Information Saved successfully ");
			 addUserResponse.setStatus("201");
			 userInfoRepositoryRepository.save(userinfo);	
			 return addUserResponse;
		}
	}
	
	//TO SAVE ADDUSERDETAILS 
	@Override
	public UserDetails addUserDetails(UserDetails userDetails) throws Exception {
		
		short notDeleted = 0;
				
		try {			
			 userDetailsRepository.save(userDetails);				
			
		}catch(Exception e) {
			
		}
			
		return userDetails;
		
	}
	
	
	
	
	
	
	private boolean emailExist(String email) {
		//boolean isValid = fa
		UserInfo userin = userInfoRepositoryRepository.findByEmail(email);
		if(userin!=null) {
			return true;
		}
		return false;
	}
//

	public void editUsersById(@Valid UserInfoEditRequest userinfoeditRequest) throws Exception {
		
		Optional<UserInfo> userinfoEntity = userInfoRepositoryRepository.findById(userinfoeditRequest.getId());
		if(userinfoEntity.isPresent()) {
			UserInfo userInfo = userinfoEntity.get();
			 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
			 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			 modelMapper.map(userinfoeditRequest,userInfo );
			userInfoRepositoryRepository.save(userInfo);
			
		}else {
			throw new Exception("User not found with the provided ID : "+userinfoeditRequest.getId());
		}
		
	}
	
/*
 * UPDATE USER DETAILS BY USER ID
 * 
 */
public ResponseMessage editUserDetailsByUserId(@Valid UserDetailsEditRequest userDetailsEditRequest) throws Exception {	
	try {
		
		ResponseMessage resMsg = new ResponseMessage();
		Optional<UserDetails> userDetailsEntity = userDetailsRepository.findByUserId(userDetailsEditRequest.getUserId());
		if(userDetailsEntity.isPresent()) {
			UserDetails userDetails = userDetailsEntity.get();
			 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
			 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			 modelMapper.map(userDetailsEditRequest,userDetails );
			 userDetailsRepository.save(userDetails);			
			resMsg.setMessage("User Details Successfully Updated...");
			resMsg.setStatus(200);
			return resMsg;
			
		}else {
			throw new Exception("User Details not found with the provided User ID : "+userDetailsEditRequest.getUserId());
		}
	}catch(Exception e) {
		ResponseMessage resMsg = new ResponseMessage();
		resMsg.setMessage("Failed to update User Details!!!");
		resMsg.setStatus(403);
		return resMsg;		
	}		
}

@Override
public UserDetailsDTO findUserDetailsByUserId(long userId) {
	UserDetailsDTO userDetailsDTO = null;
	UserDetails userDetails = userDetailsRepository.findUserDetailsByUserId(userId);
	if(userDetails !=null) {
		userDetailsDTO =  modelMapper.map(userDetails, UserDetailsDTO.class);
	}
	return userDetailsDTO;
	
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

	@Override
	public UserInfoDTO findByUserId(long userId) {
		UserInfoDTO userInfoDTO = null;
		UserInfo userInfo = userInfoRepositoryRepository.findById(userId).get();
		if(userInfo !=null) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
			String reportingManager = StringUtils.EMPTY;
			userInfoDTO.setPassword(StringUtils.EMPTY);
			
			if(userInfo.getReportingMgr() != null) {
				UserInfo rmInfo = userInfoRepositoryRepository.findById(userInfo.getReportingMgr()).get();
				reportingManager = TSMUtil.getFullName(rmInfo);
				userInfoDTO.setReportingMgrEmpId(rmInfo.getEmpId());
			}
			userInfoDTO.setReportingMgrName(reportingManager);
			ClientInfo clientInfo = clientInfoRepo.findById(userInfo.getClientId()).get();
			userInfoDTO.setClientCode(clientInfo.getClientCode());
			userInfoDTO.setClientDescription(clientInfo.getClientName());
			userInfoDTO.setClientStatus(clientInfo.getClientStatus());
			 if(userInfo.getOrgNode() !=null) {
				 userInfoDTO.setOrgNodeName(orgInfoRepository.findById(userInfo.getOrgNode()).get().getOrgNodeName());
			 }
		}
		return userInfoDTO;
	}
	
}
