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

import com.zieta.tms.dto.ProcessStepsDTO;
import com.zieta.tms.dto.UserConfigDTO;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.UserQualificationDTO;
import com.zieta.tms.dto.UsersInfoDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserQualification;
import com.zieta.tms.model.UserConfig;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.request.LoginRequest;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserConfigEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserQualificationEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.service.UserConfigService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.service.UserQualificationService;
import com.zieta.tms.serviceImpl.UserInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "User Details API")
public class UserController {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	UserConfigService userConfigService;
	
	@Autowired
	UserQualificationService userQualificationService;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "getAllUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserInfoDTO> getAllUserDetails() {
		List<UserInfoDTO> allUserData = null;
		System.out.println("in controller====>");
		/*try {
			
			allUserData = userInfoService.getAllUserInfoDetails();
			LOGGER.info("Total number of users: "+allUserData.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getting all user details",e);
		}
		return allUserData;*/
		return null;
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
	

	@RequestMapping(value = "getUserDataById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides user data based on userId",notes="Table reference: user_info, client_info, org_info")
	public UserInfoDTO getUserDataById(@RequestParam(required = true) Long userId) {
		UserInfoDTO userDetails = userInfoService.findByUserId(userId);
		
		return userDetails;
	}
	/*
	 * GET USER INFO BY ID
	 */
	@RequestMapping(value = "getUsersInfoById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides user data based on userId",notes="Table reference: user_info, client_info, org_info")
	public UsersInfoDTO getUsersInfoById(@RequestParam(required = true) Long userId) {
		UsersInfoDTO userDetails = userInfoService.findByUsersId(userId);
		
		return userDetails;
	}
	
	/*
	 * 
	 * GET USER CONFIG DATA BY ID
	 */
	
