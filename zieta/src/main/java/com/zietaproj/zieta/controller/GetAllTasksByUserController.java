package com.zietaproj.zieta.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.model.TasksByUser;
import com.zietaproj.zieta.service.TasksByUserService;



@RestController
@RequestMapping("/api")
public class GetAllTasksByUserController {

	@Autowired
	TasksByUserService tasksbyuserservice;
	
	//get all tasks by user
	@GetMapping("/getAllTasksbyuser/{user_id}")
	 public ResponseEntity<TasksByUser> getAllTasksbyuser(@PathVariable Long user_id) {
		try {
	        TasksByUser tasksbyuser = tasksbyuserservice.getAllTasksbyuser(user_id);
	         return new ResponseEntity<TasksByUser>(tasksbyuser, HttpStatus.OK);
	     } catch (NoSuchElementException e) {
	         return new ResponseEntity<TasksByUser>(HttpStatus.NOT_FOUND);
	     }        
}
	
}
