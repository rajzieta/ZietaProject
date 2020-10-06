package com.zieta.tms.controller;

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

import com.zieta.tms.dto.ProcessConfigDTO;
import com.zieta.tms.dto.ProcessMasterDTO;
import com.zieta.tms.dto.ProcessStepsDTO;
import com.zieta.tms.model.ProcessMaster;
import com.zieta.tms.model.ProcessSteps;
import com.zieta.tms.service.ProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Process API")
public class ProcessController {

	@Autowired
	ProcessService processService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);
	
	@RequestMapping(value = "getAllProcess", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProcessMasterDTO> getAllProcess() {
		List<ProcessMasterDTO> processMasters = null;
		try {
			processMasters = processService.getAllProcess();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ProcessController#getAllProcess",e);
		}
		return processMasters;
	}
	
	
	@ApiOperation(value = "creates entries in the process_master table", notes = "Table reference: process_master")
	@RequestMapping(value = "addProcess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addProcess(@Valid @RequestBody ProcessMaster processmaster) {
		processService.addProcess(processmaster);
	}

	@ApiOperation(value = "Updates the Processes for the provided Id", notes = "Table reference: process_master")
	@RequestMapping(value = "editProcessById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editProcessById(@Valid @RequestBody ProcessMasterDTO processmastersDto) throws Exception {
		processService.editProcessById(processmastersDto);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from process_master based on Id", notes = "Table reference: process_master")
	@RequestMapping(value = "deleteProcessById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteProcessById(@RequestParam(required=true) Long id) throws Exception {
		processService.deleteProcessById(id);
	}
	
	
	////Basic Operation API's for Process Steps
	
	
	@RequestMapping(value = "getAllProcessSteps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProcessStepsDTO> getAllProcessSteps(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize) {
		List<ProcessStepsDTO> processSteps = null;
		try {
			processSteps = processService.getAllProcessSteps(pageNo, pageSize);
		} catch (Exception e) {
			LOGGER.error("Error Occured in ProcessController#getAllProcessSteps",e);
		}
		return processSteps;
	}
	
	
	@RequestMapping(value = "getProcessStepsByProjectIdByClientId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProcessStepsDTO> getProcessStepsByProjectIdByClientId(@RequestParam Long clientId,@RequestParam Long projectId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		List<ProcessStepsDTO> processSteps = null;
		try {
			processSteps = processService.getProcessStepsByClientIdByProjectId(clientId, projectId, pageNo, pageSize);
		} catch (Exception e) {
			LOGGER.error("Error Occured in ProcessController#getProcessStepsByProjectIdByClientId",e);
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
	
	
	//////API Operation for ProcessConfig
	
	@RequestMapping(value = "getAllProcessConfig", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProcessConfigDTO> getAllProcessConfig() {
		List<ProcessConfigDTO> processConfig = null;
		try {
			processConfig = processService.getAllProcessConfig();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ProcessController#getAllProcessConfig",e);
		}
		return processConfig;
	}
	
	
}
