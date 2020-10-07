package com.zieta.tms.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

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
import com.zieta.tms.dto.AccessTypeMasterDTO;
import com.zieta.tms.dto.AccessTypeScreenMappingDTO;
import com.zieta.tms.model.AccessTypeMaster;
import com.zieta.tms.model.AccessTypeScreenMapping;
import com.zieta.tms.request.AccessScreensRequest;
import com.zieta.tms.request.AccessTypeAddRequest;
import com.zieta.tms.response.AccesstypesByClientResponse;
import com.zieta.tms.service.AccessTypeMasterService;
import com.zieta.tms.service.AccessTypeScreenMappingService;

//import com.zieta.tms.service.UserAccessTypeService;
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
	public  ResponseEntity<AccessTypeMaster> addAccessTypemaster(@Valid @RequestBody AccessTypeAddRequest accesstypemaster) {
		AccessTypeMaster accessTypeMaster =accesstypemasterService.addAccessTypemaster(accesstypemaster);
		return new ResponseEntity<AccessTypeMaster>(accessTypeMaster, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Adds the entries into the accesstype_screen_mapping table", notes = "Table reference: accesstype_screen_mapping")
	@RequestMapping(value = "assignScreenToAccessType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void assignScreenToAccessType(@Valid @RequestBody AccessTypeScreenMapping accessTypeScreenMapping) {
		accessTypeScreenMappingService.assignScreenToAccessType(accessTypeScreenMapping);
	}
	
	
	@RequestMapping(value = "getAllAccesstypeScreensMapping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AccessTypeScreenMappingDTO> getAllAccesstypeScreensMapping() {
		List<AccessTypeScreenMappingDTO> accesstypescreenMappingList = null;
		try {
			accesstypescreenMappingList = accessTypeScreenMappingService.getAllAccesstypeScreensMapping();
			System.out.println("AccessTypeMasters size=>" + accesstypescreenMappingList.size());

		} catch (Exception e) {
			log.error("Error Occured in AccessTypeScreenMappingService#getAllAccesstypeScreensMapping",e);
		}
		return accesstypescreenMappingList;
	}
	
	
	@GetMapping("/getAllAccesstypeScreenMappingByClient")
	@ApiOperation(value = "List AccesstypesScreensMapping based on the clientId", notes = "Table reference: accesstype_screen_mapping")
	public ResponseEntity<List<AccessTypeScreenMappingDTO>> getAllAccesstypeScreenMappingByClient(@RequestParam(required = true) Long clientId) {
		try {
			List<AccessTypeScreenMappingDTO> accesstypesbyclientList = accessTypeScreenMappingService.getAccessTypeScreenMappingByClient(clientId);
			return new ResponseEntity<List<AccessTypeScreenMappingDTO>>(accesstypesbyclientList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<AccessTypeScreenMappingDTO>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "Updates the accesstype_screen_mapping for the provided Id", notes = "Table reference: accesstype_screen_mapping")
	@RequestMapping(value = "editAccessTypeScreenMapping", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editAccessTypeScreenMapping(@Valid @RequestBody AccessTypeScreenMappingDTO accessscreenMappingdto) throws Exception {
		accessTypeScreenMappingService.editAccessScreenMapping(accessscreenMappingdto);
		
		
	}
	
	@ApiOperation(value = "Deletes entries from accesstype_screen_mapping based on Id", notes = "Table reference: accesstype_screen_mapping")
	@RequestMapping(value = "deleteAccessTypeScreensMappingById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAccessTypeScreensMappingById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		accessTypeScreenMappingService.deleteAccessScreensMappingById(id, modifiedBy);
	}
	
	
	
	@ApiOperation(value = "Updates the AccessType for the provided Id", notes = "Table reference: access_type_master")
	@RequestMapping(value = "editAccessTypesById", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editAccessTypesById(@Valid @RequestBody AccessTypeAddRequest accesstypeeditRequest) throws Exception {
		accesstypemasterService.editAccessTypesById(accesstypeeditRequest);
		
	}
	
	
	
	@ApiOperation(value = "Soft deletion of entries from access_type_master based on Id", notes = "Table reference: access_type_master")
	@RequestMapping(value = "deleteAccessTypesById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAccessTypesById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		accesstypemasterService.deleteAccessTypesById(id, modifiedBy);
	}
	
	
	@ApiOperation(value = "Deletes entries from accesstype_screen_mapping based on clientId and accessTypeId ", notes = "Table reference: accesstype_screen_mapping")
	@RequestMapping(value = "deleteAccessTypeAndScreens", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAccessTypeAndScreens(@RequestParam(required=true) Long clientId, @RequestParam(required=true) Long accessTypeId) throws Exception {
		accessTypeScreenMappingService.deleteAccessTypeAndScreens(clientId, accessTypeId);
	}
	
	@ApiOperation(value = "Adds the entries into the accesstype_screen_mapping table", notes = "Table reference: accesstype_screen_mapping")
	@RequestMapping(value = "assignMultipleScreensToAccessType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void assignMultipleScreensToAccessType(@Valid @RequestBody AccessScreensRequest accessScreensRequest) {
		accessTypeScreenMappingService.assignMultipleScreensToAccessType(accessScreensRequest);
	}
	
	
}
