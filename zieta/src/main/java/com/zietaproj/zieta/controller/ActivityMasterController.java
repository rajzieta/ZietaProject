package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.request.AcitivityRequest;
import com.zietaproj.zieta.request.ActivityTaskUserMappingRequest;
import com.zietaproj.zieta.request.ScreensMasterEditRequest;
import com.zietaproj.zieta.request.StatusByClientTypeRequest;
import com.zietaproj.zieta.response.ActivitiesByClientResponse;
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.service.ActivitiesByTaskService;
import com.zietaproj.zieta.service.ActivityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Activites API")
public class ActivityMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityMasterController.class);

	@Autowired
	ActivityService activityService;

	@Autowired
	ActivitiesByTaskService activitiesbytaskservice;

	@RequestMapping(value = "getAllActivities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActivityMasterDTO> getAllActivities() {
		List<ActivityMasterDTO> activityMasters = null;
		try {
			activityMasters = activityService.getAllActivities();

		} catch (Exception e) {
			LOGGER.error("Error Occured in ActivityMasterController#getAllActivities", e);
		}
		return activityMasters;
	}

	@RequestMapping(value = "addActivitymaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addActivitymaster(@Valid @RequestBody ActivityMaster activitymaster) {
		activityService.addActivitymaster(activitymaster);
	}

	@ApiOperation(value = "List activities based on the  clientId, taskId and projectId", notes = "Table reference: task_activity,activity_master")
	@GetMapping("/getActivitesByClientProjectTask")
	public ResponseEntity<List<ActivitiesByTaskResponse>> getActivitesByClientProjectTask(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long projectId, @RequestParam(required = true)  Long taskId) {
		try {
			List<ActivitiesByTaskResponse> activitiesbytaskList = activitiesbytaskservice.getActivitesByClientProjectTask(clientId, projectId, taskId);
			return new ResponseEntity<List<ActivitiesByTaskResponse>>(activitiesbytaskList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ActivitiesByTaskResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllActivitiesByClient")
	public ResponseEntity<List<ActivitiesByClientResponse>> getAllActivitiesByClient(@RequestParam(required=true) Long clientId) {
		try {
			List<ActivitiesByClientResponse> activitiesbyclientList = activityService.getActivitiesByClient(clientId);
			return new ResponseEntity<List<ActivitiesByClientResponse>>(activitiesbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ActivitiesByClientResponse>>(HttpStatus.NOT_FOUND);
		} 
	}
	
	@ApiOperation(value = "Maps activities with task and then with user", notes = "Table reference: task_activity,activity_user_mapping")
	@RequestMapping(value = "addActivitiesByClientProjectTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addActivitiesByClientProjectTask(@Valid @RequestBody ActivityTaskUserMappingRequest activityTaskUserMappingRequest) {
	 activityService.addActivitiesByClientProjectTask(activityTaskUserMappingRequest);
	}
	
	@RequestMapping(value = "editActivitiesById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editActivitiesById(@Valid @RequestBody AcitivityRequest acitivityRequest) throws Exception {
		activityService.editActivitiesById(acitivityRequest);
		
	}
	
	@RequestMapping(value = "editActivitiesByClientProject", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editActivitiesByClientProject(@Valid @RequestBody AcitivityRequest acitivityRequest) throws Exception {
		activityService.editActivitiesByClientProject(acitivityRequest);
		
	}

}
