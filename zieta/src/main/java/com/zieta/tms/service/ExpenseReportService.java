package com.zieta.tms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.zieta.tms.model.ExpenseDetailsReport;
import com.zieta.tms.model.ExpenseSummaryReport;

@Transactional
public interface ExpenseReportService {

	Page<ExpenseDetailsReport> getExpenseDetailsReport(long clientId, String startDate, String endDate, Integer pageNo, Integer pageSize);
	
	public ByteArrayInputStream downloadExpenseDetailsReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException ;
	


	Page<ExpenseSummaryReport> getExpenseSummaryReport(long clientId, String startDate, String endDate, Integer pageNo,
			Integer pageSize);
	
	public ByteArrayInputStream downloadExpenseSummaryReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException ;

	
}
