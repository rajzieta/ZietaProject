package com.zieta.tms.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.dto.ConnectionMasterInfoDTO;
import com.zieta.tms.dto.ProcessStepsDTO;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.UserQualificationDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserQualification;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.request.ConnectionMasterInfoEditRequest;
import com.zieta.tms.request.LoginRequest;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserQualificationEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.AddResponse;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.service.ConnectionMasterInfoService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.service.UserQualificationService;
import com.zieta.tms.serviceImpl.UserInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "Connection Master Details API")
public class ConnectionMasterController {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	UserQualificationService userQualificationService;
	
	@Autowired
	ConnectionMasterInfoService connectionMasterInfoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionMasterController.class);

	@RequestMapping(value = "getConnectionMasterDataById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides connection master data based on Id",notes="Table reference: connection_master")
	public ConnectionMasterInfoDTO getConnectionMasterDataById(@RequestParam(required = true) Long id) {
		ConnectionMasterInfoDTO connectionDetails = connectionMasterInfoService.findById(id);		
		return connectionDetails;
	}
	
	
	
	@RequestMapping(value = "getAllConnectionByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "lists connectionmasterdetails based on the provided clientId",notes="Table reference: connection_master")
	public List<ConnectionMasterInfoDTO> getAllconnectionByClient(@RequestParam(required = true) long clientId) {
		List<ConnectionMasterInfoDTO> connectionDataList = null;
		try {
			connectionDataList = connectionMasterInfoService.findByClientId(clientId);
		} catch (Exception e) {
			LOGGER.error("Error Occured in getting user details based on clientId",e);
		}
		return connectionDataList;
	}
	
	
	//public ResponseEntity<String> addConnectionMasterInfo(@Valid @RequestBody ConnectionMasterInfo connectionMasterInfo) {
	@ApiOperation(value = "creates entries in the connection_master table", notes = "Table reference: connection_master")
	@RequestMapping(value = "addConnectionMasterInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ConnectionMasterInfo  addConnectionMasterInfo( @RequestBody ConnectionMasterInfo connectionMasterInfo) throws Exception {
			return connectionMasterInfoService.addConnectionMasterInfo(connectionMasterInfo);
			
	}
	
	@ApiOperation(value = "Updates the connection master Information for the provided Id", notes = "Table reference: connection_master")
	@RequestMapping(value = "editConnectionMasterById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ConnectionMasterInfo editConnectionMasterById(@Valid @RequestBody ConnectionMasterInfoEditRequest connectionMasterInfoEditRequest) throws Exception {
		return connectionMasterInfoService.editConnectionMasterById(connectionMasterInfoEditRequest);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from connection_master based on Id", notes = "Table reference: connection_master")
	@RequestMapping(value = "deleteConnectionMasterById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteConnectionMasterById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		connectionMasterInfoService.deleteConnectionMasterById(id, modifiedBy);
	}
	
	
	
	
}
