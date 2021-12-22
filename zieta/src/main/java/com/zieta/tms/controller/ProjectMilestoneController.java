package com.zieta.tms.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.ProjectMilestoneDTO;
import com.zieta.tms.model.ProjectMilestone;
import com.zieta.tms.service.ProjectMilestoneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Project MileStone API")
public class ProjectMilestoneController {

	@Autowired
	@Qualifier("ProjectMilestoneServiceImpl")
	private ProjectMilestoneService projectMilestoneService;

	private Logger logger = LoggerFactory.getLogger(ProjectMilestoneController.class);

	@PostMapping(value = "addProjectMilestone", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add Project Milestone", notes = "Add Project Milestone")
	public void addProjectMilestone(@Valid @RequestBody ProjectMilestone projectMilestone) {
		projectMilestoneService.addProjectMilestone(projectMilestone);
	}

	@GetMapping(value = "getProjectMilestone", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get All Project Milestone Details", notes = "Get All Project Milestone Details")
	public ResponseEntity<List<ProjectMilestoneDTO>> getProjectMilestone(@RequestParam(required = true) Long clientId) {
		List<ProjectMilestoneDTO> projectList = null;
		try {
			projectList = projectMilestoneService.getProjectMilestoneDetailsByClientId(clientId);
		} catch (Exception e) {
			logger.error("Get Error from Project Milestone details");
		}
		return new ResponseEntity<List<ProjectMilestoneDTO>>(projectList, HttpStatus.OK);
	}

	@PutMapping(value = "updateProjectMilestone", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Project Milestone", notes = "Update Project Milestone")
	public void updateProjectMilestone(@Valid @RequestBody ProjectMilestone projectMilestone) {
		projectMilestoneService.updateProjectMilestone(projectMilestone);
	}

	@DeleteMapping(value = "deleteProjectMilestone", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete Project Milestone", notes = "Delete Project Milestone")
	public void deleteProjectMilestone(@RequestParam(required = true) Long id,@RequestParam(required=true) String modifiedBy) {
		projectMilestoneService.inActiveByProjectMilestone(id,modifiedBy);
	}
}
