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
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.service.ActivitiesByTaskService;
import com.zietaproj.zieta.service.ActivityMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Activites API")
public class ActivityMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityMasterController.class);

	@Autowired
	ActivityMasterService activitymasterService;

	@Autowired
	ActivitiesByTaskService activitiesbytaskservice;

	@RequestMapping(value = "getAllActivities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActivityMasterDTO> getAllActivities() {
		List<ActivityMasterDTO> activityMasters = null;
		try {
			activityMasters = activitymasterService.getAllActivities();

		} catch (Exception e) {
			LOGGER.error("Error Occured in ActivityMasterController#getAllActivities", e);
		}
		return activityMasters;
	}

	@RequestMapping(value = "addActivitymaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addActivitymaster(@Valid @RequestBody ActivityMaster activitymaster) {
		activitymasterService.addActivitymaster(activitymaster);
	}

	@ApiOperation(value = "List activities based on the  taskId", notes = "Table reference: task_activity,activity_master")
	@GetMapping("/getAllActivitiesByTask")
	public ResponseEntity<List<ActivitiesByTaskResponse>> getAllActivitiesByTask(@RequestParam Long taskId) {
		try {
			List<ActivitiesByTaskResponse> activitiesbytaskList = activitiesbytaskservice.getActivitiesByTask(taskId);
			return new ResponseEntity<List<ActivitiesByTaskResponse>>(activitiesbytaskList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<ActivitiesByTaskResponse>>(HttpStatus.NOT_FOUND);
		}
	}
}
