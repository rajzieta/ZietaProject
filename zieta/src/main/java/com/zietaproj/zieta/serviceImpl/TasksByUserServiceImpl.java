package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.repository.ProjectMasterRepository;
import com.zietaproj.zieta.repository.TaskMasterRepository;
import com.zietaproj.zieta.repository.TasksByUserRepository;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.service.TasksByUserService;

@Service
@Transactional
public class TasksByUserServiceImpl implements TasksByUserService {

	@Autowired
	TasksByUserRepository tasksbyuserRepository;

	@Autowired
	ProjectMasterRepository projectMasterRepository;

	@Autowired
	TaskMasterRepository taskMasterRepository;

	@Override
	public List<TasksByUserModel> findProjectTasksByUser(Long userId) {
		List<TasksByUserModel> tasksByUserModelList = new ArrayList<>();

		List<TasksByUser> tasksByUserList = tasksbyuserRepository.findByUserId(userId);
		TasksByUserModel tasksByUserModel = null;
		for (TasksByUser tasksByUser : tasksByUserList) {
			tasksByUserModel = new TasksByUserModel();
			long projectId = tasksByUser.getProject_id();
			long taskId = tasksByUser.getTask_id();
			TaskMaster taskMaster = taskMasterRepository.findById(taskId).get();
			String taskType = taskMaster.getTask_type();
			ProjectMaster projectMaster = projectMasterRepository.findById(projectId).get();
			String projectType = projectMaster.getProject_type();
			tasksByUserModel.setId(projectId);
			tasksByUserModel.setProject_type(projectType);
			tasksByUserModel.setTask_type(taskType);
			tasksByUserModelList.add(tasksByUserModel);
		}

		return tasksByUserModelList;

	}

}
