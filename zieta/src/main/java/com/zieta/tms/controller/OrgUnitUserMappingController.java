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

import com.zieta.tms.dto.SkillsetMasterDTO;
import com.zieta.tms.dto.OrgUnitUserMappingDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.response.OrgUnitUsersResponse;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.service.SkillsetMasterService;
import com.zieta.tms.request.OrgUnitUsersRequest;
import com.zieta.tms.service.OrgUnitUserMappingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "OrgUnitUserMapping API")
public class OrgUnitUserMappingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrgUnitUserMappingController.class);

	@Autowired
	OrgUnitUserMappingService teamMasterService;

	@RequestMapping(value = "getAllOrgUnitUserMapping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrgUnitUserMappingDTO> getAllSkillset() {
		List<OrgUnitUserMappingDTO> teamMasters = null;
		try {
			teamMasters = teamMasterService.getAllTeamMaster();
		} catch (Exception e) {
			LOGGER.error("Error Occured in teamMasterController#getAllTeamMaster",e);
		}
		return teamMasters;
	}
	
	
	@RequestMapping(value = "addOrgUnitUserMapping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addOrgUnitUserMapping(@Valid @RequestBody OrgUnitUsersRequest usersRequest) {
		teamMasterService.addTeamMaster(usersRequest);
	}

	
	
	@ApiOperation(value = "Deletes entries from team_master based on Id", notes = "Table reference: orgunit_user_mapping")
	@RequestMapping(value = "deleteOrgUnitUserMappingById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteOrgUnitUserMappingById(@RequestParam(required=true) Long id) throws Exception {
		teamMasterService.deleteTeamMasterById(id);
	}
	
	
	

	@ApiOperation(value = "Updates the TeamMaster for the provided Id", notes = "Table reference: orgunit_user_mapping")
	@RequestMapping(value = "editOrgUnitUserMappingById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editOrgUnitUserMappingById(@Valid @RequestBody OrgUnitUserMappingDTO teamdto) throws Exception {
		teamMasterService.editTeamMaster(teamdto);
	}
		
		@RequestMapping(value = "getOrgUnitUserMappingByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		@ApiOperation(value = "List OrgunitUserMapping Info based on the  clientId", notes = "Table reference: orgunit_user_mapping")
		public ResponseEntity<List<OrgUnitUserMappingDTO>> getOrgUnitUserMappingByClient(@RequestParam(required = true) Long clientId) {
			try {
				List<OrgUnitUserMappingDTO> teamsByClientList = teamMasterService.findByClientId(clientId);
				return new ResponseEntity<List<OrgUnitUserMappingDTO>>(teamsByClientList, HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<List<OrgUnitUserMappingDTO>>(HttpStatus.NOT_FOUND);
			}
		}

}
