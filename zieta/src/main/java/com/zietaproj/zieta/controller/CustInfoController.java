package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.service.CustInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Customer Informationn API")
public class CustInfoController {

	@Autowired
	CustInfoService custInfoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustInfoController.class);

	@RequestMapping(value = "getAllCustomers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CustInfo>  getAllInfo() {
		List<CustInfo> clientInformationList = null;
		try {
			clientInformationList = custInfoService.getAllCustomers();
		} catch (Exception e) {
			LOGGER.error("Error Occured in CustInfoController#getAllInfo",e);
		}
		return clientInformationList;
	}

	@ApiOperation(value = "List Customers based on the clientId", notes = "Table reference: cust_info")
	@RequestMapping(value = "getAllCustomersByClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustInfo>> getAllCustomersByClient(@RequestParam(required = true) Long  clientId) {
		try {
			List<CustInfo> cusInfoList = custInfoService.getAllCustomersByClient(clientId);

			return new ResponseEntity<List<CustInfo>>(cusInfoList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<CustInfo>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "List Customers based on the Company", notes = "Table reference:cust_orgnode_mapping,cust_info")
	@RequestMapping(value = "getAllCustomersByClienCompany", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustInfo>> getAllCustomersByClienCompany(@RequestParam(required = true) Long  clientId,
			@RequestParam(required = true) Long orgNode) {
		try {
			List<CustInfo> cusInfoList = custInfoService.findByClientIdAndOrgNode(clientId,orgNode);

			return new ResponseEntity<List<CustInfo>>(cusInfoList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<CustInfo>>(HttpStatus.NOT_FOUND);
		}
	}
	
	

}
