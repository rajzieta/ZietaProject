package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.repository.TaskMasterRepository;
import com.zietaproj.zieta.service.TaskMasterService;

@Service
public class TaskMasterServiceImpl implements TaskMasterService {

	@Autowired
	TaskMasterRepository taskMasterRepository;
	
	@Override
	public List<TaskMasterDTO> getAllTasks() {
		List<TaskMaster> taskMasters= taskMasterRepository.findAll();
		List<TaskMasterDTO> taskMasterDTOs = new ArrayList<TaskMasterDTO>();
		TaskMasterDTO taskMasterDTO = null;
		for (TaskMaster taskMaster : taskMasters) {
			taskMasterDTO = new TaskMasterDTO();
			taskMasterDTO.setId(taskMaster.getId());
			taskMasterDTO.setTask_type(taskMaster.getTask_type());
			taskMasterDTO.setClient_id(taskMaster.getClient_id());
			taskMasterDTO.setModified_by(taskMaster.getModified_by());
			taskMasterDTO.setCreated_by(taskMaster.getCreated_by());
			taskMasterDTOs.add(taskMasterDTO);
		}
		return taskMasterDTOs;
	}
	
	@Override
	public void addTaskmaster(TaskMaster taskmaster)
	{
		taskMasterRepository.save(taskmaster);
	}

	
}
