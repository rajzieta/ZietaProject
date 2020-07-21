package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.repository.TSTimeEntriesRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.request.TimeEntriesByTsIdRequest;
import com.zietaproj.zieta.request.UpdateTaskInfoRequest;
import com.zietaproj.zieta.request.UpdateTimesheetByIdRequest;
import com.zietaproj.zieta.response.TSInfoModel;
import com.zietaproj.zieta.response.TimeEntriesByTimesheetIDResponse;
import com.zietaproj.zieta.service.TimeSheetService;

@Service
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {
	
	@Autowired
	TSInfoRepository tSInfoRepository;
	
	@Autowired
	ActivityMasterRepository  activityMasterRepository;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ProjectInfoRepository  projectInfoRepository;
	
	@Autowired
	TSTimeEntriesRepository tstimeentriesRepository;
	
	@Autowired
	ClientInfoRepository  clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;

	/**
	 *  This methods returns ts_info entries based on the date range of ts_date column 
	 *  and filters based on the userid and clientid
	 */
	@Override
	public List<TSInfoModel> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate) {
		short notDeleted=0;
		List<TSInfoModel> tsInfoModelList = new ArrayList<TSInfoModel>();
		List<TSInfo> tsInfoList = tSInfoRepository.findByClientIdAndUserIdAndIsDeleteAndTsDateBetweenOrderByTaskActivityIdAscIdAsc(clientId, userId, notDeleted, startDate, endDate);
		for (TSInfo tsInfo : tsInfoList) {
			TSInfoModel taskInfoModel = new TSInfoModel();
			taskInfoModel.setTsInfo(tsInfo);
			if(tsInfo.getActivityId() != null && tsInfo.getActivityId() !=0) {
				ActivityMaster activityEntity = activityMasterRepository.findById(tsInfo.getActivityId()).get();
				taskInfoModel.setActivityCode(activityEntity.getActivityCode());
				taskInfoModel.setActivityDescription(activityEntity.getActivityDesc());
			}else {
				taskInfoModel.setActivityCode(null);
				taskInfoModel.setActivityDescription(StringUtils.EMPTY);
			}
			
			if(tsInfo.getProjectId() !=null && tsInfo.getProjectId() !=0) {
				taskInfoModel.setProjectCode(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectCode());
			}else {
				taskInfoModel.setProjectCode(null);
			}
			
			if(tsInfo.getTaskId() !=null && tsInfo.getTaskId() !=0) {
				TaskInfo taskInfoEntity =taskInfoRepository.findById(tsInfo.getTaskId()).get();
				taskInfoModel.setTaskCode(taskInfoEntity.getTaskCode());
				taskInfoModel.setTaskDescription(taskInfoEntity.getTaskDescription());
			}else {
				taskInfoModel.setTaskCode(null);
				taskInfoModel.setTaskDescription(StringUtils.EMPTY);
			}
			
			taskInfoModel.setClientCode(clientInfoRepository.findById(tsInfo.getClientId()).get().getClient_code());
			taskInfoModel.setClientDescription(clientInfoRepository.findById(tsInfo.getClientId()).get().getClient_name());
			
			tsInfoModelList.add(taskInfoModel);
			
		}

		return tsInfoModelList;
	}
	
	
	public List<TSInfo> addTimeSheet(@Valid List<TSInfo> tsinfo) {
		
		 List<TSInfo> tsinfoEntites = tSInfoRepository.saveAll(tsinfo);
		 
		 return tsinfoEntites;
	}
	
	
	@Override
	public List<TimeEntriesByTimesheetIDResponse> getTimeEntriesByTsID(Long tsId) {
		short notDeleted=0;
		List<TSTimeEntries> timeentriesByTsidList = tstimeentriesRepository.findByTsIdAndIsDelete(tsId, notDeleted);
		List<TimeEntriesByTimesheetIDResponse> timeentriesBytsIdResponseList = new ArrayList<>();
		TimeEntriesByTimesheetIDResponse timeentriesByTsIdResponse = null;
		for (TSTimeEntries timeentriesByTsId : timeentriesByTsidList) {
			timeentriesByTsIdResponse = modelMapper.map(timeentriesByTsId, 
					TimeEntriesByTimesheetIDResponse.class);
			timeentriesBytsIdResponseList.add(timeentriesByTsIdResponse);
		}

		return timeentriesBytsIdResponseList;
		
		
	}
	
	@Override
	@Transactional
	public void addTimeEntriesByTsId(@Valid List<TimeEntriesByTsIdRequest> timeentriesbytsidRequestList) throws Exception {
			for (TimeEntriesByTsIdRequest timeEntriesByTsIdRequest : timeentriesbytsidRequestList) {
				TSTimeEntries tstimeentries = modelMapper.map(timeEntriesByTsIdRequest, TSTimeEntries.class);
				tstimeentriesRepository.save(tstimeentries);
			}

	}
	
	@Override
	public void updateTimeSheetById(@Valid UpdateTimesheetByIdRequest updatetimesheetRequest) throws Exception {
		//for (UpdateTimesheetByIdRequest updateRequest : updatetimesheetRequest) {
		Optional<TSInfo> TsInfoEntity = tSInfoRepository.findById(updatetimesheetRequest.getId());
		if(TsInfoEntity.isPresent()) {
			TSInfo tsInfoSave = TsInfoEntity.get();
			TSInfo tsinfo = modelMapper.map(updatetimesheetRequest, TSInfo.class);
			
			tSInfoRepository.save(tsinfo);
		}
	
		else {
			throw new Exception("Timesheet not found with the provided ID : "+updatetimesheetRequest.getId());
		}
	}
	
	
	@Override
	@Transactional
	public void updateTimeSheetByIds(@Valid List<UpdateTimesheetByIdRequest> updatetimesheetRequest) throws Exception {
		
		for (UpdateTimesheetByIdRequest updateRequest : updatetimesheetRequest) {
			updateTimeSheetById(updateRequest);
		}
	}
	
}
