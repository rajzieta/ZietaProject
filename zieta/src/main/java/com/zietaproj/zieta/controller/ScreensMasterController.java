package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.request.ScreensMasterAddRequest;
import com.zietaproj.zieta.request.ScreensMasterEditRequest;
import com.zietaproj.zieta.service.ScreensMasterService;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Transactional
@RequestMapping("/api")
@Api(tags= "ScreensMaster Information API")
public class ScreensMasterController {

	@Autowired
	ScreensMasterService screensmasterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScreensMasterController.class);
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

		  public void addScreensmaster(@Valid @RequestBody ScreensMasterAddRequest screensmaster) { 
		   screensmasterService.addScreensmaster(screensmaster);
		   }
		
		//Delete a screen maser based on id
		@DeleteMapping("/deleteScreensMaster/{id}")
		public void deleteScreensMaster(@PathVariable long id) {
			screensmasterService.deleteById(id);
}
		
//		//Delete a screen maser based on screencode
//				@DeleteMapping("/deleteScreensMaster/{screen_code}")
//				public void deleteScreensMaster(@PathVariable String screen_code) {
//					screensmasterService.deleteByScreencode(screen_code);
//		}

		

		@RequestMapping(value = "updateScreenMaster", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateScreenMaster(@Valid @RequestBody ScreensMasterEditRequest screensmaster) {
		screensmasterService.updateScreensmaster(screensmaster);
		}
		
//			// ------------ Update a Screen based on id ------------
//			@RequestMapping(value = "/updateScreenMaster/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//			public void updateScreenMaster(@RequestBody ScreensMaster screensmaster,@PathVariable Long id) {
//				screensmasterService.updateScreenMaster(id, screensmaster);
//			}
//			

		}
		
