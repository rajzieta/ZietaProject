package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.RoleMasterDTO;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.response.RolesByClientResponse;
import com.zietaproj.zieta.service.RoleMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Roles API")
public class RoleMasterController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleMasterController.class);
	
	@Autowired
	RoleMasterService rolemasterService;

	@RequestMapping(value = "getAllUserRoles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RoleMasterDTO> getAllUserRoles() {
		List<RoleMasterDTO> roleMasterList = null;
		try {
			roleMasterList = rolemasterService.getAllRoles();
		} catch (Exception e) {
			LOGGER.error("Error Occured in RoleMasterController#getAllRoles",e);
		}
		return roleMasterList;
	}

	@RequestMapping(value = "addUserRoleMaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addUserRoleMaster(@Valid @RequestBody RoleMaster rolemaster) {
		rolemasterService.addRolemaster(rolemaster);
	}
	
	@GetMapping("/getAllUserRolesByClient")
	@ApiOperation(value = "List Roles based on the clientId", notes = "Table reference: role_master")
	public ResponseEntity<List<RolesByClientResponse>> getAllUserRolesByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<RolesByClientResponse> rolesbyclientList = rolemasterService.getRolesByClient(clientId);
			return new ResponseEntity<List<RolesByClientResponse>>(rolesbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<RolesByClientResponse>>(HttpStatus.NOT_FOUND);
		}
	}

}
