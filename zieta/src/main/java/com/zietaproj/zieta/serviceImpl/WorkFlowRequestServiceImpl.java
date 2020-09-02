package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.common.TMSConstants;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.ProcessSteps;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.model.WorkflowRequestHistory;
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
import com.zietaproj.zieta.repository.WorkflowRequestHistoryRepository;
import com.zietaproj.zieta.repository.WorkflowRequestRepository;
import com.zietaproj.zieta.request.WorkflowRequestProcessModel;
import com.zietaproj.zieta.response.WFRDetailsForApprover;
import com.zietaproj.zieta.response.WFTSTimeEntries;
import com.zietaproj.zieta.response.WorkFlowHistoryModel;
import com.zietaproj.zieta.response.WorkFlowRequestorData;
import com.zietaproj.zieta.service.WorkFlowRequestService;
import com.zietaproj.zieta.util.TSMUtil;


@Service
public class WorkFlowRequestServiceImpl implements WorkFlowRequestService {

	@Autowired
	WorkflowRequestRepository workflowRequestRepository;
	
	@Autowired
	WorkflowRequestHistoryRepository workflowRequestHistoryRepository;

	
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
	public List<WFRDetailsForApprover> findByApproverId(long approverId) {
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverId(approverId);
		List<WFRDetailsForApprover> wFRDetailsForApproverList = new ArrayList<WFRDetailsForApprover>();
		WFRDetailsForApprover wFRDetailsForApprover = null;
		for (WorkflowRequest workflowRequest : workFlowRequestList) {

			wFRDetailsForApprover = new WFRDetailsForApprover();
			wFRDetailsForApprover.setWorkflowRequest(workflowRequest);

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
		
		WorkflowRequest workFlowRequest = workflowRequestRepository.findById(
				workflowRequestProcessModel.getWorkFlowRequestId()).get();
		
		/*List<ProcessSteps> processStepsList = processStepsRepository.findByClientIdAndProjectIdAndProjectTaskId(
				workFlowRequest.getClientId(),workFlowRequest.getProjectId(), workFlowRequest.getProjectTaskId());
		int workFlowDepth = processStepsList.size();*/
		
		TSInfo tsInfo = tsInfoRepository.findById(workFlowRequest.getTsId()).get();
		long nextStep = workFlowRequest.getStepId() +1;
		
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByTsIdAndStepId(workFlowRequest.getTsId(), nextStep);
		List<WorkflowRequest> multipleApprovalsAtOneStep = new ArrayList<WorkflowRequest>();
		for (int i = 0; workFlowRequestList != null && i < workFlowRequestList.size(); i++) {
			
			if(workFlowRequestList.get(i).getId() != workflowRequestProcessModel.getWorkFlowRequestId()) {
				multipleApprovalsAtOneStep.add(workFlowRequestList.get(i));
			}
			workFlowInAction(workflowRequestProcessModel, workFlowRequestList.get(i), 3, tsInfo);
			
		}
		workflowRequestRepository.deleteAll(multipleApprovalsAtOneStep);
	}
	
	
	private void workFlowInAction(WorkflowRequestProcessModel workflowRequestProcessModel, WorkflowRequest workFlowRequest,
			 int workFlowDepth, TSInfo tsInfo) {
		if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_APPROVE)) {
			// promote the approval to next level
			long currentStep = workFlowRequest.getCurrentStep();
				if (currentStep != workFlowDepth) {
					long nextStep = currentStep + 1;
					// Promoting to next level approval
					workFlowRequest.setComments(workflowRequestProcessModel.getComments());
//					workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_INPROCESS));

					// approved from the current step point of view
					workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
					workFlowRequest.setActionDate(new Date());
					float totalRejectTime = getTotalRejectedTime(tsInfo);
					// reduce the total rejected timeentries time from the total submitted time
					float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
					tsInfo.setTsTotalApprovedTime(totalApprovedTime);
				} else {
					// Final Approval Done
					workFlowRequest.setComments(workflowRequestProcessModel.getComments());
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
					workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_COMPLETE));
					workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
					workFlowRequest.setActionDate(new Date());
				}

		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_REJECT)) {
			// Request Rejected
			workFlowRequest.setComments(workflowRequestProcessModel.getComments());
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
			workFlowRequest.setActionDate(new Date());
		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_REVISE)) {
			// Request sent for revise
			workFlowRequest.setComments(workflowRequestProcessModel.getComments());
			workFlowRequest.setActionDate(new Date());
			workFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));
			workFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REVISE));
			Long statuId = statusMasterRepository.findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(
					tsInfo.getClientId(), TMSConstants.TIMESHEET, Boolean.TRUE, (short) 0).getId();
			tsInfo.setStatusId(statuId);
			tsInfoRepository.save(tsInfo);
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
		List<WorkflowRequestHistory> workflowRequestHistoryList = workflowRequestHistoryRepository
				.findByTsIdOrderByActionDateDesc(tsId);
		List<WorkFlowHistoryModel> workFlowHistoryModelList = new ArrayList<WorkFlowHistoryModel>();
		WorkFlowHistoryModel workFlowHistoryModel = null;
		for (WorkflowRequestHistory workflowRequestHistory : workflowRequestHistoryList) {
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

}
