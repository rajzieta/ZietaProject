package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zieta.tms.dto.DateRange;
import com.zieta.tms.dto.LeaveReportDTO;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.reports.LeaveReportHelper;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.LeaveInfoRepository;
import com.zieta.tms.repository.LeaveMasterRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.service.LeaveReportService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LeaveReportServiceImpl implements LeaveReportService {

	@Autowired
	LeaveInfoRepository leaveInfoRepository;
	
	@Autowired
	LeaveReportHelper leaveReportHelper;
	
	@Autowired
	ClientInfoRepository clientInfoRepo;
	
	@Autowired
	UserInfoRepository userInfoRepo;
	
	@Autowired
	LeaveMasterRepository leaveMasterRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	

	@Override
	public List<LeaveInfo> getLeaveData(Long clientId, Date startDate, Date endDate) {

		List<LeaveInfo> leaveInfo = null;
		try {
			DateRange dateRange = TSMUtil.getFilledDateRange(startDate, endDate, true);
			short notDeleted = 0;

			leaveInfo = leaveInfoRepository.findByClientIdAndIsDeleteAndLeaveStartDateBetween(clientId, notDeleted,
					dateRange.getStartDate(), dateRange.getEndDate());
		} catch (Exception e) {

			log.info("Exception occured while fetching leaveInfo", e);
		}
		return leaveInfo;

	}
	
	@Override
	public ByteArrayInputStream  getDownloadableLeaveReport(HttpServletResponse response, Long clientId, Date startDate,
			Date endDate) {
		
		ByteArrayInputStream reportStream = null;

		try {
			List<LeaveInfo> leaveInfoDetails = getLeaveData(clientId, startDate, endDate);
			List<LeaveReportDTO> leaveReportDTOList = transformLeaveData(leaveInfoDetails);

			reportStream = leaveReportHelper.downloadReport(response, leaveReportDTOList);
		} catch (Exception ex) {
			log.error("Exception occured while construction the report !!", ex);
		}
		
		return reportStream;

	}

	private List<LeaveReportDTO> transformLeaveData(List<LeaveInfo> leaveInfoDetails) {
		List<LeaveReportDTO> leaveReportDTOList = new ArrayList<>();
		
		LeaveReportDTO leaveReportDTO = null;

		for (LeaveInfo leaveInfo : leaveInfoDetails) {
			try {

				leaveReportDTO = new LeaveReportDTO();
				String clientName = clientInfoRepo.findById(leaveInfo.getClientId()).get().getClientName();
				leaveReportDTO.setClientName(clientName);
				String userName = TSMUtil.getFullName(userInfoRepo.findById(leaveInfo.getUserId()).get());
				leaveReportDTO.setUserName(userName);
				leaveReportDTO.setLeaveDesc(leaveInfo.getLeaveDesc());
				String leaveType = leaveMasterRepository.findById(leaveInfo.getLeaveType()).get().getLeaveType();
				leaveReportDTO.setLeaveType(leaveType);
				leaveReportDTO.setLeaveStartDate(TSMUtil.getFormatteDate(leaveInfo.getLeaveStartDate(),null));
				leaveReportDTO.setStartSession(leaveInfo.getStartSession().toString());
				leaveReportDTO.setLeaveEndDate(TSMUtil.getFormatteDate(leaveInfo.getLeaveEndDate(),null));
				leaveReportDTO.setEndSession(leaveInfo.getEndSession().toString());
				String approverName = TSMUtil.getFullName(userInfoRepo.findById(leaveInfo.getApproverId()).get());
				leaveReportDTO.setApproverName(approverName);
				leaveReportDTO.setApproverComments(leaveInfo.getApproverComments());
				String status = statusMasterRepository.findById(leaveInfo.getStatusId()).get().getStatusDesc();
				leaveReportDTO.setStatus(status);
				leaveReportDTOList.add(leaveReportDTO);
			}catch(Exception e) {
				log.error("Exception occured while tranforming the leave data",e);
			}
		}
		return leaveReportDTOList;
	}

	@Override
	public List<LeaveReportDTO> getLeaveData(Long clientId, Date startDate, Date endDate, Integer pageNo, Integer pageSize) {

		Page<LeaveInfo> leaveInfo = null;
		List<LeaveInfo> leaveInfoList = null;
		try {
			DateRange dateRange = TSMUtil.getFilledDateRange(startDate, endDate, true);
			short notDeleted = 0;
			Pageable paging = PageRequest.of(pageNo, pageSize);

			leaveInfo = leaveInfoRepository.findByClientIdAndIsDeleteAndLeaveStartDateBetween(clientId, notDeleted,
					dateRange.getStartDate(), dateRange.getEndDate(), paging);
			
			leaveInfoList = leaveInfo.getContent();
			
		} catch (Exception e) {

			log.info("Exception occured while fetching leaveInfo", e);
		}
		return transformLeaveData(leaveInfoList);
	}

}
