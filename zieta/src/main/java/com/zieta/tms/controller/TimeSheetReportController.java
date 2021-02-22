package com.zieta.tms.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.model.TSReport;
import com.zieta.tms.model.TSSumReport;
import com.zieta.tms.service.TSReportService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeSheet reports  API")
public class TimeSheetReportController {

	
	@Autowired
	TSReportService tsReportService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetReportController.class);

	@RequestMapping(value = "getTimeSheetDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TSReport> getTimeSheetDetails(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) {
		List<TSReport> tsReport = null;
		try {
			tsReport = tsReportService.getTSReportEntriesFromProcedure(clientId, startDate, endDate);
			LOGGER.info("Total number of TSReport entries: " + tsReport.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getTimeSheetDetails", e);
		}
		return tsReport;
	}
	
	@RequestMapping(value = "getTimeSheetSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TSSumReport> getTimeSheetSummary(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) {
		List<TSSumReport> tsReport = null;
		try {
			tsReport = tsReportService.getTSReportSumEntriesFromProcedure(clientId, startDate, endDate);
			LOGGER.info("Total number of TSReport entries: " + tsReport.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getTimeSheetSummary", e);
		}
		return tsReport;
	}
	
	
	
	@GetMapping("/download/timesheetDetailsReport")
	public ResponseEntity<Resource> downloadTimeSheetReportDetails(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String teamId, 
			@RequestParam(required = false) String empId){
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "timesheet_detailed_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = null;
		try {
			bri = tsReportService.downloadTimeSheetReport(
					 response, clientId,startDate, endDate, projectId, teamId, empId);
		} catch (IOException e) {
			LOGGER.error("Exception occured while downloading the report",e);
		}
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}
	
	
	@GetMapping("/download/timesheetSummaryReport")
	public ResponseEntity<Resource> downloadTimeSheetSummaryReport(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String teamId, 
			@RequestParam(required = false) String empId){
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "timesheet_summary_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = null;
		try {
			bri = tsReportService.downloadTimeSheetSumReport(
					 response, clientId,startDate, endDate, projectId, teamId, empId);
		} catch (IOException e) {
			LOGGER.error("Exception occured while downloading the report",e);
		}
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}

	
	
	
}
