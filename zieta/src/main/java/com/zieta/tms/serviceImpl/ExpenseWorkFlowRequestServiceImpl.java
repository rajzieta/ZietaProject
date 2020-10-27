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
import com.zieta.tms.model.ExpenseWFRComments;
import com.zieta.tms.model.ExpenseWorkflowRequest;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseWFRCommentRepository;
import com.zieta.tms.repository.ExpenseWorkflowRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.WorkflowRequestProcessModel;
import com.zieta.tms.response.ExpenseWFRDetailsForApprover;
import com.zieta.tms.response.ExpenseWorkFlowComment;
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

		List<ExpenseWFRDetailsForApprover> expenseWFRDetailsForApproverList = new ArrayList<ExpenseWFRDetailsForApprover>();
		List<ExpenseWorkflowRequest> expenseWorkflowRequestList = expenseWorkflowRepository
				.findByApproverId(approverId);
		for (ExpenseWorkflowRequest expenseWorkflowRequest : expenseWorkflowRequestList) {
			ExpenseWFRDetailsForApprover expenseWFRDetailsForApprover = new ExpenseWFRDetailsForApprover();
			expenseWFRDetailsForApprover.setClientName(
					clientInfoRepository.findById(expenseWorkflowRequest.getClientId()).get().getClientName());
			List<ExpenseWorkFlowComment> commentList = getWFRCommentsChain(expenseWorkflowRequest.getExpId());
			expenseWFRDetailsForApprover.setExpenseWorkFlowComment(commentList);
			expenseWFRDetailsForApprover
					.setExpenseInfo(expenseInfoRepository.findById(expenseWorkflowRequest.getExpId()).get());
			expenseWFRDetailsForApprover.setExpenseEntriesList(
					expenseEntriesRepository.findByExpIdAndIsDelete(expenseWorkflowRequest.getExpId(), (short) 0));
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
				.setProjectName(orgInfoRepository.findById(expenseWorkflowRequest.getOrgUnitId()).get().getOrgNodeName());
			}
			
			expenseWFRDetailsForApproverList.add(expenseWFRDetailsForApprover);
		}
		return expenseWFRDetailsForApproverList;
	}

	@Override
	public void processWorkFlow(WorkflowRequestProcessModel workflowRequestProcessModel) throws Exception {

		ExpenseWorkflowRequest expenseWorkflowRequest = null;
		if (expenseWorkflowRepository.findById(workflowRequestProcessModel.getWorkFlowRequestId()).isPresent()) {
			expenseWorkflowRequest = expenseWorkflowRepository
					.findById(workflowRequestProcessModel.getWorkFlowRequestId()).get();

			if (workflowRequestProcessModel.getActionType() == actionTypeByName.get(TMSConstants.ACTION_APPROVE)) {

				expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_APPROVE));
				expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_COMPLETE));

			} else if (workflowRequestProcessModel.getActionType() == actionTypeByName
					.get(TMSConstants.ACTION_REJECT)) {

				expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_REJECT));
				expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_REJECT));

			} else if (workflowRequestProcessModel.getActionType() == actionTypeByName
					.get(TMSConstants.ACTION_REVISE)) {

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

		} else {
			log.error("Expense entry not found in the DB {}", workflowRequestProcessModel.getWorkFlowRequestId());
			throw new Exception(
					"Expense entry not found in the DB with ID: " + workflowRequestProcessModel.getWorkFlowRequestId());
		}

	}

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
}
