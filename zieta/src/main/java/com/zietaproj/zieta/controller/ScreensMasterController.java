package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.service.ScreensMasterService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "Screens API")
public class ScreensMasterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScreensMasterController.class);

	@Autowired
	ScreensMasterService screensmasterService;

	// Get All Screens
	@RequestMapping(value = "getAllScreens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ScreensMasterDTO> getAllScreens() {
		List<ScreensMasterDTO> screensMasters = null;
		try {
			screensMasters = screensmasterService.getAllScreens();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ScreensMasterController#getAllScreens",e);
		}
		return screensMasters;
	}

	@RequestMapping(value = "addScreensmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addScreensmaster(@Valid @RequestBody ScreensMaster screensmaster) {
		screensmasterService.addScreensmaster(screensmaster);
	}
}
