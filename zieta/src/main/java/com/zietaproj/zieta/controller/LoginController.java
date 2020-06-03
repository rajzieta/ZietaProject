package com.zietaproj.zieta.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.request.LoginRequest;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;
import com.zietaproj.zieta.service.UserAccessTypeService;
import com.zietaproj.zieta.service.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Login API")
public class LoginController {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	UserAccessTypeService userAccessTypeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

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

	
	@PostMapping("/getUserData")
	@ApiOperation(value = "provides user associated data, once login is SUCCESS",notes="Table reference: user_info,"
			+ " user_accesstype_mapping, access_ctrl_config, screen_master, access_type_master")
	public UserDetailsResponse doAuthorize(@RequestBody String userEmailId) {
		UserDetailsResponse userDetails = userInfoService.getUserData(userEmailId);
		
		return userDetails;

	}

}
