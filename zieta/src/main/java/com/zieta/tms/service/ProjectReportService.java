package com.zieta.tms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.zieta.tms.model.ProjectDetailsReport;
import com.zieta.tms.model.ProjectSummaryReport;

@Transactional
public interface ProjectReportService {

	public List<ProjectDetailsReport> getProjectDetailsReport(long clientId, String startDate, String endDate);

	public ByteArrayInputStream downloadProjectDetailsReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException ;
	
	public List<ProjectSummaryReport> getProjectSummaryReport(long clientId, String startDate, String endDate);

	public ByteArrayInputStream downloadProjectSummaryReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException ;
	
	
}
