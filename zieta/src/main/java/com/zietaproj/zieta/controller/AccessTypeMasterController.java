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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.AccessTypeMasterDTO;
import com.zietaproj.zieta.model.AccessTypeMaster;
import com.zietaproj.zieta.request.AccessTypeAddRequest;
import com.zietaproj.zieta.response.AccesstypesByClientResponse;
import com.zietaproj.zieta.response.RolesByClientResponse;
import com.zietaproj.zieta.service.AccessTypeMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "AccessTypes API")
public class AccessTypeMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessTypeMasterController.class);
	@Autowired
	AccessTypeMasterService accesstypemasterService;

	@RequestMapping(value = "getAllAccesstypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAllAccesstypes() {
		String response = "";
		try {
			List<AccessTypeMasterDTO> accesstypeMasters = accesstypemasterService.getAllAccesstypes();
			System.out.println("AccessTypeMasters size=>" + accesstypeMasters.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(accesstypeMasters);
		} catch (Exception e) {
			LOGGER.error("Error Occured in AccessTypeMasterService#getAllAccesstypes",e);
		}
		return response;
	}

	@GetMapping("/getAllAccesstypesByClient")
	@ApiOperation(value = "List Accesstypes based on the clientId", notes = "Table reference: role_master")
	public ResponseEntity<List<AccesstypesByClientResponse>> getAllAccesstypesByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<AccesstypesByClientResponse> accesstypesbyclientList = accesstypemasterService.getAccessTypesByClient(clientId);
			return new ResponseEntity<List<AccesstypesByClientResponse>>(accesstypesbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<AccesstypesByClientResponse>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	@RequestMapping(value = "addAccessTypemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addAccessTypemaster(@Valid @RequestBody AccessTypeAddRequest accesstypemaster) {
		accesstypemasterService.addAccessTypemaster(accesstypemaster);
	}
}
