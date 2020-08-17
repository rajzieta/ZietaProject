package com.zietaproj.zieta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.WorkflowDTO;
import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.service.TimeSheetService;
import com.zietaproj.zieta.service.WorkFlowRequestService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "WorkFlow API")
@Slf4j
public class TSWorkFlowController {
	

	@Autowired
	TimeSheetService timesheetService;
	
	@Autowired
	WorkFlowRequestService workFlowRequestService;

	@RequestMapping(value = "getAllWorkflowDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorkflowDTO> getAllWorkflowDetails() {
		List<WorkflowDTO> workflowDetails = null;
		try {
			workflowDetails = timesheetService.getAllWorkflowDetails();
		} catch (Exception e) {
			log.error("Error Occured in TSWorkFlowController#getAllWorkflowDetails", e);
		}
		return workflowDetails;

	}
	
	@RequestMapping(value = "getWorkFlowRequestsByApprover", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorkflowRequest> getAllWorkFlowRequests(@RequestParam(required = true) Long approverId) {
		List<WorkflowRequest> workFlowRequestList = null;
		try {
			workFlowRequestList = workFlowRequestService.findByApproverId(approverId);
		} catch (Exception e) {
			log.error("Error Occured in TSWorkFlowController#getAllWorkFlowRequests", e);
		}
		return workFlowRequestList;

	}
	
	
}
