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

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.response.TasksByClientProjectResponse;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.response.TasktypesByClientResponse;
import com.zietaproj.zieta.service.TaskMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Tasks API")
public class TaskMasterController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskMasterController.class);
	
	@Autowired
	TaskMasterService taskMasterService;


	@RequestMapping(value = "getAllTasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskMasterDTO> getAllTasks() {
		List<TaskMasterDTO> taskMasters = null;
		try {
			taskMasters = taskMasterService.getAllTasks();
		} catch (Exception e) {
			LOGGER.error("Error Occured in TaskMasterController#getAllTasks",e);
		}
		return taskMasters;
	}

	@RequestMapping(value = "addTaskmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTaskmaster(@Valid @RequestBody TaskMaster taskmaster) {
		taskMasterService.addTaskmaster(taskmaster);
	}

	
	@GetMapping("/getAllTasksByClientUser")
	@ApiOperation(value = "List tasks based on the  userId and clientId", notes = "Table reference: task_user_mapping,"
			+ " task_info, project_info")
	public ResponseEntity<List<TasksByUserModel>> getAllTasksByUser(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long userId) {
		try {
			List<TasksByUserModel> tasksByUserModelList = taskMasterService.findByClientIdAndUserId(clientId, userId);
			return new ResponseEntity<List<TasksByUserModel>>(tasksByUserModelList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TasksByUserModel>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@GetMapping("/getAllTasksByClientProject")
	@ApiOperation(value = "List tasks based on the  clientId and projectId", notes = "Table reference:"
			+ " task_info, project_info, task_type_master")
	public ResponseEntity<List<TasksByClientProjectResponse>> getAllTasksByClientProject(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long projectId) {
		try {
			List<TasksByClientProjectResponse> tasksByClientProjectResponseList = taskMasterService.findByClientIdAndProjectId(clientId, projectId);
			return new ResponseEntity<List<TasksByClientProjectResponse>>(tasksByClientProjectResponseList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TasksByClientProjectResponse>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getTaskTypesByClient")
	@ApiOperation(value = "List TaskTypes based on the clientId", notes = "Table reference: task_type_master")
	public ResponseEntity<List<TasktypesByClientResponse>> getTaskTypesByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<TasktypesByClientResponse> tasksbyclientList = taskMasterService.getTasksByClient(clientId);
			return new ResponseEntity<List<TasktypesByClientResponse>>(tasksbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TasktypesByClientResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Persists the tasks related to client and its associated project", notes = "Table reference: task_info")
	@RequestMapping(value = "addTasksByClientProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTasksByClientProject(@Valid @RequestBody TaskInfo taskInfo) {
		taskMasterService.saveTaskInfo(taskInfo);
	}
}
