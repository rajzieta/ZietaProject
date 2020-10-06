package com.zieta.tms.controller;

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

import com.zieta.tms.dto.OrgInfoDTO;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.response.OrgNodesByClientResponse;
import com.zieta.tms.service.OrgNodesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api")
@Api(tags = "OrgNodes API")
public class OrgNodeController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrgNodeController.class);
	
	@Autowired
	OrgNodesService orgnodesService;
	
	@RequestMapping(value = "getAllOrgNodes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrgInfoDTO> getAllOrgNodes() {
		List<OrgInfoDTO> orginfo = null;
		try {
			orginfo = orgnodesService.getAllOrgNodes();
		} catch (Exception e) {
			LOGGER.error("Error Occured in StatusMasterController#getAllStatus",e);
		}
		return orginfo;
	}

	@GetMapping("/getAllOrgNodesByClient")
	public ResponseEntity<List<OrgNodesByClientResponse>> getAllOrgNodesByClient(@RequestParam(required=true) Long clientId) {
		try {
			List<OrgNodesByClientResponse> orgnodesbyclientList = orgnodesService.getOrgNodesByClient(clientId);
			return new ResponseEntity<List<OrgNodesByClientResponse>>(orgnodesbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<OrgNodesByClientResponse>>(HttpStatus.NOT_FOUND);
		} 
	}
	
	
	@RequestMapping(value = "addOrgInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addOrgInfo(@Valid @RequestBody OrgInfo orginfo) {
		orgnodesService.addOrgInfo(orginfo);
	}
	
	
	@ApiOperation(value = "Updates the Org Info for the provided Id", notes = "Table reference: org_info")
	@RequestMapping(value = "editOrgInfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editOrgInfo(@Valid @RequestBody OrgInfoDTO orginfodto) throws Exception {
		orgnodesService.editOrgInfo(orginfodto);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from org_info based on Id", notes = "Table reference: org_info")
	@RequestMapping(value = "deleteOrgInfoById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteOrgInfoById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		orgnodesService.deleteOrgInfoById(id, modifiedBy);
	}
	
	
	
	@GetMapping("/getAllOrgNodesByClientAsHeirarchy")
	@ApiOperation(value = "List OrgInfo based on the  clientId", notes = "Table reference:"
			+ " org_info")
	public ResponseEntity<List<OrgNodesByClientResponse>> getAllOrgNodesByClientAsHeirarchy(@RequestParam(required=true) Long clientId) {
		try {
			List<OrgNodesByClientResponse> orgnodesbyclientList = orgnodesService.findByClientIdAsHierarchy(clientId);
			return new ResponseEntity<List<OrgNodesByClientResponse>>(orgnodesbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<OrgNodesByClientResponse>>(HttpStatus.NOT_FOUND);
		} 
	}
	
}
