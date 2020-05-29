package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
	
	@Autowired
	ModelMapper modelMapper;
	@Override
	public List<TimeTypesByClientResponse> getAllTimeTypesByClient(Long client_id) {

		List<TimeTypesByClient> timeTypesByClientList = timetypesbyclientRepo.findByClientId(client_id);
		List<TimeTypesByClientResponse> timeTypesByClientResponseList = new ArrayList<>();
		TimeTypesByClientResponse timeTypesByClientResponse = null;
		for (TimeTypesByClient timeTypesByClient : timeTypesByClientList) {
			timeTypesByClientResponse = modelMapper.map(timeTypesByClient, 
					TimeTypesByClientResponse.class);
			timeTypesByClientResponseList.add(timeTypesByClientResponse);
		}

		return timeTypesByClientResponseList;
	}
}
