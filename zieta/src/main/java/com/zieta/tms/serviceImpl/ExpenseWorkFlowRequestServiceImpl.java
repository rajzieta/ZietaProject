package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.DateRange;
import com.zieta.tms.dto.ExpenseEntryMetaData;
import com.zieta.tms.dto.ExpenseWorkFlowRequestDTO;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseWFRComments;
import com.zieta.tms.model.ExpenseWorkflowRequest;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.WorkflowRequest;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.CountryMasterRepository;
import com.zieta.tms.repository.CurrencyMasterRepository;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.repository.ExpenseWFRCommentRepository;
import com.zieta.tms.repository.ExpenseWorkflowRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.WorkflowRequestProcessModel;
import com.zieta.tms.response.ExpenseWFRDetailsForApprover;
import com.zieta.tms.response.ExpenseWorkFlowComment;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.service.ExpenseWorkFlowRequestService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ExpenseWorkFlowRequestServiceImpl implements ExpenseWorkFlowRequestService {

	@Autowired
	ExpenseWorkflowRepository expenseWorkflowRepository;

	@Autowired
	ExpenseWFRCommentRepository expenseWFRCommentRepository;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	ClientInfoRepository clientInfoRepository;

	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Autowired
	ExpenseInfoRepository expenseInfoRepository;

	@Autowired
	ExpenseEntriesRepository expenseEntriesRepository;
	
	@Autowired
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	CurrencyMasterRepository currencyMasterRepository;
	
	@Autowired
	CountryMasterRepository countryMasterRepository;
	
	@Autowired
	ExpenseTypeMasterRepository expenseTypeMasterRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	ExpenseService expenseService;
	

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
	public List<ExpenseWFRDetailsForApprover> findActiveWorkFlowRequestsByApproverId(long approverId) {
		
		List<Long> actionTypes = new ArrayList<Long>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));

		
		List<ExpenseWorkflowRequest> expenseWorkflowRequestList = expenseWorkflowRepository
				.findByApproverIdAndActionTypeNotIn(approverId,actionTypes );
		return getWorkFlowRequestDetails(expenseWorkflowRequestList);
	}
	
	

	@Override
	public List<ExpenseWFRDetailsForApprover> findWorkFlowRequestsByApproverId(long approverId, String startRequestdate, String endRequestDate) {
		
		
		List<Long> actionTypes = new ArrayList<>();
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REJECT));
		actionTypes.add(actionTypeByName.get(TMSConstants.ACTION_REVISE));

		
		List<ExpenseWorkflowRequest> expenseWorkflowRequestList = expenseWorkflowRepository.findByApproverIdAndRequestDateBetweenAndActionTypeIn(
																		approverId, startRequestdate, endRequestDate,actionTypes);
		return getWorkFlowRequestDetails(expenseWorkflowRequestList);
	}

	private List<ExpenseWFRDetailsForApprover> getWorkFlowRequestDetails(List<ExpenseWorkflowRequest> expenseWorkflowRequestList) {
		
		List<ExpenseWFRDetailsForApprover> expenseWFRDetailsForApproverList = new ArrayList<>();
		
		for (ExpenseWorkflowRequest expenseWorkflowRequest : expenseWorkflowRequestList) {
			
			
			//only active current step data
				if(expenseWorkflowRequest.getCurrentStep()==null || expenseWorkflowRequest.getCurrentStep()==1L) {			
				ExpenseEntryMetaData expenseEntryMetaData = null;
				List<ExpenseEntryMetaData> expenseEntries = new ArrayList<>();
				ExpenseWFRDetailsForApprover expenseWFRDetailsForApprover = new ExpenseWFRDetailsForApprover();
				expenseWFRDetailsForApprover.setClientName(
						clientInfoRepository.findById(expenseWorkflowRequest.getClientId()).get().getClientName());
				List<ExpenseWorkFlowComment> commentList = getWFRCommentsChain(expenseWorkflowRequest.getExpId());
				expenseWFRDetailsForApprover.setExpenseWorkFlowComment(commentList);
				expenseWFRDetailsForApprover
						.setExpenseInfo(expenseInfoRepository.findById(expenseWorkflowRequest.getExpId()).get());
				
				//fill expenseEntries data for the corresponding expenseInfo entry
				
				List<ExpenseEntries> expenseEntryList = expenseEntriesRepository.findByExpIdAndIsDelete(expenseWorkflowRequest.getExpId(), (short) 0);
				for (ExpenseEntries expenseEntry : expenseEntryList) {
					expenseEntryMetaData = new  ExpenseEntryMetaData();
					expenseEntryMetaData.setExpenseEntriesList(expenseEntry);
					expenseEntryMetaData.setExpCountry(countryMasterRepository.findById(expenseEntry.getExpCountry()).get().getCountryName());
					expenseEntryMetaData.setExpCurrency(currencyMasterRepository.findById(expenseEntry.getExpCurrency()).get().getCurrencyName());
					expenseEntryMetaData.setExpType(expenseTypeMasterRepository.findById(expenseEntry.getExpType()).get().getExpenseType());
					expenseEntries.add(expenseEntryMetaData);
				}
				
				expenseWFRDetailsForApprover.setExpenseEntries(expenseEntries);
				// end of expenseEntries
				
				expenseWFRDetailsForApprover.setWfActionType(actionTypeById.get(expenseWorkflowRequest.getActionType()));
				expenseWFRDetailsForApprover.setWfStateType(stateById.get(expenseWorkflowRequest.getStateType()));
				expenseWFRDetailsForApprover.setRequestorName(
						TSMUtil.getFullName(userInfoRepository.findById(expenseWorkflowRequest.getRequestorId()).get()));
				expenseWFRDetailsForApprover.setExpenseWorkflowRequest(expenseWorkflowRequest);
				
				if(expenseWorkflowRequest.getProjectId() != null && expenseWorkflowRequest.getProjectId() != 0) {
					expenseWFRDetailsForApprover
					.setProjectName(projectInfoRepository.findById(expenseWorkflowRequest.getProjectId()).get().getProjectName());
				}
				
				if(expenseWorkflowRequest.getOrgUnitId() != null && expenseWorkflowRequest.getOrgUnitId() != 0) {
					expenseWFRDetailsForApprover
					.setOrgName(orgInfoRepository.findById(expenseWorkflowRequest.getOrgUnitId()).get().getOrgNodeName());
				}
				
				expenseWFRDetailsForApproverList.add(expenseWFRDetailsForApprover);
			}//end active current step validation 
		}
		return expenseWFRDetailsForApproverList;
	}

	@Override
	public void processWorkFlow(WorkflowRequestProcessModel workflowRequestProcessModel) throws Exception {
		
		ExpenseWorkflowRequest expenseWorkflowRequest = null;
		if (expenseWorkflowRepository.findById(workflowRequestProcessModel.getWorkFlowRequestId()).isPresent()) {
			
			
			expenseWorkflowRequest = expenseWorkflowRepository
					.findById(workflowRequestProcessModel.getWorkFlowRequestId()).get();
			
			int workFlowDepth = expenseWorkflowRepository.countStepIdByExpId(expenseWorkflowRequest.getExpId());//added					
			long expenseId = expenseWorkflowRequest.getExpId();
			ExpenseInfo  expenseInfo = expenseInfoRepository.findById(expenseId).get();
			long nextStep = expenseWorkflowRequest.getStepId() +1;//addedd
			//METHOD CALL TO SET MULTILEVEL APPROVAL SETUP
			workFlowInAction(workflowRequestProcessModel, expenseWorkflowRequest,workFlowDepth,expenseInfo);

			/*if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_APPROVE)) {
				
				
				
				

				expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
				expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_COMPLETE));

				long statusId = statusMasterRepository
						.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(expenseInfo.getClientId(),
								TMSConstants.EXPENSE, TMSConstants.EXPENSE_APPROVED, (short) 0)
						.getId();
				expenseInfo.setStatusId(statusId);
				
				float totalRejectAmount = getTotalRejectedAmount(expenseInfo);
				float totalApprovedAmount = expenseInfo.getExpAmount() - totalRejectAmount;
				expenseInfo.setApprovedAmt(totalApprovedAmount);
				
				
				
				
				
				
				
				

			} else if (workflowRequestProcessModel.getActionType() == actionTypeByName
					.get(TMSConstants.ACTION_REJECT)) {

				long statusId = statusMasterRepository
						.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(expenseInfo.getClientId(),
								TMSConstants.EXPENSE, TMSConstants.EXPENSE_REJECTED, (short) 0)
						.getId();
				expenseInfo.setStatusId(statusId);
				expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REJECT));
				expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_REJECT));

			} else if (workflowRequestProcessModel.getActionType() == actionTypeByName
					.get(TMSConstants.ACTION_REVISE)) {

				Long statuId = statusMasterRepository.findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(
						expenseInfo.getClientId(), TMSConstants.EXPENSE, Boolean.TRUE, (short) 0).getId();
				expenseInfo.setStatusId(statuId);

				expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REVISE));
				expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));

			}
			expenseWorkflowRequest.setActionDate(new Date());

			ExpenseWFRComments workFlowRequestComment = new ExpenseWFRComments();
			workFlowRequestComment.setActionDate(expenseWorkflowRequest.getActionDate());
			workFlowRequestComment.setComments(workflowRequestProcessModel.getComments());
			workFlowRequestComment.setApproverId(expenseWorkflowRequest.getApproverId());
			workFlowRequestComment.setExpId(expenseWorkflowRequest.getExpId());
			workFlowRequestComment.setExpWrId(expenseWorkflowRequest.getId());

			expenseWFRCommentRepository.save(workFlowRequestComment);
			expenseInfoRepository.save(expenseInfo);
			log.info("Workflow process completed for the expense id: {}",expenseInfo.getId());*/

		} else {
			log.error("Expense entry not found in the DB {}", workflowRequestProcessModel.getWorkFlowRequestId());
			throw new Exception(
					"Expense entry not found in the DB with ID: " + workflowRequestProcessModel.getWorkFlowRequestId());
		}

	}
	
	//FOR MULTISTEP APPROVAL	
	private void workFlowInAction(WorkflowRequestProcessModel workflowRequestProcessModel, ExpenseWorkflowRequest expenseWorkflowRequest,
			 int workFlowDepth, ExpenseInfo expenseInfo) {
		
		
		expenseWorkflowRequest.setActionDate(new Date());
		if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_APPROVE)) {
			
			//impl
			long currentStep = expenseWorkflowRequest.getStepId();			
			if (currentStep != workFlowDepth) {
				long nextStep = currentStep + 1;
				// Promoting to next level approval
				expenseWorkflowRequest.setCurrentStep(0L);
				expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_INPROCESS));
				// approved from the current step point of view
				expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
				
				///prepare the workflow object for the next step
				List<ExpenseWorkflowRequest> nextStepExpenseWorkFlowRequestList = expenseWorkflowRepository.findByExpIdAndStepId(expenseWorkflowRequest.getExpId(), nextStep);
				
				for (ExpenseWorkflowRequest nextStepExpenseWorkFlowRequest : nextStepExpenseWorkFlowRequestList) {					
					nextStepExpenseWorkFlowRequest.setCurrentStep(1L);
					nextStepExpenseWorkFlowRequest.setStateType(stateByName.get(TMSConstants.STATE_INPROCESS));
					nextStepExpenseWorkFlowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));

				}
				
				//
