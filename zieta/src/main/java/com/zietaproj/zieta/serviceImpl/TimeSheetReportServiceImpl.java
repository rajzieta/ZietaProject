package com.zietaproj.zieta.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.TimeSheetReport;
import com.zietaproj.zieta.repository.TimeSheetReportRepository;
import com.zietaproj.zieta.service.TimeSheetReportService;
import com.zietaproj.zieta.util.CurrentWeekUtil;
import com.zietaproj.zieta.util.ReportUtil;
import com.zietaproj.zieta.util.TSMUtil;

@Service
public class TimeSheetReportServiceImpl implements TimeSheetReportService {
	
	@Autowired
	private TimeSheetReportRepository timeSheetReportRepository;

	@Override
	public Page<TimeSheetReport> getAllTimeSheets(Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TimeSheetReport> timeSheetList =  timeSheetReportRepository.findAll(pageable);
		return timeSheetList;
	}

	@Override
	public Page<TimeSheetReport> findByClientIdAndStateNameAndRequestDateBetween(long clientId, String stateName,
			Date startDate, Date endDate, Integer pageNo, Integer pageSize) {

		boolean isDatesValid = TSMUtil.validateDates(startDate, endDate);

		// defaulting to the current week date range, when there is no date range
		// mentioned from front end.
		if (!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en", "IN"));
			startDate = currentWeek.getFirstDay();
			endDate = currentWeek.getLastDay();
		}else {
			startDate = TSMUtil.getFormattedDate(startDate);
			endDate =  TSMUtil.getFormattedDate(startDate);
		}

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TimeSheetReport> timeSheetList = timeSheetReportRepository
				.findByClientIdAndStateNameAndRequestDateBetween(clientId, stateName, startDate, endDate, pageable);
		return timeSheetList;
	}

	@Override
	public Page<TimeSheetReport> findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(long clientId,
			long projectId, String stateName, Date startDate, Date endDate, Integer pageNo, Integer pageSize) {

		boolean isDatesValid = TSMUtil.validateDates(startDate, endDate);

		// defaulting to the current week date range, when there is no date range
		// mentioned from front end.
		if (!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en", "IN"));
			startDate = currentWeek.getFirstDay();
			endDate = currentWeek.getLastDay();
		}else {
			startDate = TSMUtil.getFormattedDate(startDate);
			endDate =  TSMUtil.getFormattedDate(startDate);
		}

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TimeSheetReport> timeSheetList = timeSheetReportRepository
				.findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(clientId, projectId, stateName, startDate, endDate, pageable);
		return timeSheetList;
	}

	@Override
	public List<TimeSheetReport> findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(long clientId,
			long projectId, String stateName, Date startDate, Date endDate) {
		
		boolean isDatesValid = TSMUtil.validateDates(startDate, endDate);

		// defaulting to the current week date range, when there is no date range
		// mentioned from front end.
		if (!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en", "IN"));
			startDate = currentWeek.getFirstDay();
			endDate = currentWeek.getLastDay();
		}
		return timeSheetReportRepository.findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(clientId, projectId,
				stateName, startDate, endDate);
	}

	@Override
	public void downloadTimeSheetReport(HttpServletResponse response,long clientId,
			long projectId, String stateName, Date startDate, Date endDate ) throws IOException {
		ReportUtil report = new ReportUtil();
		
		List<TimeSheetReport> timeSheetReportList = findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(clientId, projectId, 
				stateName, startDate, endDate);
		report.downloadReport(response, timeSheetReportList);
		
	}
	
}
