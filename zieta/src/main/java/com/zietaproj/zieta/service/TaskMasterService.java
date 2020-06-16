package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.response.TasksByClientProjectResponse;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.response.TasktypesByClientResponse;

public interface TaskMasterService {
	public List<TaskMasterDTO> getAllTasks();

	public void addTaskmaster(TaskMaster taskmaster);
	
	List<TasksByUserModel> findByClientIdAndUserId(Long clientId, Long userId);
	
	List<TasksByClientProjectResponse> findByClientIdAndProjectId(Long clientId, Long projectId);

	public List<TasktypesByClientResponse> getTasksByClient(Long clientId);
	
	public boolean saveTaskInfo(TaskInfo taskInfo);
	
}
