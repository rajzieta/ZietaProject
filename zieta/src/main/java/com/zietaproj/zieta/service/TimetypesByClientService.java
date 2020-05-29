package com.zietaproj.zieta.service;


import java.util.List;

import com.zietaproj.zieta.response.TimeTypesByClientResponse;

public interface TimetypesByClientService {

	public List<TimeTypesByClientResponse> getAllTimeTypesByClient(Long client_id);
}
