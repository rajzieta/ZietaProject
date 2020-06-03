package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.response.TasksByClientProjectResponse;
import com.zietaproj.zieta.response.TasksByUserModel;

public interface TaskMasterService {
	public List<TaskMasterDTO> getAllTasks();

	public void addTaskmaster(TaskMaster taskmaster);
	
	List<TasksByUserModel> findByClientIdAndUserId(Long clientId, Long userId);
	
	List<TasksByClientProjectResponse> findByClientIdAndProjectId(Long clientId, Long projectId);
	
}
