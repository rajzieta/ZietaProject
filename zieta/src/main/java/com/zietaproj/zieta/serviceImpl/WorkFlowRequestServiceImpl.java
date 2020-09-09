package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.common.TMSConstants;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.model.WorkFlowRequestComments;
import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProcessStepsRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.StateTypeMasterRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.repository.TSTimeEntriesRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.repository.TimeTypeRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.repository.WorkflowRequestCommentRepository;
import com.zietaproj.zieta.repository.WorkflowRequestRepository;
import com.zietaproj.zieta.request.WorkflowRequestProcessModel;
import com.zietaproj.zieta.response.WFRDetailsForApprover;
import com.zietaproj.zieta.response.WFTSTimeEntries;
import com.zietaproj.zieta.response.WorkFlowComment;
import com.zietaproj.zieta.response.WorkFlowHistoryModel;
import com.zietaproj.zieta.response.WorkFlowRequestorData;
import com.zietaproj.zieta.service.WorkFlowRequestService;
import com.zietaproj.zieta.util.TSMUtil;


@Service
public class WorkFlowRequestServiceImpl implements WorkFlowRequestService {

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
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverIdAndCurrentStep(approverId,currentStepPointer);
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


	public List<WorkFlowRequestorData> findByRequestorId(long requestorId) {
		List<WorkflowRequest> workFlowRequestorItems = workflowRequestRepository.findByRequestorId(requestorId);
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
					float totalRejectTime = getTotalRejectedTime(tsInfo);
					// reduce the total rejected timeentries time from the total submitted time
					float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
					tsInfo.setTsTotalApprovedTime(totalApprovedTime);
					long statusId = statusMasterRepository
							.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(tsInfo.getClientId(),
									TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_APPROVED, (short) 0).getId();
					tsInfo.setStatusId(statusId);
					
				} else {
					// Final Approval Done
					workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_COMPLETE));
					workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
					workFlowRequest.setCurrentStep(0L);
					
					long statusId = statusMasterRepository
							.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(tsInfo.getClientId(),
									TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_APPROVED, (short) 0)
							.getId();
					tsInfo.setStatusId(statusId);
					float totalRejectTime = getTotalRejectedTime(tsInfo);
					// reduce the total rejected timeentries time from the total submitted time
					float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
					tsInfo.setTsTotalApprovedTime(totalApprovedTime);
					tsInfoRepository.save(tsInfo);
					
				}

		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_REJECT)) {
			// Request Rejected
			workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_REJECT));
			workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REJECT));
			long statusId = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(
					tsInfo.getClientId(), TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_REJECTED, (short) 0).getId();
			tsInfo.setStatusId(statusId);
			float totalRejectTime = getTotalRejectedTime(tsInfo);
			// reduce the total rejected timeentries time from the total submitted time
			float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
			tsInfo.setTsTotalApprovedTime(totalApprovedTime);
			tsInfoRepository.save(tsInfo);
			
			
			nullifyNextSteps(workFlowRequest, workFlowDepth);
			
		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_REVISE)) {
			// Request sent for revise
			workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));
			workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REVISE));
			workFlowRequest.setCurrentStep(0L);
			//set the status the default one
			Long statuId = statusMasterRepository.findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(
					tsInfo.getClientId(), TMSConstants.TIMESHEET, Boolean.TRUE, (short) 0).getId();
			tsInfo.setStatusId(statuId);
			tsInfoRepository.save(tsInfo);
			nullifyNextSteps(workFlowRequest, workFlowDepth);
			
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
		for (int i= workFlowRequest.getStepId().intValue(); i <workFlowDepth; i++) {
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
	public List<WFRDetailsForApprover> findWorkFlowRequestsByApproverId(long approverId) {
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverId(approverId);
		List<WFRDetailsForApprover> wFRDetailsForApproverList = getWorkFlowRequestDetails(workFlowRequestList);
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
