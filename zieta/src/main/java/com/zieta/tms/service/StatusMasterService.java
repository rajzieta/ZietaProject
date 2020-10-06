package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.StatusMasterDTO;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.request.StatusByClientTypeRequest;
import com.zieta.tms.response.StatusByClienttypeResponse;

public interface StatusMasterService {

	public List<StatusMasterDTO> getAllStatus();

//	public void addStatusmaster(StatusMaster statusmaster);

	public List<StatusByClienttypeResponse> findByClientIdAndStatusType(Long clientId, String statusType);

	public void editStatusByClientStatusType(@Valid StatusByClientTypeRequest statusbyclienttypeRequest) throws Exception;

	public void addStatusmaster(StatusMaster statusmaster);

	public void deleteStatusById(Long id, String modifiedBy) throws Exception;

	public List<StatusByClienttypeResponse> findByClientId(Long clientId);
	
}
