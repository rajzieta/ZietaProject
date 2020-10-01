package com.zietaproj.zieta.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.WorkflowRequest;

@Repository
public interface WorkflowRequestRepository extends JpaRepository<WorkflowRequest, Long> {
	
	public List<WorkflowRequest> findByApproverIdAndActionDateBetweenAndActionTypeIn(long approverId, 
									Date startActiondate, Date endActionDate, Collection<Long> actionType);
	public List<WorkflowRequest> findByApproverId(long approverId);
	
	
	public List<WorkflowRequest> findByApproverIdAndCurrentStep(long approverId, long currentStep);
	
	public List<WorkflowRequest> findByApproverIdAndCurrentStepAndActionTypeNotIn(long approverId, long currentStep, Collection<Long> actionType);

	public List<WorkflowRequest> findByRequestorIdAndCurrentStep(long requestorId, long currentStep);
	
	public WorkflowRequest findByTsIdAndApproverId(long tsId, long approverId);
	
	public List<WorkflowRequest> findByTsId(long tsId);
	
	public List<WorkflowRequest> findByTsIdAndStepId(long tsId, long stepId);
	
	@Query("select count(distinct step_id) from WorkflowRequest where tsId = ?1")
	public int countByStepIdFromTsId(Long tsId); 
	
	public List<WorkflowRequest> findByTsIdOrderByStepId(long taskId);


}
