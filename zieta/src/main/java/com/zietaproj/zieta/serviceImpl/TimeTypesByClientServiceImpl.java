package com.zietaproj.zieta.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.TimeTypesByClient;
import com.zietaproj.zieta.repository.TimeTypesByClientRepo;
import com.zietaproj.zieta.service.TimetypesByClientService;

@Service
@Transactional
public class TimeTypesByClientServiceImpl implements TimetypesByClientService {

	@Autowired
	TimeTypesByClientRepo timetypesbyclientRepo;

	@Override
public List<String> getAllTimeTypesByClient(Long client_id) {
		
		List<TimeTypesByClient> timeTypesByClientList = timetypesbyclientRepo.findByClientId(client_id);
		List<String> timeTypeforClient = timeTypesByClientList.stream()
				.map(TimeTypesByClient::getTime_type).collect(Collectors.toList());
		
		
	   return timeTypeforClient;
	    }
}
