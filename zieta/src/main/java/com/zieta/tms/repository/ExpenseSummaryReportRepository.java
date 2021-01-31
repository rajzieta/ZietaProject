package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseSummaryReport;
import com.zieta.tms.model.TSSumReport;

@Repository
public interface ExpenseSummaryReportRepository extends JpaRepository<ExpenseSummaryReport, Integer> {

	@Query(value = "CALL SP_exp_summary(:clientId,:start_date,:end_date)", nativeQuery = true)
	List<ExpenseSummaryReport> getExpenseSummaryReport(@Param("clientId") Long client_id, @Param("start_date") String start_date,
			@Param("end_date") String end_date);
}
