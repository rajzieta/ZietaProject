package com.zietaproj.zieta.service;


import java.util.List;

import com.zietaproj.zieta.model.TimeTypesByClient;

public interface TimetypesByClientService {

	public List<String> getAllTimeTypesByClient(Long client_id);
}
