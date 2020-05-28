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
import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.service.ScreensMasterService;


@RestController
@RequestMapping("/api")
public class ScreensMasterController {

	@Autowired
	ScreensMasterService screensmasterService;
	
	// Get All Screens
		@GetMapping("/getAllScreens")
		public String getAllScreens() {
			String response="";
			try {
				List<ScreensMasterDTO> screensMasters= screensmasterService.getAllScreens();
				System.out.println("ScreensMasters size=>"+screensMasters.size());
				ObjectMapper mapper = new ObjectMapper();
				response = mapper.writeValueAsString(screensMasters);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return response;
		}
		
		@RequestMapping(value = "addScreensmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

		  public void addScreensmaster(@Valid @RequestBody ScreensMaster screensmaster) { 
		   screensmasterService.addScreensmaster(screensmaster);
		   }
}
