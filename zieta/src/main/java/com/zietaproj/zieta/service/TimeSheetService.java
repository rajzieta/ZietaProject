package com.zietaproj.zieta.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.request.TimeEntriesByTsIdRequest;
import com.zietaproj.zieta.response.TSInfoModel;
import com.zietaproj.zieta.response.TimeEntriesByTimesheetIDResponse;

public interface TimeSheetService {

	public List<TSInfoModel> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate);
	
	public List<TSInfo> addTimeSheet(@Valid List<TSInfo> tsinfo);

	public List<TimeEntriesByTimesheetIDResponse> getTimeEntriesByTsID(Long tsId);

	public void addTimeEntriesByTsId(@Valid TimeEntriesByTsIdRequest timeentriesbytsidRequest) throws Exception;
}
