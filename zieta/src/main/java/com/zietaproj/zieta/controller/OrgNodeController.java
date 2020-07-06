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

import com.zietaproj.zieta.response.OrgNodesByClientResponse;
import com.zietaproj.zieta.service.ActivityService;
import com.zietaproj.zieta.service.OrgNodesService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api")
@Api(tags = "OrgNodes API")
public class OrgNodeController {

	@Autowired
	OrgNodesService orgnodesService;
	
	

	@GetMapping("/getAllOrgNodesByClient")
	public ResponseEntity<List<OrgNodesByClientResponse>> getAllOrgNodesByClient(@RequestParam(required=true) Long clientId) {
		try {
			List<OrgNodesByClientResponse> orgnodesbyclientList = orgnodesService.getOrgNodesByClient(clientId);
			return new ResponseEntity<List<OrgNodesByClientResponse>>(orgnodesbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<OrgNodesByClientResponse>>(HttpStatus.NOT_FOUND);
		} 
	}
}
