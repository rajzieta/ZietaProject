package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.ProjectUserMapping;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.CustInfoRepository;
import com.zietaproj.zieta.repository.OrgInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.ProjectMasterRepository;
import com.zietaproj.zieta.repository.ProjectUserMappingRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;
import com.zietaproj.zieta.response.ProjectsByClientResponse;
import com.zietaproj.zieta.response.RolesByClientResponse;
import com.zietaproj.zieta.service.ProjectMasterService;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService{

	
	@Autowired
	ProjectMasterRepository projectMasterRepository;
	
	@Autowired
	ProjectUserMappingRepository projectUserMappingRepository;
	
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
	ModelMapper modelMapper;
	
	@Override
	public List<ProjectMasterDTO> getAllProjects() {
		List<ProjectMaster> projectMasters= projectMasterRepository.findAll();
		List<ProjectMasterDTO> projectMasterDTOs = new ArrayList<ProjectMasterDTO>();
		ProjectMasterDTO projectMasterDTO = null;
		for (ProjectMaster projectMaster : projectMasters) {
			projectMasterDTO = new ProjectMasterDTO();
			projectMasterDTO.setId(projectMaster.getId());
			projectMasterDTO.setProject_type(projectMaster.getType_name());
			projectMasterDTO.setClient_id(projectMaster.getClientId());
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

	@Override
	public List<ProjectDetailsByUserModel> getProjectsByUser(long userId) {
		
		List<ProjectDetailsByUserModel> projectDetailsByUserList = new ArrayList<>();
		ProjectDetailsByUserModel projectDetailsByUserModel = null;
		List<ProjectUserMapping> projectList = projectUserMappingRepository.findByUserId(userId);
		List<Long> projectIdList = projectList.stream().map(ProjectUserMapping::getProjectId).collect(Collectors.toList());
		
		List<ProjectInfo> projectInfoList = projectInfoRepository.findAllById(projectIdList);
		for(ProjectInfo projectInfo: projectInfoList) {
			projectDetailsByUserModel = new ProjectDetailsByUserModel();
			projectDetailsByUserModel.setClientId(projectInfo.getClient_id());
			projectDetailsByUserModel.setProjectCode(projectInfo.getProject_code());
			projectDetailsByUserModel.setProjectTypeName(
					projectMasterRepository.findById(projectInfo.getProject_type()).get().getType_name());
			projectDetailsByUserModel.setProjectId(projectInfo.getId());
			projectDetailsByUserModel.setProjectName(projectInfo.getProject_name());
			projectDetailsByUserModel.setProjectType(projectInfo.getProject_type());
			projectDetailsByUserModel.setOrgNode(orgInfoRepository.findById(projectInfo.getProject_orgnode())
					.get().getOrg_node_name());
			long projectManagerId = projectInfo.getProject_manager();
			projectDetailsByUserModel.setProjectManager(userInfoRepository.findById(projectManagerId).get().getUser_fname());
			projectDetailsByUserModel.setAllowUnplannedActivity(projectInfo.getAllow_unplanned());
			
			CustInfo custoInfo = custInfoRepository.findById(projectInfo.getCust_id()).get();
			projectDetailsByUserModel.setCustInfo(custoInfo);
			
			projectDetailsByUserList.add(projectDetailsByUserModel);
		}
		
		
		return projectDetailsByUserList;
		
		
	}
	
	@Override
	public List<ProjectsByClientResponse> getProjectsByClient(Long client_id) {

		List<ProjectMaster> projectsByClientList = projectMasterRepository.findByClientId(client_id);
		List<ProjectsByClientResponse> projectsByClientResponseList = new ArrayList<>();
		ProjectsByClientResponse projectsByClientResponse = null;
		for (ProjectMaster projectsByClient : projectsByClientList) {
			projectsByClientResponse = modelMapper.map(projectsByClient, 
					ProjectsByClientResponse.class);
			projectsByClientResponseList.add(projectsByClientResponse);
		}

		return projectsByClientResponseList;
		
	
	}

}
