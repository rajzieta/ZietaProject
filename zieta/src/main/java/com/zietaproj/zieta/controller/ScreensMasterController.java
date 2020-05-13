package com.zietaproj.zieta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.ScreensMasterDTO;
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
}
