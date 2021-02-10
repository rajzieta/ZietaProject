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

import com.zieta.tms.dto.LeaveReportDTO;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.service.LeaveReportService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "Leave reports  API")
public class LeaveReportController {

	@Autowired
	LeaveReportService leaveReportService;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(LeaveReportController.class);

	@RequestMapping(value = "getLeaveReports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LeaveReportDTO> getReports(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) {
		List<LeaveReportDTO> leaveReport = null;
		try {
			leaveReport = leaveReportService.getLeaveData(clientId, startDate, endDate);
			LOGGER.info("Total number of timesheet entries: " + leaveReport.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getLeaveReports", e);
		}
		return leaveReport;
	}
	
	@GetMapping("/download/leaveReport")
	public ResponseEntity<Resource> downloadLeaveReport(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) throws IOException {

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "leave_report_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = leaveReportService.getDownloadableLeaveReport(response, clientId, startDate, endDate);
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}

	
	
	

	
	
	
}
