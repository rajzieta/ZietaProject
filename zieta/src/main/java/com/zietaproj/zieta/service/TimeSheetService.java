package com.zietaproj.zieta.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.model.TSInfo;

public interface TimeSheetService {

	public List<TSInfo> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate);
	
	public void addTimeEntry(@Valid List<TSInfo> tsinfo);
}
