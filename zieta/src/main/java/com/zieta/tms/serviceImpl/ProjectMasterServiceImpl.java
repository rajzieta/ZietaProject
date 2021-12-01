package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.directconnect.model.Connection;
import com.zieta.tms.dto.ExternalProjectMasterDTO;
import com.zieta.tms.dto.ProjectMasterDTO;
import com.zieta.tms.exception.ExternalIdException;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.CustInfo;
import com.zieta.tms.model.ProcessMaster;
import com.zieta.tms.model.ProcessSteps;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.ProjectMaster;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ConnectionMasterInfoRepository;
import com.zieta.tms.repository.CustInfoRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.ProcessMasterRepository;
import com.zieta.tms.repository.ProcessStepsRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.ProjectMasterRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.TaskInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.EditProjStatusRequest;
import com.zieta.tms.request.ProjectMasterEditRequest;
import com.zieta.tms.request.ProjectTypeEditRequest;
import com.zieta.tms.response.AddProjectResponse;
import com.zieta.tms.response.FetchDataResponse;
import com.zieta.tms.response.ProjectDetailsByUserModel;
import com.zieta.tms.response.ProjectTypeByClientResponse;
import com.zieta.tms.response.ResponseData;
import com.zieta.tms.service.ProcessService;
import com.zieta.tms.service.ProjectMasterService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectMasterServiceImpl implements ProjectMasterService{

	
	@Autowired
	ProjectMasterRepository projectMasterRepository;
	
	@Autowired
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	CustInfoRepository custInfoRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
		
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	ProcessMasterRepository processMasterRepository;
	
	@Autowired
	ProcessStepsRepository processStepsRepository;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	ConnectionMasterInfoRepository connectionMasterInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<ProjectDetailsByUserModel> getAllProjects() {
		short notDeleted=0;
		List<ProjectInfo> projectList = projectInfoRepository.findByIsDelete(notDeleted);
		List<ProjectDetailsByUserModel> projectsByClientResponseList = new ArrayList<>();

		fillProjectDetails(projectList, projectsByClientResponseList);

		return projectsByClientResponseList;

	}
	
	@Override
	public void addProjectinfo(ProjectInfo projectinfo)
	{
		projectInfoRepository.save(projectinfo);
	}
	
	
	//ADD PROJECT IN PROJECT_INFO FROM BYD SYSTEM	
	@Override
	public ResponseData addExternalProjectinfo(ExternalProjectMasterDTO bydProjectinfo)
	{		
		ProjectInfo returnData =null;
		ResponseData responseData = new ResponseData();
		//manipulate bydprojectinfo data and set it to project info an save it
		try {				
				if(bydProjectinfo.getExtCustId() ==null || bydProjectinfo.getExtCustId()=="" ||
			    bydProjectinfo.getExtId() ==null || bydProjectinfo.getExtId()=="" ||bydProjectinfo.getExtProjectManagerId()==null || bydProjectinfo.getExtProjectManagerId()==""
			     || bydProjectinfo.getExtProjectStatus() == null || bydProjectinfo.getExtProjectStatus() =="" ||
			    bydProjectinfo.getExtProjectType() ==null || bydProjectinfo.getExtProjectType() =="" ||	
			    bydProjectinfo.getExtProjectOrgNode() == null || bydProjectinfo.getExtProjectOrgNode() =="") {
					
					throw new ExternalIdException("ExternalId not found");
					
				}else{
					    log.error("Checking existing project");
					    ProjectInfo projectInfo = new ProjectInfo();
					    ProjectInfo chkExist = projectInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtId().trim(),bydProjectinfo.getClientId());
					    
						Long custId = custInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtCustId().trim(), bydProjectinfo.getClientId()).getCustInfoId();						
						Long projectManager = userInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectManagerId().trim(), bydProjectinfo.getClientId()).getId();						
						Long directApprover =0L;
						
						if(!bydProjectinfo.getExtDirectApprover().isEmpty()) {						
							directApprover = userInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtDirectApprover().trim(), bydProjectinfo.getClientId()).getId();
							projectInfo.setDirectApprover(directApprover);
						}
						
						Long projectStatus = statusMasterRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectStatus().trim(), bydProjectinfo.getClientId()).getId();
						
						Long projectType = projectMasterRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectType().trim(), bydProjectinfo.getClientId()).getProjectTypeId();
						
						Long orgNode = orgInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectOrgNode().trim(), bydProjectinfo.getClientId()).getOrgUnitId();
												
						if(chkExist!= null) {
							projectInfo.setProjectInfoId(chkExist.getProjectInfoId());
						}					
						projectInfo.setExtId(bydProjectinfo.getExtId());
						projectInfo.setClientId(bydProjectinfo.getClientId());
						projectInfo.setProjectName(bydProjectinfo.getProjectName());
						projectInfo.setProjectType(projectType);
						projectInfo.setProjectOrgNode(orgNode);
						projectInfo.setProjectManager(projectManager);
						projectInfo.setTemplateId(bydProjectinfo.getTemplateId());
						
						projectInfo.setAllowUnplanned(bydProjectinfo.getAllowUnplanned());
						projectInfo.setCustId(custId);
						projectInfo.setProjectStatus(projectStatus);
						
						projectInfo.setCreatedBy(bydProjectinfo.getCreatedBy());
						projectInfo.setModifiedBy(bydProjectinfo.getCreatedBy());			
						
						returnData = projectInfoRepository.save(projectInfo);
						responseData.setId(returnData.getProjectInfoId());
						responseData.setIsSaved(true);
						log.error("External project Saved");
				}
		}catch(Exception e) {
			log.error(" Failed to add project from Byd");
		}		
		return responseData;
	}
	

	@Override
	public List<ProjectDetailsByUserModel> getProjectsByUser(long projectManagerId) {
		short notDeleted=0;
		List<ProjectDetailsByUserModel> projectDetailsByUserList = new ArrayList<>();
		List<ProjectInfo> projectmanagerMappingList = projectInfoRepository.findByProjectManagerAndIsDelete(projectManagerId, notDeleted);
		List<Long> projectIdList = projectmanagerMappingList.stream().map(ProjectInfo::getProjectInfoId).collect(Collectors.toList());
		List<ProjectInfo> projectInfoList = projectInfoRepository.findAllById(projectIdList);
		fillProjectDetails(projectInfoList, projectDetailsByUserList);
		
		return projectDetailsByUserList;
	}

	private String getProjectManagerName(ProjectInfo projectInfo) {
		long projectManagerId = projectInfo.getProjectManager();
		UserInfo userInfo = userInfoRepository.findById(projectManagerId).get();
		return TSMUtil.getFullName(userInfo);
	}
	
	@Override
	public List<ProjectDetailsByUserModel> getProjectsByClient(Long clientId) {
		short notDeleted=0;
		List<ProjectInfo> projectList = projectInfoRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<ProjectDetailsByUserModel> projectDetailsByUserList = new ArrayList<>();

		fillProjectDetails(projectList, projectDetailsByUserList);

		return projectDetailsByUserList;

	}

	private void fillProjectDetails(List<ProjectInfo> projectInfoList,
			List<ProjectDetailsByUserModel> projectDetailsByUserList) {
		for(ProjectInfo projectInfo: projectInfoList) {
			ProjectDetailsByUserModel projectDetailsByUserModel = modelMapper.map(projectInfo, ProjectDetailsByUserModel.class);
			
			//setting additonal details starts
			projectDetailsByUserModel.setClientCode(clientInfoRepository.findById(projectInfo.getClientId()).get().getClientCode());
			projectDetailsByUserModel.setClientDescription(clientInfoRepository.findById(projectInfo.getClientId()).get().getClientName());
			projectDetailsByUserModel.setClientStatus(clientInfoRepository.findById(projectInfo.getClientId()).get().getClientStatus());

			projectDetailsByUserModel.setProjectTypeName(
					projectMasterRepository.findById(projectInfo.getProjectType()).get().getTypeName());
			projectDetailsByUserModel.setOrgNodeName(orgInfoRepository.findById(projectInfo.getProjectOrgNode())
					.get().getOrgNodeName());
			String prjManagerName = getProjectManagerName(projectInfo);
			projectDetailsByUserModel.setProjectManagerName(prjManagerName);
		//	projectDetailsByUserModel.setProjectStatusDescription(statusMasterRepository.findById(projectInfo.getProjectStatus()).get().getStatusCode());
			
			projectDetailsByUserModel.setProjectStatusDescription(StringUtils.EMPTY);
			if(null != projectInfo.getProjectStatus()) {
				Optional <StatusMaster> statusmaster = statusMasterRepository.findById(projectInfo.getProjectStatus());
				if(statusmaster.isPresent()) {
					projectDetailsByUserModel.setProjectStatusDescription(statusmaster.get().getStatusCode());
				}
			}
			
			if(projectInfo.getCustId() != null && projectInfo.getCustId() !=0) {
			CustInfo custoInfo = custInfoRepository.findById(projectInfo.getCustId()).get();
			projectDetailsByUserModel.setCustInfo(custoInfo);
			}
			projectDetailsByUserModel.setTemplateDesc(StringUtils.EMPTY);
			if(null != projectInfo.getTemplateId()) {
				Optional <ProcessMaster> processmaster = processMasterRepository.findById(projectInfo.getTemplateId());
				if(processmaster.isPresent()) {
					projectDetailsByUserModel.setTemplateDesc(processmaster.get().getProcessName());
				}
			}
			projectDetailsByUserModel.setApproverName(StringUtils.EMPTY);
			if(null != projectInfo.getDirectApprover()) {
				Optional <UserInfo> userInfo = userInfoRepository.findById(projectInfo.getDirectApprover());
				if(userInfo.isPresent()) {
					String userName = TSMUtil.getFullName(userInfo.get());
					projectDetailsByUserModel.setApproverName(userName);
				}
			}
			//setting additonal details ends
			projectDetailsByUserList.add(projectDetailsByUserModel);
		}
	}

	
	
	
	public List<ProjectTypeByClientResponse> getProjecttypessByClient(Long clientId) {
		short notDeleted=0;
		List<ProjectMaster> projecttypesByClientList = projectMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<ProjectTypeByClientResponse> projecttypeslist = new ArrayList<>();
		ProjectTypeByClientResponse projecttypesByClientResponse = null;
		for (ProjectMaster projecttypesByClient : projecttypesByClientList) {
			projecttypesByClientResponse = modelMapper.map(projecttypesByClient, 
					ProjectTypeByClientResponse.class);
			projecttypesByClientResponse.setClientCode(clientInfoRepository.findById(projecttypesByClient.getClientId()).get().getClientCode());
			projecttypesByClientResponse.setClientDescription(clientInfoRepository.findById(projecttypesByClient.getClientId()).get().getClientName());
			
			projecttypeslist.add(projecttypesByClientResponse);
		}
		return projecttypeslist;		
	}
	
	@Override
	public void editProjectStatus(@Valid EditProjStatusRequest editprojStatusRequest) throws Exception {
		
		Optional<ProjectInfo> projinfoEntity = projectInfoRepository.findById(editprojStatusRequest.getProjectInfoId());
		if(projinfoEntity.isPresent()) {
			ProjectInfo projstatusSave = projinfoEntity.get();
			projstatusSave.setProjectStatus(editprojStatusRequest.getProjectStatus());
			projectInfoRepository.save(projstatusSave);
		}else {
			throw new Exception("Status not found with the provided activity ID : "+editprojStatusRequest.getProjectInfoId());
		}
	}
	
	@Override
	public void editProjectsById(@Valid ProjectMasterEditRequest projectmasterEditRequest) throws Exception {
	
		Optional<ProjectInfo> projectInfoEntity = projectInfoRepository.findById(projectmasterEditRequest.getProjectInfoId());
		if(projectInfoEntity.isPresent()) {
			ProjectInfo projectinfo = modelMapper.map(projectmasterEditRequest, ProjectInfo.class);
			projectInfoRepository.save(projectinfo);
			
		}else {
			throw new Exception("Project Details not found with the provided ID : "+projectmasterEditRequest.getProjectInfoId());
		}
		
		
	}
	
	public void deleteProjectsById(Long id, String modifiedBy) throws Exception {
		
		Optional<ProjectInfo> projectinfo = projectInfoRepository.findById(id);
		if (projectinfo.isPresent()) {
			ProjectInfo projectinfoEntitiy = projectinfo.get();
			short delete = 1;
			projectinfoEntitiy.setIsDelete(delete);
			projectinfoEntitiy.setModifiedBy(modifiedBy);
			projectInfoRepository.save(projectinfoEntitiy);

		}else {
		//	log.info("No ProjectDetails found with the provided ID{} in the DB",id);
			throw new Exception("No ProjectDetails found with the provided ID in the DB :"+id);
		}
		
		
		
	}

	@Override
	public boolean editProjectByTemplate(Long projectId, Long templateId) {
		ProjectInfo  projectInfo = null;
		try {
			projectInfo = projectInfoRepository.findById(projectId).get();
			projectInfo.setTemplateId(templateId);
		}catch (Exception e) {
			log.error("Exception occured while updating the projectInfo {} with the provided template-id {}",projectId, templateId);
			log.error(e.getMessage(),e);
			return false;
		}
		
		try {
			List<ProcessSteps> processStepsList = processStepsRepository.findByProjectId(projectId);
			//flush and fill the process-step entries based on the project id
			processStepsRepository.deleteAll(processStepsList);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
		
		try {
			//create the process steps for all the tasks associated with the project.
			List<TaskInfo> taskInfoList =  taskInfoRepository.findByProjectIdAndIsDelete(projectId, (short)0);
			List<ProcessSteps> processStepsForAllTasks = new ArrayList<ProcessSteps>();
			
			for (TaskInfo taskInfo : taskInfoList) {
				
				List<ProcessSteps> processStepForTask = processService.createProcessSteps(taskInfo, projectInfo);
				processStepsForAllTasks.addAll(processStepForTask);
				
			}
			//finally saving all the steps created for the task with new template id/
			processStepsRepository.saveAll(processStepsForAllTasks);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			return false;
		
		}
		
		return true;
	}

	
	@Override
	public List<ProjectMasterDTO> getAllProjectTypes() {
		short notDeleted = 0;
		List<ProjectMaster> projectmasters= projectMasterRepository.findByIsDelete(notDeleted);
		List<ProjectMasterDTO> projectmasterDTOs = new ArrayList<ProjectMasterDTO>();
		ProjectMasterDTO projectmasterDTO = null;
		for (ProjectMaster projectmaster : projectmasters) {
			projectmasterDTO = modelMapper.map(projectmaster, ProjectMasterDTO.class);
			projectmasterDTO.setClientCode(clientInfoRepository.findById(projectmaster.getClientId()).get().getClientCode());
			projectmasterDTO.setClientDescription(clientInfoRepository.findById(projectmaster.getClientId()).get().getClientName());
			projectmasterDTO.setClientStatus(clientInfoRepository.findById(projectmaster.getClientId()).get().getClientStatus());

			projectmasterDTOs.add(projectmasterDTO);
		}
		return projectmasterDTOs;
	}
	
	
	
	
	@Override
	public void addProjectTypeMaster(ProjectMaster projectmaster)
	{
		projectMasterRepository.save(projectmaster);
	}
	
	
	
	@Override
	public void editProjectTypesById(@Valid ProjectTypeEditRequest projectTypeEditRequest) throws Exception {
	
		Optional<ProjectMaster> projectMasterEntity = projectMasterRepository.findById(projectTypeEditRequest.getProjectTypeId());
		if(projectMasterEntity.isPresent()) {
			ProjectMaster projectmaster = modelMapper.map(projectTypeEditRequest, ProjectMaster.class);
			projectMasterRepository.save(projectmaster);
			
		}else {
			throw new Exception("Project Details not found with the provided ID : "+projectTypeEditRequest.getProjectTypeId());
		}
		
		
	}
	
	public void deleteProjectTypesById(Long id, String modifiedBy) throws Exception {
		
		Optional<ProjectMaster> projectmaster = projectMasterRepository.findById(id);
		if (projectmaster.isPresent()) {
			ProjectMaster projectmasterEntitiy = projectmaster.get();
			short delete = 1;
			projectmasterEntitiy.setIsDelete(delete);
			projectmasterEntitiy.setModifiedBy(modifiedBy);
			projectMasterRepository.save(projectmasterEntitiy);

		}else {
		//	log.info("No ProjectDetails found with the provided ID{} in the DB",id);
			throw new Exception("No ProjectTypeDetails found with the provided ID in the DB :"+id);
		}
		
		
		
	}
	
	/**
	 * GET ALL POJECT FROM BYD STSREM BY CLIENT ID
	 * 
	 */
	@Override
	public List<ProjectDetailsByUserModel> getBYDProjectsByClient(Long clientId) {
		short notDeleted=0;
		
		log.info("in service impl...");
		//CREATE CORRECT REQUEST STRUCTURE AND CALL BYD API
		
		
		/*List<ProjectInfo> projectList = projectInfoRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<ProjectDetailsByUserModel> projectDetailsByUserList = new ArrayList<>();

		fillProjectDetails(projectList, projectDetailsByUserList);

		return projectDetailsByUserList;*/
		return null;

	}
	
	//FIND PROJECT DETAILS BY PROJECTID
	public ProjectInfo findByProjectId(Long projectId) {
		
		ProjectInfo projectInfo = projectInfoRepository.findById(projectId).get();
		return projectInfo;
	}

	public FetchDataResponse findBySapDate(Long id, Long clientId) {
		short notDeleted = 0;
		ProjectInfo projectInfo = projectInfoRepository.findByProjectInfoIdAndClientIdAndIsDelete(id, clientId,
				notDeleted);
		FetchDataResponse response = new FetchDataResponse();
		response.setExtFetchDate(projectInfo.getExtFetchDate());

		return response;
	}

	public void updateBySapDate(Long id, Long clientId) {
		Date currentDate = new Date();
		projectInfoRepository.updateExtFetchDateByProjectInfoIdAndClientId(id, clientId, currentDate);

	}	
	
	
	
	
	

}
	
