package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.StatusMasterDTO;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.service.StatusMasterService;

import io.swagger.annotations.Api;

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

	@RequestMapping(value = "addStatusByClientType ", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addStatusByClientType(@Valid @RequestBody StatusMaster statusmaster) {
		statusMasterService.addStatusmaster(statusmaster);
	}

}