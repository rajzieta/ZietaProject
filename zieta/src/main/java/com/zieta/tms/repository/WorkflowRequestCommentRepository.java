package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.WorkFlowRequestComments;

@Repository
public interface WorkflowRequestCommentRepository extends JpaRepository<WorkFlowRequestComments, Long> {
	
	public List<WorkFlowRequestComments> findByTsIdOrderByIdDesc(long tsId);
	
	

}
