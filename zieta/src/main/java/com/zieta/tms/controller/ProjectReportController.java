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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.model.ExpenseSummaryReport;
import com.zieta.tms.model.ProjectDetailsReport;
import com.zieta.tms.model.ProjectSummaryReport;
import com.zieta.tms.service.ProjectReportService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "Project reports  API")
public class ProjectReportController {

	@Autowired
	ProjectReportService projectReportService;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectReportController.class);

	 /**
	  * Provides project details reports with pagination
	  * @param clientId
	  * @param startDate
	  * @param endDate
	 
	  * @return
	  */
	@RequestMapping(value = "getProjectDetailsReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProjectDetailsReport> getProjectDetailsReport(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate
			) {
		List<ProjectDetailsReport> projectDetailsReport = null;
		try {
			projectDetailsReport = projectReportService.getProjectDetailsReport(clientId, startDate, endDate);
			LOGGER.info("Total number of project details entries: " + projectDetailsReport.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getProjectDetailsReport", e);
		}
		return projectDetailsReport;
	}
	
	/**
	 * This API allows to download the project details report
	 * @param response
	 * @param clientId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws IOException
	 */
	
	@GetMapping("/download/projectDetailsReport")
	public ResponseEntity<Resource> downloadProjectDetailsReport(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) throws IOException {

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "project_details_report_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = projectReportService.downloadProjectDetailsReport(response, clientId, startDate, endDate);
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}

	
	/**
	 *  This API allows to provide the project summary report from the procedure SP_proj_summary
	 * @param clientId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	
	@RequestMapping(value = "getProjectSummaryReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProjectSummaryReport> getProjectSummaryReport(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate
			) {
		List<ProjectSummaryReport> projectSummaryReportList = null;
		try {
			projectSummaryReportList = projectReportService.getProjectSummaryReport(clientId, startDate, endDate);
			LOGGER.info("Total number of project summary entries: " + projectSummaryReportList.size());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getProjectSummaryReport", e);
		}
		return projectSummaryReportList;
	}
	
	/**
	 * This API allows to download the project summary report
	 * @param response
	 * @param clientId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws IOException
	 */
	
	@GetMapping("/download/projectSummaryReport")
	public ResponseEntity<Resource> downloadProjectSummaryReport(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) throws IOException {

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "project_summary_report_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = projectReportService.downloadProjectSummaryReport(response, clientId, startDate, endDate);
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}
	
}
