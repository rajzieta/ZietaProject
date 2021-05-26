package com.zieta.tms.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.WorkflowRequest;

@Repository
public interface WorkflowRequestRepository extends JpaRepository<WorkflowRequest, Long> {
	
	
	public List<WorkflowRequest> findByApproverIdAndActionDateBetweenAndActionTypeIn(long approverId, 
									Date startActiondate, Date endActionDate, Collection<Long> actionType);
	
	@Query("select wfr from WorkflowRequest wfr, TSInfo ts where ts.id= wfr.tsId and wfr.approverId =?1 and ts.tsDate between ?2 and ?3 and wfr.actionType in ?4")
	public List<WorkflowRequest> findByApproverIdByTsDate(long approverId, 
			Date startActiondate, Date endActionDate, Collection<Long> actionType);
	
	public List<WorkflowRequest> findByApproverId(long approverId);
	
	
	public List<WorkflowRequest> findByApproverIdAndCurrentStep(long approverId, long currentStep);
	
	public List<WorkflowRequest> findByApproverIdAndCurrentStepAndActionTypeNotIn(long approverId, long currentStep, Collection<Long> actionType);

	//IMPLEMENT DUE TO NEED FILTER WITH RESPECT TO TSDATE
	@Query("select wfr from WorkflowRequest wfr, TSInfo ts where ts.id= wfr.tsId and wfr.approverId =?1 and wfr.currentStep =?2 and wfr.actionType in ?3 and ts.tsDate>=?4 and ts.tsDate<=?5 and ts.userId=?6")
	public List<WorkflowRequest> findByApproverIdAndCurrentStepAndActionTypeNotInAndTsDateAndUserId(long approverId, long currentStep, Collection<Long> actionType,Date startDate, Date endDate,long uId);
		
	@Query("select wfr from WorkflowRequest wfr, TSInfo ts where ts.id= wfr.tsId and wfr.approverId =?1 and wfr.currentStep =?2 and wfr.actionType in ?3 and ts.userId=?4")
	public List<WorkflowRequest> findByApproverIdAndCurrentStepAndActionTypeNotInAndUserId(long approverId, long currentStep, Collection<Long> actionType,long uId);
	
	@Query("select wfr from WorkflowRequest wfr, TSInfo ts where ts.id= wfr.tsId and wfr.approverId =?1 and wfr.currentStep =?2 and wfr.actionType in ?3 and ts.tsDate>=?4 and ts.tsDate<=?5")
	public List<WorkflowRequest> findByApproverIdAndCurrentStepAndActionTypeNotInAndTsDate(long approverId, long currentStep, Collection<Long> actionType,Date startDate, Date endDate);
	
	
	
	public List<WorkflowRequest> findByRequestorIdAndCurrentStep(long requestorId, long currentStep);
	
	public WorkflowRequest findByTsIdAndApproverId(long tsId, long approverId);
	
	public List<WorkflowRequest> findByTsId(long tsId);
	
	public List<WorkflowRequest> findByTsIdAndStepId(long tsId, long stepId);
	
	@Query("select count(distinct step_id) from WorkflowRequest where tsId = ?1")
	public int countByStepIdFromTsId(Long tsId); 
	
	public List<WorkflowRequest> findByTsIdOrderByStepId(long taskId);

	public List<WorkflowRequest> findByRequestorIdAndCurrentStepAndActionDateBetween(long requestorId,
			Long currentStep, Date startDate, Date endDate);

	public List<WorkflowRequest> findByApproverIdAndRequestDateBetweenAndActionTypeIn(long approverId,
			Date startRequestdate, Date endRequestDate, List<Long> actionTypes);



}
