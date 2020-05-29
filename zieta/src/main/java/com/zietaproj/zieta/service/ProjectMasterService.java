package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;

public interface ProjectMasterService {

	public List<ProjectMasterDTO> getAllProjects();

	public void addProjectmaster(ProjectMaster projectmaster);
	
	List<ProjectDetailsByUserModel> getProjectsByUser(long userId);
}
