package com.zieta.tms.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zieta.tms.dto.DateRange;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.repository.LeaveInfoRepository;
import com.zieta.tms.service.LeaveReportService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LeaveReportServiceImpl implements LeaveReportService {
	
	@Autowired
	LeaveInfoRepository leaveInfoRepository;

	@Override
	public List<LeaveInfo> getLeaveData(Long userId, Date startDate, Date endDate) {
		
		List<LeaveInfo> leaveInfo = null;
		try {
			DateRange dateRange = TSMUtil.getFilledDateRange(startDate, endDate, true);
			short notDeleted = 0;

			leaveInfo = leaveInfoRepository.findByUserIdAndIsDeleteAndLeaveStartDateBetween(userId,
					notDeleted, dateRange.getStartDate(), dateRange.getEndDate());
		} catch (Exception e) {
			
			log.info("Exception occured while fetching leaveInfo",e);
		}
		return leaveInfo;

	}

	@Override
	public Page<LeaveInfo> getLeaveData(Long userId, Date startDate, Date endDate, Integer pageNo, Integer pageSize) {
		
		Page<LeaveInfo> leaveInfo = null;
		try {
			DateRange dateRange = TSMUtil.getFilledDateRange(startDate, endDate, true);
			short notDeleted = 0;
			Pageable paging = PageRequest.of(pageNo, pageSize);

			leaveInfo = leaveInfoRepository.findByUserIdAndIsDeleteAndLeaveStartDateBetween(userId,
					notDeleted, dateRange.getStartDate(), dateRange.getEndDate(),paging);
		} catch (Exception e) {
			
			log.info("Exception occured while fetching leaveInfo",e);
		}
		return leaveInfo;
	}

}
