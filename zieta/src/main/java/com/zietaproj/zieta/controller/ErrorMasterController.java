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

import com.zietaproj.zieta.model.ErrorMaster;
import com.zietaproj.zieta.service.ErrorMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags= "Error Details API")
public class ErrorMasterController {

	@Autowired
	ErrorMasterService errorMasterService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMasterController.class);

	@RequestMapping(value = "getAllErrors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ErrorMaster> getAllErrors() {
		List<ErrorMaster> errorList = null;
		try {
			errorList = errorMasterService.getAllErrors();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ErrorMasterController#getAllErrors",e);

		}
		return errorList;
	}

	
	@RequestMapping(value = "addErrorDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addErrorDetails(@Valid @RequestBody ErrorMaster errorMaster) {
		errorMasterService.addError(errorMaster);

	}

	@RequestMapping(value = "updateErrorDetails", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateClientinfo(@Valid @RequestBody ErrorMaster errorMaster) {
		errorMasterService.addError(errorMaster);

	}
	
	@GetMapping("/getErrorByCode")
	@ApiOperation(value = "Error details based on the error code", notes = "Table reference: error_master")
	public ResponseEntity<ErrorMaster> getErrorByCode(
			@RequestParam(required = true) String errorCode) {
		try {
			ErrorMaster errorDetails = errorMasterService.findByErrorCode(errorCode);
			return new ResponseEntity<ErrorMaster>(errorDetails, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<ErrorMaster>(HttpStatus.NOT_FOUND);
		}
	}


}
