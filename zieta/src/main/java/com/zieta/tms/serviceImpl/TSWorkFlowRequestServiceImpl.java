package com.zieta.tms.serviceImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
//import javax.net.ssl.HttpsURLConnection;//
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.EmployeeTime;
import com.zieta.tms.dto.TSWorkFlowRequestDTO;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TSTimeEntries;
import com.zieta.tms.model.TaskActivity;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.WorkFlowRequestComments;
import com.zieta.tms.model.WorkflowRequest;
import com.zieta.tms.repository.ActivitiesTaskRepository;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ConnectionMasterInfoRepository;
import com.zieta.tms.repository.ProcessStepsRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StateTypeMasterRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.TSInfoRepository;
import com.zieta.tms.repository.TSTimeEntriesRepository;
import com.zieta.tms.repository.TaskInfoRepository;
import com.zieta.tms.repository.TimeTypeRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.repository.WorkflowRequestCommentRepository;
import com.zieta.tms.repository.WorkflowRequestRepository;
import com.zieta.tms.request.WorkflowRequestProcessModel;
import com.zieta.tms.response.WFRDetailsForApprover;
import com.zieta.tms.response.WFTSTimeEntries;
import com.zieta.tms.response.WorkFlowComment;
import com.zieta.tms.response.WorkFlowHistoryModel;
import com.zieta.tms.response.WorkFlowRequestorData;
import com.zieta.tms.service.ProjectMasterService;
import com.zieta.tms.service.TaskTypeMasterService;
import com.zieta.tms.service.TimeSheetService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.service.WorkFlowRequestService;
import com.zieta.tms.util.CurrentWeekUtil;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class TSWorkFlowRequestServiceImpl implements WorkFlowRequestService {

	private static final DecimalFormat df = new DecimalFormat("0.00");
	@Autowired
	ConnectionMasterInfoRepository connectionMasterInfoRepository;
	   
	@Autowired
	WorkflowRequestRepository workflowRequestRepository;
	
	@Autowired
	WorkflowRequestCommentRepository workflowRequestCommentRepository;

	
	@Autowired
	TSInfoRepository tsInfoRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	TSTimeEntriesRepository tsTimeEntriesRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	TSTimeEntriesRepository tSTimeEntriesRepository;
	
	
	@Autowired
	StateTypeMasterRepository stateTypeMasterRepository;
	
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ProcessStepsRepository processStepsRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	
	@Autowired
	TimeTypeRepository timeTypeRepository;
	
	@Autowired
	TaskTypeMasterService taskTypeMasterService;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	ActivitiesTaskRepository activitiesTaskRepository;
	
	@Autowired
	TimeSheetService timeSheetService;
	
	//@Autowired
	//EmployeeTimeService employeeTimeService;
	
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	WorkFlowRequestService workFlowRequestService;

	@Autowired
	@Qualifier("stateByName")
	Map<String, Long> stateByName;
	
	@Autowired
	@Qualifier("actionTypeByName")
	Map<String, Long> actionTypeByName;

	
	@Autowired
	@Qualifier("stateById")
	Map<Long, String> stateById;
	
	@Autowired
	@Qualifier("actionTypeById")
	Map<Long, String> actionTypeById;
	

	@Override
	public List<WFRDetailsForApprover> findActiveWorkFlowRequestsByApproverId(long approverId) {
		Long currentStepPointer = 1L;
		List<Long> actionTypes = new ArrayList<Long>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdAndCurrentStepAndActionTypeNotIn(	approverId,currentStepPointer,actionTypes);
		List<WFRDetailsForApprover> wFRDetailsForApproverList = getWorkFlowRequestDetails(workFlowRequestList);

		return wFRDetailsForApproverList;
	}
	
	
	//TO IMPLEMENT FILTER WITH RESPECT TO USERID AND TSDATE
	@Override
	public List<WFRDetailsForApprover> findActiveWorkFlowRequestsByApproverIdAndEmployeeIdAndTsDate(long approverId, long uId, Date startDate, Date endDate) {
		Long currentStepPointer = 1L;
		List<Long> actionTypes = new ArrayList<Long>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));
		
		
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdAndCurrentStepAndActionTypeNotInAndTsDateAndUserId(approverId,currentStepPointer,actionTypes,startDate,endDate,uId);
		
		List<WFRDetailsForApprover> wFRDetailsForApproverList = getWorkFlowRequestDetails(workFlowRequestList);

		return wFRDetailsForApproverList;
	}
	
	
	//findActiveWorkFlowRequestsByApproverIdAndEmployeeId
	
	@Override
	public List<WFRDetailsForApprover> findActiveWorkFlowRequestsByApproverIdAndEmployeeId(long approverId, long uId) {
		Long currentStepPointer = 1L;
		List<Long> actionTypes = new ArrayList<Long>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));		
		
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdAndCurrentStepAndActionTypeNotInAndUserId(approverId,currentStepPointer,actionTypes,uId);		
		List<WFRDetailsForApprover> wFRDetailsForApproverList = getWorkFlowRequestDetails(workFlowRequestList);
		return wFRDetailsForApproverList;
	}
	
	@Override
	public List<WFRDetailsForApprover> findActiveWorkFlowRequestsByApproverIdAndTsDate(long approverId, Date startDate, Date endDate) {
		Long currentStepPointer = 1L;
		List<Long> actionTypes = new ArrayList<Long>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));			
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdAndCurrentStepAndActionTypeNotInAndTsDate(approverId,currentStepPointer,actionTypes,startDate,endDate);
		List<WFRDetailsForApprover> wFRDetailsForApproverList = getWorkFlowRequestDetails(workFlowRequestList);
		return wFRDetailsForApproverList;
	}
	

	private List<WFRDetailsForApprover> getWorkFlowRequestDetails(List<WorkflowRequest> workFlowRequestList) {
		List<WFRDetailsForApprover> wFRDetailsForApproverList = new ArrayList<WFRDetailsForApprover>();
		WFRDetailsForApprover wFRDetailsForApprover = null;
		for (WorkflowRequest workflowRequest : workFlowRequestList) {

			wFRDetailsForApprover = new WFRDetailsForApprover();
			wFRDetailsForApprover.setWorkflowRequest(workflowRequest);
			List<WorkFlowComment> workFlowCommentList = getWFRCommentsChain(workflowRequest.getTsId());
			wFRDetailsForApprover.setWorkFlowCommentList(workFlowCommentList);
			TSInfo tsInfo = tsInfoRepository.findById(workflowRequest.getTsId()).get();
			
			//TSInfo tsInfo2 = tsInfoRepository.findByIdAndTsDate(workflowRequest.getTsId(),"2020-12-14","2020-12-14").get();
			//log.info("184 "+tsInfo2);
			wFRDetailsForApprover.setTsinfo(tsInfo);			
			short delFlag = 0;
			List<TSTimeEntries> tsTimeEntriesList = tSTimeEntriesRepository.findByTsIdAndIsDelete(tsInfo.getId(),delFlag);
			List<WFTSTimeEntries> wfTSTimeEntrieslist = buildWfTsTimeEtnries(tsTimeEntriesList);
			wFRDetailsForApprover.setTimeEntriesList(wfTSTimeEntrieslist);

			wFRDetailsForApprover
					.setProjectName(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectName());
			wFRDetailsForApprover
					.setRequestorName(TSMUtil.getFullName(userInfoRepository.findById(tsInfo.getUserId()).get()));
			wFRDetailsForApprover
					.setClientName(clientInfoRepository.findById(workflowRequest.getClientId()).get().getClientName());
			wFRDetailsForApprover.setWfActionType(actionTypeById.get(workflowRequest.getActionType()));
			wFRDetailsForApprover.setWfStateType(stateById.get(workflowRequest.getStateType()));
			
			if(tsInfo.getTaskId() != null && tsInfo.getTaskId() !=0) {
			TaskInfo taskInfoMaster = taskInfoRepository.findById(tsInfo.getTaskId()).get();
			wFRDetailsForApprover.setTaskName(taskInfoMaster.getTaskDescription());
			}
			else 
			{
				log.info(" task record: taskid {}, tsId {}",tsInfo.getTaskId(),tsInfo.getId());
			}			
			
			if(tsInfo.getActivityId() != null && tsInfo.getActivityId() !=0) {
				ActivityMaster activityMaster = activityMasterRepository.findById(tsInfo.getActivityId()).get();
				wFRDetailsForApprover.setActivityName(activityMaster.getActivityDesc());
			}
			
			else 
			{
				log.info(" Activity record: activityid {}, tsId {}",tsInfo.getActivityId(),tsInfo.getId());
			}			
			
			if(tsInfo.getTaskActivityId() != null && tsInfo.getTaskActivityId() !=0) {
				TaskActivity taskactivity = activitiesTaskRepository.findById(tsInfo.getTaskActivityId()).get();
				wFRDetailsForApprover.setPlannedHours(taskactivity.getPlannedHrs());
				wFRDetailsForApprover.setActualHours(taskactivity.getActualHrs());
			}	
			else 
			{
				log.info(" taskactivity record: taskactivityid {}, tsId {}",tsInfo.getTaskActivityId(),tsInfo.getId());		    	
				wFRDetailsForApprover.setPlannedHours(0.0f);
				wFRDetailsForApprover.setActualHours(0.0f);
			}
			wFRDetailsForApproverList.add(wFRDetailsForApprover);
		}
		return wFRDetailsForApproverList;
	}
	
	private List<WFTSTimeEntries> buildWfTsTimeEtnries(List<TSTimeEntries> tsTimeEntriesList) {

		List<WFTSTimeEntries> wFTSTimeEntriesList = new ArrayList<WFTSTimeEntries>();
		WFTSTimeEntries wFTSTimeEntries = null;
		for (TSTimeEntries tsTimeEntry : tsTimeEntriesList) {
			wFTSTimeEntries = new WFTSTimeEntries();
			wFTSTimeEntries.setTsTimeEntries(tsTimeEntry);
			wFTSTimeEntries.setTimeType(timeTypeRepository.findById(tsTimeEntry.getTimeType()).get().getTimeType());
			wFTSTimeEntriesList.add(wFTSTimeEntries);
		}
		return wFTSTimeEntriesList;
	}

 @Override
	public List<WorkFlowRequestorData> findByRequestorId(long requestorId) {
	 
		Long currentStepPointer = 1L;
		List<WorkflowRequest> workFlowRequestorItems = workflowRequestRepository.findByRequestorIdAndCurrentStep(requestorId, currentStepPointer);
		List<WorkFlowRequestorData> workFlowRequestorDataList = new ArrayList<WorkFlowRequestorData>();
		WorkFlowRequestorData workFlowRequestorData = null;

		for (WorkflowRequest workflowRequest : workFlowRequestorItems) {
			workFlowRequestorData = new WorkFlowRequestorData();
			TSInfo tsInfo = tsInfoRepository.findById(workflowRequest.getTsId()).get();
			UserInfo userInfo = userInfoRepository.findById(tsInfo.getUserId()).get();
			String requestorName = TSMUtil.getFullName(userInfo);
			workFlowRequestorData.setRequestorName(requestorName);
			workFlowRequestorData.setTotalSubmittedTime(tsInfo.getTsTotalSubmittedTime());
			workFlowRequestorData.setTotalApprovedTime(tsInfo.getTsTotalApprovedTime());
			if (workflowRequest.getStateType() == stateByName.get(TMSConstants.STATE_COMPLETE)) {
				workFlowRequestorData.setApprovedDate(workflowRequest.getActionDate());
			}
			if (workflowRequest.getStateType() == stateByName.get(TMSConstants.STATE_REJECT)) {
				workFlowRequestorData.setRejectedDate(workflowRequest.getActionDate());
			}
			workFlowRequestorData.setSubmittedDate(workflowRequest.getRequestDate());
			workFlowRequestorData.setStatus(stateTypeMasterRepository.findById(workflowRequest.getStateType()).get().getStateName());			
			List<TSTimeEntries> tsTElist = tSTimeEntriesRepository.findByTsId(tsInfo.getId());
			workFlowRequestorData.setTsInfo(tsInfo);
			workFlowRequestorData.setTsTimeEntries(tsTElist);
			workFlowRequestorData.setProjectName(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectName());
			workFlowRequestorData.setClientName(clientInfoRepository.findById(workflowRequest.getClientId()).get().getClientName());
			TaskInfo taskInfoMaster = taskInfoRepository.findById(tsInfo.getTaskId()).get();
			workFlowRequestorData.setTaskName(taskInfoMaster.getTaskDescription());
			ActivityMaster activityMaster = activityMasterRepository.findById(tsInfo.getActivityId()).get();
			workFlowRequestorData.setActivityName(activityMaster.getActivityDesc());			
			workFlowRequestorDataList.add(workFlowRequestorData);
		}
		return workFlowRequestorDataList;
	}

	@Override
	public WorkflowRequest findByTsIdAndApproverId(long tsId, long approverId) {
		return workflowRequestRepository.findByTsIdAndApproverId(tsId, approverId);
	}
	
	private void processWorkFlow(WorkflowRequestProcessModel workflowRequestProcessModel) {
		
		WorkflowRequest currentStepWorkFlowRequest = workflowRequestRepository.findById(
				workflowRequestProcessModel.getWorkFlowRequestId()).get();
		int workFlowDepth = workflowRequestRepository.countByStepIdFromTsId(currentStepWorkFlowRequest.getTsId());
		
		TSInfo tsInfo = tsInfoRepository.findById(currentStepWorkFlowRequest.getTsId()).get();
		long nextStep = currentStepWorkFlowRequest.getStepId() +1;		
		List<WorkflowRequest> currentStepWorkFlowRequestList = workflowRequestRepository.findByTsIdAndStepId(currentStepWorkFlowRequest.getTsId(),
				currentStepWorkFlowRequest.getStepId());
		for (int i = 0; currentStepWorkFlowRequestList != null && i < currentStepWorkFlowRequestList.size(); i++) {
			if (currentStepWorkFlowRequestList.get(i).getId() != workflowRequestProcessModel.getWorkFlowRequestId()) {
				// we are in the situation, where the current step having multiple approvers, so the current step with the other approvers are made zero
				currentStepWorkFlowRequestList.get(i).setCurrentStep(0L);
			} else {
				workFlowInAction(workflowRequestProcessModel, currentStepWorkFlowRequestList.get(i), workFlowDepth,	tsInfo);
			}
		}
		log.info("Workflow completed ...");
	}
	
	
	private void workFlowInAction(WorkflowRequestProcessModel workflowRequestProcessModel, WorkflowRequest workFlowRequest,
			 int workFlowDepth, TSInfo tsInfo) {
		workFlowRequest.setActionDate(new Date());
		if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_APPROVE)) {
			// promote the approval to next level
			long currentStep = workFlowRequest.getStepId();
				if (currentStep != workFlowDepth) {
					long nextStep = currentStep + 1;
					// Promoting to next level approval
					workFlowRequest.setCurrentStep(0L);
					workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_INPROCESS));
					// approved from the current step point of view
					workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
					
					//prepare the workflow object for the next step
					List<WorkflowRequest> nextStepWorkFlowRequestList = workflowRequestRepository.findByTsIdAndStepId(workFlowRequest.getTsId(), nextStep);
					
					for (WorkflowRequest nextStepWorkFlowRequest : nextStepWorkFlowRequestList) {

						nextStepWorkFlowRequest.setCurrentStep(1L);
						nextStepWorkFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_INPROCESS));

					}
					
					//
