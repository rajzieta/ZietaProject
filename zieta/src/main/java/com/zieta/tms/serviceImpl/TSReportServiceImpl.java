package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.TSReport;
import com.zieta.tms.model.TSSumReport;
import com.zieta.tms.reports.TimeSheetReportHelper;
import com.zieta.tms.repository.TSReportRepository;
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
    
   
    
	
	
	@Override
	public List<TSReport> getTSReportEntriesFromProcedure(long clientId, String startDate, String endDate) {
		
		List<TSReport> tsReportList = tsReportRepository.getTsByDateRangeSP(clientId, startDate, endDate);
		return tsReportList;
	}
	
	
	
	@Override
	public List<TSSumReport> getTSReportSumEntriesFromProcedure(long clientId, String startDate, String endDate) {
		
		List<TSSumReport> tsReportList = tsSumReportRepository.getTsByDateRangeSumSP(clientId, startDate, endDate);
		
		return tsReportList;
	}
	
	
	@Override
	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException {
		
		List<TSReport> timeSheetReportList = getTSReportEntriesFromProcedure(clientId, startDate, endDate);
		return timeSheetReportHelper.downloadReport(response, timeSheetReportList);
		
	}

	
	
	@Override
	public ByteArrayInputStream downloadTimeSheetSumReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException {
		
		List<TSSumReport> timeSheetReportList = getTSReportSumEntriesFromProcedure(clientId, startDate, endDate);
		return timeSheetReportHelper.downloadSumReport(response, timeSheetReportList);
		
	}

	
	
}

