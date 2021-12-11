package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.ActivityMasterDTO;
import com.zieta.tms.dto.TaskActivityExtDTO;
import com.zieta.tms.exception.ExternalIdException;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.ProjectMaster;
import com.zieta.tms.model.TaskActivity;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.TaskTypeMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.ActivitiesTaskRepository;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ConnectionMasterInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.ProjectMasterRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.TaskInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.AcitivityRequest;
import com.zieta.tms.request.ActivityTaskUserMappingRequest;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientResponse;
import com.zieta.tms.response.ActivitiesByClientUserModel;
import com.zieta.tms.response.AddProjectResponse;
import com.zieta.tms.response.ResponseData;
import com.zieta.tms.service.ActivityService;
import com.zieta.tms.util.TSMUtil;
import lombok.extern.slf4j.Slf4j;



@Service
@Transactional
@Slf4j
public class ActivityServiceImpl implements ActivityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	ClientInfoRepository  clientInfoRepository;
	
	@Autowired
	ActivitiesTaskRepository activitiesTaskRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	UserInfoRepository userInfoReposistory;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
		
   @Autowired
	ProjectMasterRepository projectMastRepository;
   
   @Autowired
	ConnectionMasterInfoRepository connectionMasterInfoRepository;





	

       @Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ActivityMasterDTO> getAllActivities() {
		short notDeleted = 0;
		List<ActivityMaster> activityMasters= activityMasterRepository.findByIsDelete(notDeleted);
		List<ActivityMasterDTO> activityMasterDTOs = new ArrayList<ActivityMasterDTO>();
		ActivityMasterDTO activityMasterDTO = null;
		for (ActivityMaster activityMaster : activityMasters) {
			activityMasterDTO = modelMapper.map(activityMaster, ActivityMasterDTO.class);
			activityMasterDTO
					.setClientCode(clientInfoRepository.findById(activityMaster.getClientId())
							.get().getClientCode());
			activityMasterDTO
			.setClientDescription(clientInfoRepository.findById(activityMaster.getClientId())
					.get().getClientName());
			activityMasterDTO
			.setClientStatus(clientInfoRepository.findById(activityMaster.getClientId())
					.get().getClientStatus());
			activityMasterDTO.setActive(activityMaster.isActive());
			activityMasterDTOs.add(activityMasterDTO);
		}
		return activityMasterDTOs;
	}
	
	@Override
	public void addActivitymaster(ActivityMaster activitymaster)
	{
		activityMasterRepository.save(activitymaster);
	}

	@Override
	public List<ActivitiesByClientResponse> getActivitiesByClient(Long clientId) {
		short notDeleted = 0;
		List<ActivityMaster> activitiesByClientList = activityMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<ActivitiesByClientResponse> activitiesByClientResponseList = new ArrayList<>();
		ActivitiesByClientResponse activitiesByClientResponse = null;
		for (ActivityMaster activitiesByClient : activitiesByClientList) {
			activitiesByClientResponse = modelMapper.map(activitiesByClient, 
					ActivitiesByClientResponse.class);
			activitiesByClientResponse.setClientCode(clientInfoRepository.findById(clientId).get().getClientCode());
			activitiesByClientResponse.setClientDescription(clientInfoRepository.findById(clientId).get().getClientName());
			activitiesByClientResponseList.add(activitiesByClientResponse);
		}

		return activitiesByClientResponseList;
		
	
	}

	@Override
	@Transactional
	public void addActivitiesByClientProjectTask(@Valid List<ActivityTaskUserMappingRequest> activityTaskUserMappingRequest) {
		for (ActivityTaskUserMappingRequest activityTaskUserMappingRequests : activityTaskUserMappingRequest)
		{
			doUpSert(activityTaskUserMappingRequests);
		}
	}

	private void doUpSert(ActivityTaskUserMappingRequest activityTaskUserMappingRequests) {
	
	ActivityTaskUserMappingRequest.TaskActivity taskActivityReq = activityTaskUserMappingRequests.getTaskActivity();
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		TaskActivity taskActivityEntity = modelMapper.map(taskActivityReq, TaskActivity.class);
		TaskActivity taskActivityObj = activitiesTaskRepository.save(taskActivityEntity);
	}

	@Override
	public void editActivitiesById(AcitivityRequest acitivityRequest) throws Exception {
		Optional<ActivityMaster> activityMasterEntity = activityMasterRepository.findById(acitivityRequest.getActivityId());
		if(activityMasterEntity.isPresent()) {
			ActivityMaster activityMasterSave = activityMasterEntity.get();
			activityMasterSave.setActive(acitivityRequest.isActive());
	//		activityMasterSave.setActivityCode(acitivityRequest.getActivityCode());
			activityMasterSave.setActivityDesc(acitivityRequest.getActivityDesc());
			activityMasterSave.setModifiedBy(acitivityRequest.getModifiedBy());
			activityMasterRepository.save(activityMasterSave);
			
		}else {
			throw new Exception("Activity not found with the provided activity ID : "+acitivityRequest.getActivityId());
		}
		
	}
	
	
	
	@Override
	@Transactional
	public void editActivitiesByClientProjectTask(@Valid List<ActivityTaskUserMappingRequest> activityTaskUserMappingRequest) {
		
		for (ActivityTaskUserMappingRequest activityTaskUserMappingRequests : activityTaskUserMappingRequest)
		{
			doUpSert(activityTaskUserMappingRequests);
		}
	}
	
	
	public List<ActivitiesByClientProjectTaskResponse> getActivitesByClientProjectTask(long clientId,long projectId,long taskId) {

		short notDeleted = 0;
		List<TaskActivity> activitiesbytaskList = activitiesTaskRepository.findByClientIdAndProjectIdAndTaskIdAndIsDelete(clientId ,projectId,taskId,notDeleted );
		List<ActivitiesByClientProjectTaskResponse> activitiesByClientProjectTaskList = new ArrayList<ActivitiesByClientProjectTaskResponse>();
		for (TaskActivity activitiesbytask : activitiesbytaskList) {

			ActivitiesByClientProjectTaskResponse activitiesByClientProjectTaskResponse = new ActivitiesByClientProjectTaskResponse();
			ActivitiesByClientProjectTaskResponse.TaskActivity taskActivity = activitiesByClientProjectTaskResponse.getTaskActivity();
	
		
			ActivitiesByClientProjectTaskResponse.AdditionalDetails additionalDetails = activitiesByClientProjectTaskResponse.getAdditionalDetails();
			// task
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			taskActivity = modelMapper.map(activitiesbytask, ActivitiesByClientProjectTaskResponse.TaskActivity.class);
			taskActivity.setEndDate(TSMUtil.convertToLocalDateViaMilisecond(activitiesbytask.getEndDate()));
			taskActivity.setStartDate(TSMUtil.convertToLocalDateViaMilisecond(activitiesbytask.getStartDate()));
			activitiesByClientProjectTaskResponse.setTaskActivity(taskActivity);

			String teamMemberName = StringUtils.EMPTY;

			if (activitiesbytask.getUserId() !=null && activitiesbytask.getUserId() != 0) {
				Optional<UserInfo> userInfo = userInfoReposistory.findById(activitiesbytask.getUserId());
				if (userInfo.isPresent()) {
					teamMemberName = TSMUtil.getFullName(userInfo.get());
					// otherdetails
					additionalDetails.setUserName(teamMemberName);
				}
			}
			Optional<ActivityMaster> activitymaster = activityMasterRepository
					.findById(activitiesbytask.getActivityId());
			if (activitymaster.isPresent()) {
				// otherdetails
		//		additionalDetails.setActivityCode(activitymaster.get().getActivityCode());
				additionalDetails.setActivityDesc(activitymaster.get().getActivityDesc());
			}

			
			
			
			
			activitiesByClientProjectTaskList.add(activitiesByClientProjectTaskResponse);
		}

		return activitiesByClientProjectTaskList;
	}

	@Override
	public void deleteActivitesByClientProjectTask(long taskActivityId, String modifiedBy) throws Exception {
		Optional<TaskActivity> taskActivity = activitiesTaskRepository.findById(taskActivityId);
		if (taskActivity.isPresent()) {
			TaskActivity taskActivityEntitiy = taskActivity.get();
			short delete = 1;
			taskActivityEntitiy.setIsDelete(delete);
			taskActivityEntitiy.setModifiedBy(modifiedBy);
			activitiesTaskRepository.save(taskActivityEntitiy);

		}else {
			log.info("No task activity found with the provided ID{} in the DB",taskActivityId);
			throw new Exception("No task activity found with the provided ID in the DB :"+taskActivityId);
		}
	}

	
	@Override
	public List<ActivitiesByClientUserModel> getActivitiesByClientUser(Long clientId, Long userId) {
		
		short notDeleted = 0;
		List<TaskActivity> taskActivityList = activitiesTaskRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId, notDeleted);
		List<ProjectInfo> projInfoList = projectInfoRepository.findByIsDelete(notDeleted);
		List<TaskInfo> taskInfoList = taskInfoRepository.findByIsDelete(notDeleted);
		List<ActivityMaster> activityInfoList = activityMasterRepository.findByIsDelete(notDeleted);
		
		//if(projInfoList!=null && taskInfoList!=null && activityInfoList!=null) {
		List<ActivitiesByClientUserModel> activitiesByClientUserModelList = new ArrayList<>();
		
		for (TaskActivity taskActivity : taskActivityList) {
			ActivitiesByClientUserModel activitiesByClientUserModel = new ActivitiesByClientUserModel();
			
		//	if(activityInfoList!=null) {
			ActivityMaster activityMaster = activityMasterRepository.findById(taskActivity.getActivityId()).get();
			ProjectInfo projectInfo  = projectInfoRepository.findById(taskActivity.getProjectId()).get();
			TaskInfo taskInfo = taskInfoRepository.findById(taskActivity.getTaskId()).get();
			if (activityMaster.getIsDelete() == 0 && projectInfo.getIsDelete() == 0 && taskInfo.getIsDelete() == 0) {
				activitiesByClientUserModel.setActivityId(taskActivity.getActivityId());
				// activitiesByClientUserModel.setActivityCode(activityMaster.getActivityCode());
				activitiesByClientUserModel.setActivityDesc(activityMaster.getActivityDesc());
				activitiesByClientUserModel.setActiveStatus(activityMaster.isActive());

				// if(projInfoList!=null) {

				activitiesByClientUserModel.setProjectId(taskActivity.getProjectId());
				// activitiesByClientUserModel.setProjectCode(projectInfo.getProjectCode());
				activitiesByClientUserModel.setProjectName(projectInfo.getProjectName());
				activitiesByClientUserModel.setAllowUnplanned(projectInfo.getAllowUnplanned());

				// if(taskInfoList!=null) {

				activitiesByClientUserModel.setTaskId(taskActivity.getTaskId());
				// activitiesByClientUserModel.setTaskCode(taskInfo.getTaskCode());
				activitiesByClientUserModel.setTaskDescription(taskInfo.getTaskDescription());
				activitiesByClientUserModel.setTaskActivityId(taskActivity.getTaskActivityId());
				// }

				ClientInfo clientInfo = clientInfoRepository.findById(taskActivity.getClientId()).get();
				activitiesByClientUserModel.setClientCode(clientInfo.getClientCode());
				activitiesByClientUserModel.setClientDescription(clientInfo.getClientName());
				activitiesByClientUserModelList.add(activitiesByClientUserModel);
			}
		}
		
		return activitiesByClientUserModelList;
	//	}
	}
	
	
	public void deleteActivityById(Long id, String modifiedBy) throws Exception {
		
		
		Optional<ActivityMaster> activitymaster = activityMasterRepository.findById(id);
		if (activitymaster.isPresent()) {
			ActivityMaster activitymasterEntitiy = activitymaster.get();
			short delete = 1;
			activitymasterEntitiy.setIsDelete(delete);
			activitymasterEntitiy.setModifiedBy(modifiedBy);
			activityMasterRepository.save(activitymasterEntitiy);

		}else {
			log.info("No Activity found with the provided ID{} in the DB",id);
			throw new Exception("No Activity found with the provided ID in the DB :"+id);
		}
		
		
	}
	
	@Override
	public ResponseData addActivitiesByClientProjectTaskExternal(@Valid TaskActivityExtDTO taskActivityExtDto) {
		ResponseData resp = new ResponseData();
			log.error("Add external task activity");
		try {
			if (taskActivityExtDto.getExtProjectId() == null || taskActivityExtDto.getExtProjectId().isEmpty()
				|| taskActivityExtDto.getExtTaskId() == null || taskActivityExtDto.getExtTaskId().isEmpty()
				|| taskActivityExtDto.getExtUserId() == null || taskActivityExtDto.getExtUserId().isEmpty()
				|| taskActivityExtDto.getExtActivityId() == null || taskActivityExtDto.getExtActivityId().isEmpty()	
				) {		
				
				throw new ExternalIdException("ExternalId not found");
				
			} else {
				TaskActivity taskActivity = new TaskActivity();
				TaskInfo taskInfo = taskInfoRepository.findByExtIdAndClientId(taskActivityExtDto.getExtTaskId(), taskActivityExtDto.getClientId());
				ProjectInfo projectInfo = projectInfoRepository.findByExtIdAndClientId(taskActivityExtDto.getExtProjectId(),taskActivityExtDto.getClientId());
				UserInfo userInfo = userInfoReposistory.findByExtIdAndClientId(taskActivityExtDto.getExtUserId(),taskActivityExtDto.getClientId());
				ActivityMaster activityInfo = activityMasterRepository.findByExtId(taskActivityExtDto.getExtActivityId());
				
				//TaskActivity checkExist = ActivitiesTaskRepository.findByUserIdAndClientIdAndProjectId();
				if(taskInfo!=null) {
					taskActivity.setTaskId(taskInfo.getTaskInfoId());
				}
				if(projectInfo!=null) {
					taskActivity.setProjectId(projectInfo.getProjectInfoId());
				}				
				taskActivity.setUserId(userInfo.getId());				
				taskActivity.setActivityId(activityInfo.getActivityId());
				//taskActivity.setActualHrs(taskActivityExtDto.getActualHrs());
				taskActivity.setPlannedHrs(taskActivityExtDto.getPlannedHrs());
				taskActivity.setCreatedDate(taskActivityExtDto.getCreatedDate());
				taskActivity.setCreatedBy(taskActivityExtDto.getCreatedBy());				
				log.error("call to save task activity");
				taskActivity = activitiesTaskRepository.save(taskActivity);				
				resp.setId(taskActivity.getTaskActivityId());
				resp.setIsSaved(true);
				log.error("Saved Task Activity Data");
			}
		} catch (Exception e) {
			log.error("Activeity by client project Task unable to add "+e);
		}
		return resp;
	}

	@Override
	public ResponseData getActivitiesByClientProjectTask(String extProjectId, Long clientId, Long userId) {
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
		
		String extFetchDate ="";
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
	            List<TaskActivity> taskActivityList;
	            
	            for (int i = 0; i <jsnArray.length(); i++) {
	            	TaskActivityExtDTO taskActivityData = new TaskActivityExtDTO();
	            	
	            	//RETRIGVING DATA FROM jsnArray
	            	//String projectExtId = jsnArray.getJSONObject(i).getString("CPROJECT_ID");
	            	
	            	TaskActivity chkExist = null;
	            	//set below param
	            	if(chkExist!=null) {
	            		//taskActivityData.setActivityId(chkExist.getTaskActivityId());	
	            	}
	            	taskActivityData.setClientId(clientId);
	            	taskActivityData.setExtActivityId("");
	            	//taskActivityData.setExtId("");
	            	taskActivityData.setExtProjectId("");
	            	taskActivityData.setExtUserId("");
	            	taskActivityData.setCreatedBy("Abc");
	            	taskActivityData.setModifiedBy("Bcd");
	            	taskActivityData.setStartDate(new Date());
	            	taskActivityData.setEndDate(new Date());
	            	taskActivityData.setPlannedHrs(0);
	            	
	            	responseData = addActivitiesByClientProjectTaskExternal(taskActivityData);
	            	
	            	/*private long taskActivityId;
						private long activityId;
						private long taskId;
						private long projectId;
						private long clientId;
						private String createdBy;
						private String modifiedBy;
						private Date startDate;
						private Date endDate;
						private float plannedHrs;
						private float actualHrs;
						private Long userId;
	            	*/
	            	
	            }
		}catch(Exception e) {
			e.printStackTrace();
		}
	            
		
		

		
		
		return responseData;
	}
}