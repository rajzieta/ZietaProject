package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.repository.ProjectMasterRepository;
import com.zietaproj.zieta.service.ProjectMasterService;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService{

	
	@Autowired
	ProjectMasterRepository projectMasterRepository;
	
	@Override
	public List<ProjectMasterDTO> getAllProjects() {
		List<ProjectMaster> projectMasters= projectMasterRepository.findAll();
		List<ProjectMasterDTO> projectMasterDTOs = new ArrayList<ProjectMasterDTO>();
		ProjectMasterDTO projectMasterDTO = null;
		for (ProjectMaster projectMaster : projectMasters) {
			projectMasterDTO = new ProjectMasterDTO();
			projectMasterDTO.setId(projectMaster.getId());
			projectMasterDTO.setProject_type(projectMaster.getProject_type());
			projectMasterDTO.setClient_id(projectMaster.getClient_id());
			projectMasterDTO.setCreated_by(projectMaster.getCreated_by());
			projectMasterDTO.setModified_by(projectMaster.getModified_by());
			projectMasterDTOs.add(projectMasterDTO);
		}
		return projectMasterDTOs;
	}
	
	@Override
	public void addProjectmaster(ProjectMaster projectmaster)
	{
		projectMasterRepository.save(projectmaster);
	}

}
