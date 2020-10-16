package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.request.WorkflowRequestProcessModel;
import com.zieta.tms.response.ExpenseWFRDetailsForApprover;




public interface ExpenseWorkFlowRequestService {

	public List<ExpenseWFRDetailsForApprover> findActiveWorkFlowRequestsByApproverId(long approverId);
	
	public void processWorkFlow(WorkflowRequestProcessModel workflowRequestProcessModel) throws Exception;


}
