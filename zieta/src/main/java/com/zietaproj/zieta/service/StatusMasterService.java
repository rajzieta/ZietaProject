package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.StatusMaster;

public interface StatusMasterService {

	public List<StatusMasterDTO> getAllStatus();

	public void addStatusmaster(StatusMaster statusmaster);
	
}
