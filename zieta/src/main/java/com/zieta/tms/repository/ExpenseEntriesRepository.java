package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.TSTimeEntries;

@Repository
public interface ExpenseEntriesRepository extends JpaRepository<ExpenseEntries, Long>{

	List<ExpenseEntries> findByIsDelete(short notDeleted);

	List<ExpenseEntries> findByExpIdAndIsDelete(Long expId, short notDeleted);
	
	List<ExpenseEntries> findByExpIdAndStatusIdAndIsDelete(Long tsId, long statusId, short notDeleted);

	
}
