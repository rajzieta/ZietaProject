package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.TaskActivity;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.ActivitiesTaskRepository;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.response.ActivitiesByTaskResponse;
import com.zieta.tms.service.ActivitiesByTaskService;
import com.zieta.tms.util.TSMUtil;

@Service
@Transactional
public class ActivitiesByTaskServiceImpl implements ActivitiesByTaskService {

	@Autowired
	ActivitiesTaskRepository activitiesbytaskRepo;
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	UserInfoRepository userInfoReposistory;

	
	@Override
	public List<ActivitiesByTaskResponse> getActivitesByClientProjectTaskOld(long clientId,long projectId,long taskId) {
     //    short notDeleted=0;
		List<TaskActivity> activitiesbytaskList = activitiesbytaskRepo.findByClientIdAndProjectIdAndTaskId(clientId ,projectId,taskId);
		List<ActivitiesByTaskResponse> activitiesbytaskResponseList = new ArrayList<ActivitiesByTaskResponse>();
		ActivitiesByTaskResponse activitiesbytaskResponse = null;
		for(TaskActivity activitiesbytask : activitiesbytaskList) {
			activitiesbytaskResponse = new ActivitiesByTaskResponse();
			activitiesbytaskResponse.setTaskId(activitiesbytask.getTaskId());
			activitiesbytaskResponse.setClientId(activitiesbytask.getClientId());
			activitiesbytaskResponse.setActivityId(activitiesbytask.getActivityId());
			activitiesbytaskResponse.setEndDate(TSMUtil.getFormattedDateAsString(activitiesbytask.getEndDate()));
			activitiesbytaskResponse.setStartDate(TSMUtil.getFormattedDateAsString(activitiesbytask.getStartDate()));
			activitiesbytaskResponse.setPlannedHrs(activitiesbytask.getPlannedHrs());
			activitiesbytaskResponse.setActualHrs(activitiesbytask.getActualHrs());
			
			String teamMemberName = StringUtils.EMPTY;
				activitiesbytaskResponse.setUserId(activitiesbytask.getUserId());
				Optional<UserInfo> userInfo = userInfoReposistory.findById(activitiesbytask.getUserId());
				if(userInfo.isPresent()) {
				teamMemberName = TSMUtil.getFullName(userInfo.get());
				activitiesbytaskResponse.setUserName(teamMemberName);
				}
			
			Optional<ActivityMaster> activitymaster = activityMasterRepository.findById(activitiesbytask.getActivityId());
			if(activitymaster.isPresent()) {
				activitiesbytaskResponse.setActivityCode(activitymaster.get().getActivityCode());
				activitiesbytaskResponse.setActivityDesc(activitymaster.get().getActivityDesc());
			}
			
			activitiesbytaskResponseList.add(activitiesbytaskResponse);
		}

		return activitiesbytaskResponseList;
	}
}
