package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseWFRComments;

@Repository
public interface ExpenseWFRCommentRepository extends JpaRepository<ExpenseWFRComments, Long> {
	
	public List<ExpenseWFRComments> findByExpIdOrderByIdDesc(long expId);
	
}
