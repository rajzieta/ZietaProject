package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.TSReport;
import com.zieta.tms.model.TSSumReport;
import com.zieta.tms.reports.TimeSheetReportHelper;
import com.zieta.tms.repository.TSReportRepository;
import com.zieta.tms.repository.TSEmployeeReportRepository;
import com.zieta.tms.repository.TSSumReportRepository;
import com.zieta.tms.service.TSReportService;

@Service
public class TSReportServiceImpl implements TSReportService {
    @Autowired
	TSReportRepository tsReportRepository;
    
    @Autowired
   	TSSumReportRepository tsSumReportRepository;
    
    @Autowired
    TimeSheetReportHelper timeSheetReportHelper;
    
    @Autowired
    TSEmployeeReportRepository tsEmployeeReportRepository;
    
   
    
	
	
	@Override
	public List<TSReport> getTSReportEntriesFromProcedure(long clientId, String startDate, String endDate) {
		
		List<TSReport> tsReportList = tsReportRepository.getTsByDateRangeSP(clientId, startDate, endDate);
		return tsReportList;
	}
	
	//REPORT USING USERID AND DATE RANGE	
	@Override
	public List<TSReport> getTSReportEntriesFromProcedureByEmployeeIdAndDateRange(long employeeId, String startDate, String endDate) {
		
		List<TSReport> tsReportList = tsEmployeeReportRepository.getTsByDateRangeSP(employeeId, startDate, endDate);
		return tsReportList;
	}
	
	
	
	@Override
	public List<TSSumReport> getTSReportSumEntriesFromProcedure(long clientId, String startDate, String endDate) {
		
		List<TSSumReport> tsReportList = tsSumReportRepository.getTsByDateRangeSumSP(clientId, startDate, endDate);
		
		return tsReportList;
	}
	
	
	@Override
	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response,long clientId,
			String startDate, String endDate, String projectId, String teamId, String empId) throws IOException {

		List<TSReport> timeSheetReportList = getTSReportEntriesFromProcedure(clientId, startDate, endDate);

		if (projectId != null) {
			timeSheetReportList = timeSheetReportList.stream().parallel()
					.filter(criteria -> criteria.getProj_id() != null && criteria.getProj_id().equals(projectId))
					.collect(Collectors.toList());
		}

		if (empId != null) {
			timeSheetReportList = timeSheetReportList.stream().parallel()
					.filter(criteria -> criteria.getEmp_id() != null && criteria.getEmp_id().equals(empId))
					.collect(Collectors.toList());
		}

		if (teamId != null) {
			timeSheetReportList = timeSheetReportList.stream().parallel()
					.filter(criteria -> criteria.getTeam_id() != null && criteria.getTeam_id().equals(teamId))
					.collect(Collectors.toList());
		}

		return timeSheetReportHelper.downloadReport(response, timeSheetReportList);

	}

	
	
	@Override
	public ByteArrayInputStream downloadTimeSheetSumReport(HttpServletResponse response,long clientId,
			String startDate, String endDate, String projectId, String teamId, String empId) throws IOException {
		
		List<TSSumReport> timeSheetReportList = getTSReportSumEntriesFromProcedure(clientId, startDate, endDate);
		if (projectId != null) {
			timeSheetReportList = timeSheetReportList.stream().parallel()
					.filter(criteria -> criteria.getProj_id() != null && criteria.getProj_id().equals(projectId))
					.collect(Collectors.toList());
		}

		if (empId != null) {
			timeSheetReportList = timeSheetReportList.stream().parallel()
					.filter(criteria -> criteria.getEmp_id() != null && criteria.getEmp_id().equals(empId))
					.collect(Collectors.toList());
		}

		if (teamId != null) {
			timeSheetReportList = timeSheetReportList.stream().parallel()
					.filter(criteria -> criteria.getTeam_id() != null && criteria.getTeam_id().equals(teamId))
					.collect(Collectors.toList());
		}
		return timeSheetReportHelper.downloadSumReport(response, timeSheetReportList);
		
	}

	
	
}

