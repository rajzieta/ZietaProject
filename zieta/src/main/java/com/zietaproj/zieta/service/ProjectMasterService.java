package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.request.EditProjStatusRequest;
import com.zietaproj.zieta.request.ProjectMasterEditRequest;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;
import com.zietaproj.zieta.response.ProjectTypeByClientResponse;
import com.zietaproj.zieta.response.ProjectsByClientResponse;

public interface ProjectMasterService {

	public List<ProjectDetailsByUserModel> getAllProjects();

	public void addProjectinfo(ProjectInfo projectinfo);
	
	List<ProjectDetailsByUserModel> getProjectsByUser(long userId);

	List<ProjectDetailsByUserModel> getProjectsByClient(Long clientId);

	public void editProjectStatus(@Valid EditProjStatusRequest editprojStatusRequest) throws Exception;

	public List<ProjectTypeByClientResponse> getProjecttypessByClient(Long clientId);

	public void editProjectsById(@Valid ProjectMasterEditRequest projectmasterEditRequest) throws Exception;

	public void deleteProjectsById(Long id, String modifiedBy) throws Exception;
	
	public boolean editProjectByTemplate(Long projectId, Long templateId);
}
