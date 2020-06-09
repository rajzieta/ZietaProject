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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;
import com.zietaproj.zieta.response.ProjectsByClientResponse;
import com.zietaproj.zieta.response.RolesByClientResponse;
import com.zietaproj.zieta.service.ProjectMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Projects API")
public class ProjectMasterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectMasterController.class);
	
	@Autowired
	ProjectMasterService projectmasterService;

	@RequestMapping(value = "getAllProjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProjectMasterDTO> getAllProjects() {
		List<ProjectMasterDTO> projectMasters = null;

		try {
			projectMasters = projectmasterService.getAllProjects();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ProjectMasterController#getAllProjects",e);
		}
		return projectMasters;
	}

	@RequestMapping(value = "addProjectmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addProjectmaster(@Valid @RequestBody ProjectMaster projectmaster) {
		projectmasterService.addProjectmaster(projectmaster);
	}

	@ApiOperation(value = "List projects based on the  userId", notes = "Table reference: project_user_mapping,"
			+ " org_info, user_info, cust_info, client_info")
	@RequestMapping(value = "getAllProjectsByUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectDetailsByUserModel>> getAllProjectsByUser(@RequestParam(required = true) Long  userId) {
		try {
			List<ProjectDetailsByUserModel> projectByUserDetails = projectmasterService.getProjectsByUser(userId);

			return new ResponseEntity<List<ProjectDetailsByUserModel>>(projectByUserDetails, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ProjectDetailsByUserModel>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllProjectsByClient")
	@ApiOperation(value = "List Projects based on the clientId", notes = "Table reference: project_type_master")
	public ResponseEntity<List<ProjectsByClientResponse>> getAllProjectsByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<ProjectsByClientResponse> projectsbyclientList = projectmasterService.getProjectsByClient(clientId);
			return new ResponseEntity<List<ProjectsByClientResponse>>(projectsbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ProjectsByClientResponse>>(HttpStatus.NOT_FOUND);
		}
	}
}
