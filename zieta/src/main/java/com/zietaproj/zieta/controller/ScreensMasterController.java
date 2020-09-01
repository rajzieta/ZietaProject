package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(tags = "Screens API")
public class ScreensMasterController {

	@Autowired
	ScreensMasterService screensmasterService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ScreensMasterController.class);

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
	
	
	@GetMapping("/getAllScreensByScreenCategory")
	public ResponseEntity<List<ScreensMasterDTO>> getAllScreensByScreenCategory(@RequestParam(required=true) String screenCategory) {
		try {
			List<ScreensMasterDTO> screensbyCategoryList = screensmasterService.getAllScreensByCategory(screenCategory);
			return new ResponseEntity<List<ScreensMasterDTO>>(screensbyCategoryList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ScreensMasterDTO>>(HttpStatus.NOT_FOUND);
		} 
	}

	@RequestMapping(value = "addScreensmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addScreensmaster(@Valid @RequestBody ScreensMasterAddRequest screensmaster) {
		screensmasterService.addScreensmaster(screensmaster);
	}

	// Delete a screen maser based on id
	@DeleteMapping("/deleteScreensMaster/{id}")
	public void deleteScreensMaster(@PathVariable long id) {
		screensmasterService.deleteById(id);
	}

	@RequestMapping(value = "updateScreenMaster", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateScreenMaster(@Valid @RequestBody ScreensMasterEditRequest screensmaster) {
		screensmasterService.updateScreensmaster(screensmaster);
	}



}
