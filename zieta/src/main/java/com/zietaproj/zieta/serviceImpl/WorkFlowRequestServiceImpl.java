package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.common.ActionType;
import com.zietaproj.zieta.common.StateType;
import com.zietaproj.zieta.common.Status;
import com.zietaproj.zieta.model.ProcessSteps;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProcessStepsRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.StateTypeMasterRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.repository.TSTimeEntriesRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.repository.WorkflowRequestRepository;
import com.zietaproj.zieta.response.WFRDetailsForApprover;
import com.zietaproj.zieta.response.WorkFlowRequestorData;
import com.zietaproj.zieta.service.WorkFlowRequestService;
import com.zietaproj.zieta.util.TSMUtil;


@Service
public class WorkFlowRequestServiceImpl implements WorkFlowRequestService {

	@Autowired
	WorkflowRequestRepository workflowRequestRepository;

	
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
	
	private final static String TIMESHEET = "TimeSheet";
	

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

			wFRDetailsForApprover.setTimeEntriesList(tsTimeEntriesList);

			wFRDetailsForApprover
					.setProjectName(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectName());
			wFRDetailsForApprover
					.setRequestorName(TSMUtil.getFullName(userInfoRepository.findById(tsInfo.getUserId()).get()));
			wFRDetailsForApprover
					.setClientName(clientInfoRepository.findById(workflowRequest.getClientId()).get().getClientName());

			wFRDetailsForApproverList.add(wFRDetailsForApprover);

		}

		return wFRDetailsForApproverList;
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
			workFlowRequestorData.setTotalSubmittedTime(String.valueOf(tsInfo.getTsTotalSubmittedTime()));
			workFlowRequestorData.setTotalApprovedTime(String.valueOf(tsInfo.getTsTotalApprovedTime()));
			if (workflowRequest.getStateType() == StateType.COMPLETE.getStateTypeId()) {

				workFlowRequestorData.setApprovedDate(String.valueOf(workflowRequest.getActionDate()));
			}

			if (workflowRequest.getStateType() == StateType.REJECT.getStateTypeId()) {

				workFlowRequestorData.setRejectedDate(String.valueOf(workflowRequest.getActionDate()));
			}
			workFlowRequestorData.setSubmittedDate(workflowRequest.getRequestDate().toString());
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
	public void processWorkFlow(Long workFlowRequestId, short actionType, String comments) {
		
		WorkflowRequest workFlowRequest = workflowRequestRepository.findById(workFlowRequestId).get();
		
		List<ProcessSteps> processStepsList = processStepsRepository.findByClientIdAndProjectIdAndProjectTaskId(
				workFlowRequest.getClientId(),workFlowRequest.getProjectId(), workFlowRequest.getProjectTaskId());
		int workFlowDepth = processStepsList.size();
		
		TSInfo tsInfo = tsInfoRepository.findById(workFlowRequest.getTsId()).get();
		
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByTsId(workFlowRequest.getTsId());
		Map<String, Long> statusTypes =  getStatus(tsInfo);
		
		for (int i = 0; workFlowRequestList != null && i < workFlowRequestList.size(); i++) {

			workFlowInAction(actionType, comments, workFlowRequestList.get(i), processStepsList, workFlowDepth, tsInfo,
					statusTypes);
			if (actionType == ActionType.APPROVE.getActionType() && workFlowRequestList.get(i).getId() != workFlowRequestId) {
				
				workflowRequestRepository.deleteById(workFlowRequestList.get(i).getId());
			}
		}
		
	}
	
	
	private void workFlowInAction(short actionType, String comments, WorkflowRequest workFlowRequest,
			List<ProcessSteps> processStepsList, int workFlowDepth, TSInfo tsInfo, Map<String, Long> statusTypes) {
		if (actionType == ActionType.APPROVE.getActionType()) {
			// promote the approval to next level
			long currentStep = workFlowRequest.getCurrentStep();
			if (currentStep <= workFlowDepth) {
				if (currentStep != workFlowDepth) {
					long nextStep = currentStep + 1;
					List<ProcessSteps> processStepFiltered = processStepsList.stream()
							.filter(step -> step.getStepId().equals(nextStep)).collect(Collectors.toList());
					workFlowRequest.setCurrentStep(nextStep);
					// Promoting to next level approval
					workFlowRequest.setComments(comments);
					workFlowRequest.setApproverId(Long.valueOf(processStepFiltered.get(0).getApproverId()));
					workFlowRequest.setStateType(StateType.INPROCESS.getStateTypeId());

					// approved from the current step point of view
					workFlowRequest.setActionType(ActionType.APPROVE.getActionType());
					workFlowRequest.setActionDate(new Date());
				} else {
					// Final Approval Done
					workFlowRequest.setComments(comments);
					tsInfo.setStatusId(statusTypes.get(Status.APPROVED.getStatus()));
					tsInfoRepository.save(tsInfo);
					workFlowRequest.setStateType(StateType.COMPLETE.getStateTypeId());
					workFlowRequest.setActionType(ActionType.APPROVE.getActionType());
					workFlowRequest.setActionDate(new Date());
				}

			}
		} else if (actionType == ActionType.REJECT.getActionType()) {
			// Request Rejected
			workFlowRequest.setComments(comments);
			workFlowRequest.setStateType(StateType.REJECT.getStateTypeId());
			workFlowRequest.setActionType(ActionType.REJECT.getActionType());
			tsInfo.setStatusId(statusTypes.get(Status.REJECTED.getStatus()));
			tsInfoRepository.saveAndFlush(tsInfo);
			workFlowRequest.setActionDate(new Date());
		} else if (actionType == ActionType.REVISE.getActionType()) {
			// Request sent for revise
			workFlowRequest.setComments(comments);
			workFlowRequest.setActionDate(new Date());
			workFlowRequest.setStateType(StateType.INITIAL.getStateTypeId());
			workFlowRequest.setActionType(ActionType.REVISE.getActionType());
			tsInfo.setStatusId((statusTypes.get(Status.DRAFT.getStatus())));
			tsInfoRepository.save(tsInfo);
			// Do we need to create the new Request or if its with the same request , need
			// to start with Initial Step ?
		}
	}
	
	
	
	private Map<String, Long> getStatus(TSInfo tsInfo) {
		Map<String, Long> statusTypes;
		short notDeleted = 0;
		List<StatusMaster>  statusMasterList = statusMasterRepository.findByClientIdAndStatusTypeAndIsDelete(
				tsInfo.getClientId(), TIMESHEET, notDeleted);
		statusTypes = statusMasterList.stream().collect(Collectors.toMap(StatusMaster::getStatus, StatusMaster::getId));
		return statusTypes;
	}
	

}
