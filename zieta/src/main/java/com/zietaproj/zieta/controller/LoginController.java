package com.zietaproj.zieta.controller;

//import java.awt.PageAttributes.MediaType;
import org.springframework.http.MediaType;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.dto.UserInfoDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.request.LoginRequest;
import com.zietaproj.zieta.response.UserDetailsResponse;
import com.zietaproj.zieta.service.ActivityMasterService;
import com.zietaproj.zieta.service.UserInfoService;


@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	UserInfoService userInfoService;
	// Get All Users
	@GetMapping("/getAllUsers")
	public String getAllActivities() {
		String response="";
		try {
			List<UserInfoDTO> allUserData= userInfoService.getAllUserInfoDetails();
			System.out.println("UsersList size=>"+allUserData.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(allUserData);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@PostMapping("/authenticate")
	public UserDetailsResponse doAuthenticate(@RequestBody LoginRequest loginData) {

		UserInfoDTO dbUserInfo = userInfoService.findByEmail(loginData.getUserEmailId());
		UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder().infoMessage("").build();
		if (dbUserInfo != null) {
			if (loginData.getPassword().equals(dbUserInfo.getPassword())) {
				if (dbUserInfo.getIs_active() == 1) {
					userDetailsResponse.setClientId(dbUserInfo.getClient_id());
					userDetailsResponse
							.setInfoMessage("Able to retrieve the clientId, as its active and credentials are correct");
					return userDetailsResponse;
				} else {
					userDetailsResponse.setInfoMessage("Not able to retrieve the ClientId for user: "
							+ loginData.getUserEmailId() + ", as its InActive");
					return userDetailsResponse;
				}

			} else {
				userDetailsResponse.setInfoMessage("Not able to retrieve the ClientId for user: "
						+ loginData.getUserEmailId() + ", as the provided credentials are wrong");
				return userDetailsResponse;
			}
		}else {
			userDetailsResponse.setInfoMessage("Invalid emailId provided: "+ loginData.getUserEmailId());
			return userDetailsResponse;
		}
	}

		
	
}
