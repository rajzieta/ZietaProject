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

import com.zietaproj.zieta.model.TimeTypesByClient;
import com.zietaproj.zieta.service.TimetypesByClientService;



@RestController
@RequestMapping("/api")
public class GetTimeTypesByclient {

	@Autowired
	TimetypesByClientService timetypesbyclientservice;
	
	//get all tasks by user
	@GetMapping("/getAllTimeTypesByClient/{client_id}")
	 public ResponseEntity<List<String>> getAllTimeTypesByClient(@PathVariable Long client_id) {
		try {
	        List<String> timeTypesByClientList = timetypesbyclientservice.getAllTimeTypesByClient(client_id);
	         return new ResponseEntity<List<String>>(timeTypesByClientList, HttpStatus.OK);
	     } catch (NoSuchElementException e) {
	         return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
	     }        
}
}
