package com.zieta.tms.serviceImpl;

import java.io.File;
import java.io.IOException;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.devicefarm.model.Test;
import com.amazonaws.services.directconnect.model.Connection;
import com.zieta.tms.dto.ExternalProjectMasterDTO;
import com.zieta.tms.dto.ProjectInfoDTO;
import com.zieta.tms.dto.ProjectMasterDTO;
import com.zieta.tms.dto.UsersInfoDTO;
import com.zieta.tms.exception.ExternalIdException;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.CustInfo;
import com.zieta.tms.model.OrgInfo;
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
		ProjectInfo checkExist = null;
		short isDelete =0;
		try {
		//VALIDATE  EXISTANCE BY EXTERNAL_ID
		if(projectinfo.getExtId()!=null || !projectinfo.getExtId().isEmpty()) {
			checkExist = projectInfoRepository.findByExtIdAndIsDelete(projectinfo.getExtId(),isDelete);
		}
		if(checkExist!=null) {
			projectinfo.setProjectInfoId(checkExist.getProjectInfoId());
		}
		projectInfoRepository.save(projectinfo);
		}catch(Exception e) {
			log.error("Error occure  while adding project data "+e);
		}
	}
	
	
	//ADD PROJECT IN PROJECT_INFO FROM BYD SYSTEM	
	@Override
	public ResponseData addExternalProjectinfo(ExternalProjectMasterDTO bydProjectinfo)
	{		
		ProjectInfo returnData =null;
		ResponseData responseData = new ResponseData();
		String logMsg ="";
		//manipulate bydprojectinfo data and set it to project info an save it
		try {
		
			    log.error("Checking existing project");
			    ProjectInfo projectInfo = new ProjectInfo();
				log.error("Ext Id ::"+bydProjectinfo.getExtId().trim());
				
				
				Long custId =0L;
				Long projectManagerId =0L;
				Long directApprover =0L;
				Long projectStatusId = 0L;
				Long projectTypeId = 0L;
				Long orgNodeId = 0L;
				ProjectInfo chkExist =null;
				CustInfo custInfo =null;
				UserInfo projectManagerInfo = null;
				StatusMaster projectStatusInfo = null;
				ProjectMaster projectTypeInfo = null;
				OrgInfo orgNodeInfo = null;
				Short isDelete =0;
				if(bydProjectinfo.getExtId() ==null || bydProjectinfo.getExtId().isEmpty()){
					logMsg = logMsg+" Project extId is empty,";
				}else {
					log.error("Check exist for project by extId, clientId and isDelete");
			     //chkExist = projectInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtId().trim(),bydProjectinfo.getClientId());
			     chkExist = projectInfoRepository.findByExtIdAndClientIdAndIsdelete(bydProjectinfo.getExtId().trim(),bydProjectinfo.getClientId(),isDelete);
					
				}
				if(bydProjectinfo.getExtCustId() ==null || bydProjectinfo.getExtCustId().isEmpty()){
					logMsg = logMsg+" Cutomer  extId is empty";		
				}else {
					
					custInfo = custInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtCustId(), bydProjectinfo.getClientId());						
					
					if(custInfo!=null){
						custId = custInfo.getCustInfoId();
					}else{
						logMsg = logMsg+" Customer info doesn't exit,";
						responseData.setMessage(logMsg);
						//throw new ExternalIdException("customer not found");
					}
					
				}
				
				if(bydProjectinfo.getExtProjectManagerId()==null || bydProjectinfo.getExtProjectManagerId().isEmpty()){
					logMsg = logMsg+" Project Manager  extId is empty";
				}else {
					projectManagerInfo = userInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectManagerId().trim(), bydProjectinfo.getClientId());						
					 
					if(projectManagerInfo!=null){
						projectManagerId = projectManagerInfo.getId();
					}else{
						logMsg = logMsg+" ProjectManager info doesn't exist";
						responseData.setMessage(logMsg);							
					}
				}				
				
				/*if(!bydProjectinfo.getExtDirectApprover().isEmpty()) {						
					directApprover = userInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtDirectApprover().trim(), bydProjectinfo.getClientId()).getId();
					projectInfo.setDirectApprover(directApprover);
				}*/
				if(bydProjectinfo.getExtProjectStatus()==null || bydProjectinfo.getExtProjectStatus().isEmpty()){
					logMsg = logMsg+" Project Status  extId is empty,";
				}else {
					projectStatusInfo = statusMasterRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectStatus().trim(), bydProjectinfo.getClientId());
					if(projectStatusInfo!=null){
						projectStatusId = projectStatusInfo.getId();
					}else{
						logMsg = logMsg+" ProjectStatus info doesn't exist,";
						responseData.setMessage(logMsg);
						//throw new ExternalIdException("Project Status not found");
					}
				}
				
				if(bydProjectinfo.getExtProjectType()==null || bydProjectinfo.getExtProjectType().isEmpty()){
					logMsg = logMsg+" Project Type  extId is empty,";
				}else {
					 projectTypeInfo = projectMasterRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectType().trim(), bydProjectinfo.getClientId());
						if(projectTypeInfo!=null){
							projectTypeId = projectTypeInfo.getProjectTypeId();
						}else{
							logMsg = logMsg+" ProjectType info doesn't exist,";
							responseData.setMessage(logMsg);
							//throw new ExternalIdException("Project Type not found");
						
						}
				}
				
				if(bydProjectinfo.getExtProjectOrgNode()==null || bydProjectinfo.getExtProjectOrgNode().isEmpty()){
					logMsg = logMsg+" Project OrgNode  extId is empty,";
				}else {
					orgNodeInfo = orgInfoRepository.findByExtIdAndClientId(bydProjectinfo.getExtProjectOrgNode().trim(), bydProjectinfo.getClientId());
					if(orgNodeInfo!=null){
						orgNodeId = orgNodeInfo.getOrgUnitId();
					}else{
						logMsg = logMsg+" OrgNode info doesn't exist";
						responseData.setMessage(logMsg);
						//throw new ExternalIdException("OrgNode not found");
					}
				}
				
								
				if(chkExist!= null) {
					projectInfo.setProjectInfoId(chkExist.getProjectInfoId());
					//TO REMOVE EXIST PROCESS STEPS AN ADD AS PER CURRECT TEMPLATE
					editProjectByTemplate(chkExist.getProjectInfoId(),bydProjectinfo.getTemplateId());
					
				}	
										
				projectInfo.setExtId(bydProjectinfo.getExtId());
				projectInfo.setClientId(bydProjectinfo.getClientId());
				projectInfo.setProjectName(bydProjectinfo.getProjectName());
				
				projectInfo.setProjectType(projectTypeId);
				projectInfo.setProjectOrgNode(orgNodeId);
				projectInfo.setProjectManager(projectManagerId);
				projectInfo.setTemplateId(bydProjectinfo.getTemplateId());
				
				projectInfo.setAllowUnplanned(bydProjectinfo.getAllowUnplanned());
				projectInfo.setCustId(custId);
				projectInfo.setProjectStatus(projectStatusId);
				
				projectInfo.setCreatedBy(bydProjectinfo.getCreatedBy());
				projectInfo.setModifiedBy(bydProjectinfo.getCreatedBy());			
				responseData.setMessage(logMsg);//setting log message
				if(responseData.getMessage().isEmpty()) {
					returnData = projectInfoRepository.save(projectInfo);
					responseData.setId(returnData.getProjectInfoId());						
					logMsg = "Project Save/Update Successfully ";
					responseData.setIsSaved(true);
					log.error("External project Saved");
				}else {
					log.error("Failed to save external project info due to :"+responseData.getMessage());
				}
						
						
						
						
				
		}catch(Exception e) {
			//responseData.setMessage(e.getMessage());
			log.error(" Failed to add project from Byd"+e);
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
	
	
	//@Override
	public ProjectInfoDTO findByProjectsId(Long projectId) {
		short notDeleted=0;
		
		//ProjectInfo projectInfo= projectInfoRepository.findByProjectInfoIdAndIsDelete(projectId, notDeleted);
		ProjectInfoDTO projectInfoDTO = null;
		ProjectInfo projectInfo = projectInfoRepository.findById(projectId).get();
		
			
		if(projectInfo !=null) {
			projectInfoDTO =  modelMapper.map(projectInfo, ProjectInfoDTO.class);	
			
		}
		return projectInfoDTO;

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
		response.setExtId(projectInfo.getExtId());
		return response;
	}

	public ResponseData updateBySapDate(Long id, Long clientId) {
		ResponseData response = new ResponseData();
		Date currentDate = new Date();
		try {
			projectInfoRepository.updateExtFetchDateByProjectInfoIdAndClientId(id, clientId, currentDate);
			log.error("Updated ext_fetch Date");
		}catch(Exception e) {
			log.error("Unable to update ext_fetch Date");
		}
		response.setId(id);
		response.setIsSaved(true);
		return response;

	}

	//@Override
	/*public void readProjectFromExcel(MultipartFile projectExcelData) {
		log.error("593 called service method");  
		List<ProjectInfo> tempProjectList = new ArrayList<ProjectInfo>();
	    XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(projectExcelData.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			
		    for(int i=1;i<worksheet.getPhysicalNumberOfRows()-1 ;i++) {
		    	
		        ProjectInfo tempProjectInfo = new ProjectInfo();		       
		        XSSFRow row = worksheet.getRow(i);
		        log.error("value==>"+row.getCell(0));
		        if(row.getCell(0)==null) {
		        	break;
			     }else {
			        	log.error("=====inside===>");
				        log.error("601 inside loop"+row.getCell(0).getNumericCellValue());  
		
			            tempProjectInfo.setProjectInfoId((long) row.getCell(0).getNumericCellValue());
			            Long clientId =0L;
			           
			            String ExtId ="";
			            String desc = "";
			            String projectTypeExtId ="";
			            String projectName ="";
			            String orgNodeExtId ="";
			            String projectMngrExtId ="";
			            Long templateId= 0L;
			            String directApprovalExtId ="";
			            int allowedPlaned =0;
			            String customerExtId = "";
			            int projectStatu=0;
			            Date extFetchdate = new Date();
			            String createdBy = "";
			            Date createdDate = new Date();
			            String modifiedBy = "";
			            Date modifiedDate = new Date();
			            
			            clientId = (long) row.getCell(1).getNumericCellValue();
			            projectTypeExtId = row.getCell(2).getStringCellValue();
			            projectName = row.getCell(3).getStringCellValue();
			            projectTypeExtId = row.getCell(4).getStringCellValue();
			            orgNodeExtId = row.getCell(5).getStringCellValue();
			            
			            projectMngrExtId = row.getCell(6).getStringCellValue();
			            templateId = (long)row.getCell(7).getNumericCellValue();
			            directApprovalExtId = row.getCell(8).getStringCellValue();
			            
			            log.error("clientId: "+clientId+" projectTypeExtId: "+projectTypeExtId+" projectName: "+projectName);
			           /// tempUser.setAccessTypeId((long) row.getCell(1).getNumericCellValue());//DUE TO TABLE SPLIT
			           /* tempUser.setClientId((long) row.getCell(2).getNumericCellValue());
			            tempUser.setCreatedBy(row.getCell(1).getStringCellValue());
			            tempUser.setEmail(row.getCell(1).getStringCellValue());
			            tempUser.setEmpId(row.getCell(1).getStringCellValue());
			            ///tempUser.setExpTemplateId((long) row.getCell(2).getNumericCellValue());//DUE TO TABLE SPLIT
			            tempUser.setExtId(row.getCell(1).getStringCellValue());
			            tempUser.setIsActive((short) row.getCell(2).getNumericCellValue());
			            tempUser.setIsDelete((short) row.getCell(2).getNumericCellValue());
			            tempUser.setIsExpOpen((short) row.getCell(2).getNumericCellValue());
			            tempUser.setIsTsOpen((short) row.getCell(2).getNumericCellValue());
			            tempUser.setModifiedBy(row.getCell(1).getStringCellValue());
			            ///tempUser.setOrgNode((long) row.getCell(2).getNumericCellValue());
			            tempUser.setPassword(row.getCell(1).getStringCellValue());
			            tempUser.setPhoneNo(row.getCell(1).getStringCellValue());
			           //// tempUser.setReportingMgr((long) row.getCell(2).getNumericCellValue());//DUE TO TABLE SPLIT
			            tempUser.setUserFname(row.getCell(1).getStringCellValue());	            
			            tempUser.setUserLname(row.getCell(1).getStringCellValue());
			            tempUser.setUserMname(row.getCell(1).getStringCellValue());
		            
		            
		            	tempProjectList.add(tempProjectInfo);
			       }
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(tempProjectList.size()>0) {
	    	//CALL SAVE ALL METHOD
	    }
		
	}*/
	
	
	
	

}
	
