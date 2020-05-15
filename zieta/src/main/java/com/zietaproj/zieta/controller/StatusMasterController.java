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
import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.service.StatusMasterService;

@RestController
@RequestMapping("/api")
public class StatusMasterController {

@Autowired
	StatusMasterService statusMasterService;
	
	// Get All Status
		@GetMapping("/getAllStatus")
		public String getAllStatus() {
			String response="";
			try {
				List<StatusMasterDTO> statusMasters= statusMasterService.getAllStatus();
				System.out.println("StatusMasters size=>"+statusMasters.size());
				ObjectMapper mapper = new ObjectMapper();
				response = mapper.writeValueAsString(statusMasters);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return response;
		}
		
		// Create a new Status
		  
		 // @PostMapping("/addStatusmaster") 
		@RequestMapping(value = "addStatusmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

		  public void addStatusmaster(@Valid @RequestBody StatusMaster statusmaster) { 
		   statusMasterService.addStatusmaster(statusmaster);
		   }

}