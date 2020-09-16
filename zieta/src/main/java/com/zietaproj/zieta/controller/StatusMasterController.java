package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.request.AcitivityRequest;
import com.zietaproj.zieta.request.StatusByClientTypeRequest;

import com.zietaproj.zieta.response.StatusByClienttypeResponse;
import com.zietaproj.zieta.service.StatusMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Status API")
public class StatusMasterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StatusMasterController.class);

	@Autowired
	StatusMasterService statusMasterService;

	@RequestMapping(value = "getAllStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StatusMasterDTO> getAllStatus() {
		List<StatusMasterDTO> statusMasters = null;
		try {
			statusMasters = statusMasterService.getAllStatus();
		} catch (Exception e) {
			LOGGER.error("Error Occured in StatusMasterController#getAllStatus",e);
		}
		return statusMasters;
	}

	@RequestMapping(value = "addStatusByClientStatusType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addStatusByClientStatusType(@Valid @RequestBody StatusMaster statusmaster) {
		statusMasterService.addStatusmaster(statusmaster);
	}

	
	@RequestMapping(value = "getStatusByClientStatusType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "List Status based on the  clientId, and Statustype", notes = "Table reference: Status_master")
	public ResponseEntity<List<StatusByClienttypeResponse>> getStatusByClientStatusType(@RequestParam(required = true) Long clientId,
			@RequestParam(required = true) String statusType) {
		try {
			List<StatusByClienttypeResponse> statusByClientStatustypeList = statusMasterService.findByClientIdAndStatusType(clientId, statusType );
			return new ResponseEntity<List<StatusByClienttypeResponse>>(statusByClientStatustypeList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<StatusByClienttypeResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "editStatusByClientStatusType", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editStatusByClientStatusType(@Valid @RequestBody StatusByClientTypeRequest statusbyclienttypeRequest) throws Exception {
		statusMasterService.editStatusByClientStatusType(statusbyclienttypeRequest);
		
	}

	@ApiOperation(value = "Deletes entries from status_master based on Id", notes = "Table reference: status_master")
	@RequestMapping(value = "deleteStatusById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteStatusById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		statusMasterService.deleteStatusById(id, modifiedBy);
	}
	
	
	@RequestMapping(value = "getStatusByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "List Status based on the  clientId", notes = "Table reference: Status_master")
	public ResponseEntity<List<StatusByClienttypeResponse>> getStatusByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<StatusByClienttypeResponse> statusByClientStatustypeList = statusMasterService.findByClientId(clientId);
			return new ResponseEntity<List<StatusByClienttypeResponse>>(statusByClientStatustypeList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<StatusByClienttypeResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	
}