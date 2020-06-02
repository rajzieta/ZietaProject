package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.response.TasksByUserModel;
import com.zietaproj.zieta.service.TasksByUserService;

@RestController
@RequestMapping("/api")
public class GetAllTasksByUserController {

	@Autowired
	TasksByUserService tasksbyuserservice;

	// get all tasks by user
	@GetMapping("/getAllTasksByUser")
	public ResponseEntity<List<TasksByUserModel>> getAllTasksByUser(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) Long userId) {
		try {
			List<TasksByUserModel> tasksByUserModelList = tasksbyuserservice.
															findByClientIdAndUserId(clientId, userId);
			return new ResponseEntity<List<TasksByUserModel>>(tasksByUserModelList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TasksByUserModel>>(HttpStatus.NOT_FOUND);
		}
	}

}
