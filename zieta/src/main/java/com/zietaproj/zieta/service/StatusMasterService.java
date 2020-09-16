package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.request.StatusByClientTypeRequest;

import com.zietaproj.zieta.response.StatusByClienttypeResponse;

public interface StatusMasterService {

	public List<StatusMasterDTO> getAllStatus();

//	public void addStatusmaster(StatusMaster statusmaster);

	public List<StatusByClienttypeResponse> findByClientIdAndStatusType(Long clientId, String statusType);

	public void editStatusByClientStatusType(@Valid StatusByClientTypeRequest statusbyclienttypeRequest) throws Exception;

	public void addStatusmaster(StatusMaster statusmaster);

	public void deleteStatusById(Long id, String modifiedBy) throws Exception;

	public List<StatusByClienttypeResponse> findByClientId(Long clientId);
	
}
