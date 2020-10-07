package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TSWorkflow;

@Repository
public interface WorkflowRepository extends JpaRepository<TSWorkflow, Long> {

	
	
}
