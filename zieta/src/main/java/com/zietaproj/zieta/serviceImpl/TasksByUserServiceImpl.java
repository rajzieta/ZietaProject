package com.zietaproj.zieta.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.repository.TasksByUserRepository;
import com.zietaproj.zieta.service.TasksByUserService;


@Service
@Transactional
public class TasksByUserServiceImpl implements TasksByUserService {

	
	@Autowired
	TasksByUserRepository tasksbyuserRepository;

	@Override
public TasksByUser getAllTasksByUser(Long user_id) {
	   return tasksbyuserRepository.findById(user_id).get();
	    }

}
