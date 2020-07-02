package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.TaskTypeMaster;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.CustInfoRepository;
import com.zietaproj.zieta.repository.OrgInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.repository.ProjectMasterRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.request.EditProjStatusRequest;
import com.zietaproj.zieta.request.EditTasksByClientProjectRequest;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;
import com.zietaproj.zieta.response.ProjectTypeByClientResponse;
import com.zietaproj.zieta.response.ProjectsByClientResponse;
import com.zietaproj.zieta.response.TaskTypesByClientResponse;
import com.zietaproj.zieta.service.ProjectMasterService;
import com.zietaproj.zieta.util.TSMUtil;

@Service
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
	ModelMapper modelMapper;
	
	
	@Override
	public List<ProjectDetailsByUserModel> getAllProjects() {

		List<ProjectInfo> projectList = projectInfoRepository.findAll();
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
		
		List<ProjectDetailsByUserModel> projectDetailsByUserList = new ArrayList<>();
		List<ProjectInfo> projectmanagerMappingList = projectInfoRepository.findByProjectManager(projectManagerId);
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

		List<ProjectInfo> projectList = projectInfoRepository.findByClientId(clientId);
		List<ProjectDetailsByUserModel> projectDetailsByUserList = new ArrayList<>();

		fillProjectDetails(projectList, projectDetailsByUserList);

		return projectDetailsByUserList;

	}

	private void fillProjectDetails(List<ProjectInfo> projectInfoList,
			List<ProjectDetailsByUserModel> projectDetailsByUserList) {
		for(ProjectInfo projectInfo: projectInfoList) {
			ProjectDetailsByUserModel projectDetailsByUserModel = modelMapper.map(projectInfo, ProjectDetailsByUserModel.class);
			
			//setting additonal details starts
			projectDetailsByUserModel.setClientCode(clientInfoRepository.findById(projectInfo.getClientId()).get().getClient_code());
			projectDetailsByUserModel.setProjectTypeName(
					projectMasterRepository.findById(projectInfo.getProjectType()).get().getTypeName());
			projectDetailsByUserModel.setOrgNodeName(orgInfoRepository.findById(projectInfo.getProjectOrgnode())
					.get().getOrg_node_name());
			String prjManagerName = getProjectManagerName(projectInfo);
			projectDetailsByUserModel.setProjectManagerName(prjManagerName);
			projectDetailsByUserModel.setProjectStatusDescription(statusMasterRepository.findById(projectInfo.getProjectStatus()).get().getStatus());
			CustInfo custoInfo = custInfoRepository.findById(projectInfo.getCustId()).get();
			projectDetailsByUserModel.setCustInfo(custoInfo);
			//setting additonal details ends
			projectDetailsByUserList.add(projectDetailsByUserModel);
		}
	}

	
	
	
	public List<ProjectTypeByClientResponse> getProjecttypessByClient(Long clientId) {
		
		List<ProjectMaster> projecttypesByClientList = projectMasterRepository.findByClientId(clientId);
		List<ProjectTypeByClientResponse> projecttypeslist = new ArrayList<>();
		ProjectTypeByClientResponse projecttypesByClientResponse = null;
		for (ProjectMaster projecttypesByClient : projecttypesByClientList) {
			projecttypesByClientResponse = modelMapper.map(projecttypesByClient, 
					ProjectTypeByClientResponse.class);
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
}

	
