package com.zieta.tms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.zieta.tms.model.TSReport;
import com.zieta.tms.model.TSSumReport;

@Transactional
public interface TSReportService {

	
	public List<TSReport> getTSReportEntriesFromProcedure(long clientId, String startDate, String endDate);
	
	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException ;
	

	public List<TSSumReport> getTSReportSumEntriesFromProcedure(long clientId, String startDate, String endDate);

	public ByteArrayInputStream downloadTimeSheetSumReport(HttpServletResponse response, long clientId, String startDate,
			String endDate) throws IOException;

}
