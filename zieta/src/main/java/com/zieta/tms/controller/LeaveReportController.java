package com.zieta.tms.controller;

import java.util.Date;

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

import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.model.TimeSheetReport;
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
	public Page<LeaveInfo> getReports(@RequestParam Long userId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<LeaveInfo> leaveReport = null;
		try {
			leaveReport = leaveReportService.getLeaveData(userId, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of timesheet entries: " + leaveReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getLeaveReports", e);
		}
		return leaveReport;
	}
	
	/*@GetMapping("/download/timesheetReport")
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
	}*/

	
	
	

	
	
	
}
