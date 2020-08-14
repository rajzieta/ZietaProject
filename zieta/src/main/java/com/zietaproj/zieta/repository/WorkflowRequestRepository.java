package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.WorkflowRequest;

@Repository
public interface WorkflowRequestRepository extends JpaRepository<WorkflowRequest, Long> {

	
	
}
