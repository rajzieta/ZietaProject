package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<ExpenseDetailsReport> getExpenseDetailsReport(long clientId, String startDate, String endDate) {
		
		
		List<ExpenseDetailsReport> expenseDetailsList = null;
		try {
			expenseDetailsList = expenseDetailsRepository.getExpenseDetailsReport(clientId, startDate, endDate);
		} catch (Exception e) {
			log.error("Exception occured while fetching ExpenseDetailsReport: ",e);
		}
		
		return expenseDetailsList;
	}
	
	
	 @Override
	 public List<ExpenseSummaryReport> getExpenseSummaryReport(long clientId, String startDate, String endDate) {
		
		
		 List<ExpenseSummaryReport> expenseSummaryData = null;
			try {
				expenseSummaryData = expenseSummaryReportRepository.getExpenseSummaryReport(clientId, startDate, endDate);
			} catch (Exception e) {
				log.error("Exception occured while fetching ExpenseSummaryReport: ",e);
			}
			
			return expenseSummaryData;
	}
	
	
	
	@Override
	public ByteArrayInputStream downloadExpenseDetailsReport(HttpServletResponse response,long clientId,
			String startDate, String endDate, String empId) throws IOException {

		List<ExpenseDetailsReport> expenseDetailsReportList = getExpenseDetailsReport(clientId, startDate, endDate);
		if (empId != null) {
			expenseDetailsReportList = expenseDetailsReportList.stream()
					.filter(criteria -> criteria.getEmp_id().equals(empId)).collect(Collectors.toList());
		}

		return expenseReportHelper.downloadReport(response, expenseDetailsReportList);

	}

	
	
	@Override
	public ByteArrayInputStream downloadExpenseSummaryReport(HttpServletResponse response,long clientId,
			String startDate, String endDate,  String empId) throws IOException {
		
		List<ExpenseSummaryReport> expenseSummaryList = getExpenseSummaryReport(clientId, startDate, endDate);
		if (empId != null) {
			expenseSummaryList = expenseSummaryList.stream()
					.filter(criteria -> criteria.getEmp_id().equals(empId)).collect(Collectors.toList());
		}
		return expenseReportHelper.downloadSumReport(response, expenseSummaryList);
		
	}

	
	
}

