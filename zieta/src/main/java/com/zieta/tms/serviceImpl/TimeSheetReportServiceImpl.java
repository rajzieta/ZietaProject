package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.TimeSheetReport;
import com.zieta.tms.repository.TimeSheetReportRepository;
import com.zieta.tms.service.TimeSheetReportService;
import com.zieta.tms.util.CurrentWeekUtil;
import com.zieta.tms.util.ReportUtil;
import com.zieta.tms.util.TSMUtil;

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
			endDate =  TSMUtil.getFormattedDate(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, 1);
			endDate = c.getTime();
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
			endDate =  TSMUtil.getFormattedDate(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, 1);
			endDate = c.getTime();
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
		}else {
			startDate = TSMUtil.getFormattedDate(startDate);
			endDate =  TSMUtil.getFormattedDate(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, 1);
			endDate = c.getTime();
		}
		return timeSheetReportRepository.findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(clientId, projectId,
				stateName, startDate, endDate);
	}

	@Override
	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response,long clientId,
			long projectId, String stateName, Date startDate, Date endDate ) throws IOException {
		ReportUtil report = new ReportUtil();
		
		List<TimeSheetReport> timeSheetReportList = findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(clientId, projectId, 
				stateName, startDate, endDate);
		return report.downloadReport(response, timeSheetReportList);
		
	}
	
}
