package com.zieta.tms.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.WorkflowDTO;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.UpdateTimesheetByIdRequest;
import com.zieta.tms.response.TSInfoModel;
import com.zieta.tms.response.TimeEntriesByTimesheetIDResponse;

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

	public boolean submitTimeSheet(@Valid List<TSInfo> tsInfoList);

	public void deleteTimeEntriesById(Long id, String modifiedBy) throws Exception;

	public void deleteTsInfoById(Long id, String modifiedBy) throws Exception;
}
