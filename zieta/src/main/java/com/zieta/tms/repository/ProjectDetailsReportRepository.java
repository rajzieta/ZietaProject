package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ProjectDetailsReport;
import com.zieta.tms.model.TSReport;

@Repository
public interface ProjectDetailsReportRepository extends JpaRepository<ProjectDetailsReport, Long> {

	@Query(value = "CALL SP_proj_details(:clientId,:start_date,:end_date)", nativeQuery = true)
	List<ProjectDetailsReport> getProjectDetailsReport(@Param("clientId") Long clientId, @Param("start_date") String start_date,
			@Param("end_date") String end_date);

	
}
