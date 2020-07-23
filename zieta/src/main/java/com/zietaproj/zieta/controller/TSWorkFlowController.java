package com.zietaproj.zieta.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.dto.WorkflowDTO;
import com.zietaproj.zieta.service.TaskTypeMasterService;
import com.zietaproj.zieta.service.TimeSheetService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeSheet API")
@Slf4j
public class TSWorkFlowController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TSWorkFlowController.class);
	

	@Autowired
	TimeSheetService timesheetService;

	@RequestMapping(value = "getAllWorkflowDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorkflowDTO> getAllWorkflowDetails() {
		List<WorkflowDTO> workflowDetails = null;
		try {
			workflowDetails = timesheetService.getAllWorkflowDetails();
		} catch (Exception e) {
			LOGGER.error("Error Occured in TaskMasterController#getAllTasks",e);
		}
		return workflowDetails;
	
}
}
