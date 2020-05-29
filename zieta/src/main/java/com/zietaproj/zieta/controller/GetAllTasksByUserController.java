package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.service.TasksByUserService;



@RestController
@RequestMapping("/api")
public class GetAllTasksByUserController {

	@Autowired
	TasksByUserService tasksbyuserservice;
	
	//get all tasks by user
	@GetMapping("/getAllTasksByUser/{user_id}")
	 public ResponseEntity<List<TasksByUserModel>> getAllTasksByUser(@PathVariable Long user_id) {
		try {
			List<TasksByUserModel> tasksByUserModelList = tasksbyuserservice.findProjectTasksByUser(user_id);
	         return new ResponseEntity<List<TasksByUserModel>>(tasksByUserModelList, HttpStatus.OK);
	     } catch (NoSuchElementException e) {
	         return new ResponseEntity<List<TasksByUserModel>>(HttpStatus.NOT_FOUND);
	     }        
}
	
}
