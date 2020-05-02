package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.service.ActivityMasterService;


@RestController
@RequestMapping("/api")
public class ActivityMasterController {

	@Autowired
	ActivityMasterService activitymasterService;
	// Get All Tasks
	@GetMapping("/getAllActivities")
	public String getAllActivities() {
		String response="";
		try {
			List<ActivityMasterDTO> activityMasters= activitymasterService.getAllActivities();
			System.out.println("ActivityMasters size=>"+activityMasters.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(activityMasters);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// Create a new Task
	  
		  @PostMapping("/addActivitymaster") 
		  public void addActivitymaster(@Valid @RequestBody ActivityMaster activitymaster) { 
		   activitymasterService.addActivitymaster(activitymaster);
		   }
	
}
