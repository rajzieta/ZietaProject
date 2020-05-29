package com.zietaproj.zieta.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.model.TimeTypesbyClient;
import com.zietaproj.zieta.service.TimetypesByClientService;



@RestController
@RequestMapping("/api")
public class GetTimetypesbyclient {

	@Autowired
	TimetypesByClientService timetypesbyclientservice;
	
	//get all tasks by user
	@GetMapping("/getAllTimeTypesByClient/{client_id}")
	 public ResponseEntity<TimeTypesbyClient> getAllTimeTypesByClient(@PathVariable Long client_id) {
		try {
	        TimeTypesbyClient timetypesbyclient = timetypesbyclientservice.getAllTimeTypesByClient(client_id);
	         return new ResponseEntity<TimeTypesbyClient>(timetypesbyclient, HttpStatus.OK);
	     } catch (NoSuchElementException e) {
	         return new ResponseEntity<TimeTypesbyClient>(HttpStatus.NOT_FOUND);
	     }        
}
}
