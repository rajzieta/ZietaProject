package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
			String startDate, String endDate, String projectId, String teamId, String empId) throws IOException {
		
		List<ProjectDetailsReport> projectDetailsReportList = projectDetailsReportRepository.getProjectDetailsReport(clientId, startDate, endDate);
		
		if (projectId != null) {
			projectDetailsReportList = projectDetailsReportList.stream().parallel()
					.filter(criteria -> criteria.getProj_id() != null && criteria.getProj_id().equals(projectId))
					.collect(Collectors.toList());
		}

		if (empId != null) {
			projectDetailsReportList = projectDetailsReportList.stream().parallel()
					.filter(criteria -> criteria.getEmp_id() != null && criteria.getEmp_id().equals(empId))
					.collect(Collectors.toList());
		}

		if (teamId != null) {
			projectDetailsReportList = projectDetailsReportList.stream().parallel()
					.filter(criteria -> criteria.getTeam_id() != null && criteria.getTeam_id().equals(teamId))
					.collect(Collectors.toList());
		}
		
		return projectReportHelper.downloadProjectDetailsReport(response, projectDetailsReportList);
		
	}


	@Override
	public List<ProjectSummaryReport> getProjectSummaryReport(long clientId, String startDate, String endDate) {
		List<ProjectSummaryReport> projectSummaryReportList = projectSummaryReportRepository.getProjectSummaryReport(clientId, startDate, endDate);
		return projectSummaryReportList;
	}


	@Override
	public ByteArrayInputStream downloadProjectSummaryReport(HttpServletResponse response, long clientId,
			String startDate, String endDate, String projectId, String teamId, String empId) throws IOException {
		List<ProjectSummaryReport> projectSummaryReportList = projectSummaryReportRepository
				.getProjectSummaryReport(clientId, startDate, endDate);
		
		if (projectId != null) {
			projectSummaryReportList = projectSummaryReportList.stream().parallel()
					.filter(criteria -> criteria.getProj_id() != null && criteria.getProj_id().equals(projectId))
					.collect(Collectors.toList());
		}

		if (empId != null) {
			projectSummaryReportList = projectSummaryReportList.stream().parallel()
					.filter(criteria -> criteria.getEmp_id() != null && criteria.getEmp_id().equals(empId))
					.collect(Collectors.toList());
		}

		if (teamId != null) {
			projectSummaryReportList = projectSummaryReportList.stream().parallel()
					.filter(criteria -> criteria.getTeam_id() != null && criteria.getTeam_id().equals(teamId))
					.collect(Collectors.toList());
		}
		
		return projectReportHelper.downloadProjectSummaryReport(response, projectSummaryReportList);
	}

	
	
	
}

