package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.ActivityMasterDTO;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.TaskActivity;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.ActivitiesTaskRepository;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.TaskInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.AcitivityRequest;
import com.zieta.tms.request.ActivityTaskUserMappingRequest;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientResponse;
import com.zieta.tms.response.ActivitiesByClientUserModel;
import com.zieta.tms.service.ActivityService;
import com.zieta.tms.util.TSMUtil;
import lombok.extern.slf4j.Slf4j;



@Service
@Transactional
@Slf4j
public class ActivityServiceImpl implements ActivityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	ClientInfoRepository  clientInfoRepository;
	
	@Autowired
	ActivitiesTaskRepository activitiesTaskRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	UserInfoRepository userInfoReposistory;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ActivityMasterDTO> getAllActivities() {
		short notDeleted = 0;
		List<ActivityMaster> activityMasters= activityMasterRepository.findByIsDelete(notDeleted);
		List<ActivityMasterDTO> activityMasterDTOs = new ArrayList<ActivityMasterDTO>();
		ActivityMasterDTO activityMasterDTO = null;
		for (ActivityMaster activityMaster : activityMasters) {
			activityMasterDTO = modelMapper.map(activityMaster, ActivityMasterDTO.class);
			activityMasterDTO
					.setClientCode(clientInfoRepository.findById(activityMaster.getClientId())
							.get().getClientCode());
			activityMasterDTO
			.setClientDescription(clientInfoRepository.findById(activityMaster.getClientId())
					.get().getClientName());
			activityMasterDTO
			.setClientStatus(clientInfoRepository.findById(activityMaster.getClientId())
					.get().getClientStatus());
			activityMasterDTO.setActive(activityMaster.isActive());
			activityMasterDTOs.add(activityMasterDTO);
		}
		return activityMasterDTOs;
	}
	
	@Override
	public void addActivitymaster(ActivityMaster activitymaster)
	{
		activityMasterRepository.save(activitymaster);
	}

	@Override
	public List<ActivitiesByClientResponse> getActivitiesByClient(Long clientId) {
		short notDeleted = 0;
		List<ActivityMaster> activitiesByClientList = activityMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<ActivitiesByClientResponse> activitiesByClientResponseList = new ArrayList<>();
		ActivitiesByClientResponse activitiesByClientResponse = null;
		for (ActivityMaster activitiesByClient : activitiesByClientList) {
			activitiesByClientResponse = modelMapper.map(activitiesByClient, 
					ActivitiesByClientResponse.class);
			activitiesByClientResponse.setClientCode(clientInfoRepository.findById(clientId).get().getClientCode());
			activitiesByClientResponse.setClientDescription(clientInfoRepository.findById(clientId).get().getClientName());
			activitiesByClientResponseList.add(activitiesByClientResponse);
		}

		return activitiesByClientResponseList;
		
	
	}

	@Override
	@Transactional
	public void addActivitiesByClientProjectTask(@Valid List<ActivityTaskUserMappingRequest> activityTaskUserMappingRequest) {
		for (ActivityTaskUserMappingRequest activityTaskUserMappingRequests : activityTaskUserMappingRequest)
		{
			doUpSert(activityTaskUserMappingRequests);
		}
	}

	private void doUpSert(ActivityTaskUserMappingRequest activityTaskUserMappingRequests) {
	
	ActivityTaskUserMappingRequest.TaskActivity taskActivityReq = activityTaskUserMappingRequests.getTaskActivity();
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		TaskActivity taskActivityEntity = modelMapper.map(taskActivityReq, TaskActivity.class);
		TaskActivity taskActivityObj = activitiesTaskRepository.save(taskActivityEntity);
	}

	@Override
	public void editActivitiesById(AcitivityRequest acitivityRequest) throws Exception {
		Optional<ActivityMaster> activityMasterEntity = activityMasterRepository.findById(acitivityRequest.getActivityId());
		if(activityMasterEntity.isPresent()) {
			ActivityMaster activityMasterSave = activityMasterEntity.get();
			activityMasterSave.setActive(acitivityRequest.isActive());
			activityMasterSave.setActivityCode(acitivityRequest.getActivityCode());
			activityMasterSave.setActivityDesc(acitivityRequest.getActivityDesc());
			activityMasterSave.setModifiedBy(acitivityRequest.getModifiedBy());
			activityMasterRepository.save(activityMasterSave);
			
		}else {
			throw new Exception("Activity not found with the provided activity ID : "+acitivityRequest.getActivityId());
		}
		
	}
	
	
	
	@Override
	public void editActivitiesByClientProjectTask(ActivityTaskUserMappingRequest activityTaskUserMappingRequest) {
		
			doUpSert(activityTaskUserMappingRequest);

	}
	
	
	public List<ActivitiesByClientProjectTaskResponse> getActivitesByClientProjectTask(long clientId,long projectId,long taskId) {

		short notDeleted = 0;
		List<TaskActivity> activitiesbytaskList = activitiesTaskRepository.findByClientIdAndProjectIdAndTaskIdAndIsDelete(clientId ,projectId,taskId,notDeleted );
		List<ActivitiesByClientProjectTaskResponse> activitiesByClientProjectTaskList = new ArrayList<ActivitiesByClientProjectTaskResponse>();
		for (TaskActivity activitiesbytask : activitiesbytaskList) {

			ActivitiesByClientProjectTaskResponse activitiesByClientProjectTaskResponse = new ActivitiesByClientProjectTaskResponse();
			ActivitiesByClientProjectTaskResponse.TaskActivity taskActivity = activitiesByClientProjectTaskResponse.getTaskActivity();
	
		
			ActivitiesByClientProjectTaskResponse.AdditionalDetails additionalDetails = activitiesByClientProjectTaskResponse.getAdditionalDetails();
			// task
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			taskActivity = modelMapper.map(activitiesbytask, ActivitiesByClientProjectTaskResponse.TaskActivity.class);
			taskActivity.setEndDate(TSMUtil.convertToLocalDateViaMilisecond(activitiesbytask.getEndDate()));
			taskActivity.setStartDate(TSMUtil.convertToLocalDateViaMilisecond(activitiesbytask.getStartDate()));
			activitiesByClientProjectTaskResponse.setTaskActivity(taskActivity);

			String teamMemberName = StringUtils.EMPTY;

			if (activitiesbytask.getUserId() !=null && activitiesbytask.getUserId() != 0) {
				Optional<UserInfo> userInfo = userInfoReposistory.findById(activitiesbytask.getUserId());
				if (userInfo.isPresent()) {
					teamMemberName = TSMUtil.getFullName(userInfo.get());
					// otherdetails
					additionalDetails.setUserName(teamMemberName);
				}
			}
			Optional<ActivityMaster> activitymaster = activityMasterRepository
					.findById(activitiesbytask.getActivityId());
			if (activitymaster.isPresent()) {
				// otherdetails
				additionalDetails.setActivityCode(activitymaster.get().getActivityCode());
				additionalDetails.setActivityDesc(activitymaster.get().getActivityDesc());
			}

			
			
			
			
			activitiesByClientProjectTaskList.add(activitiesByClientProjectTaskResponse);
		}

		return activitiesByClientProjectTaskList;
	}

	@Override
	public void deleteActivitesByClientProjectTask(long taskActivityId, String modifiedBy) throws Exception {
		Optional<TaskActivity> taskActivity = activitiesTaskRepository.findById(taskActivityId);
		if (taskActivity.isPresent()) {
			TaskActivity taskActivityEntitiy = taskActivity.get();
			short delete = 1;
			taskActivityEntitiy.setIsDelete(delete);
			taskActivityEntitiy.setModifiedBy(modifiedBy);
			activitiesTaskRepository.save(taskActivityEntitiy);

		}else {
			log.info("No task activity found with the provided ID{} in the DB",taskActivityId);
			throw new Exception("No task activity found with the provided ID in the DB :"+taskActivityId);
		}
	}

	
	@Override
	public List<ActivitiesByClientUserModel> getActivitiesByClientUser(Long clientId, Long userId) {
		
		short notDeleted = 0;
		List<TaskActivity> taskActivityList = activitiesTaskRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId, notDeleted);
		
		List<ActivitiesByClientUserModel> activitiesByClientUserModelList = new ArrayList<>();
		
		for (TaskActivity taskActivity : taskActivityList) {
			ActivitiesByClientUserModel activitiesByClientUserModel = new ActivitiesByClientUserModel();
			
			ActivityMaster activityMaster = activityMasterRepository.findById(taskActivity.getActivityId()).get();
			activitiesByClientUserModel.setActivityId(taskActivity.getActivityId());
			activitiesByClientUserModel.setActivityCode(activityMaster.getActivityCode());
			activitiesByClientUserModel.setActivityDesc(activityMaster.getActivityDesc());
			
			ProjectInfo projectInfo  = projectInfoRepository.findById(taskActivity.getProjectId()).get();
			activitiesByClientUserModel.setProjectId(taskActivity.getProjectId());
			activitiesByClientUserModel.setProjectCode(projectInfo.getProjectCode());
			activitiesByClientUserModel.setProjectName(projectInfo.getProjectName());
			
			TaskInfo taskInfo = taskInfoRepository.findById(taskActivity.getTaskId()).get();
			activitiesByClientUserModel.setTaskId(taskActivity.getTaskId());
			activitiesByClientUserModel.setTaskCode(taskInfo.getTaskCode());
			activitiesByClientUserModel.setTaskDescription(taskInfo.getTaskDescription());
			activitiesByClientUserModel.setTaskActivityId(taskActivity.getTaskActivityId());
			
			ClientInfo clientInfo = clientInfoRepository.findById(taskActivity.getClientId()).get();
			activitiesByClientUserModel.setClientCode(clientInfo.getClientCode());
			activitiesByClientUserModel.setClientDescription(clientInfo.getClientName());
			activitiesByClientUserModelList.add(activitiesByClientUserModel);
		}
		return activitiesByClientUserModelList;
	}
	
	
	public void deleteActivityById(Long id, String modifiedBy) throws Exception {
		
		
		Optional<ActivityMaster> activitymaster = activityMasterRepository.findById(id);
		if (activitymaster.isPresent()) {
			ActivityMaster activitymasterEntitiy = activitymaster.get();
			short delete = 1;
			activitymasterEntitiy.setIsDelete(delete);
			activitymasterEntitiy.setModifiedBy(modifiedBy);
			activityMasterRepository.save(activitymasterEntitiy);

		}else {
			log.info("No Activity found with the provided ID{} in the DB",id);
			throw new Exception("No Activity found with the provided ID in the DB :"+id);
		}
		
		
	}
	
	
	
}
