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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.ProcessStepsDTO;
import com.zietaproj.zieta.model.ProcessSteps;
import com.zietaproj.zieta.service.ProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Process API")
public class ProcessController {

	@Autowired
	ProcessService processService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);
	
	@RequestMapping(value = "getAllProcessSteps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProcessStepsDTO> getAllProcessSteps() {
		List<ProcessStepsDTO> processSteps = null;
		try {
			processSteps = processService.getAllProcessSteps();
		} catch (Exception e) {
			LOGGER.error("Error Occured in TaskMasterController#getAllTasks",e);
		}
		return processSteps;
	}
	
	
	@ApiOperation(value = "creates entries in the process_steps table", notes = "Table reference: process_steps")
	@RequestMapping(value = "addProcessSteps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addProcessSteps(@Valid @RequestBody ProcessSteps processstep) {
		processService.addProcessSteps(processstep);
	}

	@ApiOperation(value = "Updates the ProcessSteps for the provided Id", notes = "Table reference: process_steps")
	@RequestMapping(value = "editProcessStepsById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editProcessStepsById(@Valid @RequestBody ProcessStepsDTO processstepsDto) throws Exception {
		processService.editProcessStepsById(processstepsDto);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from process_steps based on Id", notes = "Table reference: process_steps")
	@RequestMapping(value = "deleteProcessStepsById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteProcessStepsById(@RequestParam(required=true) Long id) throws Exception {
		processService.deleteProcessStepsById(id);
	}
	
	
}
