package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.response.ActivitiesByClientResponse;


public interface ActivityMasterService {

	
	public List<ActivityMasterDTO> getAllActivities();

	public void addActivitymaster(ActivityMaster activitymaster);

	public List<ActivitiesByClientResponse> getActivitiesByClient(Long clientId);
}
