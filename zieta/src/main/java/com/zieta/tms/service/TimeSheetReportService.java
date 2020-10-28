package com.zieta.tms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.zieta.tms.model.ProjectReport;
import com.zieta.tms.model.TimeSheetReport;

@Transactional
public interface TimeSheetReportService {

	public Page<TimeSheetReport> findAll(long clientId, long projectId, String empId, String stateName, Date startDate,
			Date endDate, Integer pageNo, Integer pageSize);

	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response, long clientId, long projectId,
			String stateName, String empId, Date startDate, Date endDate) throws IOException;

	public Page<ProjectReport> findAll(long clientId, long projectId, String empId, Integer pageNo, Integer pageSize);


}
