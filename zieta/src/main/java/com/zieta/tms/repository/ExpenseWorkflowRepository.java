package com.zieta.tms.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseWorkflowRequest;

@Repository
public interface ExpenseWorkflowRepository extends JpaRepository<ExpenseWorkflowRequest, Long> {

	List<ExpenseWorkflowRequest> findByApproverId(long approverId);

	List<ExpenseWorkflowRequest> findByApproverIdAndActionTypeNotIn(long approverId, Collection<Long> actionType);

	List<ExpenseWorkflowRequest> findByApproverIdAndActionDateBetweenAndActionTypeIn(long approverId,
			Date startActiondate, Date endActionDate, Collection<Long> actionType);
	
	ExpenseWorkflowRequest findByExpId(long expId);

}
