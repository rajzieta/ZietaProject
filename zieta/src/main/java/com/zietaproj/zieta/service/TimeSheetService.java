package com.zietaproj.zieta.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.WorkflowDTO;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.request.TimeEntriesByTsIdRequest;
import com.zietaproj.zieta.request.UpdateTimesheetByIdRequest;
import com.zietaproj.zieta.response.TSInfoModel;
import com.zietaproj.zieta.response.TimeEntriesByTimesheetIDResponse;

public interface TimeSheetService {

	public List<TSInfoModel> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate);

	public List<TSInfo> addTimeSheet(@Valid List<TSInfo> tsinfo);

	public List<TimeEntriesByTimesheetIDResponse> getTimeEntriesByTsID(Long tsId);

	public void addTimeEntriesByTsId(@Valid List<TimeEntriesByTsIdRequest> timeentriesbytsidRequest) throws Exception;

	public void updateTimeSheetById(@Valid UpdateTimesheetByIdRequest updatetimesheetRequest) throws Exception;

	public void updateTimeSheetByIds(@Valid List<UpdateTimesheetByIdRequest> updatetimesheetRequest) throws Exception;

	public List<WorkflowDTO> getAllWorkflowDetails();

	public void updateTimeEntriesByID(@Valid TimeEntriesByTsIdRequest timeentriesByTsIdRequest) throws Exception;

	public void updateTimeEntriesByIds(List<TimeEntriesByTsIdRequest> timeentriesByTsIdRequest) throws Exception;

	public void processWorkFlow(Long id, short actionType, String comments);
	
	public boolean submitTimeSheet(@Valid List<TSInfo> tsInfoList);
}
