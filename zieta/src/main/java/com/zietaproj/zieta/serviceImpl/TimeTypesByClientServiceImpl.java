package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.TimeTypesByClient;
import com.zietaproj.zieta.repository.TimeTypesByClientRepo;
import com.zietaproj.zieta.response.TimeTypesByClientResponse;
import com.zietaproj.zieta.service.TimetypesByClientService;

@Service
@Transactional
public class TimeTypesByClientServiceImpl implements TimetypesByClientService {

	@Autowired
	TimeTypesByClientRepo timetypesbyclientRepo;

	@Override
	public List<TimeTypesByClientResponse> getAllTimeTypesByClient(Long client_id) {

		List<TimeTypesByClient> timeTypesByClientList = timetypesbyclientRepo.findByClientId(client_id);
		List<TimeTypesByClientResponse> timeTypesByClientResponseList = new ArrayList<>();
		TimeTypesByClientResponse timeTypesByClientResponse = null;
		for (TimeTypesByClient timeTypesByClient : timeTypesByClientList) {
			timeTypesByClientResponse = new TimeTypesByClientResponse();
			timeTypesByClientResponse.setId(timeTypesByClient.getId());
			timeTypesByClientResponse.setTime_type(timeTypesByClient.getTime_type());
			timeTypesByClientResponseList.add(timeTypesByClientResponse);
		}

		return timeTypesByClientResponseList;
	}
}
