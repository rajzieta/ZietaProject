package com.zietaproj.zieta.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.service.TimeSheetService;

@Service
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {
	
	@Autowired
	TSInfoRepository tSInfoRepository;

	/**
	 *  This methods returns ts_info entries based on the date range of ts_date column 
	 *  and filters based on the userid and clientid
	 */
	@Override
	public List<TSInfo> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate) {
		
		List<TSInfo> tsInfoList = tSInfoRepository.findByClientIdAndUserIdAndTsDateBetween(clientId, userId, startDate, endDate);
		
		return tsInfoList;
	}
	
	
	public void addTimeEntry(@Valid List<TSInfo> tsinfo) {
		
		tSInfoRepository.saveAll(tsinfo);
	}
	

}
