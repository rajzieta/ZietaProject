package com.zietaproj.zieta.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.repository.WorkflowRequestRepository;
import com.zietaproj.zieta.service.WorkFlowRequestService;

@Service
public class WorkFlowRequestServiceImpl implements WorkFlowRequestService {

	@Autowired
	WorkflowRequestRepository workflowRequestRepository;

	@Override
	public List<WorkflowRequest> findByApproverId(long approverId) {
		return workflowRequestRepository.findByApproverId(approverId);
	}

}
