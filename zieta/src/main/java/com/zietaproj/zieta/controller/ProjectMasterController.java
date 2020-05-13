package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.service.ProjectMasterService;


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
	
}
