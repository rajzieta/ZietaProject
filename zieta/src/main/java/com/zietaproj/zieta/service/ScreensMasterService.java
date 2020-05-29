package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.model.ScreensMaster;

public interface ScreensMasterService {

	public List<ScreensMasterDTO> getAllScreens();

	public void addScreensmaster(ScreensMaster screensmaster);
	
	public List<ScreensMaster> getScreensByIds(List<Long> ids);
}
