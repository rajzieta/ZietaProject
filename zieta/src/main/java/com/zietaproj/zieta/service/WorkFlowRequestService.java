package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.model.WorkflowRequest;



public interface WorkFlowRequestService {

	public List<WorkflowRequest> findByApproverId(long approverId);
	
}
