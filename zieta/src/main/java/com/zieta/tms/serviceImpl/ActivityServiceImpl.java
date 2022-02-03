package com.zieta.tms.serviceImpl;

import java.io.IOException;
import java.sql.Time;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.dto.ActivityMasterDTO;
import com.zieta.tms.dto.TaskActivityExtDTO;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.ErrorLog;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.TaskActivity;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.ActivitiesTaskRepository;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ConnectionMasterInfoRepository;
import com.zieta.tms.repository.ErrorLogMasterRepository;
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
   ErrorLogMasterRepository errorLogMasterRepository;





	

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
				activitiesByClientUserModel.setExtProjectId(projectInfo.getExtId());

				// if(taskInfoList!=null) {

				activitiesByClientUserModel.setTaskId(taskActivity.getTaskId());
				// activitiesByClientUserModel.setTaskCode(taskInfo.getTaskCode());
				activitiesByClientUserModel.setTaskDescription(taskInfo.getTaskDescription());
				activitiesByClientUserModel.setTaskActivityId(taskActivity.getTaskActivityId());
				activitiesByClientUserModel.setExtTaskId(taskInfo.getExtId());
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
			String logMsg ="";
		try {		
				
				TaskActivity taskActivity = new TaskActivity();
				TaskInfo taskInfo = null;
				ProjectInfo projectInfo = null;
				UserInfo userInfo = null;
				ActivityMaster activityInfo = null;
				Long activityId = 0L;
				Long userId =0L;
				if(taskActivityExtDto.getExtProjectId() ==null || taskActivityExtDto.getExtProjectId().isEmpty()){
					logMsg = logMsg+" Project ExtId Is Empty,";		
				}else {
					projectInfo = projectInfoRepository.findByExtIdAndClientId(taskActivityExtDto.getExtProjectId(),taskActivityExtDto.getClientId());
					if(projectInfo!=null) {
						taskActivity.setProjectId(projectInfo.getProjectInfoId());
						
					}else {
						logMsg = logMsg+" ProjectInfo Does not Exist";					
						resp.setMessage(logMsg);
						//throw new ExternalIdException("ProjectInfo Does not Exist");
					}
				}
				if(taskActivityExtDto.getExtTaskId() ==null || taskActivityExtDto.getExtTaskId().isEmpty()){
					logMsg = logMsg+" Task ExtId Is Empty,";		
				}else {
					taskInfo = taskInfoRepository.findByExtIdAndClientId(taskActivityExtDto.getExtTaskId(), taskActivityExtDto.getClientId());
					if(taskInfo!=null) {
						taskActivity.setTaskId(taskInfo.getTaskInfoId());
					}else {
						logMsg = logMsg+" TaskInfo Does not Exist";					
						resp.setMessage(logMsg);
						//throw new ExternalIdException("TaskInfo Does not Exist");
					}
				}
				
				if(taskActivityExtDto.getExtUserId() ==null || taskActivityExtDto.getExtUserId().isEmpty()){
					logMsg = logMsg+" User ExtId Is Empty,";
				}else {
					userInfo = userInfoReposistory.findByExtIdAndClientId(taskActivityExtDto.getExtUserId(),taskActivityExtDto.getClientId());
					
					if(userInfo!=null) {
						userId = userInfo.getId();
					}else {
						logMsg = logMsg+" UserInfo Does not Exist";					
						resp.setMessage(logMsg);
						//throw new ExternalIdException("UserInfo Does not Exist");
					}
				}
				
				if(taskActivityExtDto.getExtActivityId() ==null || taskActivityExtDto.getExtActivityId().isEmpty()){
					logMsg = logMsg+" Activity ExtId Is Empty,";
				}else {
					activityInfo = activityMasterRepository.findByExtId(taskActivityExtDto.getExtActivityId());
					if(activityInfo!=null) {
						activityId = activityInfo.getActivityId(); 
						
					}else {
						logMsg = logMsg+" ActivityInfo Does not Exist";					
						resp.setMessage(logMsg);
						//throw new ExternalIdException("ActivityInfo Does not Exist");
					}
				}
				
				log.error("log msg===>"+logMsg);
									
				resp.setMessage(logMsg);
				
				
				
				
				if(resp.getMessage().isEmpty()) {
				
				TaskActivity checkExist = activitiesTaskRepository.
						findByUserIdAndClientIdAndProjectIdAndTaskIdAndActivityId(userInfo.getId(),taskActivityExtDto.getClientId(),projectInfo.getProjectInfoId(),taskInfo.getTaskInfoId(),activityInfo.getActivityId());
				
				
				log.error("checkExistance ====>"+checkExist);
				if(checkExist!=null) {
					taskActivity.setTaskActivityId(checkExist.getTaskActivityId());
					taskActivity.setActualHrs(checkExist.getActualHrs());
				}
				taskActivity.setUserId(userId);				
				taskActivity.setActivityId(activityId);
				
				taskActivity.setPlannedHrs(taskActivityExtDto.getPlannedHrs());// updatetion nrequired from string to time
				taskActivity.setCreatedDate(taskActivityExtDto.getCreatedDate());
				taskActivity.setCreatedBy(taskActivityExtDto.getCreatedBy());
				taskActivity.setClientId(taskActivityExtDto.getClientId());
					log.error("call to save task activity");
					taskActivity = activitiesTaskRepository.save(taskActivity);				
					resp.setId(taskActivity.getTaskActivityId());
					resp.setIsSaved(true);
					logMsg = "Project Actvity Successfully saved/Updated";
					log.error("Saved Task Activity Data");
					resp.setMessage(logMsg);
				}else {
					log.error("Failed to Save activity");
				}
				
			
		} catch (Exception e) {
			//resp.setMessage(e.getMessage());
			log.error("Activeity by client project Task unable to add "+e);
		}
		return resp;
	}

	@Override
	public ResponseData getActivitiesByClientProjectTask(String extProjectId, Long clientId, String date) {
		ResponseData response = new ResponseData();	
		ResponseData returnData = new ResponseData();	
		String logMsg ="";
		String bydUrl ="";
		short notDeleted =0;
		String connName = "TaskActivityAPI";
		String loginId =null;
		String pass =null;
		String connStr = null;
		ArrayList<String> errorList = new ArrayList<String>();
		List<ConnectionMasterInfo> listConnectionData = connectionMasterInfoRepository.findByClientIdAndConnectionNameAndNotDeleted(clientId,connName,notDeleted);
		if(listConnectionData.size()>0) {
			loginId = listConnectionData.get(0).getLoginId();
			pass = listConnectionData.get(0).getPassword();
			connStr = listConnectionData.get(0).getConnectionStr();
		}
		log.error(loginId+"::"+pass);
		log.error("connStr"+connStr);
		String extFetchDate ="";
		
		//bydUrl = "https://my351070.sapbydesign.com/sap/byd/odata/ana_businessanalytics_analytics.svc/RPZ75BAE8B00CF5C49B68CC07QueryResults?$inlinecount=allpages&$select=CTASK_UUID,CSERVICE_UUID,CASSI_EMP_UUID,CTS_START_DATE,CTS_END_DATE,KCPLAN_WORK_H_TS&$filter=(Cs2ANsA0CE1245BC75D5A%20ge%20%2720211217034706.0000000%27)%20and%20(CPROJECT_UUID%20eq%20%27CPSO75%27)&$format=json";
		bydUrl = connStr+"$filter=(Cs2ANsA0CE1245BC75D5A%20ge%20%27"+date+".0000000%27)%20and%20(CPROJECT_UUID%20eq%20%27"+extProjectId+"%27)&$format=json";
		log.error("cstr::"+bydUrl);
		HttpGet httpGet = new HttpGet(bydUrl);
		
		httpGet.setHeader("content-type", "text/XML");			
		CredentialsProvider provider = new BasicCredentialsProvider();
		//UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ZIETAUSER", "Welcome1234");
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(loginId, pass);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		
		HttpResponse resp;
		try {
				ProjectInfo projectInfo = null;
				
				projectInfo = projectInfoRepository.findByExtIdAndClientId(extProjectId, clientId);
				if(projectInfo!=null) {
					returnData.setId(projectInfo.getProjectInfoId());
				}
				
				resp = client.execute(httpGet);	
				log.error("Response ====>"+resp);	
				String respString = EntityUtils.toString(resp.getEntity());		
	
	            // CONVERT RESPONSE STRING TO JSON ARRAY
	            JSONObject jsonRespData = new JSONObject(respString);            
	            JSONObject jsnObj = (JSONObject) jsonRespData.get("d");            
	            JSONArray jsnArray = jsnObj.getJSONArray("results"); 
	            ArrayList<TaskActivityExtDTO> taskActivityDataList = new ArrayList<TaskActivityExtDTO>();
	           for(int itr=1;itr<=1;itr++) {
		            for (int i = 0; i <jsnArray.length(); i++) {
		            	TaskActivityExtDTO taskActivityData = new TaskActivityExtDTO();
		            	ResponseData errorData = new ResponseData();
		            	
		            	
		            	TaskActivity chkExist = null;
		            	
		            	String dt1 ="";
		            	String dt2 ="";
		            	Date startDate = null;
		            	Date endDate = null;
		            	dt1 = jsnArray.getJSONObject(i).get("CTS_START_DATE").toString();
		            	dt2 = jsnArray.getJSONObject(i).get("CTS_END_DATE").toString(); 
		            	
		            	if(!dt1.equalsIgnoreCase("null") && !dt2.equalsIgnoreCase("null"))
		            	{	            		
							
							String sDate = dt1.substring(dt1.indexOf("(") + 1, dt1.indexOf(")"));
							String eDate =  dt1.substring(dt2.indexOf("(") + 1, dt2.indexOf(")"));
							long lngSdate = Long.parseLong(sDate);
							long lngEdate =  Long.parseLong(eDate);            	
							startDate = new Date(lngSdate);
							endDate = new Date(lngEdate);
		            	}
						//Date startDate = new Date();
						//Date endDate = new Date();					
						taskActivityData.setClientId(clientId);
		            	taskActivityData.setExtActivityId(jsnArray.getJSONObject(i).getString("CSERVICE_UUID"));            	
		            	taskActivityData.setExtProjectId(extProjectId);
		            	taskActivityData.setExtUserId(jsnArray.getJSONObject(i).getString("CASSI_EMP_UUID"));
						taskActivityData.setExtTaskId(jsnArray.getJSONObject(i).getString("CTASK_UUID"));
		            	taskActivityData.setCreatedBy("byd user");
		            	taskActivityData.setModifiedBy("byd user");					
		            	taskActivityData.setStartDate(startDate);
		            	taskActivityData.setEndDate(endDate);
		            	
		            	taskActivityData.setPlannedHrs(Float.parseFloat(jsnArray.getJSONObject(i).getString("KCPLAN_WORK_H_TS")));
		            	String timeStr = jsnArray.getJSONObject(i).getString("KCPLAN_WORK_H_TS").toString();
		            	//NEED TO UPDATE FOR ACTUAL TIME FROM FLOAT TO TIME 
		            	//Time startingTime = new Time (timeStr);
		            	//taskActivityData.setPlannedHrs(new Time(jsnArray.getJSONObject(i).getString("KCPLAN_WORK_H_TS").toString()));
		            	
						TaskActivity taskActivity = new TaskActivity();
						TaskInfo taskInfo = null;
						//ProjectInfo projectInfo = null;
						UserInfo userInfo = null;
						ActivityMaster activityInfo = null;
						Long activityId = 0L;
						Long userId =0L;
						if(extProjectId ==null || extProjectId.isEmpty()){
							logMsg = logMsg+" Project ExtId Is Empty,";		
						}else {
							projectInfo = projectInfoRepository.findByExtIdAndClientId(extProjectId,clientId);
							if(projectInfo!=null) {
								taskActivity.setProjectId(projectInfo.getProjectInfoId());
								
							}else {
								logMsg = logMsg+" ProjectInfo Does not Exist";					
								errorData.setMessage(logMsg);
								//throw new ExternalIdException("ProjectInfo Does not Exist");
							}
						}
						if(jsnArray.getJSONObject(i).getString("CTASK_UUID") ==null || jsnArray.getJSONObject(i).getString("CTASK_UUID").isEmpty()){
							logMsg = logMsg+" Task ExtId Is Empty,";		
						}else {
							taskInfo = taskInfoRepository.findByExtIdAndClientId(jsnArray.getJSONObject(i).getString("CTASK_UUID"), clientId);
							if(taskInfo!=null) {
								taskActivity.setTaskId(taskInfo.getTaskInfoId());
							}else {
								logMsg = logMsg+" TaskInfo Does not Exist";					
								errorData.setMessage(logMsg);
								//throw new ExternalIdException("TaskInfo Does not Exist");
							}
						}
						
						if(jsnArray.getJSONObject(i).getString("CASSI_EMP_UUID") ==null || jsnArray.getJSONObject(i).getString("CASSI_EMP_UUID").isEmpty()){
							logMsg = logMsg+" User ExtId Is Empty,";
						}else {
							userInfo = userInfoReposistory.findByExtIdAndClientId(jsnArray.getJSONObject(i).getString("CASSI_EMP_UUID"),clientId);
							
							if(userInfo!=null) {
								userId = userInfo.getId();
							}else {
								logMsg = logMsg+" UserInfo Does not Exist";					
								errorData.setMessage(logMsg);
								//throw new ExternalIdException("UserInfo Does not Exist");
							}
						}
						
						if(jsnArray.getJSONObject(i).getString("CSERVICE_UUID") ==null || jsnArray.getJSONObject(i).getString("CSERVICE_UUID").isEmpty()){
							logMsg = logMsg+" Activity ExtId Is Empty,";
						}else {
							activityInfo = activityMasterRepository.findByExtId(jsnArray.getJSONObject(i).getString("CSERVICE_UUID"));
							if(activityInfo!=null) {
								activityId = activityInfo.getActivityId(); 
								
							}else {
								logMsg = logMsg+" ActivityInfo Does not Exist";					
								errorData.setMessage(logMsg);
								//throw new ExternalIdException("ActivityInfo Does not Exist");
							}
						}											
						errorList.add(errorData.getMessage());//ADDING ERROR INTO ERROR LIST						
						taskActivityDataList.add(taskActivityData);//ADDING TASKACTIVITYDATA TO LIST				
		            	
						log.error("=====called====");
		            	//responseData = addActivitiesByClientProjectTaskExternal(taskActivityData);		            	
		            		            	
		            }
	            //CALL FOR SAVE DATA
	           // if(errorList.size()==0) {
	            	response =addBulkActivitiesByClientProjectTaskExternal(taskActivityDataList);
	            /*}else {
	            	ErrorLog logData = new ErrorLog();
	            	logData.setLogSource("TaskActivity");
	            	logData.setLogMessage(errorList.toString());
	            	logData.setLogTime(new Date());
	            	errorLogMasterRepository.save(logData);
	            }*/
	         }
		}catch(Exception e) {
			e.printStackTrace();
		}	

		returnData.setMessage(errorList.toString());	
		
		return returnData;
	}
	
	public ResponseData addBulkActivitiesByClientProjectTaskExternal(@Valid List<TaskActivityExtDTO> taskActivityExtDtoList) {
		ResponseData resposne = new ResponseData();
		log.error("Called to save bulk taskactivity data");
		for(TaskActivityExtDTO taskActivityData:taskActivityExtDtoList) {
			//CALL TO SAVE DATA
			resposne =	addActivitiesByClientProjectTaskExternal(taskActivityData);
		}
		return resposne;
	}
	
	//@Override
	public void readTaskActivityFromExcel(MultipartFile projectExcelData) {
		log.error("642 called task activity service method");  
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
				       
		
			            tempProjectInfo.setProjectInfoId((long) row.getCell(0).getNumericCellValue());			            
			            
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
			            tempUser.setUserMname(row.getCell(1).getStringCellValue());*/
		            
		            
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
		
	}
	
}
