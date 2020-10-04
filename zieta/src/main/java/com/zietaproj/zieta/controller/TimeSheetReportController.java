package com.zietaproj.zieta.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.model.TimeSheetReport;
import com.zietaproj.zieta.service.TimeSheetReportService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeSheet reports  API")
public class TimeSheetReportController {

	@Autowired
	TimeSheetReportService timeSheetReportService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetReportController.class);

	@RequestMapping(value = "getAllTimeSheetsReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TimeSheetReport> getAllTimeSheetsReport(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		Page<TimeSheetReport> timeSheetReport = null;
		try {
			timeSheetReport = timeSheetReportService.getAllTimeSheets(pageNo, pageSize);
			LOGGER.info("Total number of timesheet entries: " + timeSheetReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getAllTimeSheetsReport", e);
		}
		return timeSheetReport;
	}

	@RequestMapping(value = "getByClientIdAndStateName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TimeSheetReport> getByClientIdAndStateName(@RequestParam Long clientId, @RequestParam String stateName,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<TimeSheetReport> timeSheetReport = null;
		try {
			timeSheetReport = timeSheetReportService.findByClientIdAndStateNameAndRequestDateBetween(clientId,
					stateName, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of timesheet entries: " + timeSheetReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getByClientIdAndStateNameAndRequestDateBetween", e);
		}
		return timeSheetReport;
	}

	@RequestMapping(value = "getByClientIdAndProjectIdAndStateName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TimeSheetReport> getByClientIdAndProjectIdAndStateName(@RequestParam Long clientId,
			@RequestParam Long projectId, @RequestParam String stateName,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<TimeSheetReport> timeSheetReport = null;
		try {
			timeSheetReport = timeSheetReportService.findByClientIdAndProjectIdAndStateNameAndRequestDateBetween(
					clientId, projectId, stateName, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of timesheet entries: " + timeSheetReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getByClientIdAndProjectIdAndStateNameAndRequestDateBetween", e);
		}
		return timeSheetReport;
	}
	
	@RequestMapping(value = "downloadTimeSheet", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadTimeSheet(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam Long projectId, @RequestParam String stateName,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=timesheet_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		timeSheetReportService.downloadTimeSheetReport(response, clientId, projectId, stateName, startDate, endDate);
		
		System.out.print("!___ Download Complete");
	}

}
