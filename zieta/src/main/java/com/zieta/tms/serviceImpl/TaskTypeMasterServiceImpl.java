package com.zieta.tms.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.dto.ExtTaskMasterDTO;
import com.zieta.tms.dto.TaskMasterDTO;
import com.zieta.tms.exception.ExternalIdException;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.ProcessSteps;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.TaskTypeMaster;
import com.zieta.tms.model.TasksByUser;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ConnectionMasterInfoRepository;
import com.zieta.tms.repository.CustInfoRepository;
import com.zieta.tms.repository.ProcessConfigRepository;
import com.zieta.tms.repository.ProcessMasterRepository;
import com.zieta.tms.repository.ProcessStepsRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.TaskInfoRepository;
import com.zieta.tms.repository.TaskTypeMasterRepository;
import com.zieta.tms.repository.TasksByUserRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.EditTasksByClientProjectRequest;
import com.zieta.tms.request.TaskTypesByClientRequest;
import com.zieta.tms.request.UpdateTaskInfoRequest;
import com.zieta.tms.response.AddProjectResponse;
import com.zieta.tms.response.ResponseData;
import com.zieta.tms.response.TaskTypesByClientResponse;
import com.zieta.tms.response.TasksByClientProjectResponse;
import com.zieta.tms.response.TasksByUserModel;
import com.zieta.tms.service.ProcessService;
import com.zieta.tms.service.TaskTypeMasterService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TaskTypeMasterServiceImpl implements TaskTypeMasterService {

	
	@Autowired
	TaskTypeMasterRepository taskTypeMasterRepository;

	@Autowired
	TasksByUserRepository tasksByUserRepository;

	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	StatusMasterRepository statusRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ProcessConfigRepository processConfigRepository;
	
	@Autowired
	ProcessStepsRepository processStepsRepository;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	ConnectionMasterInfoRepository connectionMasterInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	TaskTypeMasterRepository taskTypeRepo;

	@Override
	public List<TaskMasterDTO> getAllTasks() {
		short notDeleted = 0;
		List<TaskTypeMaster> taskTypeMasters = taskTypeMasterRepository.findByIsDelete(notDeleted);
		List<TaskMasterDTO> taskMasterDTOs = new ArrayList<TaskMasterDTO>();
		TaskMasterDTO taskMasterDTO = null;
		for (TaskTypeMaster taskTypeMaster : taskTypeMasters) {
			taskMasterDTO = modelMapper.map(taskTypeMaster,TaskMasterDTO.class);
			taskMasterDTO.setClientCode(clientInfoRepository.findById(taskTypeMaster.getClientId()).get().getClientCode());
			taskMasterDTO.setClientDescription(clientInfoRepository.findById(taskTypeMaster.getClientId()).get().getClientName());
			taskMasterDTO.setClientStatus(clientInfoRepository.findById(taskTypeMaster.getClientId()).get().getClientStatus());

			taskMasterDTOs.add(taskMasterDTO);
		}
		return taskMasterDTOs;
	}

	@Override
	public void addTaskmaster(TaskTypeMaster taskmaster) {
		taskTypeMasterRepository.save(taskmaster);
	}

	@Override
	public List<TasksByUserModel> findByClientIdAndUserId(Long clientId, Long userId) {
            short notDeleted=0;
		List<TasksByUserModel> tasksByUserModelList = new ArrayList<>();

		List<TasksByUser> tasksByUserList = tasksByUserRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId, notDeleted);
		TasksByUserModel tasksByUserModel = null;
		for (TasksByUser tasksByUser : tasksByUserList) {
			tasksByUserModel = new TasksByUserModel();
			long projectId = tasksByUser.getProject_id();
			long taskId = tasksByUser.getTask_id();
			long userIdent = tasksByUser.getUserId();
			TaskInfo taskInfo = taskInfoRepository.findById(taskId).get();
			String taskName = taskInfo.getTaskDescription();
			ProjectInfo projectInfo = projectInfoRepository.findById(projectId).get();
			String projectName = projectInfo.getProjectName();
			tasksByUserModel.setProjectId(projectId);
			tasksByUserModel.setProjectName(projectName);
			tasksByUserModel.setTaskName(taskName);
			tasksByUserModel.setTaskId(taskId);
			tasksByUserModel.setUserId(userIdent);
		//	tasksByUserModel.setProjectCode(tasksByUser.getProject_code());
		//	tasksByUserModel.setTaskCode(tasksByUser.getTask_code());
			tasksByUserModel.setClientCode(clientInfoRepository.findById(tasksByUser.getClientId()).get().getClientCode());
			tasksByUserModel.setClientDescription(clientInfoRepository.findById(tasksByUser.getClientId()).get().getClientName());
			
			tasksByUserModelList.add(tasksByUserModel);
		}

		return tasksByUserModelList;

	}

	@Override
	public List<TasksByClientProjectResponse> findByClientIdAndProjectId(Long clientId, Long projectId) {
		short isDeleteFlag = 0;
		List<TaskInfo> taskInfoList = taskInfoRepository.findByClientIdAndProjectIdAndIsDelete(clientId, projectId,isDeleteFlag);
		
		List<TasksByClientProjectResponse> tasksByClientProjectResponseList = new ArrayList<>();
		
		constructTaskInfoData(taskInfoList, tasksByClientProjectResponseList);
		return tasksByClientProjectResponseList;
	}

	private void constructTaskInfoData(List<TaskInfo> taskInfoList,
			List<TasksByClientProjectResponse> tasksByClientProjectResponseList) {
		for(TaskInfo taskInfo: taskInfoList) {
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			TasksByClientProjectResponse tasksByClientProjectResponse =  modelMapper.map(taskInfo, TasksByClientProjectResponse.class);
			Optional<ProjectInfo> projectInfo = projectInfoRepository.findById(taskInfo.getProjectId());
			if(projectInfo.isPresent()) {
		//		tasksByClientProjectResponse.setProjectCode(projectInfo.get().getProjectCode());
				tasksByClientProjectResponse.setProjectDescription(projectInfo.get().getProjectName());	
			}else {
		//		tasksByClientProjectResponse.setProjectCode(StringUtils.EMPTY);
				tasksByClientProjectResponse.setProjectDescription(StringUtils.EMPTY);
			}
			
			tasksByClientProjectResponse.setTaskTypeDescription(StringUtils.EMPTY);
			if(null != taskInfo.getTaskType()) {
				Optional<TaskTypeMaster> taskTypeMaster = taskTypeMasterRepository.findById(taskInfo.getTaskType());
				if(taskTypeMaster.isPresent()) {
					tasksByClientProjectResponse.setTaskTypeDescription(taskTypeMaster.get().getTaskTypeDescription());
				}
			}
			
			tasksByClientProjectResponse.setTaskManagerName(StringUtils.EMPTY);
			if(null != taskInfo.getTaskManager()) {
				Optional <UserInfo> userInfo = userInfoRepository.findById(taskInfo.getTaskManager());
				if(userInfo.isPresent()) {
					String userName = TSMUtil.getFullName(userInfo.get());
					tasksByClientProjectResponse.setTaskManagerName(userName);
				}
			}
			
			tasksByClientProjectResponse.setTaskStatusDescription(StringUtils.EMPTY);
			if(null != taskInfo.getTaskStatus()) {
				Optional <StatusMaster> statusMaster = statusRepository.findById(taskInfo.getTaskStatus());
				if(statusMaster.isPresent()) {
					tasksByClientProjectResponse.setTaskStatusDescription(statusMaster.get().getStatusCode());
				}
			}
			
			tasksByClientProjectResponseList.add(tasksByClientProjectResponse);
			
		}
	}

	
	public List<TaskTypesByClientResponse> getTasksByClient(Long clientId) {
		short notDeleted = 0;
		List<TaskTypeMaster> tasksByClientList = taskTypeMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<TaskTypesByClientResponse> tasksByClientResponseList = new ArrayList<>();
		TaskTypesByClientResponse tasksByClientResponse = null;
		for (TaskTypeMaster tasksByClient : tasksByClientList) {
			tasksByClientResponse = modelMapper.map(tasksByClient, 
					TaskTypesByClientResponse.class);
			tasksByClientResponse.setClientCode(clientInfoRepository.findById(tasksByClient.getClientId()).get().getClientCode());
			
			tasksByClientResponse.setClientDescription(clientInfoRepository.findById(tasksByClient.getClientId()).get().getClientName());
			
			tasksByClientResponseList.add(tasksByClientResponse);
	}
		return tasksByClientResponseList;
}

	@Override
	@Transactional
	public void saveTaskInfo(List<TaskInfo> taskInfo) {
		try {
			for (TaskInfo tskInfo : taskInfo) {
				addTaskInfo(tskInfo);
		}
//			
//			TaskInfo taskInfoDB = taskInfoRepository.save(tskInfo);
//			
//			ProjectInfo projectInfo = projectInfoRepository.findById(taskInfo.getProjectId()).get();
//			
//			List<ProcessSteps> processStepsList = processService.createProcessSteps(taskInfoDB, projectInfo);
//			
//			processStepsRepository.saveAll(processStepsList);
//			
	//		return true;
		} 
		catch (Exception ex) {
			log.error("Exception occured during the save task information",ex);
		//	return false;
		}
	}

	@Override
	@Transactional
	public ResponseData saveExternalTaskInfo(ExtTaskMasterDTO extTaskMaster) {
		ResponseData responseData = new ResponseData();
		try {
			TaskInfo taskInfo = null;
			//|| extTaskMaster.getExtTaskType() == null || extTaskMaster.getExtTaskType().isEmpty()
			if (extTaskMaster.getExtProjectId() == null || extTaskMaster.getExtProjectId().isEmpty()					
					|| extTaskMaster.getExtTaskParent() == null || extTaskMaster.getExtTaskParent().isEmpty()
					|| extTaskMaster.getExtTaskStatus() == null || extTaskMaster.getExtTaskStatus().isEmpty()
					) {

				throw new ExternalIdException("ExternalId not found");

			} else {
				
				TaskInfo chkExist = taskInfoRepository.findByExtIdAndClientId(extTaskMaster.getExtTaskInfoId(), extTaskMaster.getClientId());
				UserInfo taskManager=null;
				TaskInfo tskInfo = new TaskInfo();
				if(extTaskMaster.getExtTaskManager()!=""|| extTaskMaster.getExtTaskManager()!=null) {
				  taskManager = userInfoRepository.findByExtIdAndClientId(extTaskMaster.getExtTaskManager(), extTaskMaster.getClientId());
				  if(taskManager!=null)
				   tskInfo.setTaskManager(taskManager.getId());
				}
				
				TaskTypeMaster taskType = null;
				if(extTaskMaster.getExtTaskType()==null || extTaskMaster.getExtTaskType()=="") {
				 taskType = taskTypeRepo.findByExtIdAndClientId(extTaskMaster.getExtTaskType(), extTaskMaster.getClientId());
				}
				TaskInfo taskParent = null;
				 taskParent = taskInfoRepository.findByExtIdAndClientId(extTaskMaster.getExtTaskParent(), extTaskMaster.getClientId());
				//System.out.println("298 taskParent ===>"+taskParent);
				 StatusMaster statusMaster = statusRepository.findByExtIdAndClientId(extTaskMaster.getExtTaskStatus(), extTaskMaster.getClientId());
				ProjectInfo projectInfo =  projectInfoRepository.findByExtIdAndClientId(extTaskMaster.getExtProjectId(), extTaskMaster.getClientId());
				// ExtTaskInfo info = new ExtTaskInfo();
				
				if (chkExist != null) {
					tskInfo.setTaskInfoId(chkExist.getTaskInfoId());
				}
				
				tskInfo.setClientId(extTaskMaster.getClientId());
				tskInfo.setProjectId(projectInfo.getProjectInfoId());
				//tskInfo.set
				if(taskType!=null) {
					tskInfo.setTaskType(taskType.getTaskTypeId());
				}
				
				tskInfo.setTaskStatus(statusMaster.getId());
				if(taskParent!=null) {					
					tskInfo.setTaskParent(taskParent.getTaskInfoId());
				}
				tskInfo.setExtId(extTaskMaster.getExtTaskInfoId());
				tskInfo.setTaskDescription(extTaskMaster.getTaskDescription());
				tskInfo.setTaskStartDate(extTaskMaster.getTaskStartDate());
				tskInfo.setTaskendDate(extTaskMaster.getTaskendDate());
				tskInfo.setCreatedBy(extTaskMaster.getCreatedBy());
				tskInfo.setModifiedBy(extTaskMaster.getModifiedBy());
				
				taskInfo = taskInfoRepository.save(tskInfo);
				responseData.setId(taskInfo.getTaskInfoId());
				responseData.setIsSaved(true);
				// addExtTaskInfo(info);
				log.error("Ext task info data saved");
			}
		} catch (Exception ex) {
			log.error("Exception occured during the save external task information", ex);
		}
		return responseData;
	}


	
	public boolean addTaskInfo(TaskInfo tskInfo)
	{
		try {
			
			TaskInfo taskInfoDB = taskInfoRepository.save(tskInfo);
			
			ProjectInfo projectInfo = projectInfoRepository.findById(tskInfo.getProjectId()).get();
			
			List<ProcessSteps> processStepsList = processService.createProcessSteps(taskInfoDB, projectInfo);
			
			processStepsRepository.saveAll(processStepsList);
			
			return true;
		} catch (Exception ex) {
			log.error("Exception occured during the save task information",ex);
			return false;
		}

	}
	
	
	
	@Override
	@Transactional
	public void editTaskInfo(@Valid List<EditTasksByClientProjectRequest> editasksByClientProjectRequest) throws Exception {
		for (EditTasksByClientProjectRequest updateRequest : editasksByClientProjectRequest) {
			editTaskInfoById(updateRequest);
		}
	}
	
	public void editTaskInfoById(@Valid EditTasksByClientProjectRequest editasksByClientProjectRequest) throws Exception {
		Optional<TaskInfo> taskInfoEntity = taskInfoRepository.findById(editasksByClientProjectRequest.getTaskInfoId());
		if(taskInfoEntity.isPresent()) {
			TaskInfo taskInfo = modelMapper.map(editasksByClientProjectRequest, TaskInfo.class);
			taskInfoRepository.save(taskInfo);
		}else {
			throw new Exception("Task not found with the provided ID : "+editasksByClientProjectRequest.getTaskInfoId());
		}
	}
	
	@Override
	public void editTaskTypesByClient(TaskTypesByClientRequest tasktypesbyclientRequest) throws Exception
	{
		Optional<TaskTypeMaster> taskMasterEntity = taskTypeMasterRepository.findById(tasktypesbyclientRequest.getTaskTypeId());
		if(taskMasterEntity.isPresent()) {
		TaskTypeMaster taskmaster = modelMapper.map(tasktypesbyclientRequest, TaskTypeMaster.class);
		taskTypeMasterRepository.save(taskmaster);
	}else {
		throw new Exception("Task not found with the provided ID : "+tasktypesbyclientRequest.getTaskTypeId());
	}
	}
	
	@Override 
	  public void addTaskTypesByClient(TaskTypeMaster taskmaster) {
		
		taskTypeMasterRepository.save(taskmaster); 
	  
	  }
	
	
	
	@Override
	public void deleteTaskTypeByClient(Long taskTypeId, String modifiedBy) throws Exception {
		Optional<TaskTypeMaster> tasktypemaster = taskTypeMasterRepository.findById(taskTypeId);
		if (tasktypemaster.isPresent()) {
			TaskTypeMaster tasktypeEntitiy = tasktypemaster.get();
			short delete = 1;
			tasktypeEntitiy.setIsDelete(delete);
			tasktypeEntitiy.setModifiedBy(modifiedBy);
			taskTypeMasterRepository.save(tasktypeEntitiy);

		}else {
			log.info("No task type found with the provided ID{} in the DB",taskTypeId);
			throw new Exception("No task type found with the provided ID in the DB :"+taskTypeId);
		}
	}
	
	
	@Override
	public void deleteTaskInfoByClient(Long taskInfoId, String modifiedBy) throws Exception {
		Optional<TaskInfo> taskInfo = taskInfoRepository.findById(taskInfoId);
		if (taskInfo.isPresent()) {
			TaskInfo tasktypeEntitiy = taskInfo.get();
			short delete = 1;
			tasktypeEntitiy.setIsDelete(delete);
			tasktypeEntitiy.setModifiedBy(modifiedBy);
			taskInfoRepository.save(tasktypeEntitiy);

		}else {
			log.info("No task Info found with the provided ID{} in the DB",taskInfoId);
			throw new Exception("No task Info found with the provided ID in the DB :"+taskInfoId);
		}
	}
			

	@Override
	public List<TasksByClientProjectResponse> findByClientIdAndProjectIdAsHierarchy(Long clientId, Long projectId) {

		short isDeleteFlag = 0;
		List<TaskInfo> taskInfoList = taskInfoRepository.findByClientIdAndProjectIdAndIsDelete(clientId, projectId,isDeleteFlag);

		List<TasksByClientProjectResponse> tasksByClientProjectResponseList = new ArrayList<>();

		constructTaskInfoData(taskInfoList, tasksByClientProjectResponseList);

		List<TasksByClientProjectResponse> treeList = TSMUtil.createTree(tasksByClientProjectResponseList);
		return treeList;
	}

	@Override
	public void updateTaskSortKeyByID(Long taskInfoId, Long sortKey){
		Optional<TaskInfo> taskInfo = taskInfoRepository.findById(taskInfoId);
		if (taskInfo.isPresent()) {

			TaskInfo taskEntity = taskInfo.get();
			taskEntity.setSortKey(sortKey);
			taskInfoRepository.save(taskEntity);
			log.info("TaskInfoId entry {} updated with the provided SortKey {}", taskInfoId, sortKey);

		} else {
			log.error("No Record found in the TaskInfo table with the ID:  " + taskInfoId);
		}

	}
	
	/**
	 * This method updates the multiple sortKeys in one call.
	 * @param taskInfoId
	 * @param sortKey
	 * @throws Exception
	 */
	@Override
	public void updateTaskSortKeyByIDs(List<UpdateTaskInfoRequest> taskIdWithSortKeys){

		for (UpdateTaskInfoRequest updateTaskInfoRequest : taskIdWithSortKeys) {
			updateTaskSortKeyByID(updateTaskInfoRequest.getTaskInfoId(), updateTaskInfoRequest.getSortKey());
		}

	}
	
	//GET TASKINFO DATA FROM BYD SYSTEM 
	public ResponseData getExternalTaskInfoData(String extFetchDate, String extProjectId, Long clientId) {		
		
		ResponseData responseData = new ResponseData();		
		String bydUrl ="";
		short notDeleted =0;
		String connName = "TaskAPI";
		String loginId =null;
		String pass =null;
		String connStr = null;
		List<ConnectionMasterInfo> listConnectionData = connectionMasterInfoRepository.findByClientIdAndConnectionNameAndNotDeleted(clientId,connName,notDeleted);
		if(listConnectionData.size()>0) {
			loginId = listConnectionData.get(0).getLoginId();
			pass = listConnectionData.get(0).getPassword();
			connStr = listConnectionData.get(0).getConnectionStr();
		}
		
		//bydUrl = "https://my351070.sapbydesign.com/sap/byd/odata/ana_businessanalytics_analytics.svc/RPZBE98B6E3996F2677BCC388QueryResults?$select=TCHANGE_USER,CE_PLAN_END_DAT,CE_PLAN_ST_DAT,Cs2ANsF48275C9927F86E,CRESP_EMP_UUID,CPROJECT_ID,TTASK_UUID,CTASK_ID,CSTATUS_LFC,TCREATION_USER&$format=json$filter=(CLAST_CHANGE_DATE_TIME ge '2021-10-01T00:00:00') and (CPROJECT_ID eq 'CPSO52')";
		bydUrl = connStr+"&$filter=%28CLAST_CHANGE_DATE_TIME%20ge%20%27"+extFetchDate+"T00%3A00%3A00%27%29%20and%20%28CPROJECT_ID%20eq%20%27"+extProjectId+"%27%29";
		
		HttpGet httpGet = new HttpGet(bydUrl);
		
		httpGet.setHeader("content-type", "text/XML");			
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(loginId, pass);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		
		HttpResponse resp;
		try {
			ProjectInfo projectInfo = null;
			projectInfo = projectInfoRepository.findByExtIdAndClientId(extProjectId, clientId);
			if(projectInfo!=null) {
				responseData.setId(projectInfo.getProjectInfoId());
			}
			
			resp = client.execute(httpGet);			
			String respString = EntityUtils.toString(resp.getEntity());		

            // CONVERT RESPONSE STRING TO JSON ARRAY
            JSONObject jsonRespData = new JSONObject(respString);            
            JSONObject jsnObj = (JSONObject) jsonRespData.get("d");            
            JSONArray jsnArray = jsnObj.getJSONArray("results");            
            
            for (int i = 0; i <jsnArray.length(); i++) {
            	
            	ExtTaskMasterDTO extTaskMastreDTO = new ExtTaskMasterDTO();
            	//TAKE STRING DATE  AND CONVERT IT TO DATE FORMATE
            	String dt1 = jsnArray.getJSONObject(i).getString("CE_PLAN_ST_DAT");
                String dt2 = jsnArray.getJSONObject(i).getString("CE_PLAN_END_DAT"); 
                String sDate =  dt1.substring(dt1.indexOf("(") + 1, dt1.indexOf(")"));
                String eDate =  dt2.substring(dt2.indexOf("(") + 1, dt2.indexOf(")"));
               
            	long lngSdate = Long.parseLong(sDate);
            	long lngEdate =  Long.parseLong(eDate);            	
            	Date startDate = new Date(lngSdate);
            	Date endDate = new Date(lngEdate);            	
            	
            	String projectExtId = jsnArray.getJSONObject(i).getString("CPROJECT_ID");
                String createdBy = jsnArray.getJSONObject(i).getString("TCREATION_USER");
                String modifiedBy = jsnArray.getJSONObject(i).getString("TCHANGE_USER");
                String taskDesc = jsnArray.getJSONObject(i).getString("TTASK_UUID");
                String extTaskInfoId = jsnArray.getJSONObject(i).getString("CTASK_ID");
                String extTaskManager = jsnArray.getJSONObject(i).getString("CRESP_EMP_UUID");
                String extTaskParent = jsnArray.getJSONObject(i).getString("Cs2ANsF48275C9927F86E");
                String extTaskStatus = jsnArray.getJSONObject(i).getString("CSTATUS_LFC");
                
                String extTaskType = "";                
                extTaskMastreDTO.setClientId(clientId);
                extTaskMastreDTO.setExtProjectId(extProjectId);
                extTaskMastreDTO.setCreatedBy(createdBy);
                extTaskMastreDTO.setModifiedBy(modifiedBy);
                extTaskMastreDTO.setTaskDescription(taskDesc);
                extTaskMastreDTO.setExtTaskInfoId(extTaskInfoId);
                extTaskMastreDTO.setExtTaskManager(extTaskManager);
                extTaskMastreDTO.setExtTaskParent(extTaskParent);
                extTaskMastreDTO.setExtTaskStatus(extTaskStatus);
               // extTaskMastreDTO.setCreatedDate(taskStartDate);
                extTaskMastreDTO.setExtTaskType(extTaskType);
                //extTaskMastreDTO.setModifiedDate(taskEndDate);
                extTaskMastreDTO.setTaskStartDate(startDate);
                extTaskMastreDTO.setTaskendDate(endDate);        
                              
                //TO UPDATE OF CREATE TASK INFO DATA
               saveExternalTaskInfo(extTaskMastreDTO);  				
            	
            }   
            
            
            //LOOP TO UPDATE TASKINFO DATA WITH PERENT ID
        for (int i = 0; i <jsnArray.length(); i++) {
            	
            	ExtTaskMasterDTO extTaskMastreDTO = new ExtTaskMasterDTO();
            	//TAKE STRING DATE  AND CONVERT IT TO DATE FORMATE
            	String dt1 = jsnArray.getJSONObject(i).getString("CE_PLAN_ST_DAT");
                String dt2 = jsnArray.getJSONObject(i).getString("CE_PLAN_END_DAT"); 
               String sDate =  dt1.substring(dt1.indexOf("(") + 1, dt1.indexOf(")"));
               String eDate =  dt2.substring(dt2.indexOf("(") + 1, dt2.indexOf(")"));
               
            	/*eDt = dt2.substring(dt2.indexOf(")"));*/
            	long lngSdate = Long.parseLong(sDate);
            	long lngEdate =  Long.parseLong(eDate);            	
            	Date startDate = new Date(lngSdate);
            	Date endDate = new Date(lngEdate);            	
            	
            	String projectExtId = jsnArray.getJSONObject(i).getString("CPROJECT_ID");
                String createdBy = jsnArray.getJSONObject(i).getString("TCREATION_USER");
                String modifiedBy = jsnArray.getJSONObject(i).getString("TCHANGE_USER");
                String taskDesc = jsnArray.getJSONObject(i).getString("TTASK_UUID");
                String extTaskInfoId = jsnArray.getJSONObject(i).getString("CTASK_ID");
                String extTaskManager = jsnArray.getJSONObject(i).getString("CRESP_EMP_UUID");
                String extTaskParent = jsnArray.getJSONObject(i).getString("Cs2ANsF48275C9927F86E");
                String extTaskStatus = jsnArray.getJSONObject(i).getString("CSTATUS_LFC");
                
                String extTaskType = "";                
                extTaskMastreDTO.setClientId(clientId);
                extTaskMastreDTO.setExtProjectId(extProjectId);
                extTaskMastreDTO.setCreatedBy(createdBy);
                extTaskMastreDTO.setModifiedBy(modifiedBy);
                extTaskMastreDTO.setTaskDescription(taskDesc);
                extTaskMastreDTO.setExtTaskInfoId(extTaskInfoId);
                extTaskMastreDTO.setExtTaskManager(extTaskManager);
                extTaskMastreDTO.setExtTaskParent(extTaskParent);
                extTaskMastreDTO.setExtTaskStatus(extTaskStatus);
               // extTaskMastreDTO.setCreatedDate(taskStartDate);
                extTaskMastreDTO.setExtTaskType(extTaskType);
                //extTaskMastreDTO.setModifiedDate(taskEndDate);
                extTaskMastreDTO.setTaskStartDate(startDate);
                extTaskMastreDTO.setTaskendDate(endDate);                
                               
                //TO UPDATE OF CREATE TASK INFO DATA
                saveExternalTaskInfo(extTaskMastreDTO);
                log.error("Updated/Saved taskinfoData");
            }  

			responseData.setIsSaved(true);			
            //JSONArray jsnObjArr = jsnObj.getJSONArray("results");
			
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
				
		return responseData;
		
		
	}

	
}
