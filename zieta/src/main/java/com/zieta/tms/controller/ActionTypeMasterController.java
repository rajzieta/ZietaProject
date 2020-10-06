package com.zieta.tms.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.ActionTypeDTO;
import com.zieta.tms.model.ActionTypeMaster;
import com.zieta.tms.service.ActionTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "ActionType API")
public class ActionTypeMasterController {

	@Autowired
	ActionTypeService actionTypeService;
	
private static final Logger LOGGER = LoggerFactory.getLogger(ActionTypeMasterController.class);
	
	@RequestMapping(value = "getAllActionTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActionTypeDTO> getAllActionTypes() {
		List<ActionTypeDTO> actionTypes = null;
		try {
			actionTypes = actionTypeService.getAllActionTypes();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ActionTypeMasterController#getAllActionTypes",e);
		}
		return actionTypes;
	}
	
	
	@ApiOperation(value = "creates entries in the action_type_master table", notes = "Table reference: action_type_master")
	@RequestMapping(value = "addActionTypes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addActionTypes(@Valid @RequestBody ActionTypeMaster actionTypemaster) {
		actionTypeService.addActionTypes(actionTypemaster);
	}

	@ApiOperation(value = "Updates the actionTypes for the provided Id", notes = "Table reference: action_type_master")
	@RequestMapping(value = "editActionTypesById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editActionTypesById(@Valid @RequestBody ActionTypeDTO actiontypeDTO) throws Exception {
		actionTypeService.editActionTypesById(actiontypeDTO);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from action_type_master based on Id", notes = "Table reference: action_type_master")
	@RequestMapping(value = "deleteActionTypesById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteActionTypesById(@RequestParam(required=true) Long id) throws Exception {
		actionTypeService.deleteActionTypesById(id);
	}
	
}
