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
import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;
import com.zietaproj.zieta.service.AccessTypeMasterService;


@RestController
@RequestMapping("/api")
public class AccessTypeMasterController {

	
	@Autowired
	AccessTypeMasterService accesstypemasterService;
	// Get All Tasks
	@GetMapping("/getAllAccesstypes")
	public String getAllAccesstypes() {
		String response="";
		try {
			List<AccessTypeMasterDTO> accesstypeMasters= accesstypemasterService.getAllAccesstypes();
			System.out.println("AccessTypeMasters size=>"+accesstypeMasters.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(accesstypeMasters);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// Create a new AccessType
	  
	//  @PostMapping("/addAccessTypemaster") 
	@RequestMapping(value = "addAccessTypemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	  public void addAccessTypemaster(@Valid @RequestBody AccessTypeMaster accesstypemaster) { 
	   accesstypemasterService.addAccessTypemaster(accesstypemaster);
	   }
}
