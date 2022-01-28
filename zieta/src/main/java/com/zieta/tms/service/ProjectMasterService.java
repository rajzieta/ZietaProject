package com.zieta.tms.service;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.dto.ExternalProjectMasterDTO;
import com.zieta.tms.dto.ProjectInfoDTO;
import com.zieta.tms.dto.ProjectMasterDTO;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.ProjectMaster;
import com.zieta.tms.request.EditProjStatusRequest;
import com.zieta.tms.request.ProjectMasterEditRequest;
import com.zieta.tms.request.ProjectTypeEditRequest;
import com.zieta.tms.response.FetchDataResponse;
import com.zieta.tms.response.ProjectDetailsByUserModel;
import com.zieta.tms.response.ProjectTypeByClientResponse;
import com.zieta.tms.response.ResponseData;

public interface ProjectMasterService {

	public List<ProjectDetailsByUserModel> getAllProjects();

	public void addProjectinfo(ProjectInfo projectinfo);
	
	//ADD PROJECT FROM BYD SYSTEM
	public ResponseData addExternalProjectinfo(ExternalProjectMasterDTO bydProjectinfo);
	
	List<ProjectDetailsByUserModel> getProjectsByUser(long userId);

	List<ProjectDetailsByUserModel> getProjectsByClient(Long clientId);
	
	public void editProjectStatus(@Valid EditProjStatusRequest editprojStatusRequest) throws Exception;

	public List<ProjectTypeByClientResponse> getProjecttypessByClient(Long clientId);

	public void editProjectsById(@Valid ProjectMasterEditRequest projectmasterEditRequest) throws Exception;

	public void deleteProjectsById(Long id, String modifiedBy) throws Exception;
	
	public boolean editProjectByTemplate(Long projectId, Long templateId);

	public List<ProjectMasterDTO> getAllProjectTypes();

	public void addProjectTypeMaster(@Valid ProjectMaster projectmaster);

	public void editProjectTypesById(@Valid ProjectTypeEditRequest projectTypeEditRequest) throws Exception;

	public void deleteProjectTypesById(Long id, String modifiedBy) throws Exception;
	
	List<ProjectDetailsByUserModel> getBYDProjectsByClient(Long clientId);
	
	public ProjectInfo findByProjectId(Long projectId);
	
	public ProjectInfoDTO findByProjectsId(Long projectId);

	public FetchDataResponse findBySapDate(Long id, Long clientId);
	//public ProjectInfo findByProjectId(Long projectId);
	public ResponseData updateBySapDate(Long id, Long clientId);
	
	//public void readProjectFromExcel(MultipartFile projectExcelData);
}
