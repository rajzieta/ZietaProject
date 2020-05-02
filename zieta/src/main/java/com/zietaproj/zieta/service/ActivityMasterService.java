package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;


public interface ActivityMasterService {

	
	public List<ActivityMasterDTO> getAllActivities();

	public void addActivitymaster(ActivityMaster activitymaster);
}
