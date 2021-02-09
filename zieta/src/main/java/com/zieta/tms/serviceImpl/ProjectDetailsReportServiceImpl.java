package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.ProjectDetailsReport;
import com.zieta.tms.model.ProjectSummaryReport;
import com.zieta.tms.reports.ProjectReportHelper;
import com.zieta.tms.repository.ProjectDetailsReportRepository;
import com.zieta.tms.repository.ProjectSummaryReportRepository;
import com.zieta.tms.service.ProjectReportService;

@Service
public class ProjectDetailsReportServiceImpl implements ProjectReportService {
    @Autowired
    ProjectDetailsReportRepository projectDetailsReportRepository;
    
    @Autowired
    ProjectSummaryReportRepository projectSummaryReportRepository;
    
    @Autowired
    ProjectReportHelper projectReportHelper;
    
    @Autowired
	ModelMapper modelMapper;
    
	
	
	
	@Override
	public List<ProjectDetailsReport> getProjectDetailsReport(long client_id, String startDate, String endDate) {
		
		List<ProjectDetailsReport> projectDetailsReportList = projectDetailsReportRepository.getProjectDetailsReport(client_id, startDate, endDate);
		
		
		return projectDetailsReportList;
	}
	
	
	@Override
	public ByteArrayInputStream downloadProjectDetailsReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException {
		
		List<ProjectDetailsReport> projectDetailsReportList = projectDetailsReportRepository.getProjectDetailsReport(clientId, startDate, endDate);
		return projectReportHelper.downloadProjectDetailsReport(response, projectDetailsReportList);
		
	}


	@Override
	public List<ProjectSummaryReport> getProjectSummaryReport(long clientId, String startDate, String endDate) {
		List<ProjectSummaryReport> projectSummaryReportList = projectSummaryReportRepository.getProjectSummaryReport(clientId, startDate, endDate);
		return projectSummaryReportList;
	}


	@Override
	public ByteArrayInputStream downloadProjectSummaryReport(HttpServletResponse response, long clientId,
			String startDate, String endDate) throws IOException {
		List<ProjectSummaryReport> projectSummaryReportList = projectSummaryReportRepository
				.getProjectSummaryReport(clientId, startDate, endDate);
		return projectReportHelper.downloadProjectSummaryReport(response, projectSummaryReportList);
	}

	
	
	
}

