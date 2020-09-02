package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.model.AccessTypeScreenMapping;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.request.ScreensMasterAddRequest;
import com.zietaproj.zieta.request.ScreensMasterEditRequest;

public interface ScreensMasterService {

	public List<ScreensMasterDTO> getAllScreens();

	public void addScreensmaster(ScreensMasterAddRequest screensmaster);
	
	public void updateScreensmaster(ScreensMasterEditRequest screensmaster);
	
	public void deleteById(Long id);
	
	public List<ScreensMaster> getScreensByIds(List<Long> ids);

	public List<ScreensMasterDTO> getAllScreensByCategory(String screenCategory);

	//public List<ScreensMaster> getScreensListByIds(List<AccessTypeScreenMapping> accessControlConfigList);
}
