package com.zietaproj.zieta.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.model.TimeTypesbyClient;
import com.zietaproj.zieta.repository.TasksByUserRepository;
import com.zietaproj.zieta.repository.TimeTypesByClientRepo;
import com.zietaproj.zieta.service.TasksByUserService;
import com.zietaproj.zieta.service.TimetypesByClientService;

@Service
@Transactional
public class TimeTypesByClientServiceImpl implements TimetypesByClientService {

	@Autowired
	TimeTypesByClientRepo timetypesbyclientRepo;

	@Override
public TimeTypesbyClient getAllTimeTypesByClient(Long client_id) {
	   return timetypesbyclientRepo.findById(client_id).get();
	    }
}
