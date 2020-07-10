package com.zietaproj.zieta.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.response.TSInfoModel;
import com.zietaproj.zieta.service.TimeSheetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeSheet API")
@Slf4j
public class TimeSheetController {

	@Autowired
	TimeSheetService timeSheetService;
	
	@PostMapping("/addTimeEntry")
	@RequestMapping(value = "addTimeEntry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTimeEntry(@Valid @RequestBody List<TSInfo> tsinfo) {
		timeSheetService.addTimeEntry(tsinfo);
	}
	
	
	@ApiOperation(value = "Lists TimeSheet entries based on the ts_date range provided, for the provided client and user",notes="Table reference: ts_info")
	@RequestMapping(value = "getTimeEntriesByUserDates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TSInfoModel> getTimeEntriesByUserDates(@RequestParam(required = true) Long clientId, 
			@RequestParam(required = true) Long userId, 
			@RequestParam(required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
		List<TSInfoModel> tsInfoList = null;
		try {
			tsInfoList = timeSheetService.getTimeEntriesByUserDates(clientId, userId, startDate, endDate);
		} catch (Exception e) {
			log.error("Error Occured in getTimeEntriesByUserDates",e);
		}
		return tsInfoList;
	}
}
