package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.TaskTypeMaster;
import com.zietaproj.zieta.request.EditTasksByClientProjectRequest;
import com.zietaproj.zieta.request.TaskTypesByClientRequest;
import com.zietaproj.zieta.response.TasksByClientProjectResponse;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.response.TaskTypesByClientResponse;

public interface TaskTypeMasterService {
	public List<TaskMasterDTO> getAllTasks();

	public void addTaskmaster(TaskTypeMaster taskmaster);
	
	List<TasksByUserModel> findByClientIdAndUserId(Long clientId, Long userId);
	
	List<TasksByClientProjectResponse> findByClientIdAndProjectId(Long clientId, Long projectId);
	
	List<TasksByClientProjectResponse> findByClientIdAndProjectIdAsHierarchy(Long clientId, Long projectId);

	public List<TaskTypesByClientResponse> getTasksByClient(Long clientId);
	
	public boolean saveTaskInfo(TaskInfo taskInfo);

	public void editTaskTypesByClient(@Valid TaskTypesByClientRequest tasktypesbyclientRequest) throws Exception;

	public void addTaskTypesByClient(@Valid TaskTypeMaster taskmaster);

	public void editTaskInfo(@Valid EditTasksByClientProjectRequest editasksByClientProjectRequest) throws Exception;

	public void deleteTaskTypeByClient(Long taskTypeId, String modifiedBy) throws Exception;

	
}
