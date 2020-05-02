package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.TaskMaster;

public interface TaskMasterService {
	public List<TaskMasterDTO> getAllTasks();

	public void addTaskmaster(TaskMaster taskmaster);
	
}
