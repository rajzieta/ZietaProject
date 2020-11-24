package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.TaskMasterDTO;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.TaskTypeMaster;
import com.zieta.tms.request.EditTasksByClientProjectRequest;
import com.zieta.tms.request.TaskTypesByClientRequest;
import com.zieta.tms.request.UpdateTaskInfoRequest;
import com.zieta.tms.response.TaskTypesByClientResponse;
import com.zieta.tms.response.TasksByClientProjectResponse;
import com.zieta.tms.response.TasksByUserModel;

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
	
	public void  updateTaskSortKeyByID(Long taskInfoId, Long sortKey);
	
	public void updateTaskSortKeyByIDs(List<UpdateTaskInfoRequest> taskIdWithSortKeys);

	public void deleteTaskInfoByClient(Long taskInfoId, String modifiedBy) throws Exception;

	
}
