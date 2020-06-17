package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

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
import com.zietaproj.zieta.repository.ActivitiesTaskRepository;
import com.zietaproj.zieta.repository.ActivitiyUserMappingRepository;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.request.ActivityTaskUserMappingRequest;
import com.zietaproj.zieta.response.ActivitiesByClientResponse;
import com.zietaproj.zieta.service.ActivityService;



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
		
		try {
			ActivityTaskUserMappingRequest.ActivityUser activityUserReq = activityTaskUserMappingRequest.getActivityUser();
			
			ActivityTaskUserMappingRequest.TaskActivity taskActivityReq = activityTaskUserMappingRequest.getTaskActivity();
			
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			ActivityUserMapping activityUserMappingEntity = modelMapper.map(activityUserReq, ActivityUserMapping.class);
			TaskActivity taskActivityEntity = modelMapper.map(taskActivityReq, TaskActivity.class);
			
			TaskActivity taskActivityObj = activitiesTaskRepository.save(taskActivityEntity);
			ActivityUserMapping activityUserMappingObj = activitiyUserMappingRepository.save(activityUserMappingEntity);
		} catch (Exception e) {
			LOGGER.error("Exception occured during saving the ActivitiesByClientProjectTask",e );
		}
		
		
	}
}
