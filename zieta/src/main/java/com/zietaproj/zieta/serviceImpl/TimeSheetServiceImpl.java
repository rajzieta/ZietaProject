package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.response.TSInfoModel;
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
	
	

	/**
	 *  This methods returns ts_info entries based on the date range of ts_date column 
	 *  and filters based on the userid and clientid
	 */
	@Override
	public List<TSInfoModel> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate) {
		List<TSInfoModel> tsInfoModelList = new ArrayList<TSInfoModel>();
		List<TSInfo> tsInfoList = tSInfoRepository
				.findByClientIdAndUserIdAndTsDateBetweenOrderByTaskActivityIdAscTsInfoIdAsc(clientId, userId, startDate,
						endDate);
		for (TSInfo tsInfo : tsInfoList) {
			TSInfoModel taskInfoModel = new TSInfoModel();
			taskInfoModel.setTsInfo(tsInfo);
			if(tsInfo.getActivityId() != null && tsInfo.getActivityId() !=0) {
				taskInfoModel.setActivityCode(activityMasterRepository.findById(tsInfo.getActivityId()).get().getActivityCode());
			}else {
				taskInfoModel.setActivityCode(null);
			}
			
			if(tsInfo.getProjectId() !=null && tsInfo.getProjectId() !=0) {
				taskInfoModel.setProjectCode(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectCode());
			}else {
				taskInfoModel.setProjectCode(null);
			}
			
			if(tsInfo.getTaskId() !=null && tsInfo.getTaskId() !=0) {
				taskInfoModel.setTaskCode(taskInfoRepository.findById(tsInfo.getTaskId()).get().getTaskCode());
			}else {
				taskInfoModel.setTaskCode(null);
			}
			
			tsInfoModelList.add(taskInfoModel);
			
		}

		return tsInfoModelList;
	}
	
	
	public void addTimeEntry(@Valid List<TSInfo> tsinfo) {
		
		tSInfoRepository.saveAll(tsinfo);
	}
	

}
