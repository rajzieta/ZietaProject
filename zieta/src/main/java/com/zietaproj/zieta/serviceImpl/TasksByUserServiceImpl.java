package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.repository.TasksByUserRepository;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.service.TasksByUserService;

@Service
@Transactional
public class TasksByUserServiceImpl implements TasksByUserService {

	@Autowired
	TasksByUserRepository tasksbyuserRepository;

	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Autowired
	TaskInfoRepository taskInfoRepository;

	@Override
	public List<TasksByUserModel> findProjectTasksByUser(Long userId) {
		List<TasksByUserModel> tasksByUserModelList = new ArrayList<>();

		List<TasksByUser> tasksByUserList = tasksbyuserRepository.findByUserId(userId);
		TasksByUserModel tasksByUserModel = null;
		for (TasksByUser tasksByUser : tasksByUserList) {
			tasksByUserModel = new TasksByUserModel();
			long projectId = tasksByUser.getProject_id();
			long taskId = tasksByUser.getTask_id();
			TaskInfo taskInfo = taskInfoRepository.findById(taskId).get();
			String taskName = taskInfo.getTask_name();
			ProjectInfo projectInfo = projectInfoRepository.findById(projectId).get();
			String projectName = projectInfo.getProject_name();
			tasksByUserModel.setProjectId(projectId);
			tasksByUserModel.setProject_name(projectName);
			tasksByUserModel.setTask_name(taskName);
			tasksByUserModelList.add(tasksByUserModel);
		}

		return tasksByUserModelList;

	}

}
