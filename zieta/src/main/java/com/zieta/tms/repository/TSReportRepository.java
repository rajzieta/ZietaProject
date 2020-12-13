package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.TSReport;

@Repository
public interface TSReportRepository extends JpaRepository<TSReport, Integer> {

	@Query(value = "CALL SP_ts_daterange(:clientId,:start_date,:end_date)", nativeQuery = true)
	List<TSReport> getTsByDateRangeSP(@Param("clientId") Long year_in, @Param("start_date") String start_date,
			@Param("end_date") String end_date);
}
