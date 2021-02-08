package com.zieta.tms.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseWorkflowRequest;

@Repository
public interface ExpenseWorkflowRepository extends JpaRepository<ExpenseWorkflowRequest, Long> {

	List<ExpenseWorkflowRequest> findByApproverId(long approverId);

	List<ExpenseWorkflowRequest> findByApproverIdAndActionTypeNotIn(long approverId, Collection<Long> actionType);

	ExpenseWorkflowRequest findByExpId(long expId);
	
	@Query(value ="SELECT * FROM expwf_request ewfr, expense_info einfo WHERE einfo.ID=ewfr.EXP_ID AND ewfr.APPROVER_ID=?1 AND einfo.EXP_START_DATE >=?2 AND einfo.EXP_START_DATE <=?3 AND ewfr.ACTION_TYPE IN ?4", nativeQuery=true)
	List<ExpenseWorkflowRequest> findByApproverIdAndRequestDateBetweenAndActionTypeIn(long approverId, Date startRequestDate,
			Date endRequestDate, Collection<Long> actionType);

}
