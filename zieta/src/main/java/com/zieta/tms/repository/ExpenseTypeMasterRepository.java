package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseTypeMaster;

@Repository
public interface ExpenseTypeMasterRepository extends JpaRepository<ExpenseTypeMaster, Long>{

	List<ExpenseTypeMaster> findByIsDelete(short notDeleted);

	
}
