package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public ByteArrayInputStream  getDownloadableLeaveReport(HttpServletResponse response, Long clientId, String startDate,
			String endDate) {
		
		ByteArrayInputStream reportStream = null;

		try {
			List<LeaveReportDTO>  leaveInfoDetails = getLeaveData(clientId, startDate, endDate);
			reportStream = leaveReportHelper.downloadReport(response, leaveInfoDetails);
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
				leaveReportDTO.setStatusId(leaveInfo.getStatusId().toString());
				leaveReportDTO.setLeaveId(leaveInfo.getId().toString());
				leaveReportDTOList.add(leaveReportDTO);
			}catch(Exception e) {
				log.error("Exception occured while tranforming the leave data",e);
			}
		}
		return leaveReportDTOList;
	}

	@Override
	public List<LeaveReportDTO> getLeaveData(Long clientId, String startDate, String endDate) {

		List<LeaveInfo> leaveInfoList = null;
		try {
			short notDeleted = 0;
			leaveInfoList = leaveInfoRepository.findByClientIdAndIsDeleteAndLeaveStartDateBetween(clientId, notDeleted,
					startDate, endDate);
			
		} catch (Exception e) {

			log.info("Exception occured while fetching leaveInfo", e);
		}
		
		List<LeaveReportDTO> leaveReportPage = transformLeaveData(leaveInfoList);
		
		return leaveReportPage;
	}

}
