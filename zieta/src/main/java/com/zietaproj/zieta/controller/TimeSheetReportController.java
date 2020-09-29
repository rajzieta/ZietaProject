package com.zietaproj.zieta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public  Page<TimeSheetReport> getAllTimeSheetsReport(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize) {
		 Page<TimeSheetReport> timeSheetReport = null;
		try {
			timeSheetReport = timeSheetReportService.getAllTimeSheets(pageNo, pageSize);
			LOGGER.info("Total number of timesheet entries: "+timeSheetReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getAllTimeSheetsReport",e);
		}
		return timeSheetReport;
	}
	
	/*
	@RequestMapping(value = "download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  Page<TimeSheetReport> downloadReport() {
		 Page<TimeSheetReport> timeSheetReport = null;
		try {
			timeSheetReport = timeSheetReportService.getAllTimeSheets(0, 10).getContent();
			LOGGER.info("Total number of timesheet entries: "+timeSheetReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getAllTimeSheetsReport",e);
		}
		return timeSheetReport;
	}*/

	
}
