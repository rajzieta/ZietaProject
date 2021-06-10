package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.UserQualificationDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserQualification;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserQualificationEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.request.UserQualificationEditRequest;

public interface UserQualificationService {
	
	
	public AddUserResponse addUsersQualification(@Valid List<UserQualification> userQualification) throws Exception;
	
	List<UserQualification> findByUserId(@Valid long userId);
	
	ResponseMessage editUserQualificationByUserId(List<UserQualification> userQualificationList);
	
	
}
