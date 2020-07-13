package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;
import com.zietaproj.zieta.model.AccessTypeScreenMapping;
import com.zietaproj.zieta.model.UserAccessType;
import com.zietaproj.zieta.service.AccessTypeMasterService;
import com.zietaproj.zieta.service.AccessTypeScreenMappingService;
import com.zietaproj.zieta.service.UserAccessTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "AccessTypes API")
@Slf4j
public class AccessTypeController {

	
	@Autowired
	AccessTypeMasterService accesstypemasterService;
	
	@Autowired
	AccessTypeScreenMappingService accessTypeScreenMappingService;
	
	@Autowired
	UserAccessTypeService userAccessTypeService;
	

	@RequestMapping(value = "getAllAccesstypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAllAccesstypes() {
		String response = "";
		try {
			List<AccessTypeMasterDTO> accesstypeMasters = accesstypemasterService.getAllAccesstypes();
			System.out.println("AccessTypeMasters size=>" + accesstypeMasters.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(accesstypeMasters);
		} catch (Exception e) {
			log.error("Error Occured in AccessTypeMasterService#getAllAccesstypes",e);
		}
		return response;
	}

	@RequestMapping(value = "addAccessTypemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addAccessTypemaster(@Valid @RequestBody AccessTypeMaster accesstypemaster) {
		accesstypemasterService.addAccessTypemaster(accesstypemaster);
	}
	
	
	@ApiOperation(value = "Adds the entries into the accesstype_screen_mapping table", notes = "Table reference: accesstype_screen_mapping")
	@RequestMapping(value = "assignScreenToAccessType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void assignScreenToAccessType(@Valid @RequestBody AccessTypeScreenMapping accessTypeScreenMapping) {
		accessTypeScreenMappingService.assignScreenToAccessType(accessTypeScreenMapping);
	}
	
	
	@ApiOperation(value = "Adds the entries into the accesstype_user_mapping table", notes = "Table reference: accesstype_user_mapping")
	@RequestMapping(value = "assignAccessTypeToUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void assignAccessTypeToUser(@Valid @RequestBody UserAccessType userAccessType) {
		userAccessTypeService.assignAccessTypeToUser(userAccessType);
	}
}