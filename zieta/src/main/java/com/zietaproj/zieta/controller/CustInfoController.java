package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.response.CustomerInfoModel;
import com.zietaproj.zieta.response.CustomerInformationModel;
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
	
	@ApiOperation(value = "List Customers along with clientCode", notes = "Table reference:client_info,cust_info")
	@RequestMapping(value = "getAllCustomers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CustomerInformationModel>  getAllInfo() {
		List<CustomerInformationModel> clientInformationList = null;
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
	@RequestMapping(value = "getAllCustomersByClientCompany", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerInfoModel>> getAllCustomersByClientCompany(@RequestParam(required = true) Long  clientId,
			@RequestParam(required = true) Long orgNode) {
		try {
			List<CustomerInfoModel> cusInfoList = custInfoService.findByClientIdAndOrgNode(clientId,orgNode);

			return new ResponseEntity<List<CustomerInfoModel>>(cusInfoList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<CustomerInfoModel>>(HttpStatus.NOT_FOUND);
		}
	}
	
	

}