//				float totalRejectTime = getTotalRejectedTime(tsInfo);
				// reduce the total rejected timeentries time from the total submitted time
//				float totalApprovedTime = tsInfo.getTsTotalSubmittedTime() - totalRejectTime;
//				tsInfo.setTsTotalApprovedTime(totalApprovedTime);
				log.info("Process inprogress with multistep...");
				
			}else {
												
				//expenseWorkflowRequest.setCurrentStep(0L);///ADDEDD FOR FINAL APPROVAL	
				///prevBeing							
				expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
				expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_COMPLETE));

				long statusId = statusMasterRepository
						.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(expenseInfo.getClientId(),
								TMSConstants.EXPENSE, TMSConstants.EXPENSE_APPROVED, (short) 0)
						.getId();
				expenseInfo.setStatusId(statusId);
				
				float totalRejectAmount = getTotalRejectedAmount(expenseInfo);
				float totalApprovedAmount = expenseInfo.getExpAmount() - totalRejectAmount;
				expenseInfo.setApprovedAmt(totalApprovedAmount);
				
				//prevEnd
				
			}
			

		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_REJECT)) {
			long statusId = statusMasterRepository
					.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(expenseInfo.getClientId(),
							TMSConstants.EXPENSE, TMSConstants.EXPENSE_REJECTED, (short) 0)
					.getId();
			expenseInfo.setStatusId(statusId);
			expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REJECT));
			expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_REJECT));
			
			
			expenseWorkflowRequest.setCurrentStep(1L);
			
			
			

		} else if (workflowRequestProcessModel.getActionType() == actionTypeByName
				.get(TMSConstants.ACTION_REVISE)) {

			Long statuId = statusMasterRepository.findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(
					expenseInfo.getClientId(), TMSConstants.EXPENSE, Boolean.TRUE, (short) 0).getId();
			expenseInfo.setStatusId(statuId);

			expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REVISE));
			expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));
			expenseWorkflowRequest.setCurrentStep(1L);

		}
		expenseWorkflowRequest.setActionDate(new Date());

		ExpenseWFRComments workFlowRequestComment = new ExpenseWFRComments();
		workFlowRequestComment.setActionDate(expenseWorkflowRequest.getActionDate());
		workFlowRequestComment.setComments(workflowRequestProcessModel.getComments());
		workFlowRequestComment.setApproverId(expenseWorkflowRequest.getApproverId());
		workFlowRequestComment.setExpId(expenseWorkflowRequest.getExpId());
		workFlowRequestComment.setExpWrId(expenseWorkflowRequest.getId());
		
		
		expenseWFRCommentRepository.save(workFlowRequestComment);
		expenseInfoRepository.save(expenseInfo);
		log.info("Workflow process completed for the expense id: {}",expenseInfo.getId());
		
	}	
	
	
	@Override
	@Transactional
	public void processExpenseWorkFlow(ExpenseWorkFlowRequestDTO expenseWorkFlowRequestDTO) throws Exception {
		if(!expenseWorkFlowRequestDTO.getExpenseEntriesDTO().isEmpty()) {
			expenseService.editExpenseEntriesById(expenseWorkFlowRequestDTO.getExpenseEntriesDTO());
			log.info("Updated the expense entries");
		}
		processWorkFlow(expenseWorkFlowRequestDTO.getWorkflowRequestProcessModel());
		
	}

	@Override
	public List<ExpenseWorkFlowComment> getWFRCommentsChain(long expId) {

		List<ExpenseWorkFlowComment> workFlowCommentList = new ArrayList<>();
		ExpenseWorkFlowComment workFlowComment = null;
		List<ExpenseWFRComments> workFlowRequestCommentsList = expenseWFRCommentRepository
				.findByExpIdOrderByIdDesc(expId);
		for (ExpenseWFRComments workFlowRequestComment : workFlowRequestCommentsList) {
			workFlowComment = new ExpenseWorkFlowComment();
			workFlowComment.setApproverName(
					TSMUtil.getFullName(userInfoRepository.findById(workFlowRequestComment.getApproverId()).get()));
			workFlowComment.setExpenseWFRComments(workFlowRequestComment);
			workFlowCommentList.add(workFlowComment);

		}
		return workFlowCommentList;
	}
	
	
	
	private float getTotalRejectedAmount(ExpenseInfo expenseInfo) {
		
		 long expenseEntryRejectStatusId = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(
				 expenseInfo.getClientId(),TMSConstants.EXPENSEENTRY, TMSConstants.EXPENSEENTRY_REJECTED, (short)0).getId();
		 List<ExpenseEntries> rejectedExpenseEntriesList = expenseEntriesRepository.findByExpIdAndStatusIdAndIsDelete(
				 expenseInfo.getId(), expenseEntryRejectStatusId, (short)0);
		 
		 log.info("Getting rejected expense amount: expenseEntryRejectStatusId {}, expenseInfo.getId() {}, rejectedExpenseEntriesList.size {} ",expenseEntryRejectStatusId, expenseInfo.getId(),rejectedExpenseEntriesList.size());
		 float totalRejectAmount = 0.0f;
		 for (ExpenseEntries expenseEntries : rejectedExpenseEntriesList) {
			 
			 totalRejectAmount += expenseEntries.getExpAmtInr();
		}
		return totalRejectAmount;
	}
	
	
}
