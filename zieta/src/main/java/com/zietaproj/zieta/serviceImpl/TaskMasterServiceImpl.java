package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.repository.TaskMasterRepository;
import com.zietaproj.zieta.repository.TasksByUserRepository;
import com.zietaproj.zieta.response.TasksByClientProjectResponse;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.service.TaskMasterService;

@Service
public class TaskMasterServiceImpl implements TaskMasterService {

	@Autowired
	TaskMasterRepository taskMasterRepository;

	@Autowired
	TasksByUserRepository tasksByUserRepository;

	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Autowired
	TaskInfoRepository taskInfoRepository;

	@Override
	public List<TaskMasterDTO> getAllTasks() {
		List<TaskMaster> taskMasters = taskMasterRepository.findAll();
		List<TaskMasterDTO> taskMasterDTOs = new ArrayList<TaskMasterDTO>();
		TaskMasterDTO taskMasterDTO = null;
		for (TaskMaster taskMaster : taskMasters) {
			taskMasterDTO = new TaskMasterDTO();
			taskMasterDTO.setId(taskMaster.getId());
			taskMasterDTO.setTask_type(taskMaster.getType_name());
			taskMasterDTO.setClient_id(taskMaster.getClient_id());
			taskMasterDTO.setModified_by(taskMaster.getModified_by());
			taskMasterDTO.setCreated_by(taskMaster.getCreated_by());
			taskMasterDTOs.add(taskMasterDTO);
		}
		return taskMasterDTOs;
	}

	@Override
	public void addTaskmaster(TaskMaster taskmaster) {
		taskMasterRepository.save(taskmaster);
	}

	@Override
	public List<TasksByUserModel> findByClientIdAndUserId(Long clientId, Long userId) {

		List<TasksByUserModel> tasksByUserModelList = new ArrayList<>();

		List<TasksByUser> tasksByUserList = tasksByUserRepository.findByClientIdAndUserId(clientId, userId);
		TasksByUserModel tasksByUserModel = null;
		for (TasksByUser tasksByUser : tasksByUserList) {
			tasksByUserModel = new TasksByUserModel();
			long projectId = tasksByUser.getProject_id();
			long taskId = tasksByUser.getTask_id();
			long userIdent = tasksByUser.getUserId();
			TaskInfo taskInfo = taskInfoRepository.findById(taskId).get();
			String taskName = taskInfo.getTask_name();
			ProjectInfo projectInfo = projectInfoRepository.findById(projectId).get();
			String projectName = projectInfo.getProject_name();
			tasksByUserModel.setProjectId(projectId);
			tasksByUserModel.setProject_name(projectName);
			tasksByUserModel.setTask_name(taskName);
			tasksByUserModel.setTaskId(taskId);
			tasksByUserModel.setUserId(userIdent);
			tasksByUserModel.setProject_code(tasksByUser.getProject_code());
			tasksByUserModel.setTask_code(tasksByUser.getTask_code());
			tasksByUserModelList.add(tasksByUserModel);
		}

		return tasksByUserModelList;

	}

	@Override
	public List<TasksByClientProjectResponse> findByClientIdAndProjectId(Long clientId, Long projectId) {
		List<TaskInfo> taskInfoList = taskInfoRepository.findByClientIdAndProjectId(clientId, projectId);
		
		List<TasksByClientProjectResponse> tasksByClientProjectResponseList = new ArrayList<>();
		
		for(TaskInfo taskInfo: taskInfoList) {
			TasksByClientProjectResponse tasksByClientProjectResponse = new TasksByClientProjectResponse();
			ProjectInfo projectInfo = projectInfoRepository.findById(taskInfo.getProjectId()).get();
			tasksByClientProjectResponse.setTaskCode(taskInfo.getTask_code());
			tasksByClientProjectResponse.setTaskDescription(taskInfo.getTask_name());
			tasksByClientProjectResponse.setProjectCode(projectInfo.getProject_code());
			tasksByClientProjectResponse.setProjectDescription(projectInfo.getProject_name());
			tasksByClientProjectResponseList.add(tasksByClientProjectResponse);
			
		}
		return tasksByClientProjectResponseList;
	}

}
