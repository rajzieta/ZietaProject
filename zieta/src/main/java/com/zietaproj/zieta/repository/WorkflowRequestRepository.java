package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.WorkflowRequest;

@Repository
public interface WorkflowRequestRepository extends JpaRepository<WorkflowRequest, Long> {
	
	
	public List<WorkflowRequest> findByApproverId(long approverId);

	public List<WorkflowRequest> findByRequestorId(long requestorId);
	

}
