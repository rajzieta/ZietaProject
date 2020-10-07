package com.zieta.tms.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.hibernate.type.TimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.TimeTypeDTO;
import com.zieta.tms.model.TimeType;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.request.TimeTypeEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.response.TimeTypesByClientResponse;
import com.zieta.tms.service.TimeTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeType API")
public class TimeTypeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeTypeController.class);

	@Autowired
	TimeTypeService timetypeService;

	@RequestMapping(value = "getAllTimetypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TimeTypeDTO> getAllTimetypes() {
		List<TimeTypeDTO> timetypes = null;
		try {
			timetypes = timetypeService.getAllTimetypes();
		} catch (Exception e) {
			LOGGER.error("Error Occured in TimeTypeController#getAllTimetypes",e);
		}
		return timetypes;
	}

	@PostMapping("/addTimetypemaster")
	@RequestMapping(value = "addTimetypemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTimetypemaster(@Valid @RequestBody TimeType timetype) {
		timetypeService.addTimetypemaster(timetype);
	}

	

	
	
	@ApiOperation(value = "Updates the timetypes Information for the provided Id", notes = "Table reference: time_type")
	@RequestMapping(value = "editTimeTypesById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editTimeTypesById(@Valid @RequestBody TimeTypeEditRequest timetypeEditRequest) throws Exception {
		timetypeService.editTimeTypesById(timetypeEditRequest);
		
		
	}
	
	
	@ApiOperation(value = "Deletes entries from time_type based on Id", notes = "Table reference: time_type")
	@RequestMapping(value = "deleteTimeTypesById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTimeTypesById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		timetypeService.deleteTimeTypesById(id, modifiedBy);
	}
	
	
	
	
	@GetMapping("/getAllTimeTypesByClient")
	@ApiOperation(value = "List time types based on the clientId", notes = "Table reference: time_type_master")
	public ResponseEntity<List<TimeTypesByClientResponse>> getAllTimeTypesByClient(
			@RequestParam(required = true) Long client_id) {
		try {
			List<TimeTypesByClientResponse> timeTypesByClientList = timetypeService.getAllTimeTypesByClient(client_id);
			return new ResponseEntity<List<TimeTypesByClientResponse>>(timeTypesByClientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TimeTypesByClientResponse>>(HttpStatus.NOT_FOUND);
		}
	}

}
