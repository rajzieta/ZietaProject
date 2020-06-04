package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TaskActivity;
import com.zietaproj.zieta.repository.ActivitiesTaskRepository;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.service.ActivitiesByTaskService;

@Service
@Transactional
public class ActivitiesByTaskServiceImpl implements ActivitiesByTaskService {

	@Autowired
	ActivitiesTaskRepository activitiesbytaskRepo;
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Override
	public List<ActivitiesByTaskResponse> getActivitiesByTask(Long taskId) {

		List<TaskActivity> activitiesbytaskList = activitiesbytaskRepo.findByTaskId(taskId);
		List<ActivitiesByTaskResponse> activitiesbytaskResponseList = new ArrayList<ActivitiesByTaskResponse>();
		ActivitiesByTaskResponse activitiesbytaskResponse = null;
		for(TaskActivity activitiesbytask : activitiesbytaskList) {
			activitiesbytaskResponse = new ActivitiesByTaskResponse();
			activitiesbytaskResponse.setTask_id(activitiesbytask.getTaskId());
			activitiesbytaskResponse.setClient_id(activitiesbytask.getClientId());
			activitiesbytaskResponse.setActivity_id(activitiesbytask.getActivity_id());
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
