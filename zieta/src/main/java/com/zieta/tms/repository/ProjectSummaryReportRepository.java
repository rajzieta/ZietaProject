package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ProjectDetailsReport;
import com.zieta.tms.model.ProjectSummaryReport;

@Repository
public interface ProjectSummaryReportRepository extends JpaRepository<ProjectSummaryReport, Long> {

	@Query(value = "CALL SP_proj_summary(:clientId,:start_date,:end_date)", nativeQuery = true)
	List<ProjectSummaryReport> getProjectSummaryReport(@Param("clientId") Long clientId, @Param("start_date") String startDate,
			@Param("end_date") String endDate);

	
}
