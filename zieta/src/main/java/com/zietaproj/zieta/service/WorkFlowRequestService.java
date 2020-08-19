package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.model.WorkflowRequest;

import com.zietaproj.zieta.response.WorkFlowRequestorData;




public interface WorkFlowRequestService {

	public List<WorkflowRequest> findByApproverId(long approverId);
	

	public List<WorkFlowRequestorData> findByRequestorId(long requestorId);

	public WorkflowRequest findByTsIdAndApproverId(long tsId, long approverId);

}
