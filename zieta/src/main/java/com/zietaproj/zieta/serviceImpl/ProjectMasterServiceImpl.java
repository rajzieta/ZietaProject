package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zietaproj.zieta.model.ActivityUserMapping;
import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.repository.ActivitiyUserMappingRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.CustInfoRepository;
import com.zietaproj.zieta.repository.OrgInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.ProjectMasterRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.response.ProjectDetailsByUserModel;
import com.zietaproj.zieta.response.ProjectsByClientResponse;
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
	ActivitiyUserMappingRepository activitiyUserMappingRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<ProjectsByClientResponse> getAllProjects() {

		List<ProjectInfo> projectList = projectInfoRepository.findAll();
		List<ProjectsByClientResponse> projectsByClientResponseList = new ArrayList<>();

		fillProjectDetails(projectList, projectsByClientResponseList);

		return projectsByClientResponseList;

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
		
		List<ActivityUserMapping> activityUserMappingList = activitiyUserMappingRepository.findByUserId(userId);
		List<Long> projectIdList = activityUserMappingList.stream().map(ActivityUserMapping::getProjectId).collect(Collectors.toList());
		
		List<ProjectInfo> projectInfoList = projectInfoRepository.findAllById(projectIdList);
		for(ProjectInfo projectInfo: projectInfoList) {
			projectDetailsByUserModel = new ProjectDetailsByUserModel();
			projectDetailsByUserModel.setClientId(projectInfo.getClientId());
			projectDetailsByUserModel.setClientCode(clientInfoRepository.findById(projectInfo.getClientId()).get().getClient_code());
			
			projectDetailsByUserModel.setProject_code(projectInfo.getProject_code());
			projectDetailsByUserModel.setProjectTypeName(
					projectMasterRepository.findById(projectInfo.getProject_type()).get().getType_name());
			projectDetailsByUserModel.setProjectId(projectInfo.getId());
			projectDetailsByUserModel.setProject_name(projectInfo.getProject_name());
			projectDetailsByUserModel.setProjectType(projectInfo.getProject_type());
			projectDetailsByUserModel.setProjectStatus(projectInfo.getProject_status());
			projectDetailsByUserModel.setOrgNode(orgInfoRepository.findById(projectInfo.getProject_orgnode())
					.get().getOrg_node_name());
			String prjManagerName = getProjectManagerName(projectInfo);
			projectDetailsByUserModel.setProjectManager(prjManagerName);
			projectDetailsByUserModel.setAllowUnplannedActivity(projectInfo.getAllow_unplanned());
			
			CustInfo custoInfo = custInfoRepository.findById(projectInfo.getCust_id()).get();
			projectDetailsByUserModel.setCustInfo(custoInfo);
			
			projectDetailsByUserList.add(projectDetailsByUserModel);
		}
		
		
		return projectDetailsByUserList;
		
		
	}

	private String getProjectManagerName(ProjectInfo projectInfo) {
		long projectManagerId = projectInfo.getProject_manager();
		UserInfo userInfo = userInfoRepository.findById(projectManagerId).get();
		return TSMUtil.getFullName(userInfo);
	}
	
	@Override
	public List<ProjectsByClientResponse> getProjectsByClient(Long clientId) {

		List<ProjectInfo> projectList = projectInfoRepository.findByClientId(clientId);
		List<ProjectsByClientResponse> projectsByClientResponseList = new ArrayList<>();

		fillProjectDetails(projectList, projectsByClientResponseList);

		return projectsByClientResponseList;

	}

	private void fillProjectDetails(List<ProjectInfo> projectList,
			List<ProjectsByClientResponse> projectsByClientResponseList) {
		for (ProjectInfo projectInfo : projectList) {
			ProjectsByClientResponse projectsByClientResponse = modelMapper.map(projectInfo,
					ProjectsByClientResponse.class);
			projectsByClientResponse.setClientCode(clientInfoRepository.findById(projectInfo.getClientId()).get().getClient_code());
			projectsByClientResponse.setProjectManager(getProjectManagerName(projectInfo));
			projectsByClientResponse
					.setProjectTypeName(projectMasterRepository.findById(projectInfo.getProject_type()).get().getType_name());
			projectsByClientResponse.setProjectStatus(projectInfo.getProject_status());
			projectsByClientResponse.setProjectType(projectInfo.getProject_type());
			projectsByClientResponse.setOrgNode(orgInfoRepository.findById(projectInfo.getProject_orgnode())
					.get().getOrg_node_name());
			projectsByClientResponse.setAllowUnplannedActivity(projectInfo.getAllow_unplanned());
			CustInfo custoInfo = custInfoRepository.findById(projectInfo.getCust_id()).get();
			projectsByClientResponse.setCustInfo(custoInfo);
			projectsByClientResponseList.add(projectsByClientResponse);
		}
	}

}
