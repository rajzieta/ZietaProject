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

import com.zietaproj.zieta.dto.ActivityMasterDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.service.ActivityMasterService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(tags = "Activites API")
public class ActivityMasterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityMasterController.class);

	@Autowired
	ActivityMasterService activitymasterService;

	@RequestMapping(value = "getAllActivities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActivityMasterDTO> getAllActivities() {
		List<ActivityMasterDTO> activityMasters = null;
		try {
			activityMasters = activitymasterService.getAllActivities();
			
		} catch (Exception e) {
			LOGGER.error("Error Occured in ActivityMasterController#getAllActivities",e);
		}
		return activityMasters;
	}

	@RequestMapping(value = "addActivitymaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addActivitymaster(@Valid @RequestBody ActivityMaster activitymaster) {
		activitymasterService.addActivitymaster(activitymaster);
	}

}
