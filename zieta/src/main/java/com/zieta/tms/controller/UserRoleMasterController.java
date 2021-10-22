package com.zieta.tms.controller;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.UserRoleMasterDTO;
import com.zieta.tms.model.UserRoleMaster;
import com.zieta.tms.service.UserRoleMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "User Role Master API")
public class UserRoleMasterController {

	private static final Logger log = LoggerFactory.getLogger(UserRoleMasterController.class);

	@Autowired
	UserRoleMasterService userRoleMasterService;

	@ApiOperation(value = "creates entries in the user_role_master table", notes = "Table reference: user_role_master")
	@RequestMapping(value = "addUserRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addUserRoleMaster(@Valid @RequestBody UserRoleMaster userRoleMaster) {
		userRoleMasterService.addUserRoleMaster(userRoleMaster);
	}
	

	
	@RequestMapping(value = "getAllUserRoleMaster", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserRoleMasterDTO> getAllUserRoleMaster() {
		List<UserRoleMasterDTO> userRoles = null;
		try {
			userRoles = userRoleMasterService.getAllUserRoleMaster();
		} catch (Exception e) {
			log.error("Error Occured in UserRoleMasterController#getAllUserRoleMaster",e);
		}
		System.out.println("userRoles ===>"+userRoles);
		return userRoles;
	}
	
	@ApiOperation(value = "Deletes entries from user_role_master based on Id", notes = "Table reference: user_role_master")
	@RequestMapping(value = "deleteUserRoleMasterById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserRoleMasterById(@RequestParam(required=true) Long id) throws Exception {
		userRoleMasterService.deleteUserRoleMasterById(id);
	}
	
	@ApiOperation(value = "Updates the userRoleMaster for the provided Id", notes = "Table reference: user_role_master")
	@RequestMapping(value = "editUserRoleMasterById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editUserRoleMasterById(@Valid @RequestBody UserRoleMasterDTO userRoleMasterDTO) throws Exception {
		userRoleMasterService.editUserRoleMasterById(userRoleMasterDTO);
		
		
	}
}
