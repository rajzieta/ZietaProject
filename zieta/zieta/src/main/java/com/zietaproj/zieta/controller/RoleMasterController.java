package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.RoleMasterDTO;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.service.RoleMasterService;

@RestController
@RequestMapping("/api")
public class RoleMasterController {

	@Autowired
	RoleMasterService rolemasterService;
	// Get All Roles
	@GetMapping("/getAllRoles")
	public String getAllRoles() {
		String response="";
		try {
			List<RoleMasterDTO> RoleMasters= rolemasterService.getAllRoles();
			System.out.println("RoleMasters size=>"+RoleMasters.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(RoleMasters);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	 // Create a new Role
	  
	  //@PostMapping("/addRolemaster") 
	@RequestMapping(value = "addRolemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	  public void addRolemaster(@Valid @RequestBody RoleMaster rolemaster) { 
	   rolemasterService.addRolemaster(rolemaster);
	   }	
	
	
}
