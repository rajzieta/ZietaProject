package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.request.AcitivityRequest;
import com.zietaproj.zieta.request.ActivityTaskUserMappingRequest;
import com.zietaproj.zieta.response.ActivitiesByClientProjectTaskResponse;
import com.zietaproj.zieta.response.ActivitiesByClientResponse;
import com.zietaproj.zieta.response.ActivitiesByClientUserModel;


public interface ActivityService {

	
	public List<ActivityMasterDTO> getAllActivities();

	public void addActivitymaster(ActivityMaster activitymaster);

	public List<ActivitiesByClientResponse> getActivitiesByClient(Long clientId);
	
	public void  addActivitiesByClientProjectTask(ActivityTaskUserMappingRequest activityTaskUserMappingRequest);
	
	public void editActivitiesById(AcitivityRequest acitivityRequest) throws Exception;

	public void editActivitiesByClientProjectTask(ActivityTaskUserMappingRequest activityTaskUserMappingRequest);
	
	public List<ActivitiesByClientProjectTaskResponse> getActivitesByClientProjectTask(long clientId,
			long projectId,long taskId);
	
	public void deleteActivitesByClientProjectTask(long taskActivityId, String modifiedBy) throws Exception;
	
	public List<ActivitiesByClientUserModel> getActivitiesByClientUser(Long clientId, Long userId);

	public void deleteActivityById(Long id, String modifiedBy) throws Exception;
}
