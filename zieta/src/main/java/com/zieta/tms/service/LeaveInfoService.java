package com.zieta.tms.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.LeaveInfoDTO;
import com.zieta.tms.dto.LeaveTypeMasterDTO;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.model.LeaveTypeMaster;

public interface LeaveInfoService {

	public void addLeaveInfo(@Valid LeaveInfo leaveinfo);

	public void editleaveInfoById(@Valid LeaveInfoDTO leaveinfoDTO) throws Exception;

	public void addLeaveTypeMaster(@Valid LeaveTypeMaster leavemaster);

	public void editLeaveMasterById(@Valid LeaveTypeMasterDTO leavemasterDTO) throws Exception;

	public List<LeaveInfoDTO> getAllLeaveInfo();

	public List<LeaveTypeMasterDTO> getAllLeaveMaster();

	public List<LeaveInfoDTO> getAllLeavesByClient(Long clientId);

	public List<LeaveTypeMasterDTO> getAllLeaveTypesByClient(Long clientId);

	public List<LeaveInfoDTO> getAllLeavesByClientUser(Long clientId, Long userId);
	
	public List<LeaveInfoDTO> getAllLeavesByClientAndDateRange(Long clientId, String startDate, String endDate);
	
	public List<LeaveInfoDTO> getAllLeavesByClientUserAndDateRange(Long clientId, Long userId, String startDate, String endDate);
	
	public void deleteLeaveInfoById(Long id, String modifiedBy) throws Exception;

	public List<LeaveInfoDTO> findActiveLeavesByClientIdAndApproverId(Long clientId, Long approverId);

	public void deleteLeaveTypeById(Long id, String modifiedBy) throws Exception;
	
	public List<LeaveInfoDTO> getLeaveHistoryByApprover(Long clientId, Long approverId, String startDate, String endDate);
	
	



	
	
}
