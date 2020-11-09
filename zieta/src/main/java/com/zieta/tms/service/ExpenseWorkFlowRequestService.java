package com.zieta.tms.service;

import java.util.Date;
import java.util.List;

import com.zieta.tms.dto.ExpenseWorkFlowRequestDTO;
import com.zieta.tms.request.WorkflowRequestProcessModel;
import com.zieta.tms.response.ExpenseWFRDetailsForApprover;
import com.zieta.tms.response.ExpenseWorkFlowComment;




public interface ExpenseWorkFlowRequestService {

	public List<ExpenseWFRDetailsForApprover> findActiveWorkFlowRequestsByApproverId(long approverId);
	
	public void processWorkFlow(WorkflowRequestProcessModel workflowRequestProcessModel) throws Exception;
	
	public void processExpenseWorkFlow(ExpenseWorkFlowRequestDTO expenseWorkFlowRequestDTO) throws Exception;
	
	public List<ExpenseWFRDetailsForApprover> findWorkFlowRequestsByApproverId(long approverId, Date startActiondate, Date endActionDate);

	public List<ExpenseWorkFlowComment> getWFRCommentsChain(long expId);



}
