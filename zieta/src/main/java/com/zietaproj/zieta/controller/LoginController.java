package com.zietaproj.zieta.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.request.LoginRequest;
import com.zietaproj.zieta.response.LoginResponse;
import com.zietaproj.zieta.response.UserDetailsResponse;
import com.zietaproj.zieta.service.UserInfoService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	UserInfoService userInfoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	// Get All Users
	@GetMapping("/getAllUsers")
	public String getAllUserDetails() {
		String response = "";
		try {
			List<UserInfoDTO> allUserData = userInfoService.getAllUserInfoDetails();
			System.out.println("UsersList size=>" + allUserData.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(allUserData);
		} catch (Exception e) {
			LOGGER.error("Error Occured in getting all user details",e);
		}
		return response;
	}

	@PostMapping("/authenticate")
	public LoginResponse doAuthenticate(@RequestBody LoginRequest loginData) {
		return userInfoService.authenticateUser(loginData.getUserEmailId(), loginData.getPassword());

	}

	@PostMapping("/getUserData")
	public UserDetailsResponse doAuthorize(@RequestBody String userEmailId) {

		return userInfoService.getUserData(userEmailId);

	}

}
