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
import com.zieta.tms.dto.UserQualificationDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserQualification;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.AccessTypeScreenMappingRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.MessageMasterRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.repository.UserQualificationRepository;
import com.zieta.tms.repository.UserDetailsRepository;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserQualificationEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.service.AccessTypeMasterService;
import com.zieta.tms.service.ScreensMasterService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.service.UserQualificationService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class UserQualificationServiceImpl implements UserQualificationService {

	
	
	@Autowired
	UserQualificationRepository userQualificationRepository;
	
	//@Autowired
	//UserQualificationEditRequest UserQualificationEditRequest;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public AddUserResponse addUsersQualification(@Valid List<UserQualification> userQualificationList) throws Exception {
		
		AddUserResponse addUserResponse = new AddUserResponse();
		try {
			 addUserResponse.setInfoMessage("User Information Saved successfully ");
			 addUserResponse.setStatus("201");
			 for(UserQualification userQualification :userQualificationList) {
				 userQualificationRepository.save(userQualification);
			 }
			 
		}catch(Exception e) {
			addUserResponse.setInfoMessage("Failed to Save Data ");
			addUserResponse.setStatus("501");			
		}
		return addUserResponse;

	}


	@Override
	public List<UserQualification> findByUserId(@Valid long userId) {
		
		List<UserQualification> userQualificationList = userQualificationRepository.findByUserId(userId);
		return userQualificationList;
	}


	@Override
	public ResponseMessage editUserQualificationByUserId(List<UserQualification> userQualificationList) {
			ResponseMessage resMsg = new ResponseMessage();
			try {
				Boolean saveData =false;
				Boolean updateData = false;	
				for(UserQualification userQualification :userQualificationList) {				
				Optional<UserQualification> userQualificationEntity = userQualificationRepository.findById(userQualification.getId());
				
				if(userQualificationEntity.isPresent()) {
						UserQualification userQul = userQualificationEntity.get();
						 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
						 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
						 modelMapper.map(userQualification,userQul );
						 userQualificationRepository.save(userQul);			
						resMsg.setMessage("User Qualification Successfully Updated...");
						resMsg.setStatus(200);
						updateData = true;	
						
					}else {
						userQualificationRepository.save(userQualification);
						resMsg.setMessage("User Qualification Successfully Updated...");
						resMsg.setStatus(200);
						saveData = true;
					}
				if(updateData && saveData)
					resMsg.setMessage("User Qualification Successfully Updated & saved...");
					
				}
			}catch(Exception e) {				
				resMsg.setMessage("Failed to update User Details!!!");
				resMsg.setStatus(403);
						
			}		
		 
			return resMsg;
	}


	
	
}
