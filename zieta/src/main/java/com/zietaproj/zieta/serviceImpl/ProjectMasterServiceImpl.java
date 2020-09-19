package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.ProcessMaster;
import com.zietaproj.zieta.model.ProcessSteps;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.CustInfoRepository;
import com.zietaproj.zieta.repository.OrgInfoRepository;
import com.zietaproj.zieta.repository.ProcessMasterRepository;
import com.zietaproj.zieta.repository.ProcessStepsRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.ProjectMasterRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.request.EditProjStatusRequest;
import com.zietaproj.zieta.request.ProjectMasterEditRequest;
import com.zietaproj.zieta.request.ProjectTypeEditRequest;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;
import com.zietaproj.zieta.response.ProjectTypeByClientResponse;
import com.zietaproj.zieta.service.ProcessService;
import com.zietaproj.zieta.service.ProjectMasterService;
import com.zietaproj.zieta.util.TSMUtil;

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
			
			CustInfo custoInfo = custInfoRepository.findById(projectInfo.getCustId()).get();
			projectDetailsByUserModel.setCustInfo(custoInfo);
			
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
		//	projectmasterDTO.setClientDescription(clientInfoRepository.findById(projectmaster.getClientId()).get().getClientName());
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
	
	
	
	
	
	
	
	
	
	
	
}

	
