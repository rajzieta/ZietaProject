package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TaskActivity;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ActivitiesTaskRepository;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.request.AcitivityRequest;
import com.zietaproj.zieta.request.ActivityTaskUserMappingRequest;
import com.zietaproj.zieta.response.ActivitiesByClientProjectTaskResponse;
import com.zietaproj.zieta.response.ActivitiesByClientResponse;
import com.zietaproj.zieta.service.ActivityService;
import com.zietaproj.zieta.util.TSMUtil;



@Service
@Transactional
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
	ModelMapper modelMapper;
	
	@Override
	public List<ActivityMasterDTO> getAllActivities() {
		List<ActivityMaster> activityMasters= activityMasterRepository.findAll();
		List<ActivityMasterDTO> activityMasterDTOs = new ArrayList<ActivityMasterDTO>();
		ActivityMasterDTO activityMasterDTO = null;
		for (ActivityMaster activityMaster : activityMasters) {
			activityMasterDTO = modelMapper.map(activityMaster, ActivityMasterDTO.class);
			activityMasterDTO
					.setClientCode(clientInfoRepository.findById(activityMaster.getClientId())
							.get().getClient_code());
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

		List<ActivityMaster> activitiesByClientList = activityMasterRepository.findByClientId(clientId);
		List<ActivitiesByClientResponse> activitiesByClientResponseList = new ArrayList<>();
		ActivitiesByClientResponse activitiesByClientResponse = null;
		for (ActivityMaster activitiesByClient : activitiesByClientList) {
			activitiesByClientResponse = modelMapper.map(activitiesByClient, 
					ActivitiesByClientResponse.class);
			activitiesByClientResponse.setClientCode(clientInfoRepository.findById(clientId).get().getClient_code());
			activitiesByClientResponseList.add(activitiesByClientResponse);
		}

		return activitiesByClientResponseList;
		
	
	}

	@Override
	public void addActivitiesByClientProjectTask(ActivityTaskUserMappingRequest activityTaskUserMappingRequest) {
		
			doUpSert(activityTaskUserMappingRequest);
		
	}

	private void doUpSert(ActivityTaskUserMappingRequest activityTaskUserMappingRequest) {
		
		ActivityTaskUserMappingRequest.TaskActivity taskActivityReq = activityTaskUserMappingRequest.getTaskActivity();
		
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

		List<TaskActivity> activitiesbytaskList = activitiesTaskRepository.findByClientIdAndProjectIdAndTaskId(clientId ,projectId,taskId );
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
	public void deleteActivitesByClientProjectTask(long taskActivityId) {
		activitiesTaskRepository.deleteById(taskActivityId);
	}
}
