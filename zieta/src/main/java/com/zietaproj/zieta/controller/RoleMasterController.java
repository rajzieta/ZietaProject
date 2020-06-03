package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.RoleMasterDTO;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.service.RoleMasterService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "Roles API")
public class RoleMasterController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleMasterController.class);
	
	@Autowired
	RoleMasterService rolemasterService;

	@RequestMapping(value = "getAllRoles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RoleMasterDTO> getAllRoles() {
		List<RoleMasterDTO> roleMasterList = null;
		try {
			roleMasterList = rolemasterService.getAllRoles();
		} catch (Exception e) {
			LOGGER.error("Error Occured in RoleMasterController#getAllRoles",e);
		}
		return roleMasterList;
	}

	@RequestMapping(value = "addRolemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addRolemaster(@Valid @RequestBody RoleMaster rolemaster) {
		rolemasterService.addRolemaster(rolemaster);
	}

}
