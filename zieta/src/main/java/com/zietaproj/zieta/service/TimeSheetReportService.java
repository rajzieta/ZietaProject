package com.zietaproj.zieta.service;

import org.springframework.data.domain.Page;

import com.zietaproj.zieta.model.TimeSheetReport;

public interface TimeSheetReportService {

	public Page<TimeSheetReport> getAllTimeSheets(Integer pageNo, Integer pageSize);

}
