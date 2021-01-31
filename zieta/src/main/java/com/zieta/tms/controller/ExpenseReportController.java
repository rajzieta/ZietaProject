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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.model.ExpenseDetailsReport;
import com.zieta.tms.model.ExpenseSummaryReport;
import com.zieta.tms.service.ExpenseReportService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "Expense reports  API")
public class ExpenseReportController {

	@Autowired
	ExpenseReportService expenseReportService;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseReportController.class);

	 /**
	  * Provides expense details reports with pagination
	  * @param clientId
	  * @param startDate
	  * @param endDate
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	@RequestMapping(value = "getExpesneDetailsReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ExpenseDetailsReport> getExpenseDetailsReport(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<ExpenseDetailsReport> expenseDetailsReport = null;
		try {
			expenseDetailsReport = expenseReportService.getExpenseDetailsReport(clientId, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of expense details entries: " + expenseDetailsReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getExpesneDetailsReport", e);
		}
		return expenseDetailsReport;
	}
	
	/**
	 * This API allows to download the expense details report
	 * @param response
	 * @param clientId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws IOException
	 */
	
	@GetMapping("/download/expensedetailreport")
	public ResponseEntity<Resource> downloadExpenseDetailsReport(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) throws IOException {

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "expense_details_report_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = expenseReportService.downloadExpenseDetailsReport(response, clientId, startDate, endDate);
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}

	
	@RequestMapping(value = "getExpesneSummarReport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ExpenseSummaryReport> getExpesneSummarReport(@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		Page<ExpenseSummaryReport> expenseDetailsReport = null;
		try {
			expenseDetailsReport = expenseReportService.getExpenseSummaryReport(clientId, startDate, endDate, pageNo, pageSize);
			LOGGER.info("Total number of ExpesneSummarReport entries: " + expenseDetailsReport.getSize());
		} catch (Exception e) {
			LOGGER.error("Error Occured in getExpesneSummarReport", e);
		}
		return expenseDetailsReport;
	}
	
	@GetMapping("/download/expensesummaryreport")
	public ResponseEntity<Resource> downloadExpenseSummaryReport(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate) throws IOException {

		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "expense_summary_report_" + currentDateTime + ".xlsx";
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        ByteArrayInputStream bri = expenseReportService.downloadExpenseSummaryReport(response, clientId, startDate, endDate);
        InputStreamResource file = new InputStreamResource(bri);
        
		return ResponseEntity.ok().headers(header).body(file);
	}
	
	
	

	
	
	
}
