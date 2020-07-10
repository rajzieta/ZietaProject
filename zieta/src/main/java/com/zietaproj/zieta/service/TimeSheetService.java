package com.zietaproj.zieta.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.response.TSInfoModel;

public interface TimeSheetService {

	public List<TSInfoModel> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate);
	
	public void addTimeEntry(@Valid List<TSInfo> tsinfo);
}
