package com.zieta.tms.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;

import com.zieta.tms.dto.LeaveReportDTO;
import com.zieta.tms.model.LeaveInfo;

public interface LeaveReportService {
	
	public ByteArrayInputStream getDownloadableLeaveReport(HttpServletResponse response, Long clientId, String startDate, String endDate);
	
	
	public List<LeaveReportDTO> getLeaveData(Long clientId, String startDate, String endDate);
	
	

}
