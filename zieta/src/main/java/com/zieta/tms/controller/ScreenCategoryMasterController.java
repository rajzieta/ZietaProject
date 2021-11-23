package com.zieta.tms.controller;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.ExpTemplateStepsDTO;
import com.zieta.tms.dto.ExpenseTemplateDTO;
import com.zieta.tms.dto.ScreenCategoryMasterDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.request.ExpenseTemplateEditRequest;
import com.zieta.tms.service.ExpenseTemplateService;
import com.zieta.tms.service.ScreenCategoryMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Screen Category Master API")
public class ScreenCategoryMasterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScreenCategoryMasterController.class);

	
	@Autowired
	ScreenCategoryMasterService screenCategoryMasterService;
	
	/*
	 * FIND ALL SCREEN MASTER
	 */

	@ApiOperation(value = "List Screen Category Master Info", notes = "Table reference:screen_category_master")
	@RequestMapping(value = "getAllScreenCategoryMaster", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ScreenCategoryMasterDTO> getAllScreenCategoryMaster() {
		
		List<ScreenCategoryMasterDTO> screenCategoryMaster = null;
		try {
				screenCategoryMaster = screenCategoryMasterService.getAllScreenCategoryMaster();	

		} catch (Exception e) {
			
			LOGGER.error("Error Occured in ScreenCategoryMasterController#getAllScreencategory", e);
		}
		return screenCategoryMaster;
	}
	
	
	/*
	 * GET SCREE CATEGORY MASTER DETAILS FROM screen_category_master TABLE BASED ON ID
	 */
	@RequestMapping(value = "getScreenCategoryById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find One Screen Category By Id",notes="Table reference:screen_category_master")
	public ScreenCategoryMasterDTO getScreenCategoryById(@RequestParam(required = true) Long id) {
		ScreenCategoryMasterDTO screenCategoryMasterDTO = screenCategoryMasterService.findScreenCategoryMasterById(id);
		
		return screenCategoryMasterDTO;
	}
	
	
	
	
	
	
}
