package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseDetailsReport;
import com.zieta.tms.model.TSSumReport;

@Repository
public interface ExpenseDetailsRepository extends JpaRepository<ExpenseDetailsReport, Integer> {

	@Query(value = "CALL SP_exp_details(:clientId,:start_date,:end_date)", nativeQuery = true)
	List<ExpenseDetailsReport> getExpenseDetailsReport(@Param("clientId") Long client_id, @Param("start_date") String start_date,
			@Param("end_date") String end_date);
}
