package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;
import com.zietaproj.zieta.service.ProjectMasterService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api")
public class ProjectMasterController {
	@Autowired
	ProjectMasterService projectmasterService;
	// Get All Projects
	@GetMapping("/getAllProjects")
	public String getAllProjects() {
		String response="";
		try {
			List<ProjectMasterDTO> projectMasters= projectmasterService.getAllProjects();
			System.out.println("ProjectMasters size=>"+projectMasters.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(projectMasters);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	 // Create a new Task
	  
	  //@PostMapping("/addProjectmaster") 
	@RequestMapping(value = "addProjectmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	  public void addProjectmaster(@Valid @RequestBody ProjectMaster projectmaster) { 
	   projectmasterService.addProjectmaster(projectmaster);
	   }	
	
	@ApiOperation(value = "List projects based on the  userId",notes="Table reference: project_user_mapping,"
			+ " org_info, user_info, cust_info, client_info")
	@PostMapping("/getAllProjectsByUser")
	public ResponseEntity<List<ProjectDetailsByUserModel>> getAllProjectsByUser(@RequestBody Long userId) {
		try {
			List<ProjectDetailsByUserModel> projectByUserDetails = projectmasterService.getProjectsByUser(userId);

			return new ResponseEntity<List<ProjectDetailsByUserModel>>(projectByUserDetails, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ProjectDetailsByUserModel>>(HttpStatus.NOT_FOUND);
		}
	}
}
