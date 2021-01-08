package com.zieta.tms.service;

import javax.validation.Valid;

import com.zieta.tms.dto.CustInfoDTO;
import com.zieta.tms.dto.LeaveInfoDTO;
import com.zieta.tms.model.LeaveInfo;

public interface LeaveInfoService {

	public void addLeaveInfo(@Valid LeaveInfo leaveinfo);

	public void editleaveInfoById(@Valid LeaveInfoDTO leaveinfoDTO) throws Exception;



	
	
}
