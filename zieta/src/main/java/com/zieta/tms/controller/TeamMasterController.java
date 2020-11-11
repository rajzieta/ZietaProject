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
import com.zieta.tms.dto.TeamMasterDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.TeamMaster;
import com.zieta.tms.service.SkillsetMasterService;
import com.zieta.tms.service.TeamMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "TeamMaster API")
public class TeamMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeamMasterController.class);

	@Autowired
	TeamMasterService teamMasterService;

	@RequestMapping(value = "getAllTeamMaster", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TeamMasterDTO> getAllSkillset() {
		List<TeamMasterDTO> teamMasters = null;
		try {
			teamMasters = teamMasterService.getAllTeamMaster();
		} catch (Exception e) {
			LOGGER.error("Error Occured in teamMasterController#getAllTeamMaster",e);
		}
		return teamMasters;
	}
	
	
	@RequestMapping(value = "addTeamMaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTeamMaster(@Valid @RequestBody TeamMaster teammaster) {
		teamMasterService.addTeamMaster(teammaster);
	}

	
	
	@ApiOperation(value = "Deletes entries from team_master based on Id", notes = "Table reference: team_master")
	@RequestMapping(value = "deleteTeamMasterById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTeamMasterById(@RequestParam(required=true) Long id) throws Exception {
		teamMasterService.deleteTeamMasterById(id);
	}
	
	
	

	@ApiOperation(value = "Updates the TeamMaster for the provided Id", notes = "Table reference: team_master")
	@RequestMapping(value = "editTeamMasterById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editTeamMasterById(@Valid @RequestBody TeamMasterDTO teamdto) throws Exception {
		teamMasterService.editTeamMaster(teamdto);
	}
		
		@RequestMapping(value = "getTeamMasterByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		@ApiOperation(value = "List Status based on the  clientId", notes = "Table reference: Skillset_master")
		public ResponseEntity<List<TeamMasterDTO>> getTeamMasterByClient(@RequestParam(required = true) Long clientId) {
			try {
				List<TeamMasterDTO> teamsByClientList = teamMasterService.findByClientId(clientId);
				return new ResponseEntity<List<TeamMasterDTO>>(teamsByClientList, HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<List<TeamMasterDTO>>(HttpStatus.NOT_FOUND);
			}
		}
	

}
