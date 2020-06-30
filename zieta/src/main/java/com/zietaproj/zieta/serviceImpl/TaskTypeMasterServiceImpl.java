package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.TaskTypeMaster;
import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.repository.TaskTypeMasterRepository;
import com.zietaproj.zieta.repository.TasksByUserRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.request.EditTasksByClientProjectRequest;
import com.zietaproj.zieta.request.TaskTypesByClientRequest;
import com.zietaproj.zieta.response.TasksByClientProjectResponse;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.response.TaskTypesByClientResponse;
import com.zietaproj.zieta.service.TaskTypeMasterService;
import com.zietaproj.zieta.util.TSMUtil;

@Service
@Transactional
public class TaskTypeMasterServiceImpl implements TaskTypeMasterService {

	@Autowired
	TaskTypeMasterRepository taskTypeMasterRepository;

	@Autowired
	TasksByUserRepository tasksByUserRepository;

	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	StatusMasterRepository statusRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<TaskMasterDTO> getAllTasks() {
		List<TaskTypeMaster> taskTypeMasters = taskTypeMasterRepository.findAll();
		List<TaskMasterDTO> taskMasterDTOs = new ArrayList<TaskMasterDTO>();
		TaskMasterDTO taskMasterDTO = null;
		for (TaskTypeMaster taskTypeMaster : taskTypeMasters) {
			taskMasterDTO = modelMapper.map(taskTypeMaster,TaskMasterDTO.class);
			taskMasterDTO.setClientCode(clientInfoRepository.findById(taskTypeMaster.getClientId()).get().getClient_code());
			taskMasterDTOs.add(taskMasterDTO);
		}
		return taskMasterDTOs;
	}

	@Override
	public void addTaskmaster(TaskTypeMaster taskmaster) {
		taskTypeMasterRepository.save(taskmaster);
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
			String taskName = taskInfo.getTaskDescription();
			ProjectInfo projectInfo = projectInfoRepository.findById(projectId).get();
			String projectName = projectInfo.getProjectName();
			tasksByUserModel.setProjectId(projectId);
			tasksByUserModel.setProjectName(projectName);
			tasksByUserModel.setTaskName(taskName);
			tasksByUserModel.setTaskId(taskId);
			tasksByUserModel.setUserId(userIdent);
			tasksByUserModel.setProjectCode(tasksByUser.getProject_code());
			tasksByUserModel.setTaskCode(tasksByUser.getTask_code());
			tasksByUserModelList.add(tasksByUserModel);
		}

		return tasksByUserModelList;

	}

	@Override
	public List<TasksByClientProjectResponse> findByClientIdAndProjectId(Long clientId, Long projectId) {
		List<TaskInfo> taskInfoList = taskInfoRepository.findByClientIdAndProjectId(clientId, projectId);
		
		List<TasksByClientProjectResponse> tasksByClientProjectResponseList = new ArrayList<>();
		
		for(TaskInfo taskInfo: taskInfoList) {
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			TasksByClientProjectResponse tasksByClientProjectResponse =  modelMapper.map(taskInfo, TasksByClientProjectResponse.class);
			Optional<ProjectInfo> projectInfo = projectInfoRepository.findById(taskInfo.getProjectId());
			if(projectInfo.isPresent()) {
				tasksByClientProjectResponse.setProjectCode(projectInfo.get().getProjectCode());
				tasksByClientProjectResponse.setProjectDescription(projectInfo.get().getProjectName());	
			}else {
				tasksByClientProjectResponse.setProjectCode(StringUtils.EMPTY);
				tasksByClientProjectResponse.setProjectDescription(StringUtils.EMPTY);
			}
			
			tasksByClientProjectResponse.setTaskTypeDescription(StringUtils.EMPTY);
			if(null != taskInfo.getTaskType()) {
				Optional<TaskTypeMaster> taskTypeMaster = taskTypeMasterRepository.findById(taskInfo.getTaskType());
				if(taskTypeMaster.isPresent()) {
					tasksByClientProjectResponse.setTaskTypeDescription(taskTypeMaster.get().getTaskTypeDescription());
				}
			}
			
			tasksByClientProjectResponse.setTaskManagerName(StringUtils.EMPTY);
			if(null != taskInfo.getTaskManager()) {
				Optional <UserInfo> userInfo = userInfoRepository.findById(taskInfo.getTaskManager());
				if(userInfo.isPresent()) {
					String userName = TSMUtil.getFullName(userInfo.get());
					tasksByClientProjectResponse.setTaskManagerName(userName);
				}
			}
			
			tasksByClientProjectResponse.setTaskStatusDescription(StringUtils.EMPTY);
			if(null != taskInfo.getTaskStatus()) {
				Optional <StatusMaster> statusMaster = statusRepository.findById(taskInfo.getTaskStatus());
				if(statusMaster.isPresent()) {
					tasksByClientProjectResponse.setTaskStatusDescription(statusMaster.get().getStatus());
				}
			}
			
			tasksByClientProjectResponseList.add(tasksByClientProjectResponse);
			
		}
		return tasksByClientProjectResponseList;
	}

	
	public List<TaskTypesByClientResponse> getTasksByClient(Long clientId) {
		List<TaskTypeMaster> tasksByClientList = taskTypeMasterRepository.findByClientId(clientId);
		List<TaskTypesByClientResponse> tasksByClientResponseList = new ArrayList<>();
		TaskTypesByClientResponse tasksByClientResponse = null;
		for (TaskTypeMaster tasksByClient : tasksByClientList) {
			tasksByClientResponse = modelMapper.map(tasksByClient, 
					TaskTypesByClientResponse.class);
		
			tasksByClientResponseList.add(tasksByClientResponse);
	}
		return tasksByClientResponseList;
}

	@Override
	@Transactional
	public boolean saveTaskInfo(TaskInfo taskInfo) {
		try {
			TaskInfo taskInfoDB = taskInfoRepository.save(taskInfo);
			System.out.println("Task ID: "+taskInfoDB.getTaskInfoId());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	public void editTaskInfo(@Valid EditTasksByClientProjectRequest editasksByClientProjectRequest) throws Exception {
		Optional<TaskInfo> taskInfoEntity = taskInfoRepository.findById(editasksByClientProjectRequest.getId());
		if(taskInfoEntity.isPresent()) {
			TaskInfo taskInfo = modelMapper.map(editasksByClientProjectRequest, TaskInfo.class);
			taskInfoRepository.save(taskInfo);
		}else {
			throw new Exception("Task not found with the provided ID : "+editasksByClientProjectRequest.getId());
		}
	}
	
	@Override
	public void editTaskTypesByClient(TaskTypesByClientRequest tasktypesbyclientRequest) throws Exception
	{
		Optional<TaskTypeMaster> taskMasterEntity = taskTypeMasterRepository.findById(tasktypesbyclientRequest.getTaskTypeId());
		if(taskMasterEntity.isPresent()) {
		TaskTypeMaster taskmaster = modelMapper.map(tasktypesbyclientRequest, TaskTypeMaster.class);
		taskTypeMasterRepository.save(taskmaster);
	}else {
		throw new Exception("Task not found with the provided ID : "+tasktypesbyclientRequest.getTaskTypeId());
	}
	}
	
	@Override 
	  public void addTaskTypesByClient(TaskTypeMaster taskmaster) {
		
		taskTypeMasterRepository.save(taskmaster); 
	  
	  }
}
