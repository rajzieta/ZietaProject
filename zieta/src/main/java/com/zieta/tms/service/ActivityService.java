package com.zieta.tms.service;

import java.util.List;
import com.zieta.tms.dto.ActivityMasterDTO;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.request.AcitivityRequest;
import com.zieta.tms.request.ActivityTaskUserMappingRequest;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientResponse;
import com.zieta.tms.response.ActivitiesByClientUserModel;
import javax.validation.Valid;



public interface ActivityService {

	
	public List<ActivityMasterDTO> getAllActivities();

	public void addActivitymaster(ActivityMaster activitymaster);

	public List<ActivitiesByClientResponse> getActivitiesByClient(Long clientId);
	
	public void  addActivitiesByClientProjectTask(@Valid List<ActivityTaskUserMappingRequest> activityTaskUserMappingRequest);
	
	public void editActivitiesById(AcitivityRequest acitivityRequest) throws Exception;

	public void editActivitiesByClientProjectTask(@Valid List<ActivityTaskUserMappingRequest> activityTaskUserMappingRequest);
	
	public List<ActivitiesByClientProjectTaskResponse> getActivitesByClientProjectTask(long clientId,
			long projectId,long taskId);
	
	public void deleteActivitesByClientProjectTask(long taskActivityId, String modifiedBy) throws Exception;
	
	public List<ActivitiesByClientUserModel> getActivitiesByClientUser(Long clientId, Long userId);

	public void deleteActivityById(Long id, String modifiedBy) throws Exception;
}
