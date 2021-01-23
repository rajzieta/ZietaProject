package com.zieta.tms.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.zieta.tms.model.LeaveInfo;

public interface LeaveReportService {
	
	public List<LeaveInfo> getLeaveData(Long userId, Date startDate, Date endDate);
	
	public Page<LeaveInfo> getLeaveData(Long userId, Date startDate, Date endDate, Integer pageNo, Integer pageSize);
	
	

}
