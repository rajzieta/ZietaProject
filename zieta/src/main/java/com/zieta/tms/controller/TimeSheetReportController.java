package com.zieta.tms.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.TimeSheetReportDTO;
import com.zieta.tms.model.ProjectReport;
import com.zieta.tms.model.TimeSheetReport;
import com.zieta.tms.service.TSReportService;
import com.zieta.tms.service.TimeSheetReportService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeSheet reports  API")
public class TimeSheetReportController {

	@Autowired
	TimeSheetReportService timeSheetReportService;
	
	@Autowired
	TSReportService tsReportService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetReportController.class);

	@RequestMapping(value = "getReports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TimeSheetReport> getReports(@RequestParam Long clientId,@RequestParam(defaultValue = "0", required = false) Long projectId,
			@RequestParam(required = false) String empId, @RequestParam(required = false) String stateName,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<TimeSheetReport> timeSheetReport = null;
		try {
			timeSheetReport = timeSheetReportService.findAll(
					clientId, projectId, empId, stateName, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of timesheet entries: " + timeSheetReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getByClientIdAndProjectIdAndStateNameAndRequestDateBetween", e);
		}
		return timeSheetReport;
	}
	
	@GetMapping("/download/timesheetReport")
	public ResponseEntity<Resource> downloadTimeSheet(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(defaultValue = "0", required = false) Long projectId, 
			@RequestParam(required = false) String stateName,
			@RequestParam(required = false) String empId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) throws IOException {

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "timesheet_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = timeSheetReportService.downloadTimeSheetReport(
				 response, clientId, projectId, stateName, empId, startDate, endDate);
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}

	
	
	
	@RequestMapping(value = "getProjReports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ProjectReport> getProjReports(@RequestParam Long clientId,@RequestParam(defaultValue = "0", required = false) Long projectId,
			@RequestParam(required = false) String empId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<ProjectReport> projectReport = null;
		try {
			projectReport = timeSheetReportService.findAll(
					clientId, projectId, empId, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of projectReport entries: " + projectReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getByClientIdAndProjectIdAndEmpId", e);
		}
		return projectReport;
	}
	
	
	
	
	@GetMapping("/download/projectReport")
	public ResponseEntity<Resource> downloadProject(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(defaultValue = "0", required = false) Long projectId, 
			//@RequestParam(required = false) String stateName,
			@RequestParam(required = false) String empId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) throws IOException {

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "projectReport_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = timeSheetReportService.downloadProjectReport(
				 response, clientId, projectId, empId, startDate, endDate);
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}
	
	
	// Reports from Stored procedure
	//TODO need to check with Santosh on the clearence of previous reports based on the "views"
	
	@RequestMapping(value = "getTsByDateRange", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TimeSheetReportDTO> getTsByDateRange(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<TimeSheetReportDTO> tsReport = null;
		try {
			tsReport = tsReportService.getTsByDateRange(clientId, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of TSReport entries: " + tsReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getTsByDateRange", e);
		}
		return tsReport;
	}
	
	@GetMapping("/download/timesheetReportSP")
	public ResponseEntity<Resource> downloadTimeSheetReportSP(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate){
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "timesheet_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = null;
		try {
			bri = tsReportService.downloadTimeSheetReport(
					 response, clientId,startDate, endDate);
		} catch (IOException e) {
			LOGGER.error("Exception occured while downloading the report",e);
		}
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}
	
}
