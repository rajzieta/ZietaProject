package com.zieta.tms.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.CustInfoDTO;
import com.zieta.tms.dto.LeaveInfoDTO;
import com.zieta.tms.model.CustInfo;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.service.LeaveInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Leave Informationn API")
public class LeaveController {

	@Autowired
	LeaveInfoService leaveInfoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LeaveController.class);
	
	
	@RequestMapping(value = "addLeaveInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addLeaveInfo(@Valid @RequestBody LeaveInfo leaveinfo) {
		leaveInfoService.addLeaveInfo(leaveinfo);
	}
	
	
	@ApiOperation(value = "Updates the UserRoles for the provided Id", notes = "Table reference: cust_info")
	@RequestMapping(value = "editLeaveInfoById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editLeaveInfoById(@Valid @RequestBody LeaveInfoDTO leaveinfoDTO) throws Exception {
		leaveInfoService.editleaveInfoById(leaveinfoDTO);
		
		
	}
	
}
