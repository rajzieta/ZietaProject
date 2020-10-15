package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseWorkflowRequest;

@Repository
public interface ExpenseWorkflowRepository extends JpaRepository<ExpenseWorkflowRequest, Long> {
	
	List<ExpenseWorkflowRequest> findByApproverId(long approverId);

}