//					float totalRejectTime = getTotalRejectedTime(tsInfo);
					// reduce the total rejected timeentries time from the total submitted time
//					float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
//					tsInfo.setTsTotalApprovedTime(totalApprovedTime);
					log.info("Process inprogress with multistep...");
					
				} else {
					// Final Approval Done
					workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_COMPLETE));
					workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
					//Marking the current step(where final action taken) as 1 which is made by the previous step, 
					//change request raise by Santhosh, for reporting purpose..
					//workFlowRequest.setCurrentStep(0L);
					
					long statusId = statusMasterRepository
							.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(tsInfo.getClientId(),
									TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_APPROVED, (short) 0)
							.getId();
					tsInfo.setStatusId(statusId);
					float totalRejectTime = getTotalRejectedTime(tsInfo);
					// reduce the total rejected timeentries time from the total submitted time
					float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
					tsInfo.setTsTotalApprovedTime(totalApprovedTime);
					
					
//					activitiesTaskRepository
				    Long taskActivityId = tsInfo.getTaskActivityId();
				    TaskActivity taskActivity =  activitiesTaskRepository.findByTaskActivityIdAndUserId(taskActivityId,tsInfo.getUserId());
				   
				    if(taskActivityId !=null &&  taskActivityId.longValue() != 0 && taskActivity != null ) {
				    	 log.info("Actual hoours before updating {}",taskActivity.getActualHrs());
				    	//adding the totalapproved to actualhours.
				    	float totalActualHours =  totalApprovedTime + taskActivity.getActualHrs();
				    	taskActivity.setActualHrs(totalActualHours);
				    	log.info("updating the existing taskactivity record: taskactivityid {}, userid {}",taskActivityId,tsInfo.getUserId());
				    	log.info("Total approved time {}, actualhours {}, rejectedhrs {}", totalApprovedTime,taskActivity.getActualHrs(),totalRejectTime);
				    	
				    }else {
				    	//we are in the situation to handle unplanned activity here.
				    	 taskActivity = new TaskActivity();
				    	 taskActivity.setClientId(tsInfo.getClientId());
				    	 taskActivity.setProjectId(tsInfo.getProjectId());
				    	 taskActivity.setTaskId(tsInfo.getTaskId());
				    	 taskActivity.setActivityId(tsInfo.getActivityId());
				    	 taskActivity.setUserId(tsInfo.getUserId());
				    	 taskActivity.setPlannedHrs(0.0f);
				    	 taskActivity.setActualHrs(totalApprovedTime);
				    	 taskActivity.setCreatedDate(new Date());
				    	 taskActivity.setModifiedDate(new Date());
				    	 
				    	 taskActivity =  activitiesTaskRepository.save(taskActivity);
				    	 tsInfo.setTaskActivityId(taskActivity.getTaskActivityId());
				    	 log.info("Creating  he new taskactivity record: taskactivityid {}, userid {}, taskActivityid {}",taskActivityId,tsInfo.getUserId(),taskActivity);
				    	 log.info("Total approved time {}, actualhours {}, rejectedhrs {}", totalApprovedTime,taskActivity.getActualHrs(),totalRejectTime);
				    	 
				    }
					
				    tsInfoRepository.save(tsInfo);
				    log.info("Workflow Final Approval done ... ");
				    
				    //*****************SYNC TIMESHEET DATA AT THE TIME OF FINAL APPROVAL****************************			    
			    	// employeeTimeService.syncTimesheetData(tsInfo);
			    	Short isDelete=0;
			    	ClientInfo clientInfo = clientInfoRepository.findByClientIdAndIsDelete(tsInfo.getClientId(),isDelete);
			    	if(clientInfo.getExtConnection() ==1) {
			    		log.error("Ready to call timesheet sync method");
			    		syncTimesheetData(tsInfo);				    	
			    	}
				    //*****************END FINAL APPROVAL***********************************************************
				    		    
				}

		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_REJECT)) {
			// Request Rejected
			long statusId = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(
					tsInfo.getClientId(), TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_REJECTED, (short) 0).getId();
			tsInfo.setStatusId(statusId);
			//Marking the current step(where final action taken) as 1 which is made by the previous step, 
			//change request raise by Santhosh, for reporting purpose.
			float totalRejectTime = getTotalRejectedTime(tsInfo);
			// reduce the total rejected timeentries time from the total submitted time
			float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
			tsInfo.setTsTotalApprovedTime(totalApprovedTime);
			tsInfoRepository.save(tsInfo);			
			nullifyNextSteps(workFlowRequest, workFlowDepth);
			//Marking the current step(where final action taken) as 1 which is made by the previous step, 
			//change request raise by Santhosh, for reporting purpose.
			workFlowRequest.setCurrentStep(1L);
			
			workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_REJECT));
			workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REJECT));
			
		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_REVISE)) {
			// Request sent for revise
	
			//set the status the default one
			Long statuId = statusMasterRepository.findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(
					tsInfo.getClientId(), TMSConstants.TIMESHEET, Boolean.TRUE, (short) 0).getId();
			tsInfo.setStatusId(statuId);
			tsInfoRepository.save(tsInfo);
			nullifyNextSteps(workFlowRequest, workFlowDepth);
			//Marking the current step(where final action taken) as 1 which is made by the previous step, 
			//change request raise by Santhosh, for reporting purpose.
			workFlowRequest.setCurrentStep(1L);
			
			workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));
			workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REVISE));
			
			
		}
		
		//fill the workflow comments data
		WorkFlowRequestComments workFlowRequestComment = new WorkFlowRequestComments();
		workFlowRequestComment.setActionDate(workFlowRequest.getActionDate());
		workFlowRequestComment.setComments(workflowRequestProcessModel.getComments());
		workFlowRequestComment.setApproverId(workFlowRequest.getApproverId());
		workFlowRequestComment.setTsId(workFlowRequest.getTsId());
		workFlowRequestComment.setWrId(workFlowRequest.getId());
		
		workflowRequestCommentRepository.save(workFlowRequestComment);
	}

	private void nullifyNextSteps(WorkflowRequest workFlowRequest, int workFlowDepth) {
		List<WorkflowRequest> nextStepsWorkFlowRequestList = workflowRequestRepository.findByTsId(workFlowRequest.getTsId());
		for (int i= workFlowRequest.getStepId().intValue() +1 ; i <workFlowDepth; i++) {
			WorkflowRequest workflowRequest = nextStepsWorkFlowRequestList.get(i);
			workflowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));
			workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
			workFlowRequest.setCurrentStep(0L);
			
		}
	}

	
	private float getTotalRejectedTime(TSInfo tsInfo) {
		
		 long timeEntryRejectStatusId = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(
				 tsInfo.getClientId(),TMSConstants.TIMEENTRY, TMSConstants.TIMEENTRY_REJECTED, (short)0).getId();
		 List<TSTimeEntries> rejectedTimeEntriesList = tSTimeEntriesRepository.findByTsIdAndStatusIdAndIsDelete(
				 tsInfo.getId(), timeEntryRejectStatusId, (short)0);
		 
		 log.info("Getting rejected time: timeEntryRejectStatusId {}, tsInfo.getId() {}, rejectedTimeEntriesList.size {} ",timeEntryRejectStatusId, tsInfo.getId(),rejectedTimeEntriesList.size());
		 float totalRejectTime = 0.0f;
		 for (TSTimeEntries tsTimeEntries : rejectedTimeEntriesList) {
			 
			 totalRejectTime += tsTimeEntries.getTsDuration();
		}
		return totalRejectTime;
	}

	@Override
	public List<WorkFlowHistoryModel> getWorkFlowHistoryForTS(Long tsId) {
		List<WorkflowRequest> workflowRequestHistoryList = workflowRequestRepository
				.findByTsIdOrderByStepId(tsId);
		List<WorkFlowHistoryModel> workFlowHistoryModelList = new ArrayList<WorkFlowHistoryModel>();
		WorkFlowHistoryModel workFlowHistoryModel = null;
		for (WorkflowRequest workflowRequestHistory : workflowRequestHistoryList) {
			workFlowHistoryModel = new WorkFlowHistoryModel();
			TSInfo tsInfo = tsInfoRepository.findById(tsId).get();
			workFlowHistoryModel = new WorkFlowHistoryModel();
			UserInfo userInfo = userInfoRepository.findById(workflowRequestHistory.getRequestorId()).get();
			String requestorName = TSMUtil.getFullName(userInfo);
			workFlowHistoryModel.setRequestorName(requestorName);
			userInfo = userInfoRepository.findById(workflowRequestHistory.getApproverId()).get();
			String approverName = TSMUtil.getFullName(userInfo);
			workFlowHistoryModel.setApproverName(approverName);
			String activityName = activityMasterRepository.findById(tsInfo.getActivityId()).get().getActivityDesc();
			String taskName = taskInfoRepository.findById(tsInfo.getTaskId()).get().getTaskDescription();
			workFlowHistoryModel.setActivityName(activityName);
			workFlowHistoryModel.setTaskName(taskName);
			workFlowHistoryModel.setWorkflowRequestHistory(workflowRequestHistory);

			workFlowHistoryModelList.add(workFlowHistoryModel);
		}
		return workFlowHistoryModelList;
	}

	@Override
	public List<WFRDetailsForApprover> findWorkFlowRequestsByApproverId(long approverId, Date startRequestdate, Date endRequestDate) {
		boolean isDatesValid = TSMUtil.validateDates(startRequestdate,endRequestDate);
		
		//defaulting to the current week date range, when there is no date range mentioned from front end.
		if(!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en","IN"));
			startRequestdate =currentWeek.getFirstDay();
			endRequestDate = currentWeek.getLastDay();
		}else {
			startRequestdate = TSMUtil.getFormattedDate(startRequestdate);
			endRequestDate =  TSMUtil.getFormattedDate(endRequestDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endRequestDate);
		    //c.add(Calendar.DATE, 1);
			endRequestDate = c.getTime();
		}
		
		List<Long> actionTypes = new ArrayList<Long>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));
		
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdByTsDate(
				approverId, startRequestdate, endRequestDate,actionTypes);
		HashMap<Long,WorkflowRequest > tempWFRMap = new HashMap<>();
		for (WorkflowRequest workflowRequest : workFlowRequestList) {
			if (tempWFRMap.containsKey(workflowRequest.getTsId())) {
				if (tempWFRMap.get(workflowRequest.getTsId()).getStepId() < workflowRequest.getStepId()) {
					tempWFRMap.put(workflowRequest.getTsId(), workflowRequest);
				}

			} else {
				tempWFRMap.put(workflowRequest.getTsId(), workflowRequest);
			}
		}
		List<WorkflowRequest> wfrCollection = new ArrayList<WorkflowRequest>(tempWFRMap.values());
		List<WFRDetailsForApprover> wFRDetailsForApproverList = getWorkFlowRequestDetails(wfrCollection);
		return wFRDetailsForApproverList;
	}
	

	@Override
	public List<WorkFlowComment> getWFRCommentsChain(long tsId) {
		
		List<WorkFlowComment> workFlowCommentList = new ArrayList<>();
		WorkFlowComment workFlowComment = null;
		List<WorkFlowRequestComments> workFlowRequestCommentsList = workflowRequestCommentRepository.findByTsIdOrderByIdDesc(tsId);
		for (WorkFlowRequestComments workFlowRequestComment : workFlowRequestCommentsList) {
			workFlowComment = new WorkFlowComment();
			workFlowComment.setApproverName(TSMUtil.getFullName(
					userInfoRepository.findById(workFlowRequestComment.getApproverId()).get()));
			workFlowComment.setWorkFlowRequestComments(workFlowRequestComment);
			workFlowCommentList.add(workFlowComment);
			
		}
		return workFlowCommentList;
	}

	@Override
	@Transactional
	public void processTSWorkFlow(TSWorkFlowRequestDTO tSWorkFlowRequestDTO) throws Exception {
		if(!tSWorkFlowRequestDTO.getTimeentriesByTsIdRequest().isEmpty()) {
			timeSheetService.updateTimeEntriesByIds(tSWorkFlowRequestDTO.getTimeentriesByTsIdRequest());
			log.info("Updated the timeentries");
		}
		processWorkFlow(tSWorkFlowRequestDTO.getWorkflowRequestProcessModel());
		
	}
	
	@Override
	public List<WorkFlowRequestComments> findByTsIdDesc(long tsId) {
		return workflowRequestCommentRepository.findByTsIdDesc(tsId);
	}	
	
	
	//IMPLEMENTATION FOR BYD INTEGRATION
	//SYNC TIMESHEETDATA	
	public void syncTimesheetData(TSInfo tsInfo) {
				log.error("Called SYNC method for timesheet Data");
				//log.error("userId "+tsInfo.getUserId());
				UserInfoDTO userInfo = userInfoService.findByUserId(tsInfo.getUserId());
				EmployeeTime request = new EmployeeTime();
				request.setObjectNodeSenderTechnicalID("1");
				request.setEmployeeID(userInfo.getExtId());					
				request.setItemTypeCode("IN0010");
				EmployeeTime.DatePeriod datePeroid = new EmployeeTime.DatePeriod();
				
				String dt[] = tsInfo.getTsDate().toString().split("\\s");
				datePeroid.setEndDate(dt[0]);
				datePeroid.setStartDate(dt[0]);
				request.setDatePeriod(datePeroid);
				EmployeeTime.TimePeriod timePeroid = new EmployeeTime.TimePeriod();
				///timePeroid.setEndTime("09:00:00");
				///timePeroid.setStartTime("17:00:00");
				//request.setTimePeriod(timePeroid);
				log.error(df.format(tsInfo.getTsTotalSubmittedTime()));
				String duartion =df.format(tsInfo.getTsTotalSubmittedTime()).toString();				
				//request.setDuration("PT17H00M");
				String[] parts = duartion.split("\\.");
				//converting duration to require format 
				String cusDuration = "PT"+parts[0]+"H"+parts[1]+"M";
				request.setDuration(cusDuration);
				//request.setDifferentBillableTimeRecordedIndicator1(false);	
				//log.error("project data===>  "+tsInfo.getProjectId());
				ProjectInfo projectInfo = projectMasterService.findByProjectId(tsInfo.getProjectId());	
				TaskInfo taskInfo = taskTypeMasterService.findByClientIdAndTaskId(tsInfo.getClientId(), tsInfo.getTaskId());
				request.setProjectElementID(taskInfo.getExtId());
				
				//List<WorkFlowRequestComments> workFlowRequestComments = workFlowRequestService.findByTsIdDesc(tsInfo.getId());
				List<TSTimeEntries> tsTimesheetEntriesList = tsTimeEntriesRepository.findByTsId(tsInfo.getId());
				
				/*if(workFlowRequestComments.size()>0) {
					request.setTimeSheetDescription(workFlowRequestComments.get(0).getComments());
				}else {
					request.setTimeSheetDescription("");
				}*/
				
				ActivityMaster activityInfo = activityMasterRepository.findByActivityId(tsInfo.getActivityId());
				if(activityInfo!=null) {
					request.setServiceProductInternalID(activityInfo.getExtId());
				}
				
				if(tsTimesheetEntriesList.size()>0) {
					String comments ="";
					for(TSTimeEntries tsTimeEntries:tsTimesheetEntriesList) {
						comments = comments + "||"+tsTimeEntries.getTimeDesc();
					}
					
					request.setWorkDescriptionText(comments);
				}
				
				
				log.error("projectInfo.getExtId()===>"+projectInfo.getExtId());				
				List<Pair<Integer, String>> list = null;		

				JAXBContext context;
				try {
					context = JAXBContext.newInstance(EmployeeTime.class);
					Marshaller mar = context.createMarshaller();
					mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					StringWriter sw = new StringWriter();
					mar.marshal(request, sw);
					String result = sw.toString();
					String requestData = result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
					String data = requestData.replace("<EmployeeTime>", "<EmployeeTime actionCode=\"01\">");
					
					data = data.replace("<WorkDescriptionText>", "<WorkDescriptionText languageCode=\"EN\">");
					//data = data.replace("</TimeSheetDescription>", "</a3x:TimeSheetDescription>");

					String finalString = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\" xmlns:a3x=\"http://sap.com/xi/AP/CustomerExtension/BYD/A3XM9\">\n"
							+ "<soap:Header/>\n" + "<soap:Body>\n" + "<glob:EmployeeTimeAsBundleMaintainRequest_sync>\n"
							+  data
							+ "</glob:EmployeeTimeAsBundleMaintainRequest_sync>\n" + "</soap:Body>\n" + "</soap:Envelope>";
					
					log.error("finalString: " +finalString+":");			
					try {
							list = bydHttpRequest(finalString,tsInfo.getClientId());
							//SET RESPONSE IN TRCKING TABLE
							//log.error("Timesheet Response ==>"+list);
							
						} catch (IOException e) {
							//SET RESPONSE IN TRCKING TABLE						
							e.printStackTrace();
						}
						
					
				} catch (JAXBException e) {
					
					e.printStackTrace();
				}	
				//return list;			
				
			}
			
			private List<Pair<Integer, String>> bydHttpRequest(String finalString, Long clientId) throws ClientProtocolException, IOException {
				
				String connName = "TimesheetAPI";
				String loginId =null;
				String pass =null;
				String connStr = null;
				short notDeleted =0;
				ArrayList<String> errorList = new ArrayList<String>();
				
				List<ConnectionMasterInfo> listConnectionData = connectionMasterInfoRepository.findByClientIdAndConnectionNameAndNotDeleted(clientId,connName,notDeleted);
				
				if(listConnectionData.size()>0) {
					loginId = listConnectionData.get(0).getLoginId();
					pass = listConnectionData.get(0).getPassword();
					connStr = listConnectionData.get(0).getConnectionStr();
				}
				
				
				//String url = "https://my351070.sapbydesign.com/sap/bc/srt/scs/sap/manageemployeetimein";
				String url = connStr;
				
				HttpPut httpPut = new HttpPut(url);
				//.setHeader("content-type", "text/xml");
				httpPut.setHeader("content-type", "application/soap+xml");
				//application/soap+xml
				CredentialsProvider provider = new BasicCredentialsProvider();
				//UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("_ZPORTAL", "Welcome1234");			    
				UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(loginId, pass);
				provider.setCredentials(AuthScope.ANY, credentials);
				//HttpsClient 
				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
				StringEntity entity = new StringEntity(finalString);
				httpPut.setEntity(entity);
				HttpResponse resp = client.execute(httpPut);
				Integer httpStatusCd = resp.getStatusLine().getStatusCode();
				String respString = EntityUtils.toString(resp.getEntity());				
				log.error("Response Data  ::"+respString);
				//log.info("Response Data from portal {} ", respString);
				List<Pair<Integer, String>> listOfPairs = new ArrayList<>();
				listOfPairs.add(new Pair<>(httpStatusCd, respString));
				//log.info("Status_Code and Response Binded to listOfPairs {} ", listOfPairs);
				return listOfPairs;
			}
		

	

}
