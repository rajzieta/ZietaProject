package com.zietaproj.zieta.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TimeSheetReport;

@Repository
@Transactional
public interface TimeSheetReportRepository extends JpaRepository<TimeSheetReport, String> {
	
	Page<TimeSheetReport> findByClientIdAndStateNameAndRequestDateBetween(long clientId, String stateName, Date startDate, Date endDate,
			Pageable pageable);
	
	Page<TimeSheetReport> findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(long clientId, long projectId, 
			String stateName, Date startDate, Date endDate, Pageable pageable);
	
	List<TimeSheetReport> findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(long clientId, long projectId, 
			String stateName, Date startDate, Date endDate);
	
	

}
