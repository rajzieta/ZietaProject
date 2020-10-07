package com.zieta.tms.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.ProcessStepsDTO;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.request.LoginRequest;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.service.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "User Details API")
public class UserController {

	@Autowired
	UserInfoService userInfoService;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "getAllUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserInfoDTO> getAllUserDetails() {
		List<UserInfoDTO> allUserData = null;
		try {
			allUserData = userInfoService.getAllUserInfoDetails();
			LOGGER.info("Total number of users: "+allUserData.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getting all user details",e);
		}
		return allUserData;
	}

	@PostMapping("/authenticate")
	public LoginResponse doAuthenticate(@RequestBody LoginRequest loginData) {
		return userInfoService.authenticateUser(loginData.getUserEmailId(), loginData.getPassword());

	}

	
	@RequestMapping(value = "getUserData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides user associated data, once login is SUCCESS",notes="Table reference: user_info,"
			+ " accesstype_user_mapping, access_ctrl_config, screen_master, access_type_master")
	public UserDetailsResponse doAuthorize(@RequestParam(required = true) String userEmailId) {
		UserDetailsResponse userDetails = userInfoService.getUserData(userEmailId);
		
		return userDetails;
	}
	
	
	@RequestMapping(value = "getAllUsersByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "lists userdetails based on the provided clientId",notes="Table reference: user_info,"
			+ "client_info")
	public List<UserInfoDTO> getAllUsersByClient(@RequestParam(required = true) long clientId) {
		List<UserInfoDTO> userDataList = null;
		try {
			userDataList = userInfoService.findByClientId(clientId);
		} catch (Exception e) {
			LOGGER.error("Error Occured in getting user details based on clientId",e);
		}
		return userDataList;
	}



	@ApiOperation(value = "creates entries in the user_info table", notes = "Table reference: user_info")
	@RequestMapping(value = "addUsersInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addUsersInfo(@Valid @RequestBody UserInfo userinfo) {
		userInfoService.addUsersInfo(userinfo);
	}

	
	
	@ApiOperation(value = "Updates the Users Information for the provided Id", notes = "Table reference: user_info")
	@RequestMapping(value = "editUsersById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editUsersById(@Valid @RequestBody UserInfoEditRequest userinfoeditRequest) throws Exception {
		userInfoService.editUsersById(userinfoeditRequest);
		
		
	}
	
	
	@ApiOperation(value = "Deletes entries from user_info based on Id", notes = "Table reference: user_info")
	@RequestMapping(value = "deleteUsersById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUsersById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		userInfoService.deleteUsersById(id, modifiedBy);
	}
	
	///change password
	
	@ApiOperation(value = "Change the password for the provided emailId", notes = "Table reference: user_info")
	@RequestMapping(value = "EditPasswordByEmailId", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void EditPasswordByEmailId(@Valid @RequestBody PasswordEditRequest passwordeditRequest) throws Exception {
		userInfoService.EditPasswordByEmailId(passwordeditRequest);
		
	}
	
	
	
	
	
	
}
