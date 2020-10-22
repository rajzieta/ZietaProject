package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.model.ActivityMaster;
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
import com.zieta.tms.service.WorkFlowRequestService;
import com.zieta.tms.util.CurrentWeekUtil;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class TSWorkFlowRequestServiceImpl implements WorkFlowRequestService {

	@Autowired
	WorkflowRequestRepository workflowRequestRepository;
	
	@Autowired
	WorkflowRequestCommentRepository workflowRequestCommentRepository;

	
	@Autowired
	TSInfoRepository tsInfoRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
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
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	ActivitiesTaskRepository activitiesTaskRepository;

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
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdAndCurrentStepAndActionTypeNotIn(
															approverId,currentStepPointer,actionTypes);
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
			wFRDetailsForApprover.setTsinfo(tsInfo);

			List<TSTimeEntries> tsTimeEntriesList = tSTimeEntriesRepository.findByTsId(tsInfo.getId());
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
			
			TaskInfo taskInfoMaster = taskInfoRepository.findById(tsInfo.getTaskId()).get();
			wFRDetailsForApprover.setTaskName(taskInfoMaster.getTaskDescription());
			
			ActivityMaster activityMaster = activityMasterRepository.findById(tsInfo.getActivityId()).get();
			wFRDetailsForApprover.setActivityName(activityMaster.getActivityDesc());

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
			workFlowRequestorData
					.setStatus(stateTypeMasterRepository.findById(workflowRequest.getStateType()).get().getStateName());
			
			List<TSTimeEntries> tsTElist = tSTimeEntriesRepository.findByTsId(tsInfo.getId());
			workFlowRequestorData.setTsInfo(tsInfo);
			workFlowRequestorData.setTsTimeEntries(tsTElist);
			workFlowRequestorData
					.setProjectName(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectName());
			workFlowRequestorData
					.setClientName(clientInfoRepository.findById(workflowRequest.getClientId()).get().getClientName());
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
	
	
	
	@Transactional
	public void processWorkFlow(WorkflowRequestProcessModel workflowRequestProcessModel) {
		
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
				workFlowInAction(workflowRequestProcessModel, currentStepWorkFlowRequestList.get(i), workFlowDepth,
						tsInfo);
			}

		}
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
				    log.info("Actual hoours before updating {}",taskActivity.getActualHrs());
				    if(taskActivityId !=null &&  taskActivityId.longValue() != 0 && taskActivity != null ) {
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
				    log.info("Workflow completed... ");
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
	public List<WFRDetailsForApprover> findWorkFlowRequestsByApproverId(long approverId, Date startActiondate, Date endActionDate) {
		boolean isDatesValid = TSMUtil.validateDates(startActiondate,endActionDate);
		
		//defaulting to the current week date range, when there is no date range mentioned from front end.
		if(!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en","IN"));
			startActiondate =currentWeek.getFirstDay();
			endActionDate = currentWeek.getLastDay();
		}else {
			startActiondate = TSMUtil.getFormattedDate(startActiondate);
			endActionDate =  TSMUtil.getFormattedDate(endActionDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endActionDate);
			c.add(Calendar.DATE, 1);
			endActionDate = c.getTime();
		}
		
		List<Long> actionTypes = new ArrayList<Long>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));
		
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdAndActionDateBetweenAndActionTypeIn(
				approverId, startActiondate, endActionDate,actionTypes);
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

}
