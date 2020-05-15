package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.service.ActivityMasterService;



@Service
public class ActivityMasterServiceImpl implements ActivityMasterService {

	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Override
	public List<ActivityMasterDTO> getAllActivities() {
		List<ActivityMaster> activityMasters= activityMasterRepository.findAll();
		List<ActivityMasterDTO> activityMasterDTOs = new ArrayList<ActivityMasterDTO>();
		ActivityMasterDTO activityMasterDTO = null;
		for (ActivityMaster activityMaster : activityMasters) {
			activityMasterDTO = new ActivityMasterDTO();
			activityMasterDTO.setId(activityMaster.getId());
			activityMasterDTO.setClient_id(activityMaster.getClient_id());
			activityMasterDTO.setProject_id(activityMaster.getProject_id());
			activityMasterDTO.setActivity_code(activityMaster.getActivity_code());
			activityMasterDTO.setActivity_desc(activityMaster.getActivity_desc());
			activityMasterDTO.setCreated_by(activityMaster.getCreated_by());
			activityMasterDTO.setModified_by(activityMaster.getModified_by());
			activityMasterDTOs.add(activityMasterDTO);
		}
		return activityMasterDTOs;
	}
	
	@Override
	public void addActivitymaster(ActivityMaster activitymaster)
	{
		activityMasterRepository.save(activitymaster);
	}

	
}
