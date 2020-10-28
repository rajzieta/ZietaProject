package com.zieta.tms.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ProjectReport;


@Repository
@Transactional
public interface ProjectReportRepository extends JpaRepository<ProjectReport, String>,JpaSpecificationExecutor<ProjectReport> {

	Page<ProjectReport> findByClientIdAndProjectIdAndEmpId(long clientId, long projectId, 
			long empId, Pageable pageable);
}
