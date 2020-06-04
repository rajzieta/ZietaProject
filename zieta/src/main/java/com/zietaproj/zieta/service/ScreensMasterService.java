package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.request.ScreensMasterAddRequest;
import com.zietaproj.zieta.request.ScreensMasterEditRequest;

public interface ScreensMasterService {

	public List<ScreensMasterDTO> getAllScreens();

	public void addScreensmaster(ScreensMasterAddRequest screensmaster);
	
	public void updateScreensmaster(ScreensMasterEditRequest screensmaster);
	
	//public void updateScreensMaster(Long id, ScreensMaster screensmaster);
	
	//public void deleteByScreencode(String screen_code);
	
	public void deleteById(Long id);
}
