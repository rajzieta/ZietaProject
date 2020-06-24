package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.ActivityUserMapping;
import com.zietaproj.zieta.model.TaskActivity;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ActivitiesTaskRepository;
import com.zietaproj.zieta.repository.ActivitiyUserMappingRepository;
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
public class ActivityServiceImpl implements ActivityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	ClientInfoRepository  clientInfoRepository;
	
	@Autowired
	ActivitiyUserMappingRepository activitiyUserMappingRepository;
	
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
					.setClient_code(clientInfoRepository.findById(activityMaster.getClientId())
							.get().getClient_code());
			activityMasterDTO.setIS_ACTIVE(activityMaster.isActive());
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
	@Transactional
	public void addActivitiesByClientProjectTask(ActivityTaskUserMappingRequest activityTaskUserMappingRequest) {
		
			ActivityTaskUserMappingRequest.ActivityUser activityUserReq = activityTaskUserMappingRequest.getActivityUser();
			
			ActivityTaskUserMappingRequest.TaskActivity taskActivityReq = activityTaskUserMappingRequest.getTaskActivity();
			
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			ActivityUserMapping activityUserMappingEntity = modelMapper.map(activityUserReq, ActivityUserMapping.class);
			TaskActivity taskActivityEntity = modelMapper.map(taskActivityReq, TaskActivity.class);
			
			TaskActivity taskActivityObj = activitiesTaskRepository.save(taskActivityEntity);
			ActivityUserMapping activityUserMappingObj = activitiyUserMappingRepository.save(activityUserMappingEntity);
		
	}

	@Override
	public void editActivitiesById(AcitivityRequest acitivityRequest) throws Exception {
		Optional<ActivityMaster> activityMasterEntity = activityMasterRepository.findById(acitivityRequest.getId());
		if(activityMasterEntity.isPresent()) {
			ActivityMaster activityMasterSave = activityMasterEntity.get();
			activityMasterSave.setActive(acitivityRequest.isActive());
			activityMasterSave.setActivityCode(acitivityRequest.getActivityCode());
			activityMasterSave.setActivity_desc(acitivityRequest.getActivity_desc());
			activityMasterSave.setModified_by(acitivityRequest.getModified_by());
			activityMasterRepository.save(activityMasterSave);
			
		}else {
			throw new Exception("Activity not found with the provided activity ID : "+acitivityRequest.getId());
		}
		
	}
	
	
	
	@Override
	public void editActivitiesByClientProjectTask(ActivityTaskUserMappingRequest activityTaskUserMappingRequest) { 
		
		ActivityTaskUserMappingRequest.ActivityUser activityUserReq = activityTaskUserMappingRequest.getActivityUser();
		
		ActivityTaskUserMappingRequest.TaskActivity taskActivityReq = activityTaskUserMappingRequest.getTaskActivity();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ActivityUserMapping activityUserMappingEntity = modelMapper.map(activityUserReq, ActivityUserMapping.class);
		TaskActivity taskActivityEntity = modelMapper.map(taskActivityReq, TaskActivity.class);
		
		TaskActivity taskActivityObj = activitiesTaskRepository.save(taskActivityEntity);
		ActivityUserMapping activityUserMappingObj = activitiyUserMappingRepository.save(activityUserMappingEntity);
		
		//fetch the JPA entities
//		TaskActivity taskActivityObj = activitiesTaskRepository.findById(taskActivityReq.getId()).get();
//		ActivityUserMapping activityUserMappingObj = activitiyUserMappingRepository.findById(activityUserReq.getId()).get();
		
		
  }
	
	
	public List<ActivitiesByClientProjectTaskResponse> getActivitesByClientProjectTaskTest(long clientId,long projectId,long taskId) {

		List<TaskActivity> activitiesbytaskList = activitiesTaskRepository.findByClientIdAndProjectIdAndTaskId(clientId ,projectId,taskId );
		List<ActivitiesByClientProjectTaskResponse> activitiesByClientProjectTaskList = new ArrayList<ActivitiesByClientProjectTaskResponse>();
		for (TaskActivity activitiesbytask : activitiesbytaskList) {

			ActivitiesByClientProjectTaskResponse activitiesByClientProjectTaskResponse = new ActivitiesByClientProjectTaskResponse();
			ActivitiesByClientProjectTaskResponse.ActivityUser activityUser = activitiesByClientProjectTaskResponse.getActivityUser();
			ActivitiesByClientProjectTaskResponse.TaskActivity taskActivity = activitiesByClientProjectTaskResponse.getTaskActivity();
			ActivitiesByClientProjectTaskResponse.TaskActivityUserDetails taskActivityUserDetails = activitiesByClientProjectTaskResponse.getTaskActivityUserDetails();
			// task
			taskActivity.setActivity_id(activitiesbytask.getActivity_id());
			taskActivity.setActualHrs(activitiesbytask.getActualHrs());
			taskActivity.setClientId(activitiesbytask.getClientId());
			taskActivity.setEndDate(TSMUtil.convertToLocalDateViaMilisecond(activitiesbytask.getEndDate()));
			taskActivity.setId(activitiesbytask.getId());
			taskActivity.setModified_by(activitiesbytask.getModified_by());
			taskActivity.setCreated_by(activitiesbytask.getCreated_by());
			taskActivity.setPlannedHrs(activitiesbytask.getPlannedHrs());
			taskActivity.setProjectId(activitiesbytask.getProjectId());
			taskActivity.setStartDate(TSMUtil.convertToLocalDateViaMilisecond(activitiesbytask.getStartDate()));
			taskActivity.setTaskId(activitiesbytask.getTaskId());

			String teamMemberName;
			Optional<ActivityUserMapping> activityUserMapping = activitiyUserMappingRepository
					.findById(activitiesbytask.getActivity_id());
			if (activityUserMapping.isPresent()) {

				// activity
				activityUser.setActivityId(activityUserMapping.get().getActivityId());
				activityUser.setClientId(activityUserMapping.get().getClientId());
				activityUser.setId(activityUserMapping.get().getId());
				activityUser.setProjectId(activityUserMapping.get().getProjectId());
				activityUser.setTaskId(activityUserMapping.get().getTaskId());
				activityUser.setUserId(activityUserMapping.get().getUserId());
				activityUser.setCreated_by(activityUserMapping.get().getCreated_by());
				activityUser.setModified_by(activityUserMapping.get().getModified_by());

				Optional<UserInfo> userInfo = userInfoReposistory.findById(activityUserMapping.get().getUserId());
				if (userInfo.isPresent()) {
					teamMemberName = TSMUtil.getFullName(userInfo.get());
					// otherdetails
					taskActivityUserDetails.setUserName(teamMemberName);
				}
			}

			Optional<ActivityMaster> activitymaster = activityMasterRepository
					.findById(activitiesbytask.getActivity_id());
			if (activitymaster.isPresent()) {
				// otherdetails
				taskActivityUserDetails.setActivityCode(activitymaster.get().getActivityCode());
				taskActivityUserDetails.setActivityDesc(activitymaster.get().getActivity_desc());
			}

			activitiesByClientProjectTaskList.add(activitiesByClientProjectTaskResponse);
		}

		return activitiesByClientProjectTaskList;
	}
}
