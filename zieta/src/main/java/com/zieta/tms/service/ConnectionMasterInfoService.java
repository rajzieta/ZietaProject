package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.ConnectionMasterInfoDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.request.ConnectionMasterInfoEditRequest;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.AddResponse;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.response.ResponseMessage;

public interface ConnectionMasterInfoService {
	
	public ConnectionMasterInfoDTO findById(long id);
	
	public List<ConnectionMasterInfoDTO> findByClientId(Long client_id);
	
	
	public AddResponse addConnectionMasterInfo(@Valid ConnectionMasterInfo connectionMasterInfo) throws Exception;
	
	public void editConnectionMasterById(@Valid ConnectionMasterInfoEditRequest connectionMasterInfoEditRequest) throws Exception;
	
	public void deleteConnectionMasterById(Long id, String modifiedBy) throws Exception;
	
	
}
