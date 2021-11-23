package com.zieta.tms.byd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.byd.service.BYDProjectService;
//import com.zieta.tms.byd.service.BYDProjectService;
//import com.zieta.tms.byd.connectivity.service.BYDProjectService;
import com.zieta.tms.dto.ConnectionMasterInfoDTO;
import com.zieta.tms.service.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "BYD Project Details API")
public class ProjectController {
	
@Autowired	
UserInfoService userInfoService;
//BYDProjectService bydProjectService;


	
	@RequestMapping(value = "getAllBYDProject", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "lists BYD Project ",notes="Table reference: ")
	public List<Object> getAllBYDProject(@RequestParam(required = true) long clientId) {
		List<Object> bydProjectData = null;
		try {
			System.out.println("in controller====>");
			///bydProjectData=	userInfoService.getAllBYDProject();temp comment
			System.out.println("after ccalled====>");
		} catch (Exception e) {
			
			//LOGGER.error("Error Occured in getting user details based on clientId",e);
		}
		return bydProjectData;
	}
	

}
