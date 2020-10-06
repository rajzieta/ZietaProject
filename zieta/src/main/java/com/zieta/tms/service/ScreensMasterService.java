package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.dto.ScreensMasterDTO;
import com.zieta.tms.model.AccessTypeScreenMapping;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.request.ScreensMasterAddRequest;
import com.zieta.tms.request.ScreensMasterEditRequest;

public interface ScreensMasterService {

	public List<ScreensMasterDTO> getAllScreens();

	public void addScreensmaster(ScreensMasterAddRequest screensmaster);
	
	public void updateScreensmaster(ScreensMasterEditRequest screensmaster);
	
	public void deleteById(Long id);
	
	public List<ScreensMaster> getScreensByIds(List<Long> ids);

	public List<ScreensMasterDTO> getAllScreensByCategory(String screenCategory);

	//public List<ScreensMaster> getScreensListByIds(List<AccessTypeScreenMapping> accessControlConfigList);
}