	@RequestMapping(value = "getUserConfigDataById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides user config data based on id",notes="Table reference: user_info, user_config")
	public UserConfigDTO getUserConfigDataById(@RequestParam(required = true) Long id) {
		short isDelete =0;
		UserConfigDTO userConfigDetails;
		try {
			userConfigDetails = userConfigService.findUserConfigById(id, isDelete);
			return userConfigDetails;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
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
	
	@RequestMapping(value = "getAllQualificationByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "lists userqualification based on the provided userId",notes="Table reference: user_qualification,"
			+ "client_info")
	public List<UserQualification> getAllQualificationByUserId(@RequestParam(required = true) long userId) {
		List<UserQualification> userQualificationDataList = null;
		try {
			userQualificationDataList = userQualificationService.findByUserId(userId);
		} catch (Exception e) {
			LOGGER.error("Error Occured in getting user details based on clientId",e);
		}
		return userQualificationDataList;
	}	
	
	@ApiOperation(value = "creates entries in the user_info table", notes = "Table reference: user_info")
	@RequestMapping(value = "addUsersInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public AddUserResponse addUsersInfo(@Valid @RequestBody UserInfo userinfo) {
		//public ResponseEntity<String> addUsersInfo(@Valid @RequestBody UserInfo userinfo) {
		try {
			AddUserResponse response =userInfoService.addUsersInfo(userinfo);
			/*if(response.getStatus()=="501") {				
				 return new ResponseEntity<>("EmailId already exist", 
						   HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				return new ResponseEntity<>("result successful result", 
						   HttpStatus.OK);
			}*/
			//return userInfoService.addUsersInfo(userinfo);
			return response;
			
		}catch(Exception e) {
			
		}
		return null;
		
	}
	/*
	 * ADD USER DETAILS DATA WITH RESPECT OT USER ID
	 */
	@ApiOperation(value = "creates user details entries in the user_details table", notes = "Table reference: user_details")
	@RequestMapping(value = "addUserDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDetails addUserDetails(@Valid @RequestBody UserDetails userDetails) {
		try {
			 	userInfoService.addUserDetails(userDetails);			 
			 	//userDetails = userInfoService.getUserDetailsByUserId(userDetails.getUserId());
		}catch(Exception e) {
			
		}
		return userDetails;
	}
	
	/*
	 * 
	 * ADD USER CONFIG
	 */
	@ApiOperation(value = "creates user config entries in the user_config table", notes = "Table reference: user_config")
	@RequestMapping(value = "addUserconfig", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserConfig addUserconfig(@Valid @RequestBody UserConfig userConfig) {
		try {
			userConfigService.addUserConfig(userConfig);			 
			 	//userDetails = userInfoService.getUserDetailsByUserId(userDetails.getUserId());
		}catch(Exception e) {
			
		}
		return userConfig;
	}	
	
	
	
	@ApiOperation(value = "creates entries in the user_qualification table", notes = "Table reference: user_qualification")
	@RequestMapping(value = "addUsersQualification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public AddUserResponse addUsersQualification(@Valid @RequestBody List<UserQualification> userQualificationList) {
		try {
			return userQualificationService.addUsersQualification(userQualificationList);
		}catch(Exception e) {
			
		}
		return null;
	}
	
	

	/*
	 * GET USER DETAILS FORM user_details TABLE USING USER ID
	 */
	@RequestMapping(value = "getUserDetailsByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "provides user details based on userId",notes="Table reference: user_details")
	public UserDetailsDTO getUserDetailsByUserId(@RequestParam(required = true) Long userId) {
		UserDetailsDTO userDetails = userInfoService.findUserDetailsByUserId(userId);
		
		return userDetails;
	}
	
	@ApiOperation(value = "Updates the Users Information for the provided Id", notes = "Table reference: user_info")
	@RequestMapping(value = "editUsersById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editUsersById(@Valid @RequestBody UserInfoEditRequest userinfoeditRequest) throws Exception {
		userInfoService.editUsersById(userinfoeditRequest);		
		
	}
	/*
	 * UPDATE USERDETAILS
	 * 
	 */
	@ApiOperation(value = "Updates the User Details Information for the provided User Id", notes = "Table reference: user_details")
	@RequestMapping(value = "editUserDetailsByUserId", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage editUserDetailsByUserId(@Valid @RequestBody UserDetailsEditRequest userDetailsEditRequest) throws Exception {
		return userInfoService.editUserDetailsByUserId(userDetailsEditRequest);
	}
	
	/*
	 * UPDATE USER CONFIG DATA
	 * 
	 */
	
	@ApiOperation(value = "Updates the User config Details Information for the provided User Id", notes = "Table reference: user_config")
	@RequestMapping(value = "editUserConfigByUserId", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage editUserConfigByUserId(@Valid @RequestBody UserConfigEditRequest UserConfigEditRequest) throws Exception {
		return userConfigService.editUserConfigByUserId(UserConfigEditRequest);
		//return null;
	}	
	
	
	/*
	 * UPDATE USER QUALIFICATION
	 */
	@ApiOperation(value = "Updates the User Qualification Details Information for the provided User Id", notes = "Table reference: user_qualification")
	@RequestMapping(value = "editUserQualificationByUserId", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage editUserQualificationByUserId(@Valid @RequestBody List<UserQualification> userQualificationEditRequest) throws Exception {
		return userQualificationService.editUserQualificationByUserId(userQualificationEditRequest);
		//return null;
	}	
	
	
	/*
	 * DELETE ENTRIES BASED ON USER_ING
	 */
	@ApiOperation(value = "Deletes entries from user_info based on Id", notes = "Table reference: user_info")
	@RequestMapping(value = "deleteUsersById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUsersById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		userInfoService.deleteUsersById(id, modifiedBy);
	}
	/*
	 * 
	 * DELETE USER CONFIG BY ID
	 */
	
	@ApiOperation(value = "Deletes entries from user_config based on Id", notes = "Table reference: user_config")
	@RequestMapping(value = "deleteUserConfigById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserConfigById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		userInfoService.deleteUsersById(id, modifiedBy);
	}
	
	///change password	
	@ApiOperation(value = "Change the password for the provided emailId", notes = "Table reference: user_info")
	@RequestMapping(value = "EditPasswordByEmailId", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void EditPasswordByEmailId(@Valid @RequestBody PasswordEditRequest passwordeditRequest) throws Exception {
		userInfoService.EditPasswordByEmailId(passwordeditRequest);
		
	}	
	
	
	@ApiOperation(value = "Upload Bulk users from excel file", notes = "Table reference: user_info")
	@RequestMapping(value = "UploadUsersData", method = RequestMethod.POST)	
	public void uploadUsersData(@RequestPart(required=true,value = "multipartFile") MultipartFile multipartFile,
			@RequestParam(required=true) Long clientId) {		
		System.out.println("User data upload in progress");
		userInfoService.uploadUsersData(multipartFile, clientId);
		System.out.println("Data uploaded");
		
	
	}
	
	
	
	
}
