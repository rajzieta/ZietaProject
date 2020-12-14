package com.zieta.tms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.zieta.tms.dto.TimeSheetReportDTO;

@Transactional
public interface TSReportService {

	Page<TimeSheetReportDTO> getTsByDateRange(long client_id, String startDate, String endDate, Integer pageNo, Integer pageSize);
	
	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response,long clientId,
			String startDate, String endDate) throws IOException ;
	
	public List<TimeSheetReportDTO> getTSReportEntriesFromProcedure(long client_id, String startDate, String endDate);
}
