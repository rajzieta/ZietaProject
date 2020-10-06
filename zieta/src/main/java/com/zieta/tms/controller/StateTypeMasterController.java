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

import com.zieta.tms.dto.StateTypeDTO;
import com.zieta.tms.model.StateTypeMaster;
import com.zieta.tms.service.StateTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "StateType API")
public class StateTypeMasterController {

	@Autowired
	StateTypeService stateTypeService;
	
private static final Logger LOGGER = LoggerFactory.getLogger(StateTypeMasterController.class);
	
	@RequestMapping(value = "getAllStateTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StateTypeDTO> getAllStateTypes() {
		List<StateTypeDTO> stateTypes = null;
		try {
			stateTypes = stateTypeService.getAllStateTypes();
		} catch (Exception e) {
			LOGGER.error("Error Occured in StateTypeMasterController#getAllStateTypes",e);
		}
		return stateTypes;
	}
	
	
	@RequestMapping(value = "getStateByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public StateTypeMaster getStateByName(@RequestParam(required = true) String stateName) {
		StateTypeMaster stateTypeMaster = null;
		try {
			stateTypeMaster = stateTypeService.getStateByName(stateName);
		} catch (Exception e) {
			LOGGER.error("Error Occured in StateTypeMasterController#getStateByName",e);
		}
		return stateTypeMaster;
	}
	
	
	@ApiOperation(value = "creates entries in the state_type_master table", notes = "Table reference: state_type_master")
	@RequestMapping(value = "addStateTypes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addStateTypes(@Valid @RequestBody StateTypeMaster stateTypemaster) {
		stateTypeService.addStateTypes(stateTypemaster);
	}

	@ApiOperation(value = "Updates the stateTypes for the provided Id", notes = "Table reference: state_type_master")
	@RequestMapping(value = "editStateTypesById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editStateTypesById(@Valid @RequestBody StateTypeDTO statetypeDTO) throws Exception {
		stateTypeService.editStateTypesById(statetypeDTO);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from state_type_master based on Id", notes = "Table reference: state_type_master")
	@RequestMapping(value = "deleteStateTypesById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteStateTypesById(@RequestParam(required=true) Long id) throws Exception {
		stateTypeService.deleteStateTypesById(id);
	}
	
}
