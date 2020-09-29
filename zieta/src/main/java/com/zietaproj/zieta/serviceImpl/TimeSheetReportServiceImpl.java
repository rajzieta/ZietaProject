package com.zietaproj.zieta.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.TimeSheetReport;
import com.zietaproj.zieta.repository.TimeSheetReportRepository;
import com.zietaproj.zieta.service.TimeSheetReportService;


@Service
public class TimeSheetReportServiceImpl implements TimeSheetReportService {
	
	@Autowired
	private TimeSheetReportRepository timeSheetReportRepository;

	@Override
	public Page<TimeSheetReport> getAllTimeSheets(Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<TimeSheetReport> timeSheetList =  timeSheetReportRepository.findAll(pageable);
		return timeSheetList;
	}
	
}
