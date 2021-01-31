package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.ExpenseDetailsReport;
import com.zieta.tms.model.ExpenseSummaryReport;
import com.zieta.tms.reports.ExpenseReportHelper;
import com.zieta.tms.repository.ExpenseDetailsRepository;
import com.zieta.tms.repository.ExpenseSummaryReportRepository;
import com.zieta.tms.service.ExpenseReportService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExpenseReportServiceImpl implements ExpenseReportService {
    @Autowired
	ExpenseDetailsRepository expenseDetailsRepository;
    
    @Autowired
   	ExpenseSummaryReportRepository expenseSummaryReportRepository;
    
    @Autowired
    ExpenseReportHelper expenseReportHelper;
    
    @Autowired
	ModelMapper modelMapper;
    
	@Override
	public Page<ExpenseDetailsReport> getExpenseDetailsReport(long clientId, String startDate, String endDate, Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		List<ExpenseDetailsReport> expenseDetailsList = getExpenseDetailsReport(clientId, startDate, endDate);
		int start =(int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > expenseDetailsList.size() ? expenseDetailsList.size() : (start + pageable.getPageSize());

		Page<ExpenseDetailsReport> page = new PageImpl<ExpenseDetailsReport>(expenseDetailsList.subList(start, end), pageable, expenseDetailsList.size());
		
		
		return page;
	}
	
	
	private List<ExpenseDetailsReport> getExpenseDetailsReport(long client_id, String startDate, String endDate) {
		
		List<ExpenseDetailsReport> expenseDetailsList = null;
		try {
			expenseDetailsList = expenseDetailsRepository.getExpenseDetailsReport(client_id, startDate, endDate);
		} catch (Exception e) {
			log.error("Exception occured while fetching ExpenseDetailsReport: ",e);
		}
		
		return expenseDetailsList;
	}
	
	 @Override
	 public Page<ExpenseSummaryReport> getExpenseSummaryReport(long clientId, String startDate, String endDate, Integer pageNo,
			Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		List<ExpenseSummaryReport> expenseSummaryList = getExpenseSummaryReportData(clientId, startDate, endDate);
		int start =(int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > expenseSummaryList.size() ? expenseSummaryList.size() : (start + pageable.getPageSize());

		Page<ExpenseSummaryReport> page = new PageImpl<ExpenseSummaryReport>(expenseSummaryList.subList(start, end), pageable, expenseSummaryList.size());
		
		
		return page;
	}
	
	
	private List<ExpenseSummaryReport> getExpenseSummaryReportData(long client_id, String startDate, String endDate) {
		
		List<ExpenseSummaryReport> expenseSummaryData = null;
		try {
			expenseSummaryData = expenseSummaryReportRepository.getExpenseSummaryReport(client_id, startDate, endDate);
		} catch (Exception e) {
			log.error("Exception occured while fetching ExpenseSummaryReport: ",e);
		}
		
		return expenseSummaryData;
	}
	
	@Override
	public ByteArrayInputStream downloadExpenseDetailsReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException {
		
		List<ExpenseDetailsReport> expenseDetailsReportList = getExpenseDetailsReport(clientId, startDate, endDate);
		return expenseReportHelper.downloadReport(response, expenseDetailsReportList);
		
	}

	
	
	@Override
	public ByteArrayInputStream downloadExpenseSummaryReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException {
		
		List<ExpenseSummaryReport> expenseSummaryList = getExpenseSummaryReportData(clientId, startDate, endDate);
		return expenseReportHelper.downloadSumReport(response, expenseSummaryList);
		
	}

	
	
}

