package com.zietaproj.zieta.service;

import java.util.Date;
import java.util.List;

import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.request.WorkflowRequestProcessModel;
import com.zietaproj.zieta.response.WFRDetailsForApprover;
import com.zietaproj.zieta.response.WorkFlowComment;
import com.zietaproj.zieta.response.WorkFlowHistoryModel;
import com.zietaproj.zieta.response.WorkFlowRequestorData;




public interface WorkFlowRequestService {

	public List<WFRDetailsForApprover> findActiveWorkFlowRequestsByApproverId(long approverId);
	
	public List<WFRDetailsForApprover> findWorkFlowRequestsByApproverId(long approverId, Date startActiondate, Date endActionDate);
	

	public List<WorkFlowRequestorData> findByRequestorId(long requestorId);

	public WorkflowRequest findByTsIdAndApproverId(long tsId, long approverId);
	
	public void processWorkFlow(WorkflowRequestProcessModel workflowRequestProcessModel);
	
	public List<WorkFlowHistoryModel> getWorkFlowHistoryForTS(Long tsId);
	
	public List<WorkFlowComment> getWFRCommentsChain(long tsId);

}
