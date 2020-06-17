package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TaskActivity;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ActivitiesTaskRepository;
import com.zietaproj.zieta.repository.ActivitiyUserMappingRepository;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.service.ActivitiesByTaskService;
import com.zietaproj.zieta.util.TSMUtil;

@Service
@Transactional
public class ActivitiesByTaskServiceImpl implements ActivitiesByTaskService {

	@Autowired
	ActivitiesTaskRepository activitiesbytaskRepo;
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	UserInfoRepository userInfoReposistory;
	
	@Autowired 
	ActivitiyUserMappingRepository activitiyUserMappingRepository;
	
	@Override
	public List<ActivitiesByTaskResponse> getActivitesByClientProjectTask(long clientId,long projectId,long taskId) {

		List<TaskActivity> activitiesbytaskList = activitiesbytaskRepo.findByClientIdAndProjectIdAndTaskId(clientId ,projectId,taskId );
		List<ActivitiesByTaskResponse> activitiesbytaskResponseList = new ArrayList<ActivitiesByTaskResponse>();
		ActivitiesByTaskResponse activitiesbytaskResponse = null;
		for(TaskActivity activitiesbytask : activitiesbytaskList) {
			activitiesbytaskResponse = new ActivitiesByTaskResponse();
			activitiesbytaskResponse.setTask_id(activitiesbytask.getTaskId());
			activitiesbytaskResponse.setClient_id(activitiesbytask.getClientId());
			activitiesbytaskResponse.setActivity_id(activitiesbytask.getActivity_id());
			long userId = activitiyUserMappingRepository.findById(activitiesbytask.getActivity_id())
					.get().getUserId();
			activitiesbytaskResponse.setUserId(userId);
			UserInfo userInfo = userInfoReposistory.findById(userId).get();
			String teamMemberName = TSMUtil.getFullName(userInfo);
			activitiesbytaskResponse.setUserName(teamMemberName);
			
			Optional<ActivityMaster> activitymaster = activityMasterRepository.findById(activitiesbytask.getActivity_id());
			if(activitymaster.isPresent()) {
				activitiesbytaskResponse.setActivity_code(activitymaster.get().getActivity_code());
				activitiesbytaskResponse.setActivity_desc(activitymaster.get().getActivity_desc());
			}
			
			activitiesbytaskResponseList.add(activitiesbytaskResponse);
		}

		return activitiesbytaskResponseList;
	}
}
