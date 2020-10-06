package com.zieta.tms.controller;

import java.util.HashMap;
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

import com.zieta.tms.dto.TaskMasterDTO;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.TaskTypeMaster;
import com.zieta.tms.request.EditTasksByClientProjectRequest;
import com.zieta.tms.request.TaskTypesByClientRequest;
import com.zieta.tms.request.UpdateTaskInfoRequest;
import com.zieta.tms.response.TaskTypesByClientResponse;
import com.zieta.tms.response.TasksByClientProjectResponse;
import com.zieta.tms.response.TasksByUserModel;
import com.zieta.tms.service.TaskTypeMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Tasks API")
public class TaskController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	TaskTypeMasterService taskTypeMasterService;

	@RequestMapping(value = "getAllTaskTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskMasterDTO> getAllTaskTypes() {
		List<TaskMasterDTO> taskMasters = null;
		try {
			taskMasters = taskTypeMasterService.getAllTasks();
		} catch (Exception e) {
			LOGGER.error("Error Occured in TaskMasterController#getAllTasks",e);
		}
		return taskMasters;
	}

	@ApiOperation(value = "creates entries in the task_type_master table", notes = "Table reference: task_type_master")
	@RequestMapping(value = "addTaskTypeMaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTaskmaster(@Valid @RequestBody TaskTypeMaster taskmaster) {
		taskTypeMasterService.addTaskmaster(taskmaster);
	}

	
	@GetMapping("/getAllTasksByClientTaskManager")
	@ApiOperation(value = "List tasks based on the  userId and clientId", notes = "Table reference: task_user_mapping,"
			+ " task_info, project_info")
	public ResponseEntity<List<TasksByUserModel>> getAllTasksByClientTaskManager(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long taskManagerId) {
		try {
			List<TasksByUserModel> tasksByUserModelList = taskTypeMasterService.findByClientIdAndUserId(clientId, taskManagerId);
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
			List<TasksByClientProjectResponse> tasksByClientProjectResponseList = taskTypeMasterService.findByClientIdAndProjectId(clientId, projectId);
			return new ResponseEntity<List<TasksByClientProjectResponse>>(tasksByClientProjectResponseList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TasksByClientProjectResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllTasksByClientProjectInTreeForm")
	@ApiOperation(value = "List tasks based on the  clientId and projectId", notes = "Table reference:"
			+ " task_info, project_info, task_type_master")
	public ResponseEntity<List<TasksByClientProjectResponse>> getAllTasksByClientProjectInTreeForm(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long projectId) {
		try {
			List<TasksByClientProjectResponse> tasksByClientProjectResponseList = taskTypeMasterService.findByClientIdAndProjectIdAsHierarchy(clientId, projectId);
			return new ResponseEntity<List<TasksByClientProjectResponse>>(tasksByClientProjectResponseList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TasksByClientProjectResponse>>(HttpStatus.NOT_FOUND);
		}
	}


	@GetMapping("/getAllTaskTypesByClient")
	@ApiOperation(value = "List TaskTypes based on the clientId", notes = "Table reference: task_type_master")
	public ResponseEntity<List<TaskTypesByClientResponse>> getAllTaskTypesByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<TaskTypesByClientResponse> tasksbyclientList = taskTypeMasterService.getTasksByClient(clientId);
			return new ResponseEntity<List<TaskTypesByClientResponse>>(tasksbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TaskTypesByClientResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	//
	@RequestMapping(value = "editTaskTypesByClient", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editTaskTypesByClient(@Valid @RequestBody TaskTypesByClientRequest tasktypesbyclientRequest) throws Exception {
		taskTypeMasterService.editTaskTypesByClient(tasktypesbyclientRequest);
		
	}
	
	@RequestMapping(value = "addTaskTypesByClient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Persists the tasktypes related to client", notes = "Table reference: task_type_master")
	public void addTaskTypesByClient(@Valid @RequestBody TaskTypeMaster taskmaster) throws Exception {
		taskTypeMasterService.addTaskTypesByClient(taskmaster);
		
	}
	
	@ApiOperation(value = "Deletes entries from task_type_master based on tasktypeId", notes = "Table reference: task_type_master")
	@RequestMapping(value = "deleteTaskTypeByClient", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTaskTypeByClient(@RequestParam(required=true) Long taskTypeId, @RequestParam(required=true) String modifiedBy) throws Exception {
		taskTypeMasterService.deleteTaskTypeByClient(taskTypeId, modifiedBy);
	}
	
	@ApiOperation(value = "Persists the tasks related to client and its associated project", notes = "Table reference: task_info")
	@RequestMapping(value = "addTasksByClientProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTasksByClientProject(@Valid @RequestBody TaskInfo taskInfo) {
		taskTypeMasterService.saveTaskInfo(taskInfo);
	}
	
	@ApiOperation(value = "Persists the tasks related to client and its associated project", notes = "Table reference: task_info")
	@RequestMapping(value = "editTasksByClientProject", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editTasksByClientProject(@Valid @RequestBody EditTasksByClientProjectRequest ediTasksByClientProjectRequest) throws Exception {
		taskTypeMasterService.editTaskInfo(ediTasksByClientProjectRequest);
	}
	
	@ApiOperation(value = "Updates the sortKey for the provided taskInfoId", notes = "Table reference: task_info")
	@RequestMapping(value = "updateTaskSortKeyByID", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTaskSortKeyByID(@RequestParam(required=true) Long taskInfoId, @RequestParam(required=true) Long sortKey) throws Exception {
		taskTypeMasterService.updateTaskSortKeyByID(taskInfoId, sortKey);
		
	}
	
	@ApiOperation(value = "Updates collection of sortKeys for the provided taskInfoIds", notes = "Table reference: task_info")
	@RequestMapping(value = "updateTaskSortKeyByIDs", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTaskSortKeyByIDs( @RequestBody List<UpdateTaskInfoRequest> taskIdWithSortKeys) throws Exception {
		taskTypeMasterService.updateTaskSortKeyByIDs(taskIdWithSortKeys);
		
	}
}
