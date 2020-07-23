package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSWorkflow;

@Repository
public interface WorkflowRepository extends JpaRepository<TSWorkflow, Long> {

	
	
}
