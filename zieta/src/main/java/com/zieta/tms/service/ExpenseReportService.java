package com.zieta.tms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.zieta.tms.model.ExpenseDetailsReport;
import com.zieta.tms.model.ExpenseSummaryReport;

@Transactional
public interface ExpenseReportService {

	List<ExpenseDetailsReport> getExpenseDetailsReport(long clientId, String startDate, String endDate);
	
	public ByteArrayInputStream downloadExpenseDetailsReport(HttpServletResponse response,long clientId,
			String startDate, String endDate, String empId) throws IOException ;
	


	List<ExpenseSummaryReport> getExpenseSummaryReport(long clientId, String startDate, String endDate);
	
	public ByteArrayInputStream downloadExpenseSummaryReport(HttpServletResponse response,long clientId,
			String startDate, String endDate, String empId) throws IOException ;

	
}
