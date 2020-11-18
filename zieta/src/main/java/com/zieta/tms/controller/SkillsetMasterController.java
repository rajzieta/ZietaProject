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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.OrgInfoDTO;
import com.zieta.tms.dto.SkillsetMasterDTO;
import com.zieta.tms.dto.SkillsetUserMappingDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.SkillsetUserMapping;
import com.zieta.tms.response.StatusByClienttypeResponse;
import com.zieta.tms.service.SkillsetMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Skillset API")
public class SkillsetMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SkillsetMasterController.class);

	@Autowired
	SkillsetMasterService skillsetMasterService;

	@RequestMapping(value = "getAllSkillset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SkillsetMasterDTO> getAllSkillset() {
		List<SkillsetMasterDTO> skillMasters = null;
		try {
			skillMasters = skillsetMasterService.getAllSkillset();
		} catch (Exception e) {
			LOGGER.error("Error Occured in SkillMasterController#getAllSkillset",e);
		}
		return skillMasters;
	}
	
	
	@RequestMapping(value = "addSkillsetMaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addSkillsetMaster(@Valid @RequestBody SkillsetMaster skillmaster) {
		skillsetMasterService.addSkillmaster(skillmaster);
	}

	
	
	@ApiOperation(value = "Deletes entries from status_master based on Id", notes = "Table reference: skillset_master")
	@RequestMapping(value = "deleteSkillsetById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteSkillsetById(@RequestParam(required=true) Long id) throws Exception {
		skillsetMasterService.deleteSkillsetById(id);
	}
	
	
	@RequestMapping(value = "getSkillsetByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "List Status based on the  clientId", notes = "Table reference: Skillset_master")
	public ResponseEntity<List<SkillsetMasterDTO>> getSkillsetByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<SkillsetMasterDTO> skillsByClientList = skillsetMasterService.findByClientId(clientId);
			return new ResponseEntity<List<SkillsetMasterDTO>>(skillsByClientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<SkillsetMasterDTO>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@ApiOperation(value = "Updates the Skillset for the provided Id", notes = "Table reference: Skillset_master")
	@RequestMapping(value = "editSkillsetById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editSkillsetById(@Valid @RequestBody SkillsetMasterDTO skilldto) throws Exception {
		skillsetMasterService.editskillmaster(skilldto);
		
		
	}
	
	
	////Skillset User Mapping 
	
	

	@RequestMapping(value = "getAllSkillsetUserMapping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SkillsetUserMappingDTO> getAllSkillsetUserMapping() {
		List<SkillsetUserMappingDTO> skillusersMapping = null;
		try {
			skillusersMapping = skillsetMasterService.getAllSkillsetUserMapping();
		} catch (Exception e) {
			LOGGER.error("Error Occured in SkillMasterController#getAllSkillsetUserMapping",e);
		}
		return skillusersMapping;
	}
	
	
	@RequestMapping(value = "addSkillsetUserMapping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addSkillsetUserMapping(@Valid @RequestBody SkillsetUserMapping skilluserMapping) {
		skillsetMasterService.addSkillsetUserMapping(skilluserMapping);
	}

	
	
	@ApiOperation(value = "Deletes entries from status_master based on Id", notes = "Table reference: skillset_user_mapping")
	@RequestMapping(value = "deleteSkillsetUserMappingById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteSkillsetUserMappingById(@RequestParam(required=true) Long id) throws Exception {
		skillsetMasterService.deleteSkillUserMappingById(id);
	}
	
	
	@RequestMapping(value = "getSkillsetUserMappingByClientAndUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "List Status based on the  clientId", notes = "Table reference: skillset_user_mapping")
	public ResponseEntity<List<SkillsetUserMappingDTO>> getSkillsetUserMappingByClientAndUser(@RequestParam(required = true) Long clientId, @RequestParam(required = true) Long userId) {
		try {
			List<SkillsetUserMappingDTO> skillsByClientList = skillsetMasterService.findByClientIdAndUserId(clientId, userId);
			return new ResponseEntity<List<SkillsetUserMappingDTO>>(skillsByClientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<SkillsetUserMappingDTO>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}
