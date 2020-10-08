
package com.zieta.tms.controller;

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

import com.zieta.tms.dto.ActivityMasterDTO;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.request.AcitivityRequest;
import com.zieta.tms.request.ActivityTaskUserMappingRequest;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientResponse;
import com.zieta.tms.response.ActivitiesByClientUserModel;
import com.zieta.tms.response.ActivitiesByTaskResponse;
import com.zieta.tms.service.ActivitiesByTaskService;
import com.zieta.tms.service.ActivityService;

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
	
	@Deprecated
	@ApiOperation(value = "List activities based on the  clientId, taskId and projectId", notes = "Table reference: task_activity,activity_master")
	@GetMapping("/getActivitesByClientProjectTaskOld")
	public ResponseEntity<List<ActivitiesByTaskResponse>> getTestActivitesByClientProjectTask(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long projectId, @RequestParam(required = true)  Long taskId) {
		try {
			List<ActivitiesByTaskResponse> activitiesbytaskList = activitiesbytaskservice.getActivitesByClientProjectTaskOld(clientId, projectId, taskId);
			return new ResponseEntity<List<ActivitiesByTaskResponse>>(activitiesbytaskList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ActivitiesByTaskResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@ApiOperation(value = "List activities based on the  clientId, taskId and projectId", notes = "Table reference: task_activity,activity_master")
	@GetMapping("/getActivitesByClientProjectTask")
	public ResponseEntity<List<ActivitiesByClientProjectTaskResponse>> getActivitesByClientProjectTask(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long projectId, @RequestParam(required = true)  Long taskId) {
		try {
			List<ActivitiesByClientProjectTaskResponse> activitiesbytaskList = activityService.getActivitesByClientProjectTask(clientId, projectId, taskId);
			return new ResponseEntity<List<ActivitiesByClientProjectTaskResponse>>(activitiesbytaskList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ActivitiesByClientProjectTaskResponse>>(HttpStatus.NOT_FOUND);
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
	
	@ApiOperation(value = "Maps activities with task and then with user", notes = "Table reference: task_activity")
	@RequestMapping(value = "addActivitiesByClientProjectTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addActivitiesByClientProjectTask(@Valid @RequestBody List<ActivityTaskUserMappingRequest> activityTaskUserMappingRequest) {
	 activityService.addActivitiesByClientProjectTask(activityTaskUserMappingRequest);
	}
	
	@RequestMapping(value = "editActivitiesById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editActivitiesById(@Valid @RequestBody AcitivityRequest acitivityRequest) throws Exception {
		activityService.editActivitiesById(acitivityRequest);
		
	}
	 
	@ApiOperation(value = "Edits the entries from the task_activity table", notes = "Table reference: task_activity")
	@RequestMapping(value = "editActivitiesByClientProjectTask", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editActivitiesByClientProjectTask(@Valid @RequestBody List<ActivityTaskUserMappingRequest> activityTaskUserMappingRequest) throws Exception {
		activityService.editActivitiesByClientProjectTask(activityTaskUserMappingRequest);
		
	}
	
	@ApiOperation(value = "Deletes entries from task_activity based on taskActivityId", notes = "Table reference: task_activity")
	@RequestMapping(value = "deleteActivitiesByClientProjectTask", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteActivitesByClientProjectTask(@RequestParam(required=true) Long taskActivitiyId, @RequestParam(required=true) String modifiedBy) throws Exception {
		activityService.deleteActivitesByClientProjectTask(taskActivitiyId, modifiedBy);
	}
	
	
	@ApiOperation(value = "List activities based on the  clientId and userId", notes = "Table reference: task_activity,activity_master,project_info,task_info")
	@GetMapping("/getActivitiesByClientUser")
	public ResponseEntity<List<ActivitiesByClientUserModel>> getActivitiesByClientUser(@RequestParam(required=true) Long clientId, @RequestParam(required=true) Long userId){
		
		try {
			List<ActivitiesByClientUserModel> activitiesByClientUserModelList = activityService.getActivitiesByClientUser(clientId, userId);
			return new ResponseEntity<List<ActivitiesByClientUserModel>>(activitiesByClientUserModelList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ActivitiesByClientUserModel>>(HttpStatus.NOT_FOUND);
		} 
	}

	
	
	@ApiOperation(value = "Deletes entries from activity_master based on ActivityId", notes = "Table reference: activity_master")
	@RequestMapping(value = "deleteActivityById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteActivityById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		activityService.deleteActivityById(id, modifiedBy);
	}
	
	
	
}
