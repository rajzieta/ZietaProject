package com.zietaproj.zieta.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.zietaproj.zieta.model.TimeSheetReport;

@Transactional
public interface TimeSheetReportService {

	public Page<TimeSheetReport> getAllTimeSheets(Integer pageNo, Integer pageSize);
	
	public Page<TimeSheetReport> findByClientIdAndStateNameAndRequestDateBetween(long clientId, 
					String stateName, Date startDate, Date endDate, Integer pageNo, Integer pageSize);
	
	public Page<TimeSheetReport> findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(long clientId,
			long projectId, String stateName, Date startDate, Date endDate, Integer pageNo, Integer pageSize);
	
	public List<TimeSheetReport> findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(long clientId, long projectId, 
			String stateName, Date startDate, Date endDate);
	
	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response, long clientId,
			long projectId, String stateName, Date startDate, Date endDate) throws IOException;

}
