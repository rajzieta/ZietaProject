package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.response.TimeTypesByClientResponse;
import com.zietaproj.zieta.service.TimetypesByClientService;



@RestController
@RequestMapping("/api")
public class GetTimeTypesByclient {

	@Autowired
	TimetypesByClientService timetypesbyclientservice;
	
	//get all tasks by user
	@GetMapping("/getAllTimeTypesByClient")
	public ResponseEntity<List<TimeTypesByClientResponse>> getAllTimeTypesByClient(
			@RequestParam(required = true) Long client_id, @RequestParam(required = false) Long userId) {
		try {
			List<TimeTypesByClientResponse> timeTypesByClientList = timetypesbyclientservice
					.getAllTimeTypesByClient(client_id);
			return new ResponseEntity<List<TimeTypesByClientResponse>>(timeTypesByClientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TimeTypesByClientResponse>>(HttpStatus.NOT_FOUND);
		}
	}
}
