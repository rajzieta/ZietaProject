package com.zietaproj.zieta.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TimeType;
import com.zietaproj.zieta.service.TimeTypeService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeEntry API")
public class TimeEntryController {

	@Autowired
	TimeTypeService timetypeService;
	
	@PostMapping("/addTimeEntry")
	@RequestMapping(value = "addTimeEntry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTimeEntry(@Valid @RequestBody TSInfo tsinfo) {
		timetypeService.addTimeEntry(tsinfo);
	}
}
